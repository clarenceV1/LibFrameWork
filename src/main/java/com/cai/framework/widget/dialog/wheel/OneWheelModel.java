package com.cai.framework.widget.dialog.wheel;

/**
 * oneWheeldialog配置类
 */
public class OneWheelModel {

	WheelModel wheelModel=new WheelModel();
	/**
	 * 显示的标题
	 */
	String title = "";
	/**
	 * 标题的颜色
	 */
	private float titleSize = 0;
	/**
	 * 内容文字大小
	 */
	int contentTextSize;

	public boolean isCircle() {
		return wheelModel.getCircle();
	}

	public void setCircle(boolean isCircle) {
		this.wheelModel.setCircle(isCircle);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String[] getContent() {
		return wheelModel.getContent();
	}

	public void setContent(String[] content) {
		wheelModel.setContent(content);
	}

	public int getCurrentPosition() {
		return  wheelModel.getPosition();
	}

	public void setCurrentPosition(int currentPosition) {
		wheelModel.setPosition(currentPosition);
	}

	public int getContentTextSize() {
		return contentTextSize;
	}

	public void setContentTextSize(int contentTextSize) {
		this.contentTextSize = contentTextSize;
	}

	public float getTitleSize() {
		return titleSize;
	}

	public void setTitleSize(float titleSize) {
		this.titleSize = titleSize;
	}
}
