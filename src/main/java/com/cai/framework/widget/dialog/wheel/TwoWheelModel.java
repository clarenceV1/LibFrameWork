package com.cai.framework.widget.dialog.wheel;

/**
 * TwoWheeldialog配置类
 */
public class TwoWheelModel {
	/**
	 * 左边
	 */
	private WheelModel leftWheelModel=new WheelModel();
	/**
	 * 右边
	 */
	private WheelModel rightWheelModel=new WheelModel();
	/**
	 * 显示的标题
	 */
	String title = "";

	public TwoWheelModel(WheelModel leftWheelModel, WheelModel rightWheelModel, String title) {
		this.leftWheelModel = leftWheelModel;
		this.rightWheelModel = rightWheelModel;
		this.title = title;
	}

	public TwoWheelModel(WheelModel leftWheelModel, WheelModel rightWheelModel) {
		this.leftWheelModel = leftWheelModel;
		this.rightWheelModel = rightWheelModel;
	}

	public TwoWheelModel() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public String[] getLeftContent() {
		return leftWheelModel.getContent();
	}

	public void setLeftContent(String[] leftContent) {
		this.leftWheelModel.setContent(leftContent);
	}

	public String[] getRightContent() {
		return rightWheelModel.getContent();
	}

	public void setRightContent(String[] rightContent) {
		this.rightWheelModel.setContent(rightContent);
	}

	public int getLeftCurrentPosition() {
		return leftWheelModel.getPosition();
	}

	public void setLeftCurrentPosition(int leftCurrentPosition) {
		this.leftWheelModel.setPosition(leftCurrentPosition);
	}

	public int getRightCurrentPosition() {
		return rightWheelModel.getPosition();
	}

	public void setRightCurrentPosition(int rightCurrentPosition) {
		this.rightWheelModel.setPosition(rightCurrentPosition);
	}

	public boolean isLeftCircle() {
		return leftWheelModel.getCircle();
	}

	public void setLeftCircle(boolean isLeftCircle) {
		leftWheelModel.setCircle(isLeftCircle);
	}

	public boolean isRightCircle() {
		return  rightWheelModel.getCircle();
	}

	public void setRightCircle(boolean isRightCircle) {
		rightWheelModel.getCircle();
	}
}
