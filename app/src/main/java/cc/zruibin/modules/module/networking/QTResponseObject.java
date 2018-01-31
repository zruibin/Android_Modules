package cc.zruibin.modules.module.networking;

import com.alibaba.fastjson.JSON;


/**
 * Created by ruibin.chow on 14/01/2018.
 */

public final class QTResponseObject extends Object {

    public final static Integer CODE_SUCCESS = 10000;
    public final static String ERROR_CODE = "ERROR_CODE";
    public final static String ERROR_MESSAGE = "ERROR_MESSAGE";
    public final static int CODE_ERROR_TOKEN_NOT_FOUND = 11003; //"登录过期，请重新登录", "token或用户uuid未找到"

    private int code;
    private String message;
    private Object data;

    public static QTResponseObject createInstance(String string) {

        QTResponseObject obj = null;
        try {
            obj = JSON.parseObject(string, QTResponseObject.class);
        } catch (Exception e) {

        }

        return obj;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
