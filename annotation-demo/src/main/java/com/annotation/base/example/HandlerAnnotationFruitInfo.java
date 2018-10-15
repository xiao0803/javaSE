package com.annotation.base.example;

import java.lang.reflect.Field;

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
}
