package com.cai.framework.qrcode;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;

import com.cai.framework.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Hashtable;

/**
 * 二维码生成
 * Created by davy on 2018/3/21.
 */
public class QRCodeCreat {

    //二维码生成
    public static void createQRcode(File file, String inviteUrl) {
        try {
            createLogoQRImage(inviteUrl, 270, null, null/*QRCodeCreat.resourceToBitmap(context, R.color.white)*/, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //二维码生成
    public static void createQRcode(Context context, int iconResourceId, File file, String inviteUrl) {
        try {
            createLogoQRImage(inviteUrl, 270, QRCodeCreat.resourceToBitmap(context, iconResourceId), null/*QRCodeCreat.resourceToBitmap(context, R.color.white)*/, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成二维码
     *
     * @param content 上下文
     * @param size    宽度
     * @param logoBm  logo图标
     * @param file    二维码图片保存路径
     * @return
     */
    public static boolean createLogoQRImage(String content, int size, Bitmap logoBm, Bitmap backgroundBm, File file) {
        try {
            if (TextUtils.isEmpty(content)) {
                return false;
            }
            //配置参数
            Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //容错级别
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            //设置空白边距的宽度
            hints.put(EncodeHintType.MARGIN, 1); //default is 4

            // 图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, size, size, hints);
            int[] pixels = new int[size * size];
            // 下面这里按照二维码的算法，逐个生成二维码的图片，
            // 两个for循环是图片横列扫描的结果
            for (int y = 0; y < size; y++) {
                for (int x = 0; x < size; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * size + x] = 0xff000000;
                    } else {
                        pixels[y * size + x] = 0xffffffff;
                    }
                }
            }
            // 生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, size, 0, 0, size, size);

            if (logoBm != null) {
                bitmap = addLogo(bitmap, logoBm);
            }
            if (backgroundBm != null) {
                bitmap = addBackground(bitmap, backgroundBm);
            }
            //必须使用compress方法将bitmap保存到文件中再进行读取。直接返回的bitmap是没有任何压缩的，内存消耗巨大！
            return bitmap != null && bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file.getPath()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    public static Bitmap resourceToBitmap(Context context, int resId) {
        Resources r = context.getResources();
        InputStream is = r.openRawResource(resId);
        BitmapDrawable bmpDraw = new BitmapDrawable(is);
        return bmpDraw.getBitmap();
    }

    /**
     * 在二维码中间添加Logo图案
     *
     * @param src
     * @param logo
     * @return
     */
    private static Bitmap addLogo(Bitmap src, Bitmap logo) {
        if (src == null) {
            return null;
        }

        if (logo == null) {
            return src;
        }

        //获取图片的宽高
        int srcWidth = src.getWidth();
        int srcHeight = src.getHeight();
        int logoWidth = logo.getWidth();
        int logoHeight = logo.getHeight();

        if (srcWidth == 0 || srcHeight == 0) {
            return null;
        }

        if (logoWidth == 0 || logoHeight == 0) {
            return src;
        }

        //logo大小为二维码整体大小的1/5
        float scaleFactor = srcWidth * 1.0f / 5 / logoWidth;
        Bitmap bitmap = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);
        try {
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(src, 0, 0, null);
            canvas.scale(scaleFactor, scaleFactor, srcWidth / 2, srcHeight / 2);
            canvas.drawBitmap(logo, (srcWidth - logoWidth) / 2, (srcHeight - logoHeight) / 2, null);

            canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.restore();
        } catch (Exception e) {
            bitmap = null;
            e.getStackTrace();
        }

        return bitmap;
    }

    private static Bitmap addBackground(Bitmap src, Bitmap bgBitmap) {
        if (src == null) {
            return null;
        }

        if (bgBitmap == null) {
            return src;
        }

        //获取图片的宽高
        int srcWidth = src.getWidth();
        int srcHeight = src.getHeight();
        int bgWidth = bgBitmap.getWidth();
        int bgHeight = bgBitmap.getHeight();

        if (srcWidth == 0 || srcHeight == 0) {
            return null;
        }

        if (bgWidth == 0 || bgHeight == 0) {
            return src;
        }

        int letfStart = bgWidth / 2 - srcWidth / 2;
        Bitmap bitmap = Bitmap.createBitmap(bgWidth, bgHeight, Bitmap.Config.ARGB_8888);
        try {
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(bgBitmap, 0, 0, null);
            //canvas.scale(scaleFactor, scaleFactor, srcWidth / 2, srcHeight / 2);
            canvas.drawBitmap(src, letfStart, 900, null);
            canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.restore();
        } catch (Exception e) {
            bitmap = null;
            e.getStackTrace();
        }

        return bitmap;
    }


}
