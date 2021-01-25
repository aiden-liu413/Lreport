package com.kxingyi.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.EntityEnclosingMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;

import net.sf.json.JSONObject;
/**
 * http(s)相关工具类
 * @author admin
 *
 */
public class HttpsUtils {
	
	public static HttpClient httpclient;
	public static MultiThreadedHttpConnectionManager connectionManager;

	static {
		connectionManager = new MultiThreadedHttpConnectionManager();
		HttpConnectionManagerParams params = connectionManager.getParams();
		params.setConnectionTimeout(30000);
		params.setSoTimeout(30000);
		params.setDefaultMaxConnectionsPerHost(32);
		params.setMaxTotalConnections(256);
		httpclient = new HttpClient(connectionManager);
		//不开启重试机制
        httpclient.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0,false));

	}
	
	public static void main(String[] args) {
		JSONObject json = new JSONObject();
		JSONObject sendByHttpPost = sendByHttpPost(json, "http://localhost:8080/xxl-job-admin/jobinfo/add");
		System.out.println(sendByHttpPost);
	}
	
	/**
     * 用post请求调用http接口(参数名自定义)
     *
     * @param params
     *            参数（key为参数名,value为参数值）
     * @param url
     * @return
     * @throws HttpException
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
	public static String sendByHttpPost(JSONObject params, String url, Header header) {
        StringBuffer stringBuffer = new StringBuffer();
        PostMethod method = new PostMethod(url);
        if(header!=null)
        	method.addRequestHeader(header);
        method.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if(params != null){
            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                nvps.add(new NameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        NameValuePair[] nval = new NameValuePair[nvps.size()];
        for (int i = 0; i < nval.length; i++) {
            nval[i] = nvps.get(i);
        }
        InputStream in = null;
        BufferedReader reader = null;
        try {
            httpclient.getHttpConnectionManager().getParams().setSoTimeout(5000);
            httpclient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
            method.setRequestBody(nval);
            httpclient.executeMethod(method);
            // 读取内容
            in = method.getResponseBodyAsStream();

            reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
            String str = "";
            while ((str = reader.readLine()) != null) {
                stringBuffer.append(str);
            }
        } catch (Exception e){
            e.printStackTrace();
            JSONObject errorJson = new JSONObject();
            errorJson.put("code", -1);
            errorJson.put("msg", e.getMessage());
            return errorJson.toString();
        } finally {
            try {
                if(reader != null){
                    reader.close();
                }
                if(in != null){
                    in.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }

            if(method != null){
                method.releaseConnection();
            }
            httpclient.getHttpConnectionManager().getParams().setSoTimeout(30000);
            httpclient.getHttpConnectionManager().getParams().setConnectionTimeout(30000);

        }
        return stringBuffer.toString();
    }
    /**
     * 用post请求调用http接口(参数名自定义)
     *
     * @param params
     *            参数（key为参数名,value为参数值）
     * @param url
     * @return
     * @throws HttpException
     * @throws IOException
     */
    public static JSONObject sendByHttpPost(JSONObject params, String url) {
    	JSONObject resultJson = new JSONObject();
    	resultJson.put("code", -1);
		String resultStr = sendByHttpPost(params, url, null);
		if (StringUtils.isBlank(resultStr)) return resultJson;
		try {
			resultJson = JSONObject.fromObject(resultStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultJson;
    }

    /**
     * 通过get方式请求数据
     * @param url
     * @return
     */
    public static String sendByHttpGet(String url) {
        StringBuffer stringBuffer = new StringBuffer();
        EntityEnclosingMethod method = new GetMethod(url);
        InputStream in = null;
        BufferedReader reader = null;
        try {
            method.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

            httpclient.executeMethod(method);
            // 读取内容
            in = method.getResponseBodyAsStream();
            reader = new BufferedReader(new InputStreamReader(in, "utf-8"));

            String str = "";
            while ((str = reader.readLine()) != null) {
                stringBuffer.append(str);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(reader != null){
                    reader.close();
                }
                if(in != null){
                    in.close();
                }
                if(method != null){
                    method.releaseConnection();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return stringBuffer.toString();
    }
}




class DeleteMethod extends EntityEnclosingMethod {
	public DeleteMethod(String uri) {
		super(uri);
	}

	@Override
	public String getName() {
		return "DELETE";
	}

}
class GetMethod extends EntityEnclosingMethod {
	public GetMethod(String uri) {
		super(uri);
	}

	@Override
	public String getName() {
		return "GET";
	}

}
class PatchMethod extends EntityEnclosingMethod {
	public PatchMethod(String uri) {
		super(uri);
	}

	@Override
	public String getName() {
		return "PATCH";
	}

}
