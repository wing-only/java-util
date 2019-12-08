package com.wing.java.util;

import com.wing.java.util.generatejava.GenerateJavaConstant;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 根据class创建对象
 */
public class ReflectUtil {

    public static Object reflex(Map value, String tableName) throws Exception {
        String entityName = StringUtil.transCamel(tableName);
        FileSystemClassLoader fileSystemClassLoader = new FileSystemClassLoader(GenerateJavaConstant.getEntityClassDir(tableName));
//        Class<?> clazz = Class.forName(GenerateJavaConstant.DEFAULT_PACKAGE + "." + entityName);
        Class<?> clazz = fileSystemClassLoader.findClass(GenerateJavaConstant.DEFAULT_PACKAGE + "." + entityName);

//        log.info("-- valueMap:" + value);
//        log.info("-- clazz:" + clazz);


        Method[] methods = clazz.getMethods();
        Map<String, Method> methodMap = getMethodMap(methods);

        Object instance = clazz.newInstance();
//        log.info("-- instance1:" + instance);
//        log.info("-- instance2:" + instance.getClass().getName());

        Set<Map.Entry<String, Object>> entrySet = value.entrySet();
        for (Map.Entry<String, Object> entry : entrySet) {
            String attrName = entry.getKey();
//            log.info("[attrName]" + attrName);
            Method method = methodMap.get(getMethodName(attrName));
//            log.info("-- method:" + method.getName());

            Object attrValue = entry.getValue();
//            log.info("-- attrValue" + attrValue + " : " + attrValue.getClass());

            Class<?> attrType = method.getParameterTypes()[0];
            Constructor<?> constructor = attrType.getConstructor(String.class);
            Object attrObj = constructor.newInstance(attrValue);

            method.invoke(instance, attrObj);
        }
        return instance;
    }

    private static Map getMethodMap(Method[] methods) {
        HashMap<String, Method> map = new HashMap<>();
        for (Method method : methods) {
//            log.info("[method]" + method.getName());
            map.put(method.getName(), method);
        }
        return map;
    }

    private static String getMethodName(String name) {
        String s = StringUtil.transCamel(name);
//        log.info("-- camelName:" + s);
        return "set" + s;
    }

    /**
     * 获取obj对象fieldName的Field
     *
     * @param obj
     * @param fieldName
     * @return
     */
    public static Field getFieldByFieldName(Object obj, String fieldName) {
        if (obj == null || fieldName == null) {
            return null;
        }
        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredField(fieldName);
            } catch (Exception e) {
            }
        }
        return null;
    }

    /**
     * 获取obj对象fieldName的属性值
     *
     * @param obj
     * @param fieldName
     * @return
     */
    public static Object getValueByFieldName(Object obj, String fieldName) {
        Object value = null;
        try {
            Field field = getFieldByFieldName(obj, fieldName);
            if (field != null) {
                if (field.isAccessible()) {
                    value = field.get(obj);
                } else {
                    field.setAccessible(true);
                    value = field.get(obj);
                    field.setAccessible(false);
                }
            }
        } catch (Exception e) {
        }
        return value;
    }

    /**
     * 获取obj对象fieldName的属性值
     *
     * @param obj
     * @param fieldType
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getValueByFieldType(Object obj, Class<T> fieldType) {
        Object value = null;
        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                Field[] fields = superClass.getDeclaredFields();
                for (Field f : fields) {
                    if (f.getType() == fieldType) {
                        if (f.isAccessible()) {
                            value = f.get(obj);
                            break;
                        } else {
                            f.setAccessible(true);
                            value = f.get(obj);
                            f.setAccessible(false);
                            break;
                        }
                    }
                }
                if (value != null) {
                    break;
                }
            } catch (Exception e) {
            }
        }
        return (T) value;
    }

    /**
     * 设置obj对象fieldName的属性值
     *
     * @param obj
     * @param fieldName
     * @param value
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static boolean setValueByFieldName(Object obj, String fieldName, Object value) {
        try {
            //java.lang.Class.getDeclaredField()方法用法实例教程 - 方法返回一个Field对象，它反映此Class对象所表示的类或接口的指定已声明字段。
            //此方法返回这个类中的指定字段的Field对象
            Field field = obj.getClass().getDeclaredField(fieldName);
            /**
             * public void setAccessible(boolean flag)
             *       throws SecurityException将此对象的 accessible 标志设置为指示的布尔值。值为 true 则指示反射的对象在使用时应该取消 Java 语言访问检查。值为 false 则指示反射的对象应该实施 Java 语言访问检查。
             * 	首先，如果存在安全管理器，则在 ReflectPermission("suppressAccessChecks") 权限下调用 checkPermission 方法。
             * 	如果 flag 为 true，并且不能更改此对象的可访问性（例如，如果此元素对象是 Class 类的 Constructor 对象），则会引发 SecurityException。
             * 	如果此对象是 java.lang.Class 类的 Constructor 对象，并且 flag 为 true，则会引发 SecurityException。
             * 	参数：
             * 	flag - accessible 标志的新值
             * 	抛出：
             * 	SecurityException - 如果请求被拒绝。
             */
            if (field.isAccessible()) {//获取此对象的 accessible 标志的值。
                field.set(obj, value);//将指定对象变量上此 Field 对象表示的字段设置为指定的新值
            } else {
                field.setAccessible(true);
                field.set(obj, value);
                field.setAccessible(false);
            }
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 测试方法
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {
        String tableName = "sys_user";
        Map value = new HashMap();
        value.put("id", 1001L);
        value.put("user_name", "wing");
        value.put("ctime", "2019-08-09 12:34:18");
        value.put("sex", true);
        value.put("length", new BigDecimal(12.34));

        Object student = reflex(value, tableName);
        System.out.println(student);
        System.out.println(student.getClass().getSimpleName());
    }
}
