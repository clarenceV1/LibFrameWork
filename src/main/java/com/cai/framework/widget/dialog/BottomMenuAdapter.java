package com.cai.framework.widget.dialog;

import android.view.View;
import android.widget.TextView;

import com.cai.framework.R;
import com.cai.framework.base.GodBaseAdapter;
import com.cai.framework.bean.CBaseData;
import com.cai.framework.utils.ViewHolder;

import java.util.List;

public class BottomMenuAdapter extends GodBaseAdapter {

    BottomMenuModel.BottomMenuItemClickListener mClickListener;
    BottomMenuDialog dialog;

    public BottomMenuAdapter(BottomMenuDialog dialog, List data, BottomMenuModel.BottomMenuItemClickListener clickListener) {
        super(dialog.getContext(), data);
        this.mClickListener = clickListener;
        this.dialog = dialog;
    }

    @Override
    public void initItemView(View convertView, CBaseData itemData, int position) {
        final BottomMenuModel.BottomMenuItemModel menuItemModel = (BottomMenuModel.BottomMenuItemModel) itemData;
        TextView tvMenu = ViewHolder.getTextView(convertView, R.id.tvMenu);
        tvMenu.setText(menuItemModel.getMenuName());
        if (mClickListener != null) {
            tvMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.onClick(dialog, v, menuItemModel);
                }
            });
        }
        if (position == dataList.size() - 1) {
            ViewHolder.getView(convertView, R.id.divide).setVisibility(View.VISIBLE);
            ViewHolder.getView(convertView, R.id.divide2).setVisibility(View.GONE);
        } else {
            ViewHolder.getView(convertView, R.id.divide).setVisibility(View.GONE);
            ViewHolder.getView(convertView, R.id.divide2).setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected int getItemLayout() {
        return R.layout.dialog_layout_menu_item;
    }
}
