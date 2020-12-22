package me.henry.lib_base.others.util;

import android.text.TextUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * 反射工具类
 * @author chenf
 */
public class ReflectionUtil {

    /**
     * 调用无参数方法
     * @param className     类名
     * @param methodName    方法名
     */
    public static void invokeNoParameterMethod(String className, String methodName) {
        try {
            Class<?> cls = Class.forName(className);
            Object obj = cls.newInstance();
            Method method = cls.getMethod(methodName);
            method.invoke(obj);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    /**
     * 调用单一参数方法
     * @param className         类名
     * @param methodName        方法名
     * @param parameterType     参数类型
     * @param arg               参数
     */
    public static void invokeSingleParameterMethod(String className, String methodName, Class<?> parameterType, Object arg) {
        try {
            Class<?> cls = Class.forName(className);
            Object obj = cls.newInstance();
            Method method = cls.getMethod(methodName, parameterType);
            method.invoke(obj, arg);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    /**
     * 复制 源实体类里属性 到 目标实体类的属性
     * @param target   目标实体类
     * @param source   源实体类
     * @return target  目标实体类
     */
    public static Object copyObject(Object target, Object source) {
        return copyObject(target,source,false);
    }

    /**
     * 复制 源实体类里属性 到 目标实体类的属性(不会复制"serialVersionUID"属性)
     * @param target   目标实体类
     * @param source   源实体类
     * @return target  目标实体类
     */
    public static Object copyObjectWithoutSerialID(Object target, Object source) {
        return ReflectionUtil.copyObject(target,source,new String[]{"serialVersionUID"});
    }


    /**
     * 复制 源实体类里属性 到 目标实体类的属性
     * @param target   目标实体类
     * @param source   源实体类
     * @param isCover  是否保留obje类里不为null的属性值(保留源值，属性为null则赋值)
     * @return target  目标实体类
     */
    public static Object copyObject(Object target,
                                    Object source,
                                    boolean isCover) {
        return copyObject(target,source,false,null);
    }

    /**
     * 复制 源实体类里属性 到 目标实体类的属性
     * @param target   目标实体类
     * @param source   源实体类
     * @param withoutKey  不复制的属性
     * @return target  目标实体类
     */
    public static Object copyObject(Object target,
                                    Object source,
                                    String[] withoutKey) {
        return copyObject(target,source,false,withoutKey);
    }

    /**
     * 复制 源实体类里属性 到 目标实体类的属性
     * @param target   目标实体类
     * @param source   源实体类
     * @param isCover  是否保留obje类里不为null的属性值(保留源值，属性为null则赋值)
     * @param withoutKey  不复制的属性
     * @return target  目标实体类
     */
    public static Object copyObject(Object target,
                                    Object source,
                                    boolean isCover,
                                    String[] withoutKey) {
        Field[] fields = source.getClass().getDeclaredFields();
        for (int i = 0, j = fields.length; i < j; i++) {
            String propertyName = fields[i].getName();
            if (!TextUtils.isEmpty(withoutKey.toString())
                    && Arrays.asList(withoutKey).contains(propertyName))
                continue; // 不处理黑名单属性
            Object propertyValue = getFieldValue(source, propertyName);
            if (isCover) {
                if (getFieldValue(target, propertyName) == null
                        && propertyValue != null) {
                    setFieldValue(target, propertyName,propertyValue);
                }
            } else {
                setFieldValue(target, propertyName,propertyValue);
            }

        }
        return target;
    }


    /**
     * 直接读取对象属性值,无视private/protected修饰符,不经过getter函数.
     */
    public static <T> T getFieldValue(final Object object, final String fieldName) {
        Field field = getDeclaredField(object, fieldName);

        if (field == null)
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");

        makeAccessible(field);

        T result = null;
        try {
            result = (T) field.get(object);
        } catch (IllegalAccessException e) {
          //  LogUtil.e("不可能抛出的异常{}", e.getMessage());
        }
        return result;
    }

    /**
     * 直接设置对象属性值,无视private/protected修饰符,不经过setter函数.
     */
    public static void setFieldValue(final Object object, final String fieldName, final Object value) {
        Field field = getDeclaredField(object, fieldName);

        if (field == null)
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");

        makeAccessible(field);

        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
         //   LogUtil.e("不可能抛出的异常:{}", e.getMessage());
        }
    }

    /**
     * 循环向上转型,获取对象的DeclaredField.
     */
    protected static Field getDeclaredField(final Object object, final String fieldName) {
        return getDeclaredField(object.getClass(), fieldName);
    }

    /**
     * 循环向上转型,获取类的DeclaredField.
     */
    @SuppressWarnings("unchecked")
    protected static Field getDeclaredField(final Class clazz, final String fieldName) {
        for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                // Field不在当前类定义,继续向上转型
            }
        }
        return null;
    }

    /**
     * 强制转换fileld可访问.
     */
    protected static void makeAccessible(final Field field) {
        if (!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
            field.setAccessible(true);
        }
    }

    /**
     * 通过反射,获得定义Class时声明的父类的泛型参数的类型. 如public UserDao extends HibernateDao<User>
     *
     * @param clazz The class to introspect
     * @return the first generic declaration, or Object.class if cannot be
     * determined
     */
    @SuppressWarnings("unchecked")
    public static Class getSuperClassGenricType(final Class clazz) {
        return getSuperClassGenricType(clazz, 0);
    }

    /**
     * 通过反射,获得定义Class时声明的父类的泛型参数的类型. 如public UserDao extends
     * HibernateDao<User,Long>
     *
     * @param clazz clazz The class to introspect
     * @param index the Index of the generic ddeclaration,start from 0.
     * @return the index generic declaration, or Object.class if cannot be
     * determined
     */

    @SuppressWarnings("unchecked")
    public static Class getSuperClassGenricType(final Class clazz, final int index) {

        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
          //  LogUtil.w(clazz.getSimpleName() + "'s superclass not ParameterizedType");
            return Object.class;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
          //  LogUtil.w("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: " + params.length);
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
          //  LogUtil.w(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
            return Object.class;
        }
        return (Class) params[index];
    }

}
