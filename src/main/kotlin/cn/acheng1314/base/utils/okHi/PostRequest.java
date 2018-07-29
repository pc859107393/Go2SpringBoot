package cn.acheng1314.base.utils.okHi;

import cn.acheng1314.base.utils.okHi.model.HttpParams;
import cn.acheng1314.base.utils.okHi.model.HttpParamsEntry;
import cn.acheng1314.base.utils.okHi.utils.TextUtils;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by pc on 2017/8/8.
 */
public class PostRequest<T extends Serializable> {
    private String url;
    private final String baseUrl;
    protected long cacheTime;                           //默认缓存的超时时间
    protected HttpParams params = new HttpParams();     //添加的param
    private Request request;
    private boolean isFile = false;
    private File fileValue;
    private String fileKey;

    public PostRequest(String url) {
        this.url = url;
        this.baseUrl = url;
    }


    public PostRequest params(String key, String value) {
        params.put(key, value);
        return this;
    }

    public PostRequest params(String key, int value) {
        params.put(key, value);
        return this;
    }

    public PostRequest params(String key, boolean value) {
        params.put(key, value + "");
        return this;
    }

    public PostRequest params(String key, float value) {
        params.put(key, value + "");
        return this;
    }

    public PostRequest params(String key, double value) {
        params.put(key, value + "");
        return this;
    }

    public PostRequest params(String key, char value) {
        params.put(key, value);
        return this;
    }

    public PostRequest params(String key, File value) {
        this.isFile = true;
        this.fileKey = key;
        this.fileValue = value;
        params.put(key, value);
        return this;
    }

    public void removeParam(String key) {
        params.removeParam(key);
    }


    public void removeAllParams() {
        params.clearParams();
    }

    /**
     * 同步执行的http请求
     *
     * @return 响应
     * @throws IOException
     */
    public Response execute() throws IOException {
        if (TextUtils.isEmpty(url)) throw new IllegalArgumentException("Url can not be null!");
        initRequest();
        return new OkHttpClient().newCall(request).execute();
    }

    /**
     * 异步执行的okhttp请求
     *
     * @param callback 回调
     */
    public void enqueue(Callback callback) {
        if (TextUtils.isEmpty(url)) throw new IllegalArgumentException("Url can not be null!");
        initRequest();
        new OkHttpClient().newCall(request).enqueue(callback);
    }

    private Headers initHeaders() {
        //创建Headers
        ArrayList<HttpParamsEntry> headers = params.getHeaders();
        Headers.Builder headerBuilder = new Headers.Builder();
        for (HttpParamsEntry entry : headers) {
            headerBuilder.add(entry.k, entry.v);
        }
        return headerBuilder.build();
    }

    private RequestBody initBody() {
        RequestBody body = null;
        if (!isFile)
            body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), params.getUrlParams().toString());
        else {
            //创建文件请求体
            RequestBody fileBody = RequestBody.create(MultipartBody.FORM, fileValue);
            body = new MultipartBody.Builder()
                    .addFormDataPart(fileKey, fileValue.getName(), fileBody)
                    .build();
//                    MyPostBody.create(MultipartBody.FORM, params.getBytesContent());
        }
        return body;
    }

    private void initRequest() {
        request = new Request.Builder()
                .url(url)
                .headers(initHeaders())
                .post(initBody())
                .build();
    }

    public PostRequest header(String key, String value) {
        params.putHeaders(key, value);
        return this;
    }
}
