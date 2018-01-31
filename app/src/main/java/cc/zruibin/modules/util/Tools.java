package cc.zruibin.modules.util;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.IdRes;

import com.alibaba.fastjson.JSONObject;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by ruibin.chow on 14/01/2018.
 */

final public class Tools {

    public static Uri getResoucesUri(@IdRes int resourcesString) {
        Uri uri = Uri.parse("res://cc.zruibin.modules/" + resourcesString);
        return uri;
    }

    public static String getPasteBoardURLString(Activity activity) {
        String urlString = null;
        //获取剪贴板管理服务
        ClipboardManager mClipboardManager =(ClipboardManager)activity.getSystemService(Context.CLIPBOARD_SERVICE);
        //GET贴板是否有内容
        ClipData mClipData = mClipboardManager.getPrimaryClip();
        //获取到内容
        if (mClipData != null) {
            int count = mClipData.getItemCount();
            if (count > 0) {
                ClipData.Item item = mClipData.getItemAt(count-1);
                String text = item.getText().toString();
                urlString = StringUtil.pickupURLString(text);
            }
        }
        return urlString;
    }




}
