/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) MapSupports.java 2013-07-28 00:35
 */

package com.ibbface.domain.shared;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * {@link MapSupport} 接口处理的实用工具类。
 *
 * @author Fuchun
 * @version $Id: MapSupports.java 29241 2013-05-21 10:43:14Z C629 $
 * @since 1.0
 */
public abstract class MapSupports {
    private MapSupports() {
    }

    /**
     * 从指定的 map 信息中构建一个指定类型的实现 {@link MapSupport} 接口的类实例。
     *
     * @param map map 信息。
     * @param beanClass 实现 {@link MapSupport} 接口的类信息。
     * @param <T> 实现 {@link MapSupport} 接口的类型
     */
    public static <T> T fromMap(Map<String, Object> map, Class<T> beanClass) {
        if (!map.containsKey(MapSupport.CLASS_TYPE_KEY)) {
            throw new IllegalArgumentException(String.format("The given map must be contains '%s' key.",
                    MapSupport.CLASS_TYPE_KEY));
        }
        String className = (String) map.get(MapSupport.CLASS_TYPE_KEY);
        if (className != null && !beanClass.getName().equals(className)) {
            throw new IllegalArgumentException(String.format("The given map for mapping class (%s) actual is %s",
                    beanClass, className));
        }

        T beanInstance = newInstance(beanClass);
        Field[] fields = getAllFields(beanClass, false);
        for (Field field : fields) {
            String fieldName = field.getName();
            if (map.containsKey(fieldName)) {
                invokeSetter(beanInstance, fieldName, map.get(fieldName));
            }
        }
        return beanInstance;
    }

    /**
     * 将指定的 map 的 K 和 V 转换为 byte[] 类型的 map 映射。
     */
    public static Map<byte[], byte[]> toByteMap(Map<String, Object> map) {
        Map<byte[], byte[]> result = Maps.newHashMapWithExpectedSize(map.size());
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            result.put(entry.getKey().getBytes(Charsets.UTF_8), serialize(entry.getValue()));
        }
        return result;
    }

    /**
     * 构建一个指定类型信息的新实例，指定的类必须包含一个公用的无参构造。
     *
     * @param beanClass 指定的类信息。
     * @param <T> 指定的类型。
     */
    public static <T> T newInstance(Class<T> beanClass) {
        try {
            return beanClass.newInstance();
        } catch (InstantiationException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    /**
     * 返回指定类型的的所有字段信息，由 {@code containsStatic} 指定返回的列表中是否包含静态字段。
     *
     * @param beanClass 指定的类型。
     * @param containsStatic 是否包含静态字段。
     */
    public static Field[] getAllFields(Class<?> beanClass, boolean containsStatic) {
        Set<Field> fieldSet = Sets.newHashSet();
        Class<?> superClass = beanClass;
        while (superClass != Object.class) {

            Field[] fields = superClass.getDeclaredFields();
            for (Field field : fields) {
                if (!containsStatic && Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                fieldSet.add(field);
            }
            superClass = superClass.getSuperclass();
        }
        return fieldSet.toArray(new Field[fieldSet.size()]);
    }

    /**
     * 调用指定对象中的 {@code propName} 属性的 setter 方法。
     *
     * @param bean Javabean 对象。
     * @param propName 属性。
     * @param param 参数。
     * @throws IllegalArgumentException if {@code propName == null || propName.length() == 0}
     */
    public static void invokeSetter(Object bean, String propName, @Nonnull Object param) {
        checkArgument(propName != null && propName.length() > 0);
        assert propName != null && propName.length() > 0;
        final String methodName = propName.length() > 1 ? String.format("set%s%s",
                propName.substring(0, 1).toUpperCase(), propName.substring(1)) :
                String.format("set%s", propName);


        try {
            Method m = bean.getClass().getMethod(methodName, param.getClass());
            m.invoke(bean, param);
        } catch (NoSuchMethodException e) {
            // ignore: setter method not found
        } catch (IllegalAccessException e) {
            // ignore private | protected
        } catch (InvocationTargetException e) {
            // setter method error
        }
    }

    public static byte[] serialize(Object value) {
        if (value instanceof String) {
            return ((String) value).getBytes(Charsets.UTF_8);
        } else if (value instanceof CharSequence) {
            return String.valueOf(value).getBytes(Charsets.UTF_8);
        } else {
            List<SerializerFeature> features = Lists.newArrayList(SerializerFeature.UseISO8601DateFormat);
            if (!(value instanceof Number)) {
                features.add(SerializerFeature.WriteClassName);
            }
            return JSON.toJSONBytes(value, features.toArray(new SerializerFeature[features.size()]));
        }
    }
}