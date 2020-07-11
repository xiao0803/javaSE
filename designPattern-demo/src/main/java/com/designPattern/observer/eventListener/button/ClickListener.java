package com.designPattern.observer.eventListener.button;

import java.util.EventListener;

//点击监听器
interface ClickListener extends EventListener {

	void click(ClickEvent clickEvent);

}

// 双击监听器
interface DblClickListener extends EventListener {

	void dblClick(DblClickEvent dblClickEvent);

}

// 鼠标移动监听器
interface MouseMoveListener extends EventListener {

	void mouseMove(MouseMoveEvent mouseMoveEvent);

}
