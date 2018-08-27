package com.cai.framework.imageload;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * 实现圆角图片
 */
public class GlideRoundTransform extends BitmapTransformation {

    private static float radius = 0f;
    boolean leftTop = true;
    boolean rightTop = true;
    boolean leftBottom = true;
    boolean rightBottom = true;

    public GlideRoundTransform(Context context) {
        this(context, 4);
    }

    public GlideRoundTransform(Context context, int corner, boolean leftTop, boolean rightTop, boolean leftBottom, boolean rightBottom) {
        this(context, corner);
        this.leftTop = leftTop;
        this.leftBottom = leftBottom;
        this.rightBottom = rightBottom;
        this.rightTop = rightTop;
    }

    public GlideRoundTransform(Context context, int dp) {
        super(context);
        this.radius = Resources.getSystem().getDisplayMetrics().density * dp;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return roundCrop(pool, toTransform, leftTop, rightTop, leftBottom, rightBottom);
    }

    private static Bitmap roundCrop(BitmapPool pool, Bitmap source, boolean leftTop, boolean rightTop, boolean leftBottom, boolean rightBottom) {
        if (source == null) return null;

        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
        canvas.drawRoundRect(rectF, radius, radius, paint);

        if (!leftTop) {
            canvas.drawRect(0, 0, radius, radius, paint);
        }
        if (!rightTop) {
            canvas.drawRect(rectF.right - radius, 0, rectF.right, radius, paint);
        }
        if (!leftBottom) {
            canvas.drawRect(0, rectF.bottom - radius, radius, rectF.bottom, paint);
        }
        if (!rightBottom) {
            canvas.drawRect(rectF.right - radius, rectF.bottom - radius, rectF.right, rectF.bottom, paint);
        }
        return result;
    }

    @Override
    public String getId() {
        return getClass().getName() + Math.round(radius);
    }
}
