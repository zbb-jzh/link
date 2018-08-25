package com.future.link.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by zhengbingbing on 2016/3/11.
 */
public class ReflectUtils {

        public static Object getValue(Object target, String property){
            Object rst = null;
            Method getter = getGetter(target.getClass(), property);
            if(getter != null) {
                try {
                    rst = getter.invoke(target, new Object[0]);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            return rst;
        }

        public static Object setValue(Object target, String property, Object value){
            Object rst = null;
            Method setter = getSetter(target.getClass(), property);
            if(setter != null) {
                try {
                    rst = setter.invoke(target, value);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            return rst;
        }

        /**
         * 取类(clazz)中指定getter属性名(property)的方法
         *
         * @param clazz Class bean's class
         * @param property String
         * @return Method
         */
        private static Method getGetter(Class<?> clazz, String property) {
            String methodName = "get" + property;
            Method getter = null;
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (!isGetter(method)) {
                    continue;
                }
                if (method.getName().equalsIgnoreCase(methodName)) {
                    getter = method;
                    break;
                }
            }
            return getter;
        }

        /**
         * 取类(clazz)中指定getter属性名(property)的方法
         *
         * @param clazz Class bean's class
         * @param property String
         * @return Method
         */
        private static Method getSetter(Class<?> clazz, String property) {
            String methodName = "set" + property;
            Method setter = null;
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (!isSetter(method)) {
                    continue;
                }
                if (method.getName().equalsIgnoreCase(methodName)) {
                    setter = method;
                    break;
                }
            }
            return setter;
        }

        /**
         * 判断是否为 getter
         *
         * @param method Method
         * @return boolean
         */
        private static boolean isGetter(Method method) {
            String name = method.getName();
            if (name.startsWith("get") && method.getParameterTypes().length == 0 && method.getReturnType() != void.class) {
                return true;
            }
            if (name.startsWith("is") && method.getParameterTypes().length == 0 && method.getReturnType() == boolean.class) {
                return true;
            }
            return false;
        }
        /**
         * 判断是否为 setter
         *
         * @param method Method
         * @return boolean
         */
        private static boolean isSetter(Method method) {
            String name = method.getName();
            if (name.startsWith("set") && method.getParameterTypes().length != 0 && method.getReturnType() == void.class) {
                return true;
            }
            return false;
        }

        public static  Class<?> getFieldType(Class<?> clazz, String property) {
            try {
                Field field = clazz.getDeclaredField(property);
                return field.getType();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * 将String类型的值转化成对象的某个属性相同类型的值，这里只能转换基本类型
         * @param clazz 参考的class
         * @param fieldName 参看的属性名称
         * @param value 要转换的String类型的值
         * @return 转换好的值
         */
        public static Object formatStringToField(Class<?> clazz, String fieldName, String value) {
            Object rst = null;
            Class<?> type = getFieldType(clazz, fieldName);
            if(type.getName().equals("java.lang.String")) {
                rst= value;
            } else if(type.getName().equals("java.lang.Integer")) {
                rst = Integer.parseInt(value);
            } else if(type.getName().equals("java.lang.Long")) {
                rst = Long.parseLong(value);
            } else if(type.getName().equals("java.lang.Float")) {
                rst = Float.parseFloat(value);
            } else if(type.getName().equals("java.lang.Double")) {
                rst = Double.parseDouble(value);
            } else {
                throw new RuntimeException("not support type:"+ type);
            }
            return rst;
        }
}
