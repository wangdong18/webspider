package http;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author: mortal
 * @program: webSpider
 * use org.apache.commons.httpclient.3
 *
 **/
public class HttpClient3 {
    public String getMethod(String url) {
        InputStream is = null;
        BufferedReader bufferedReader = null;
        String result = null;
        HttpClient httpClient = new HttpClient();
        //设置连接主机服务器的超时时间
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(15000);
        GetMethod getMethod = new GetMethod(url);

        //请求超时时间
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,60000);
        //设置请求重试机智，默认重置参数啊3次，参数设置为true,表示可用，false相反
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler(3,true));

        try {
            int statusCode = httpClient.executeMethod(getMethod);
            if (statusCode != ResponseCodeEnum.success.code()) {
                System.err.println("get  fail:" + getMethod.getStatusLine());
            } else {
                is = getMethod.getResponseBodyAsStream();
                bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                StringBuffer sbf = new StringBuffer();
                String temp = null;
                while ((temp = bufferedReader.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                result = sbf.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (null!=is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null!=bufferedReader) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            getMethod.releaseConnection();
        }
        return result;
    }

    public String postMethod(String url, Map<String, String> param) {
        InputStream is = null;
        BufferedReader bufferedReader=null;
        String result = null;
        HttpClient httpClient = new HttpClient();
        //连接主机服务器超时时间
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(15000);

        PostMethod postMethod = new PostMethod(url);
        //设置post请求时间
        postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,60000);

        //设置请求表头
        postMethod.setRequestHeader("Cookie","");
        postMethod.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");

        NameValuePair[] nameValuePairs = null;

        if (null!=param&&param.size()>0) {
            nameValuePairs = new NameValuePair[param.size()];
            int index = 0;
            Set<Map.Entry<String, String>> entries = param.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                try {
                    nameValuePairs[index] = new NameValuePair(entry.getKey(),
                            new String(entry.getValue().getBytes().toString().getBytes("UTF-8"), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                index++;
            }
        }
        if (null!=nameValuePairs&&nameValuePairs.length>0) {
            postMethod.setRequestBody(nameValuePairs);
        }
        try {
            int statusCode = httpClient.executeMethod(postMethod);
            if (statusCode!= ResponseCodeEnum.success.code()) {
                System.err.println("past fail"+postMethod.getStatusLine());
            }
            is=postMethod.getResponseBodyAsStream();
            bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuffer sbf = new StringBuffer();
            String temp = null;
            while ((temp = bufferedReader.readLine()) != null) {
                sbf.append(temp);
                sbf.append("\r\n");
            }
            result = sbf.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (null!=is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null!=bufferedReader) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            postMethod.releaseConnection();
            return result;
        }
    }

    public static void main(String[] args) {
        HttpClient3 client3 = new HttpClient3();
        String url = "https://www.baidu.com/";

        System.err.println(client3.postMethod(url,null));
    }
}
