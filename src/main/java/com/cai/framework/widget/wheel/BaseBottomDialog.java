package com.cai.framework.widget.wheel;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cai.framework.R;

import static android.widget.LinearLayout.VERTICAL;

/**
 * 底部对话框 基类
 */
public abstract class BaseBottomDialog extends LinganDialog {
	protected Context mContext;
	/**
	 * 获取对话框布局
	 * @return
	 */
	public abstract int getLayoutId();
	/**
	 * 初始化数据
	 */
	public abstract void initDatas(Object... params);

	/**
	 * 获取跟布局，为了做popup动画
	 * @return
	 */
	public  View getRootView(){
		return mRootView;
	}


	private BottomDialogLinearLayout mLinearLayout;
	private int mAllHeight;
	private int mContentHeight;
	private int mSpaceHeight;
	private TextView mTextView;

	public View mRootView;
	private boolean isCancel = true;
	private boolean isAnimation = true;
	/**
	 * 初始化UI
	 */
	public abstract void initUI(Object... params);

	public BaseBottomDialog(Context context, Object... params) {
		super(context);
		mContext = context;
		init(params);
	}

	public BaseBottomDialog(int style, Context context, Object... params) {
		super(context, style);
		mContext = context;
		init(params);
	}

	private void init(Object... params) {
		try {
			//getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			// 布局
			//setContentView(getLayoutId());

			setContentView(R.layout.layout_bottom_dialog_base);
			mLinearLayout = (BottomDialogLinearLayout) super.findViewById(R.id.linearDialogContainer);
			mLinearLayout.setOrientation(VERTICAL);
			mLinearLayout.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					cancelable();
				}
			});
			//加入外界视图
			layoutView = LayoutInflater.from(mContext).inflate(getLayoutId(), null);
			mRootView = layoutView;
			mLinearLayout.addView(layoutView);
			//加入白色区域，用于pop动画弹起的那一瞬间，背景也是白色的
			mTextView = new TextView(mContext);
			mTextView.setBackgroundColor(mContext.getResources().getColor(R.color.white_a));

			//mImageView.setBackgroundColor(SkinManager.getInstance().getAdapterColor(R.color.white_a));

			//计算内容视图高度
			measureView(layoutView);
			//取内容高度的0.1作为popup高度，需要在animi动画指定10%
			mContentHeight = layoutView.getMeasuredHeight();
			mSpaceHeight = (int)(mContentHeight*0.15);
			mAllHeight = mContentHeight+mSpaceHeight;
			LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mSpaceHeight);
			//params1.bottomMargin = -whiteHeight;
//			mLinearLayout.addView(mTextView,params1);

			// 初始化数据
			initDatas(params);

			// 初始化UI
			initUI(params);

			// 宽度和动画
			Window dialogWindow = getWindow();
			ColorDrawable dw = new ColorDrawable(0x0000ff00);
			dialogWindow.setBackgroundDrawable(dw);
			dialogWindow.setGravity(Gravity.BOTTOM);
			//dialogWindow.setWindowAnimations(R.style.DialogBottomPopupAnimation);


		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}


	private void startAnimation(){
		mLinearLayout.getScroller().setFriction(35);

		mLinearLayout.getScroller().startScroll(0,-mAllHeight, 0, mAllHeight, 300);
		mLinearLayout.invalidate();
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				mLinearLayout.getScroller().startScroll(0,0, 0, -mSpaceHeight, 500);
				mLinearLayout.invalidate();
			}
		},150);
	}

	//计算视图高度
	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}

		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight, View.MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = View.MeasureSpec.makeMeasureSpec(0,
					View.MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	@Override
	public View findViewById(@IdRes int id) {
		return mLinearLayout.findViewById(id);
		//return super.findViewById(id);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void show() {
		super.show();
		//setCanceledOnTouchOutside(true);
		WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.width = (display.getWidth()); //设置宽度
//		getWindow().setWindowAnimations(R.style.DialogBottomPopupAnimationNull);
		getWindow().setAttributes(lp);

		//disable animation for current
//		if(isAnimation){
//			startAnimation();
//		}
		Window dialogWindow = getWindow();
		ColorDrawable dw = new ColorDrawable(0x0000ff00);
		dialogWindow.setBackgroundDrawable(dw);
		dialogWindow.setGravity(Gravity.BOTTOM);
		dialogWindow.setWindowAnimations(R.style.DialogBottomAnimation_NEW);
	}

	/**
	 * 设置底部 Popup动画时的填充颜色，默认白色
	 * @param color
	 */
	public void setBottomPopupFillColor(int color){
		try {
			mTextView.setBackgroundColor(mContext.getResources().getColor(R.color.white_a));
			//mImageView.setBackgroundColor(mContext.getResources().getColor(R.color.xiyou_white));
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}

	public void setCancelable(boolean flag) {
		isCancel = flag;
		setCanceledOnTouchOutside(flag);
	}

	public void cancelable() {
		if(isCancel){
			dismiss();
		}
	}

	public void isAnimation(boolean isAnimation) {
		this.isAnimation = isAnimation;
	}
}