package cc.zruibin.modules.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ruibin.chow on 19/01/2018.
 */

public final class ClassReflectionUtils {

    /**
     * 获取一个类的所有属性(包含父类)
     *
     * @param clazz 类
     * @return 属性列表
     */
    public static Collection<Field> getAllClassFields(Class clazz) {
        Map<String, Field> resutlMap = new LinkedHashMap<String, Field>();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                resutlMap.put(field.getName(), field);
            }
        }
        return resutlMap.values();
    }

    /**
     * 获取一个类的所有属性名称(包含父类)
     *
     * @param clazz 类
     * @return 属性列表
     */
    public static Collection<String> getAllClassFieldNames(Class clazz) {
        Map<String, Field> resutlMap = new LinkedHashMap<String, Field>();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                resutlMap.put(field.getName(), field);
            }
        }
        return resutlMap.keySet();
    }

    /**
     *  获取一个类的所有属性(不包含父类)
     * @param clazz 类
     * @return 属性列表
     */
    public static Collection<Field> getClassFields(Class clazz) {
        Map<String, Field> resutlMap = new LinkedHashMap<String, Field>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            resutlMap.put(field.getName(), field);
        }
        return resutlMap.values();
    }

    /**
     *  获取一个类的所有属性名称(不包含父类)
     * @param clazz 类
     * @return 属性列表
     */
    public static Collection<String> getClassFieldNames(Class clazz) {
        Map<String, Field> resutlMap = new LinkedHashMap<String, Field>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            resutlMap.put(field.getName(), field);
        }
        return resutlMap.keySet();
    }

    /**
     * 根据属性名获取属性值
     * 不考虑从祖先类继承的属性，只获取当前类属性，包括四类访问权限，private，protect，default，public
     *
     * @param fieldName 属性
     * @param object 对象
     * @return 属性值
     */
    public static String getFieldValueByFieldName(String fieldName, Object object) {
        try {
            Field field = object.getClass().getField(fieldName);
            //设置对象的访问权限，保证对private的属性的访问
            field.setAccessible(true);
            return (String) field.get(object);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据属性名获取属性元素，包括各种安全范围和所有父类
     *
     * @param fieldName 属性名
     * @param object 对象
     * @return 该属性
     */
    public static Field getFieldByClasss(String fieldName, Object object) {
        Field field = null;
        Class<?> clazz = object.getClass();

        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
            } catch (Exception e) {
                // 这里甚么都不能抛出去。
                // 如果这里的异常打印或者往外抛，则就不会进入
            }
        }
        return field;
    }

    /**
     *  获取一个类的所有方法(不包含父类)
     * @param clazz 类
     * @return 方法集合
     */
    public static Method[] getClassMethods(Class clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        return methods;
    }

    /**
     * 获得一个类的所有方法名称(不包含父类)
     * @param clazz 类
     * @return 类方法名称集合
     */
    public static Collection<String> getClassMethodNames(Class clazz) {
        List<String> methodNames = new ArrayList<String>();
        Method[] methods = getClassMethods(clazz);
        for (Method method : methods) {
            methodNames.add(method.getName());
        }
        return methodNames;
    }

    /**
     * 获得一个类的所有方法(包含父类)
     * @param clazz 类
     * @return 类方法名称集合
     */
    public static Collection<Method> getClassAllMethods(Class clazz)  {
        Map<String, Method> resutlMap = new LinkedHashMap<String, Method>();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                resutlMap.put(method.getName(), method);
            }
        }
        return resutlMap.values();
    }

    /**
     * 获得一个类的所有方法名称(包含父类)
     * @param clazz 类
     * @return 类方法名称集合
     */
    public static Collection<String> getClassAllMethodNames(Class clazz)  {
        Map<String, Method> resutlMap = new LinkedHashMap<String, Method>();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                resutlMap.put(method.getName(), method);
            }
        }
        return resutlMap.keySet();
    }

    /**
     * 循环向上转型, 获取对象的 DeclaredMethod
     *
     * @param object         : 子类对象
     * @param methodName     : 父类中的方法名
     * @param parameterTypes : 父类中的方法参数类型
     * @return 父类中的方法对象
     */
    public static Method getSuperClassDeclaredMethod(Object object, String methodName, Class<?> ... parameterTypes){
        Method method = null ;
        for(Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                method = clazz.getDeclaredMethod(methodName, parameterTypes) ;
                return method ;
            } catch (Exception e) {
                //这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
                //如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了
            }
        }
        return null;
    }

    /**
     * 直接调用对象方法, 而忽略修饰符(private, protected, default)
     *
     * @param object : 子类对象
     * @param methodName : 父类中的方法名
     * @param parameterTypes : 父类中的方法参数类型
     * @param parameters : 父类中的方法参数
     * @return 父类中方法的执行结果
     */
    public static Object invokeSuperClassMethod(Object object, String methodName, Class<?> [] parameterTypes,
                                      Object [] parameters) {
        //根据 对象、方法名和对应的方法参数 通过反射 调用上面的方法获取 Method 对象
        Method method = getSuperClassDeclaredMethod(object, methodName, parameterTypes) ;

        //抑制Java对方法进行检查,主要是针对私有方法而言
        method.setAccessible(true) ;

        try {
            if(null != method) {
                //调用object 的 method 所代表的方法，其方法的参数是 parameters
                return method.invoke(object, parameters) ;
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 循环向上转型, 获取对象的 DeclaredField
     *
     * @param object : 子类对象
     * @param fieldName : 父类中的属性名
     * @return 父类中的属性对象
     */
    public static Field getSuperClassDeclaredField(Object object, String fieldName){
        Field field = null ;
        Class<?> clazz = object.getClass() ;
        for(; clazz != Object.class ; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName) ;
                return field ;
            } catch (Exception e) {
                //这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
                //如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了
            }
        }
        return null;
    }

    /**
     * 直接设置对象属性值, 忽略 private/protected 修饰符, 也不经过 setter
     *
     * @param object : 子类对象
     * @param fieldName : 父类中的属性名
     * @param value : 将要设置的值
     */
    public static void setSuperClassFieldValue(Object object, String fieldName, Object value){
        //根据 对象和属性名通过反射 调用上面的方法获取 Field对象
        Field field = getSuperClassDeclaredField(object, fieldName) ;
        //抑制Java对其的检查
        field.setAccessible(true) ;
        try {
            //将 object 中 field 所代表的值 设置为 value
            field.set(object, value) ;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 直接读取对象的属性值, 忽略 private/protected 修饰符, 也不经过 getter
     *
     * @param object : 子类对象
     * @param fieldName : 父类中的属性名
     * @return : 父类中的属性值
     */
    public static Object getSuperClassFieldValue(Object object, String fieldName){
        //根据 对象和属性名通过反射 调用上面的方法获取 Field对象
        Field field = getSuperClassDeclaredField(object, fieldName) ;
        //抑制Java对其的检查
        field.setAccessible(true) ;
        try {
            //获取 object 中 field 所代表的属性值
            return field.get(object) ;
        } catch(Exception e) {
            e.printStackTrace() ;
        }
        return null;
    }
}
