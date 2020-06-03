package http;

import com.alibaba.fastjson.JSON;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: mortal
 * @program: main
 **/
public class OkHttp {
    public  void getMethod(String urlAddress) throws IOException {
        //创建一个请求对象
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.SECONDS)//读取时间
                .build();

        //默认get请求
        Request request = new Request.Builder().url(urlAddress).build();
        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            System.out.println(response.body().string());
        } else {
            System.err.println(response.message());
        }
    }

    public  void postMethod(String urlAddress,Map<String,String> map) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.SECONDS)//读取时间
                .build();

        MediaType mediaType = MediaType.parse("text/x-markdown;charset=utf-8");
        String requestBody = JSON.toJSONString(map);
        RequestBody body = RequestBody.create(mediaType, requestBody);
        Request request = new Request.Builder().url(urlAddress).post(body).build();
        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            System.out.println(response.body());
        } else {
            System.err.println(response.message());
        }

    }

    public  void syncGet(String urlAddress){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.SECONDS)//读取时间
                .build();
        //默认get请求
        Request request = new Request.Builder().url(urlAddress).build();

        //enqueue和同步(execute)的不一样
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.err.println(e.getMessage());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println(response.body().string());
            }
        });
    }

    public  void syncPost(String urlAddress, Map<String,String> map){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.SECONDS)//读取时间
                .build();
        MediaType mediaType = MediaType.parse("text/x-markdown;charset=utf-8");
        String requestBody = JSON.toJSONString(map);
        System.err.println(requestBody);
        RequestBody body = RequestBody.create(mediaType, requestBody);
        //默认get请求
        Request request = new Request.Builder().url(urlAddress).post(body).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.err.println(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println(response.body().string());
            }
        });

    }

    public static void main(String[] args) {
        OkHttp okHttp = new OkHttp();
        String url = "http://www.baidu.com";
        Map<String, String> map = new HashMap<>();
        map.put("name", "mortal");
        map.put("age", "12");
        try {
            okHttp.postMethod(url,map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
