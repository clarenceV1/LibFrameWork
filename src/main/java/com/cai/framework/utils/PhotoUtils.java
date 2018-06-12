package com.cai.framework.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.cai.annotation.aspect.Permission;

import java.io.File;

public class PhotoUtils {

    public static final String USER_IMAGE_NAME = "image.png";
    private final String USER_CROP_IMAGE_NAME = "temporary.png";
    private Uri imageUriFromCamera;
    private final int TAKE_PHOTO = 2;
    private final int CROP_IMAGE = 3;
    private final int GET_IMAGE = 1;
    private boolean isCrop;//是否要裁剪；

    public static class LazyHolder {
        public final static PhotoUtils INSTANCE = new PhotoUtils();
    }

    private PhotoUtils() {
    }

    public static PhotoUtils getInstance() {
        return PhotoUtils.LazyHolder.INSTANCE;
    }

    public void setCrop(boolean crop) {
        isCrop = crop;
    }

    @Permission(value = Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void choosePhone(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, GET_IMAGE);
    }

    @Permission(value = Manifest.permission.CAMERA)
    public void takePhoto(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File photoFile = new File(activity.getExternalCacheDir(), USER_IMAGE_NAME);
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

            /*
             * 这里就是高版本需要注意的，需用使用FileProvider来获取Uri，同时需要注意getUriForFile
             * 方法第二个参数要与AndroidManifest.xml中provider的里面的属性authorities的值一致
             * */
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            imageUriFromCamera = FileProvider.getUriForFile(activity, "com.cai.work2.fileprovider", photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriFromCamera);

            activity.startActivityForResult(intent, TAKE_PHOTO);
        } else {
            File file = new File(activity.getExternalCacheDir(), USER_IMAGE_NAME);
            imageUriFromCamera = Uri.fromFile(file);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriFromCamera);
            activity.startActivityForResult(intent, TAKE_PHOTO);
        }
    }

    public String onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        if (requestCode == GET_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = activity.getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            c.close();
            return imagePath;
        } else if (requestCode == TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            if (isCrop) {
                cropImage(activity, imageUriFromCamera, 1, 1, CROP_IMAGE);
            } else {
                File file = new File(activity.getExternalCacheDir(), USER_IMAGE_NAME);
                return file.getPath();
            }
        } else if (requestCode == CROP_IMAGE && resultCode == Activity.RESULT_OK) {
            String imagePath = activity.getExternalCacheDir() + "/" + USER_CROP_IMAGE_NAME;
            return imagePath;
        }
        return null;
    }

    public void cropImage(Activity activity, Uri imageUri, int aspectX, int aspectY, int return_flag) {
        File file = new File(activity.getExternalCacheDir(), USER_CROP_IMAGE_NAME);
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //高版本一定要加上这两句话，做一下临时的Uri
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            FileProvider.getUriForFile(activity, "com.cai.work2.fileprovider", file);
        }
        Uri cropImageUri = Uri.fromFile(file);

        intent.setDataAndType(imageUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropImageUri);

        activity.startActivityForResult(intent, return_flag);
    }
}
