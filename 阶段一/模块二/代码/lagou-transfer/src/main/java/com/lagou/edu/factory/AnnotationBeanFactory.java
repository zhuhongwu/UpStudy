package com.lagou.edu.factory;

import com.lagou.edu.annotation.Autowired;
import com.lagou.edu.annotation.Service;
import com.lagou.edu.annotation.Transactional;
import com.lagou.edu.utils.ClassUtils;
import net.sf.cglib.proxy.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhuhongwu
 * @date 2020/5/8
 */
public class AnnotationBeanFactory {
    private static Map<String, Object> singletonMap = new HashMap<>();

    private static Map<Class, Object> cacheMap = new HashMap<>();

    static {
        //暂时写死，可以转换成配置文件的方式
        String packageName = "com.lagou.edu";

        List<Class<?>> classes = ClassUtils.getClasses(packageName);
        classes.forEach(aClass -> {

            //不处理接口
            if (aClass.isInterface()) {
                return;
            }

            Service annotation = aClass.getAnnotation(Service.class);

            if (annotation != null) {

                String id = annotation.value();
                if (null == id || "".equals(id)) {

                    String className = aClass.getName();
                    id = ClassUtils.getShortName(className);
                }

                //判断类是否有接口实现


                Class<?>[] interfaces = aClass.getInterfaces();

                //有接口实现 jdk动态代理
                if (interfaces.length > 0) {

                    try {


                        Object o = aClass.newInstance();
                        singletonMap.put(id, o);
                        cacheMap.put(aClass, o);

                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }


                } else {
                    //使用cglib
                    Enhancer enhancer = new Enhancer();
                    enhancer.setSuperclass(aClass);
                    enhancer.setCallback(new MethodInterceptor() {
                        @Override
                        public Object intercept(Object o, Method method, Object[] objects, MethodProxy proxy) throws Throwable {
                            Object o1 = proxy.invokeSuper(o, objects);

                            return o1;
                        }
                    });
                    Object o = enhancer.create();
                    singletonMap.put(id, o);
                    cacheMap.put(aClass, o);

                }


            }
        });

        ProxyFactory proxyFactory = (ProxyFactory) singletonMap.get("proxyFactory");
        //处理事务管理需要先在属性之前注入
        classes.forEach(aClass -> {
            Transactional annotation = aClass.getAnnotation(Transactional.class);
            if (annotation != null) {
                //代表需要进行事务处理


                //事务处理后的对象
                Object jdkProxy = proxyFactory.getJdkProxy(cacheMap.get(aClass));
                String id = getKey(cacheMap.get(aClass));
                singletonMap.put(id, jdkProxy);


            }

        });
        //属性注入

        classes.forEach(aClass -> {
            Field[] fields = aClass.getDeclaredFields();


            for (Field field : fields) {


                //   Annotation[] declaredAnnotations = field.getDeclaredAnnotations();

                Autowired declaredAnnotation = field.getDeclaredAnnotation(Autowired.class);


                if (declaredAnnotation != null) {
                    String name = field.getName();
                    //属性值
                    Object o = singletonMap.get(name);

                    //从缓存获取需要被注入的bean
                    Object o1 = cacheMap.get(aClass);

                    String id = getKey(o1);


                    try {

                        field.setAccessible(true);
                        field.set(o1, o);
                        singletonMap.put(id, o1);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }


                }

            }
        });


    }

    public static String getKey(Object value) {
        List<Object> keyList = new ArrayList<>();
        for (String key : singletonMap.keySet()) {
            Object o = singletonMap.get(key);
            if (o == value) {
                return key;
            }
        }
        return null;
    }

    public static Object getBean(String id) {
        return singletonMap.get(id);
    }


}


