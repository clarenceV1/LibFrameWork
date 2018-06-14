package com.cai.framework.widget.dialog.wheel;

/**
 * Created by caishuxing on 2015/11/25.
 */
public class WheelModel {
    /**
     * 显示的值
     */
    private String[] content;
    /**
     * 位置
     */
    private int position = 0;
    /**
     * 是否循环
     */
    private boolean circle;

    /**
     * 实际的值
     * @return
     */
    private String[] values;

    public String[] getValues() {
        return values;
    }

    public void setValues(String[] values) {
        this.values = values;
    }

    public String[] getContent() {
        return content;
    }

    public void setContent(String[] content) {
        this.content = content;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean getCircle() {
        return circle;
    }

    public void setCircle(boolean circle) {
        this.circle = circle;
    }
}
