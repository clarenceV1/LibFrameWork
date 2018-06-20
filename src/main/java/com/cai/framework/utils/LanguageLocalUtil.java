package com.cai.framework.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;

import com.cai.framework.bean.LanguageModel;
import com.example.clarence.utillibrary.StringUtils;

import java.util.List;
import java.util.Locale;

/**
 * APP内应用语言
 * Created by davy on 2018/3/1.
 */

public class LanguageLocalUtil {
    private static List<LanguageModel> languageModels;

    public static void changeLanguage(Context mContext,String language) {
        if (!StringUtils.isEmpty(language)) {
            // 本地语言设置
            Locale myLocale = new Locale(language);
            Resources resources = mContext.getResources();
            Configuration configuration = resources.getConfiguration();
            DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                configuration.setLocale(myLocale);
            }else {
                configuration.locale = myLocale;
            }
            resources.updateConfiguration(configuration, displayMetrics);
        }
    }

    public static List<LanguageModel> getAllLanguage(Context context) {
        if (languageModels == null) {
//            languageModels = new ArrayList<>();
//            String json = AssetUtil.getStringFromAsset(getContext(), "language.json");
//            try {
//                JSONArray jsonArray = new JSONArray(json);
//                int size = jsonArray.length();
//                for (int i = 0; i < size; i++) {
//                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//                    LanguageModel model = new LanguageModel();
//                    model.setName(jsonObject.optString("name"));
//                    model.setLocal(jsonObject.optString("local"));
//                    //
//                    languageModels.add(model);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

        }
        return languageModels;
    }
}
