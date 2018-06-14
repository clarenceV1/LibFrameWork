package com.cai.framework.widget.dialog;

import android.view.View;

import com.cai.framework.bean.CBaseData;

import java.util.List;

/**
 * 底部菜单列表
 */
public class BottomMenuModel {
    private String title;
    private List<BottomMenuItemModel> menuList;
    private BottomMenuItemClickListener clickListener;

    public BottomMenuItemClickListener getClickListener() {
        return clickListener;
    }

    public List<BottomMenuItemModel> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<BottomMenuItemModel> menuList) {
        this.menuList = menuList;
    }

    public void setClickListener(BottomMenuItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public interface BottomMenuItemClickListener {
        void onClick(BottomMenuDialog dialog,View v, BottomMenuItemModel itemModel);
    }

    public static class BottomMenuItemModel<T> implements CBaseData {
        public BottomMenuItemModel() {
        }

        private String menuName;
        /**
         * 给外部提供缓存数据功能
         */
        private T tag;
        private boolean isCancleMenu;

        public boolean isCancleMenu() {
            return isCancleMenu;
        }

        public void setCancleMenu(boolean cancleMenu) {
            isCancleMenu = cancleMenu;
        }

        public String getMenuName() {
            return menuName;
        }

        public void setMenuName(String menuName) {
            this.menuName = menuName;
        }

        public T getTag() {
            return tag;
        }

        public void setTag(T tag) {
            this.tag = tag;
        }
    }

}
