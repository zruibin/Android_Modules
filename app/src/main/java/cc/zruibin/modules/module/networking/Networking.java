package cc.zruibin.modules.module.networking;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by ruibin.chow on 14/01/2018.
 */

public class Networking extends Object {

    public static OkHttpClient httpClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS).build();
        return client;
    }

    public static Request requestURL(String baseURL, String method, Map<String, String> params) {
        if (method.length() == 0) {
            method = "GET";
        }

        Request request = null;
        if (method.equalsIgnoreCase("GET")) {
            StringBuffer stringBuffer = new StringBuffer();
            for (String key : params.keySet()) {
                try {
                    String value = URLEncoder.encode(params.get(key), "utf-8");
                    stringBuffer.append(key + "=" + value + "&");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            String url = baseURL + "?" + stringBuffer.toString();
            request = new Request.Builder().url(url).get().build();
        } else {
            FormBody.Builder builder = new FormBody.Builder();
            for (String key : params.keySet()) {
                String value = params.get(key);
                builder.add(key, value);
            }

            RequestBody requestBodyPost = builder.build();
            request = new Request.Builder()
                    .url(baseURL)
                    .post(requestBodyPost)
                    .build();
        }
        return request;
    }

    public static void handleRequest (String url, String method, Map<String, String>params,
                                      final Success success, final Failure failure) {
        final Handler handler = new Handler(Looper.getMainLooper()) { //主线程
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        failure.failure((IOException)msg.obj);
                        break;
                    case 2:
                        String responseString = (String)msg.obj;
                        success.success(responseString);
                        break;
                }
            }
        };

        OkHttpClient client = Networking.httpClient();
        Request requestPost = Networking.requestURL(url, method, params);
        client.newCall(requestPost).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message msg = handler.obtainMessage();
                msg.what = 1;
                msg.obj = e;
                msg.sendToTarget(); //异步线程返回数据到主线程
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseString = response.body().string();
                Message msg = handler.obtainMessage();
                msg.what = 2;
                msg.obj = responseString;
                msg.sendToTarget(); //异步线程返回数据到主线程
            }
        });
    }

    public static void handlePOSTWithFormData(String url, Map<String, String>params, String filePath,
                                              final Success success, final Failure failure) {
        File file = new File(filePath);
        // form 表单形式上传
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if(file != null){
            // MediaType.parse() 里面是上传的文件类型。
            RequestBody filebody = RequestBody.create(MediaType.parse("image/jpeg"), file);
            String filename = file.getName();
            // 参数分别为， 请求key ，文件名称 ， RequestBody
            builder.addFormDataPart("file", filename, filebody);
        }
        // map 里面是请求中所需要的 key 和 value
        for (Map.Entry<String, String> map : params.entrySet()) {
            String key = map.getKey().toString();
            String value = null;
            if (map.getValue() == null) {
                value = "";
            } else {
                value = map.getValue();
            }
            builder.addFormDataPart(key, value);
        }
        RequestBody body = builder.build();
        //结果返回
        OkHttpClient client = Networking.httpClient();
        final Request request = new Request.Builder().url(url).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        failure.failure(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        success.success(result);
                    }
                });
            }
        });
    }

    public abstract static class Success {
        public abstract void success(String responseString);
    }

    public abstract static class Failure {
        public abstract void failure(IOException error);
    }
}
