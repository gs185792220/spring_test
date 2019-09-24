package com.main.test.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ObjectToMapUtil {

    public static Map<String, Object> setConditionMap(Object obj) {
        Map<String, Object> map = new HashMap<>();
        if (obj == null) {
            return null;
        }
        Field[] fields = obj.getClass().getDeclaredFields();//获取类的各个属性值
        for (Field field : fields) {
            String fieldName = field.getName();//获取类的属性名称
            if (getValueByFieldName(fieldName, obj) != null)//获取类的属性名称对应的值
                map.put(fieldName, getValueByFieldName(fieldName, obj));
        }
        return map;
    }

    /**
     * 根据属性名获取该类此属性的值
     *
     * @param fieldName
     * @param object
     * @return
     */
    public static String getValueByFieldName(String fieldName, Object object) {

        String firstLetter = fieldName.substring(0, 1).toUpperCase();
        String getter = "get" + firstLetter + fieldName.substring(1);
        try {
            Method method = object.getClass().getMethod(getter, new Class[]{});
            Object obj = method.invoke(object, new Object[]{});
            if (obj != null) {
                String value;
                if (obj instanceof Integer) {
                    value = obj.toString();
                } else if (obj instanceof String) {
                    value = obj.toString();
                } else if (obj instanceof Float) {
                    value = obj.toString();
                } else if (obj instanceof Double) {
                    value = obj.toString();
                } else if (obj instanceof Boolean) {
                    value = obj.toString();
                } else {
                    value = null;
                }
                return value;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

}
