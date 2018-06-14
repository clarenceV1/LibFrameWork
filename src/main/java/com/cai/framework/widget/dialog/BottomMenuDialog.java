package com.cai.framework.widget.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.cai.framework.R;
import com.cai.framework.widget.ListViewEx;

public class BottomMenuDialog extends BaseBottomDialog {

    BottomMenuModel menuModel;
    BottomMenuAdapter adapter;
    TextView dialogTitle;
    ListViewEx listViewEx;
    View divide;

    public BottomMenuDialog(Context context, BottomMenuModel params) {
        super(context, params);
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_layout_menu;
    }

    @Override
    public void initDatas(Object... params) {
        if (params.length > 0) {
            menuModel = (BottomMenuModel) params[0];
        }
    }

    @Override
    public void initUI(Object... params) {
        if (menuModel != null && menuModel.getMenuList() != null && menuModel.getMenuList().size() > 0) {
            dialogTitle = (TextView) findViewById(R.id.dialogTitle);
            listViewEx = (ListViewEx) findViewById(R.id.listViewEx);
            divide = findViewById(R.id.divide);
            if (TextUtils.isEmpty(menuModel.getTitle())) {
                dialogTitle.setVisibility(View.GONE);
                divide.setVisibility(View.GONE);
            } else {
                dialogTitle.setVisibility(View.VISIBLE);
                dialogTitle.setText(menuModel.getTitle());
                divide.setVisibility(View.VISIBLE);
            }
            adapter = new BottomMenuAdapter(this, menuModel.getMenuList(), menuModel.getClickListener());
            listViewEx.setAdapter(adapter);
        }

    }
}
