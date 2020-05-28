package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: mortal
 * @program: webSpider
 *
 * use java.net.http.HttpURLConnection
 **/

public class HttpURLConnection {

    public String getMethod(String addressUrl) {
        java.net.HttpURLConnection urlConnection = null;
        InputStream is = null;
        BufferedReader readerBuffer = null;
        String result = null;
        try {
            URL url = new URL(addressUrl);
            urlConnection = (java.net.HttpURLConnection) url.openConnection();
            //方法 必须大写
            urlConnection.setRequestMethod("GET");
            //连接服务器时间
            urlConnection.setConnectTimeout(15000);
            //读取远程数据的时间
            urlConnection.setReadTimeout(6000);

            //增加表头请求
//            urlConnection.setRequestProperty("token","5e9d2a1c4e9222b19ee301f088a5ab36");
//            urlConnection.setRequestProperty("Cookie", "");
//            urlConnection.setRequestProperty("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");


            urlConnection.connect();
            if (urlConnection.getResponseCode() == ResponseCodeEnum.success.code()) {
                is = urlConnection.getInputStream();
                readerBuffer = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                StringBuffer sbf = new StringBuffer();
                String temp = null;
                while ((temp = readerBuffer.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                result = sbf.toString();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (null!=readerBuffer) {
                try {
                    readerBuffer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null!=is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            urlConnection.disconnect();
        }
        return result;
    }

    public String postMethod(String addressUrl, Map<String,String> param) {
        java.net.HttpURLConnection httpURLConnection = null;
        InputStream is = null;
        OutputStream ot = null;
        BufferedReader readerBuffer = null;
        String result = null;
        try {
            URL url = new URL(addressUrl);
            httpURLConnection= (java.net.HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setReadTimeout(6000);

            //增加表头请求
            httpURLConnection.setRequestProperty("token","5e9d2a1c4e9222b19ee301f088a5ab36");
            httpURLConnection.setRequestProperty("Cookie", "");
            httpURLConnection.setRequestProperty("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
            System.err.println(param.toString());
            // 默认值为：false，当向远程服务器传送数据/写数据时，需要设置为true
            httpURLConnection.setDoOutput(true);
            // 默认值为：false，当向远程服务器读取数据时，需要设置为true
            httpURLConnection.setDoInput(true);
            ot = httpURLConnection.getOutputStream();
            if (null!=param&&!param.isEmpty()) {
                ot.write( param.toString().getBytes());
            }
            httpURLConnection.connect();
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == ResponseCodeEnum.success.code()) {
                is=httpURLConnection.getInputStream();
                readerBuffer = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                StringBuffer sbf = new StringBuffer();
                String temp = null;
                while ((temp = readerBuffer.readLine())!= null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                result = sbf.toString();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
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
            if (null!=readerBuffer) {
                try {
                    readerBuffer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            httpURLConnection.disconnect();
        }
        return result;
    }


    public static void main(String[] args) {
        HttpURLConnection urlConnection = new HttpURLConnection();

        String method = urlConnection.postMethod("http://www.baidu.com",null);
        System.err.println(method);
    }
}
