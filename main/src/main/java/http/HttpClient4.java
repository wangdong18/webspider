package http;


import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @Author: mortal
 * @program: webSpider
 * use  org.apache.http.client4
 **/
public class HttpClient4 {
    public String getMethod(String url) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        String result = null;
        try {
            httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);

            //设置表头
            httpGet.setHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)//连接主机超时时间
                                                       .setConnectionRequestTimeout(35000)//请求超时时间
                                                       .setSocketTimeout(60000)//读取数据超时时间
                                                       .build();
            httpGet.setConfig(requestConfig);
            httpResponse = httpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            result = EntityUtils.toString(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (null!=httpResponse) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null!=httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return result;
        }
    }


    public String postMethod(String urlAddress, Map<String,String> param) {
        CloseableHttpClient httpClient =HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String result = null;
        HttpPost httpPost = new HttpPost(urlAddress);
        //配置实例参数实例
        RequestConfig config = RequestConfig.custom().setConnectTimeout(35000)//连接主机服务超时时间
                                           .setConnectionRequestTimeout(35000)//连接请求超时时间
                                           .setSocketTimeout(60000)//读取数据连接超时时间
                                           .build();
        httpPost.setConfig(config);

        //设置请求头
        httpPost.addHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
        httpPost.addHeader("Cookie","_ga=GA1.2.1040300975.1547602907; _pykey_=8716a0d7-2065-567b-acd6-895e01dd0d3a; _gid=GA1.2.265243743.1590642810; Hm_lvt_104008c2f25f506d136d824e42db7c3e=1589522493,1589858524,1590048394,1590642811; PX_PARTNER_TOKEN=5e9d2a1c4e9222b19ee301f088a5ab36; stat_mac=C962B257DB9B55CF; _gat=1; Hm_lpvt_104008c2f25f506d136d824e42db7c3e=1590664803");
        if (null != param && param.size() > 0) {
            List<NameValuePair> nameValuePairs = new ArrayList<>();

            Set<Map.Entry<String, String>> entries = param.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        try {
            response=httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            result=EntityUtils.toString(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        HttpClient4 client4 = new HttpClient4();
        String url = "https://www.baidu.com/";
        System.err.println(client4.postMethod(url,null));
    }
}
