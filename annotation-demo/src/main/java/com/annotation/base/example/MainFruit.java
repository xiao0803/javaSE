package com.annotation.base.example;

/**
 * 输出结果
 */
public class MainFruit {
	public static void main(String[] args) {
		UseAnnotationApple useAnnotationApple = new UseAnnotationApple();
		//useAnnotationApple.setAppleName("苹果名称：");
		useAnnotationApple.setAppleColor("苹果颜色：");
		useAnnotationApple.setAppleProvider("供应商信息：");
		HandlerAnnotationFruitInfo.getFruitInfo(useAnnotationApple);
		//HandlerAnnotationFruitInfo.getFruitInfo(UseAnnotationApple.class);
	}
}
