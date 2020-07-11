package com.annotation.base.example;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 注解处理器
 */
public class HandlerAnnotationFruitInfo {
	public static void getFruitInfo(Class<?> clazz) {

		String strFruitName = " 水果名称：";
		String strFruitColor = " 水果颜色：";
		String strFruitProvicer = "供应商信息：";

		Field[] fields = clazz.getDeclaredFields();

		for (Field field : fields) {
			if (field.isAnnotationPresent(AnnotationFruitName.class)) {
				AnnotationFruitName fruitName = (AnnotationFruitName) field.getAnnotation(AnnotationFruitName.class);
				strFruitName = strFruitName + fruitName.value();
				System.out.println(strFruitName);
			} else if (field.isAnnotationPresent(AnnotationFruitColor.class)) {
				AnnotationFruitColor fruitColor = (AnnotationFruitColor) field.getAnnotation(AnnotationFruitColor.class);
				strFruitColor = strFruitColor + fruitColor.fruitColor().toString();
				System.out.println(strFruitColor);
			} else if (field.isAnnotationPresent(AnnotationFruitProvider.class)) {
				AnnotationFruitProvider fruitProvider = (AnnotationFruitProvider) field.getAnnotation(AnnotationFruitProvider.class);
				strFruitProvicer = " 供应商编号：" + fruitProvider.id() + " 供应商名称：" + fruitProvider.name() + " 供应商地址：" + fruitProvider.address();
				System.out.println(strFruitProvicer);
			}
		}
	}
	
	public static void getFruitInfo(UseAnnotationApple useAnnotationApple) {
		Class<?> clazz = useAnnotationApple.getClass();
		Field[] fields = clazz.getDeclaredFields();

		for (Field field : fields) {
			if (field.isAnnotationPresent(AnnotationFruitName.class)) {
				AnnotationFruitName fruitName = (AnnotationFruitName) field.getAnnotation(AnnotationFruitName.class);
				String strFruitName = getFieldValueByName(useAnnotationApple, field.getName()) + fruitName.value();
				System.out.println(strFruitName);
			} else if (field.isAnnotationPresent(AnnotationFruitColor.class)) {
				AnnotationFruitColor fruitColor = (AnnotationFruitColor) field.getAnnotation(AnnotationFruitColor.class);
				String strFruitColor = getFieldValueByName(useAnnotationApple, field.getName()) + fruitColor.fruitColor().toString();
				System.out.println(strFruitColor);
			} else if (field.isAnnotationPresent(AnnotationFruitProvider.class)) {
				AnnotationFruitProvider fruitProvider = (AnnotationFruitProvider) field.getAnnotation(AnnotationFruitProvider.class);
				String strFruitProvicer = getFieldValueByName(useAnnotationApple, field.getName()) + " 供应商编号：" + fruitProvider.id() + " 供应商名称：" + fruitProvider.name() + " 供应商地址：" + fruitProvider.address();
				System.out.println(strFruitProvicer);
			}
		}
	}
	
	private static String getFieldValueByName(Object o, String fieldName) {
		try {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getter = "get" + firstLetter + fieldName.substring(1);
			Method method = o.getClass().getMethod(getter, new Class[] {});
			Object value = method.invoke(o, new Object[] {});
			if (value != null) {
				return (String)value;
			} else {
				return "空空空：";
			}
		} catch (Exception e) {
			System.out.println("属性名：" + fieldName);
			System.out.println("依据属性名获取属性值异常：" + e.getMessage());
			return null;
		}
	}

}
