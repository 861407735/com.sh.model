package com.sh.model.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

@Service
public class HttpUtil{  
    private  PoolingHttpClientConnectionManager cm;  //连接池
    private  RequestConfig requestConfig; //请求配置
    private  String result = "";  //请求返回的结果
    public HttpUtil() {
    	if (cm == null) {  
            cm = new PoolingHttpClientConnectionManager();  // 创建Http 客户端连接池管理器
            cm.setMaxTotal(800);// 整个连接池最大连接数  
            cm.setDefaultMaxPerRoute(10);// 每路由最大连接数，默认值是2  
            requestConfig=RequestConfig.custom()  //建立构造器
    				.setConnectTimeout(500000)   //设置连接超时时间
    				.setConnectionRequestTimeout(500000) // 设置请求超时时间
    				.setSocketTimeout(500000)
    				.setRedirectsEnabled(true)//默认允许自动重定向
    				.build();
        }  
	}
	/*通过连接池获取HttpClient  */  
    private  CloseableHttpClient getHttpClient() {
        //HttpClients设置连接池管理器，设置默认请求配置
        return HttpClients.custom().setConnectionManager(cm).setDefaultRequestConfig(requestConfig).build();  
    }  
    /*中间工具，将map对象转为list 键值对 */
    private  ArrayList<NameValuePair> mapToList(Map<String, Object> params) {  
        ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();  
        for (Map.Entry<String, Object> param : params.entrySet()) {  
        	list.add(new BasicNameValuePair(param.getKey(), String.valueOf(param.getValue())));  
        }   
        return list;  
    }  
    //输入请求得到结果,utf-8得到结果
    private  String getResult(HttpRequestBase request) {  
        CloseableHttpClient httpClient = getHttpClient();  
        try {  
            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity(); 
            if (entity != null) {  
                result = EntityUtils.toString(entity,Consts.UTF_8);
                response.close();  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return result;  
    }  
    /*get的URL请求*/
    public  String httpGetRequest(String url) {  
        HttpGet httpGet = new HttpGet(url);  
        return getResult(httpGet);  
    }  
   /* get的URL的参数map请求*/
    public  String httpGetRequest(String url, Map<String, Object> params) throws URISyntaxException {  
    	ArrayList<NameValuePair> pairs = mapToList(params);
        URI urlPath = new URIBuilder().setPath(url).setParameters(pairs).setCharset(Consts.UTF_8).build();   
        HttpGet httpGet = new HttpGet(urlPath);  
        return getResult(httpGet);  
    }  
    /*get的URL参数map，头map参数*/
    public  String httpGetRequest(String url, Map<String, Object> headers, Map<String, Object> params)  
            throws URISyntaxException {   
        ArrayList<NameValuePair> pairs = mapToList(params);
        // 通过URI 将请求参数信息 设置setParameters() 参数为List类型 需要中间件的转换
        URI urlPath = new URIBuilder().setPath(url).setParameters(pairs).setCharset(Consts.UTF_8).build();
        HttpGet httpGet = new HttpGet(urlPath);
        for (Map.Entry<String, Object> param : headers.entrySet()) {
            //遍历将头信息添加在httpget中
            httpGet.addHeader(param.getKey(), String.valueOf(param.getValue()));  
        }  
        return getResult(httpGet);  
    }  
    
    /*post的URL请求*/
    public  String httpPostRequest(String url) {  
        HttpPost httpPost = new HttpPost(url);  
        return getResult(httpPost);  
    }  
    /*post的URL的参数map请求*/
    public  String httpPostRequest(String url, Map<String, Object> params) throws UnsupportedEncodingException {  
        HttpPost httpPost = new HttpPost(url);  
        ArrayList<NameValuePair> pairs = mapToList(params);  
        httpPost.setEntity(new UrlEncodedFormEntity(pairs,Consts.UTF_8));  
        return getResult(httpPost);  
    }  
    /* post的URL的参数map请求map头*/
    public  String httpPostRequest(String url, Map<String, Object> headers, Map<String, Object> params)throws UnsupportedEncodingException {  
        HttpPost httpPost = new HttpPost(url);  
        ArrayList<NameValuePair> pairs = mapToList(params);  
        httpPost.setEntity(new UrlEncodedFormEntity(pairs,Consts.UTF_8)); //Url 编码表单实体
        for (Map.Entry<String, Object> param : headers.entrySet()) {  
            httpPost.addHeader(param.getKey(), String.valueOf(param.getValue()));  
        }       
        return getResult(httpPost);  
    }  
    /*发送的json字符串的参数请求*/
    public  String httpPostJSON(String url, String json) throws UnsupportedEncodingException {
        HttpPost httpPost = new HttpPost(url);
        StringEntity entity = new StringEntity(json, ContentType.create("application/json",Consts.UTF_8));
        httpPost.setEntity(entity);
        return getResult(httpPost);
    }
    /*发送的json对象的请求*/
    public  String doPostJSON(String url, Object json) {
    	HttpPost httpPost=new HttpPost(url);
        StringEntity entity = new StringEntity(json.toString(),ContentType.create("application/json", Consts.UTF_8));
        httpPost.setEntity(entity);
        return getResult(httpPost);
    }
    /*发送的xml字符串*/
    public  String httpPostXML(String url,String xmlData) throws UnsupportedEncodingException{
    	HttpPost post = new HttpPost(url);
        StringEntity entity = new StringEntity(xmlData,ContentType.create("application/json", Consts.UTF_8));
        post.setEntity(entity);
        return getResult(post);
    }
    //发送http请求
    public InputStream httpGetForStream(String url) {
    	HttpGet request=new HttpGet(url);
    	 CloseableHttpClient httpClient = getHttpClient();  
         try {  
             CloseableHttpResponse response = httpClient.execute(request);
             HttpEntity entity = response.getEntity(); 
             if (entity != null) {  
                 InputStream is=entity.getContent();
                 return is;
             }  
         } catch (Exception e) {  
             e.printStackTrace();  
         }
		return null;
    }   
 
}  

