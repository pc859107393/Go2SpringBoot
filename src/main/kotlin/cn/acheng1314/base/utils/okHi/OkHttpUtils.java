package cn.acheng1314.base.utils.okHi;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created by pc on 2017/8/6.
 */
public class OkHttpUtils {

    public static void loge(String msg) {
        System.out.println(msg);
        System.out.println("|=============over===============|");
    }

    public final static void main(String[] args) {
        try {
            Response response = get("http://acheng1314.cn/api/v1/findPostsByKey/java")
                    .params("pageNum", 1)
                    .params("pageSize", "3")
                    .execute();
            loge(response.body().string());

            response = get("http://acheng1314.cn/api/v1/findPostsByKey/java?pageNum=5")
                    .params("pageSize", "3")
                    .execute();
            loge(response.body().string());

            get("http://acheng1314.cn/api/v1/findPostsByKey/java")
                    .params("pageNum", 500)
                    .params("pageSize", "3")
                    .enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            loge(response.body().string());
                        }
                    });

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Response response = post("http://acheng1314.cn/api/v1/findPostsByKey/java")
                    .params("pageNum", 1)
                    .params("pageSize", "3")
                    .execute();
            loge(response.body().string());

            post("http://acheng1314.cn/manage/uploadFile")
                    .params("file", new File("/Users/pc/Desktop/idea.vmoptions"))
                    .header("Cookie", "JSESSIONID=A0A7BFEEB710E52D1853A89C4BB35F02")
                    .enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            loge(e.getMessage());
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            loge(response.body().string());
                        }
                    });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static <T extends Serializable> GetRequest<T> get(String url) {
        return new GetRequest<>(url);
    }

    public static <T extends Serializable> PostRequest<T> post(String url) {
        return new PostRequest<>(url);
    }
}
