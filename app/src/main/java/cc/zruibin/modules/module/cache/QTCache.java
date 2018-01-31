package cc.zruibin.modules.module.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by ruibin.chow on 18/01/2018.
 */

public final class QTCache extends Object {

    private static QTCache instance = new QTCache();

    private QTCache(){}

    public static QTCache sharedCache(){
        return instance;
    }

    private ACache cache = null;

    public void initCache(Context ctx) {
        instance.cache = ACache.get(ctx);
    }

    private boolean nullCache() {
        if (this.cache == null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean remove(String key) {
        if (nullCache()) {
            return false;
        }
        return this.cache.remove(key);
    }

    public void clear() {
        if (nullCache()) { return; }
        this.cache.clear();
    }

    public void put(String key, int value) {
        put(key, String.valueOf(value));
    }

    public int getInteger(String key) {
        String value = getString(key);
        if (value == null) {
            return 0;
        } else {
            return Integer.parseInt(value);
        }
    }

    public void put(String key, String value) {
        if (nullCache()) { return; }
        this.cache.put(key, value);
    }

    public String getString(String key) {
        if (nullCache()) { return null; }
        return this.cache.getAsString(key);
    }

    public void put(String key, Serializable serializable) {
        if (nullCache()) { return; }
        this.cache.put(key, serializable);
    }

    public Object getObject(String key) {
        if (nullCache()) { return null; }
        return this.cache.getAsObject(key);
    }

    public void put(String key, JSONArray jsonArray) {
        if (nullCache()) { return; }
        this.cache.put(key, jsonArray);
    }

    public JSONArray getJSONArray(String key) {
        if (nullCache()) { return null; }
        return this.cache.getAsJSONArray(key);
    }

    public void put(String key, JSONObject jsonObject) {
        if (nullCache()) { return; }
        this.cache.put(key, jsonObject);
    }

    public JSONObject getJSONObject(String key) {
        if (nullCache()) { return null; }
        return this.cache.getAsJSONObject(key);
    }

    public void put(String key, Bitmap bitmap) {
        if (nullCache()) { return; }
        this.cache.put(key, bitmap);
    }

    public Bitmap getBitmap(String key) {
        if (nullCache()) { return null; }
        return this.cache.getAsBitmap(key);
    }

    public void put(String key, Drawable drawable) {
        if (nullCache()) { return; }
        this.cache.put(key, drawable);
    }

    public Drawable getDrawable(String key) {
        if (nullCache()) { return null; }
        return this.cache.getAsDrawable(key);
    }

    public void put(String key, byte[] bytes) {
        if (nullCache()) { return; }
        this.cache.put(key, bytes);
    }

    public byte[] getBinary(String key) {
        if (nullCache()) { return null; }
        return this.cache.getAsBinary(key);
    }

}
