package cn.acheng1314.base.utils.okHi;

import cn.acheng1314.base.utils.okHi.model.HttpParams;
import cn.acheng1314.base.utils.okHi.model.HttpParamsEntry;
import cn.acheng1314.base.utils.okHi.utils.TextUtils;
import okhttp3.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;


/**
 * Created by pc on 2017/8/8.
 */
public class GetRequest<R extends Serializable> {
    private String url;
    private final String baseUrl;
    protected long cacheTime;                           //默认缓存的超时时间
    protected HttpParams params = new HttpParams();     //添加的param
    private Request request;

    public GetRequest(String url) {
        this.url = url;
        this.baseUrl = url;
    }

    public GetRequest params(String key, String value) {
        params.put(key, value);
        return this;
    }

    public GetRequest params(String key, int value) {
        params.put(key, value);
        return this;
    }

    public GetRequest params(String key, boolean value) {
        params.put(key, value + "");
        return this;
    }

    public GetRequest params(String key, float value) {
        params.put(key, value + "");
        return this;
    }

    public GetRequest params(String key, double value) {
        params.put(key, value + "");
        return this;
    }

    public GetRequest params(String key, char value) {
        params.put(key, value);
        return this;
    }

    public GetRequest removeParam(String key) {
        params.removeParam(key);
        return this;
    }


    public GetRequest removeAllParams() {
        params.clearParams();
        return this;
    }

    /**
     * 同步执行的http请求
     *
     * @return 响应
     * @throws IOException
     */
    public Response execute() throws IOException {
        if (TextUtils.isEmpty(url)) throw new IllegalArgumentException("Url can not be null!");
        initParams();
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
        initParams();
        initRequest();
        new OkHttpClient().newCall(request).enqueue(callback);
    }

    private void initParams() {
        if (!url.contains("?"))
            url = baseUrl + "?" + params.getUrlParams();
        else if (url.contains("?"))
            url = baseUrl + "&" + params.getUrlParams();
    }


    public GetRequest header(String key, String value) {
        params.putHeaders(key, value);
        return this;
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


    private void initRequest() {
        request = new Request.Builder()
                .url(url)
                .headers(initHeaders())
                .get()
                .build();
    }

}
