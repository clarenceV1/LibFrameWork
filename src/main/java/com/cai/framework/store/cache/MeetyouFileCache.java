package com.cai.framework.store.cache;

/**
 * Created by clarence on 2018/2/5.
 */

import android.content.Context;

import com.cai.framework.utils.FileUitls;

import java.io.File;

public class MeetyouFileCache extends AbstractMeetyouCache {
    protected Context mContext;

    public MeetyouFileCache(Context context, String name) {
        this(context, null, name);
    }

    public MeetyouFileCache(Context context, String path, String name) {
        this.setContext(context);
        this.setName(name);
        this.setPath(path);
    }

    protected void setContext(Context context) {
        this.mContext = context;
    }

    public String getName() {
        return this.mName;
    }

    public String getPath() {
        return this.mPath;
    }

    protected void setName(String name) {
        this.mName = name;
    }

    protected void setPath(String path) {
        this.mPath = path;
    }

    public synchronized boolean write() {
        if (this.mContext == null) {
            return false;
        } else {
            try {
                File cache = FileUitls.getFile(mContext, this.mPath, this.getName());
                this.processInfoCache("MeetyouCache.last_save_time");
                this.write(cache);
                return true;
            } catch (Exception var2) {
                var2.printStackTrace();
                return false;
            }
        }
    }

    public synchronized boolean read() {
        if (this.mContext == null) {
            return false;
        } else {
            try {
                File cache = FileUitls.getFile(mContext, this.mPath, this.getName());
                this.read(cache);
                this.processInfoCache("MeetyouCache.last_restore_time");
                return true;
            } catch (Exception var2) {
                return false;
            }
        }
    }

    public boolean delete() {
        if (this.mContext == null) {
            return false;
        } else {
            return FileUitls.deleteFile(mContext, this.getName());
        }
    }
}

