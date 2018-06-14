package com.cai.framework.widget.dialog.wheel;

/**
 * ThreeWheelDialog配置类
 */
public class ThreeWheelModel {
    /**
     * 左边
     */
    private WheelModel leftWheelModel = new WheelModel();
    /**
     * 中间
     */
    private WheelModel centerWheelModel = new WheelModel();
    /**
     * 右边
     */
    private WheelModel rightWheelModel = new WheelModel();

    /**
     * 显示的标题
     */
    private String title = "";

    /**
     * wheel滚动监听
     */
    private WheelSelectedChangeListener wheelSelectedChangeListener;

    public WheelSelectedChangeListener getWheelSelectedChangeListener() {
        return wheelSelectedChangeListener;
    }

    public void setWheelSelectedChangeListener(WheelSelectedChangeListener wheelSelectedChangeListener) {
        this.wheelSelectedChangeListener = wheelSelectedChangeListener;
    }

    public ThreeWheelModel(String title, WheelModel leftWheelModel, WheelModel centerWheelModel, WheelModel rightWheelModel) {
        this.leftWheelModel = leftWheelModel;
        this.centerWheelModel = centerWheelModel;
        this.rightWheelModel = rightWheelModel;
        this.title = title;
    }

    public ThreeWheelModel(WheelModel leftWheelModel, WheelModel centerWheelModel, WheelModel rightWheelModel) {
        this.leftWheelModel = leftWheelModel;
        this.centerWheelModel = centerWheelModel;
        this.rightWheelModel = rightWheelModel;
    }

    public ThreeWheelModel() {

    }

    public WheelModel getLeftWheelModel() {
        return leftWheelModel;
    }

    public void setLeftWheelModel(WheelModel leftWheelModel) {
        this.leftWheelModel = leftWheelModel;
    }

    public WheelModel getCenterWheelModel() {
        return centerWheelModel;
    }

    public void setCenterWheelModel(WheelModel centerWheelModel) {
        this.centerWheelModel = centerWheelModel;
    }

    public WheelModel getRightWheelModel() {
        return rightWheelModel;
    }

    public void setRightWheelModel(WheelModel rightWheelModel) {
        this.rightWheelModel = rightWheelModel;
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

    public String[] getCenterContent() {
        return centerWheelModel.getContent();
    }

    public void setCenterContent(String[] centerContent) {
        this.centerWheelModel.setContent(centerContent);
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

    public int getCenterCurrentPosition() {
        return centerWheelModel.getPosition();
    }

    public void setCenterCurrentPosition(int centerCurrentPosition) {
        this.centerWheelModel.setPosition(centerCurrentPosition);
    }

    public boolean isLeftCircle() {
        return leftWheelModel.getCircle();
    }

    public void setLeftCircle(boolean isLeftCircle) {
        leftWheelModel.setCircle(isLeftCircle);
    }

    public boolean isRightCircle() {
        return rightWheelModel.getCircle();
    }

    public void setRightCircle(boolean isRightCircle) {
        rightWheelModel.getCircle();
    }

    public boolean isCenterCircle() {
        return centerWheelModel.getCircle();
    }

    public void setCenterCircle(boolean isCenterCircle) {
        centerWheelModel.setCircle(isCenterCircle);
    }

    public void setCenterValue(String[] value) {
        centerWheelModel.setValues(value);
    }

    public String[] getCenterValue() {
        return centerWheelModel.getValues();
    }

    public void setLeftValue(String[] value) {
        leftWheelModel.setValues(value);
    }

    public String[] getLeftValue() {
        return leftWheelModel.getValues();
    }

    public void setRightValue(String[] value) {
        rightWheelModel.setValues(value);
    }

    public String[] getRightValue() {
        return rightWheelModel.getValues();
    }
}
