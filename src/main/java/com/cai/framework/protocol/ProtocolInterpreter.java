package com.cai.framework.protocol;

import com.cai.framework.annotation.apt.ProtocolUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProtocolInterpreter {
    private List<BeanFactory> mBeanFactoryList = new ArrayList<>();
    private Map<Class<?>, InvocationHandler> mInvocationHandlerMap = new HashMap<>();
    private Map<Class<?>, Object> mShadowBeanMap = new HashMap<>();
    private DefaultBeanFactory mDefaultBeanFactory;

    private static class Holder {
        static ProtocolInterpreter instance = new ProtocolInterpreter();
    }

    private ProtocolInterpreter() {
        mDefaultBeanFactory = new DefaultBeanFactory();
        mBeanFactoryList.add(mDefaultBeanFactory);
    }

    public static ProtocolInterpreter getDefault() {
        return Holder.instance;
    }

    /**
     * 使用入口
     *
     * @param stub interface lei
     * @param <T>  clazz
     * @return obj clazz
     */
    public <T> T create(Class<T> stub) {
        if (mShadowBeanMap.get(stub) != null) {
            return (T) mShadowBeanMap.get(stub);
        }
        InvocationHandler handler = null;
        try {
            handler = findHandler(stub);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("error! findHandler!");
        }
        T result = (T) Proxy.newProxyInstance(stub.getClassLoader(), new Class[]{stub}, handler);
        mShadowBeanMap.put(stub, result);
        return result;
    }


    private <T> InvocationHandler findHandler(Class<T> stub) throws ClassNotFoundException {
        if (mInvocationHandlerMap.keySet().contains(stub)) {
            return mInvocationHandlerMap.get(stub);
        }
        String simpleName = stub.getSimpleName();
        Class shadowMiddle = Class.forName(ProtocolUtil.getClassNameForPackage(simpleName));
        String value = ProtocolUtil.getValueFromClass(shadowMiddle);
        Class valueClass = Class.forName(ProtocolUtil.getClassNameForPackage(value));
        String targetClazzName = ProtocolUtil.getValueFromClass(valueClass);

        if (targetClazzName == null
                || targetClazzName.equals("")
                || targetClazzName.equals("null")) {
            throw new RuntimeException("error! targetClazzName null");
        }

        Object obj = null;
        final Class clazz = Class.forName(targetClazzName);
        for (BeanFactory beanFactory : mBeanFactoryList) {
            obj = beanFactory.getBean(clazz);
            if (obj != null) {
                break;
            }
        }
        if (obj == null) {
            obj = defaultGetBean(clazz);
        }
        if (obj == null) {
            throw new RuntimeException("error! obj is null,cannot find action obj in all factory!");
        }
        final Object action = obj;
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Method realMethod = clazz.getDeclaredMethod(method.getName(), method.getParameterTypes());
                realMethod.setAccessible(true);
                return realMethod.invoke(action, args);
            }
        };
        mInvocationHandlerMap.put(stub, handler);
        return handler;
    }

    //缺省采用 无参数构造bean
    private Object defaultGetBean(Class clazz) {
        try {
            Constructor constructor = clazz.getConstructor();
            return constructor.newInstance();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 初始化解释器,如果传入 beanFactory 怎具有更强大灵活地配置响应者的能力
     * 如果要定制  则要在使用该bean之前 add进来
     *
     * @param beanFactory beanFactory
     */
    public void addFactory(BeanFactory beanFactory) {
        if (!mBeanFactoryList.contains(beanFactory)) this.mBeanFactoryList.add(beanFactory);
    }

    /**
     * 自己定制 bean 而不是要解释器new出来
     * 如果要定制 则要在使用该bean之前 add进来
     *
     * @param clazz class
     * @param obj   object
     */
    public void addBean(Class<?> clazz, Object obj) {
        mDefaultBeanFactory.put(clazz, obj);
    }
}
