package com.demo.utils;
/**
 * @Filename：HttpUtil.java
 * @Author：caiqf
 * @Date�??013-9-23
 */

import com.jfinal.kit.HttpKit;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.StrKit;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.eclipse.jetty.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;


@SuppressWarnings("all")
public class HttpUtil {
	
	 /** 
     * 发送 post请求访问本地应用并根据传递参数不同返回不同结果 
	 * @throws Exception 
     */  
    public static String httpClientPost(String url,  List<NameValuePair> formparams) {
        // 创建默认的httpClient实例.    
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);
        UrlEncodedFormEntity uefEntity;
        try {   
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
            httppost.setEntity(uefEntity);  
            System.out.println("executing request " + httppost.getURI());  
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {  
                HttpEntity entity = response.getEntity();
                String res = EntityUtils.toString(entity, "UTF-8");
                res = URLDecoder.decode(res, "UTF-8");
				System.out.println("res:"+res);
				return res;              
            } finally {  
                response.close();
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            // 关闭连接,释放资源    
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }
		return null;  
    }  	
	
	public static String httpClientPost(String reqUrl,String param) {
		String res ="";
		try {
			HttpPost httpPost = new HttpPost(reqUrl);
			StringEntity entityParams = new StringEntity(param, "utf-8");
			httpPost.setEntity(entityParams);
			httpPost.setHeader("Content-Type", "text/xml;charset=ISO-8859-1");
			CloseableHttpClient client = HttpClients.createDefault();
			CloseableHttpResponse response = client.execute(httpPost);
			if(response != null && response.getEntity() != null){
				res = new String(EntityUtils.toByteArray(response.getEntity()),"utf-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}


	public static String httpClientPost(String reqUrl,String param,String charset) {
		String res ="";
		try {
			HttpPost httpPost = new HttpPost(reqUrl);
			StringEntity entityParams = new StringEntity(param, charset);
			httpPost.setEntity(entityParams);
			httpPost.setHeader("Content-Type", "text/xml;charset=ISO-8859-1");
			CloseableHttpClient client = HttpClients.createDefault();
			CloseableHttpResponse response = client.execute(httpPost);
			if(response != null && response.getEntity() != null){
				res = new String(EntityUtils.toByteArray(response.getEntity()),"utf-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	/*public static String httpClientPostJson(String reqUrl,String param) {
		String res ="";
		try {
			HttpPost httpPost = new HttpPost(reqUrl);
			StringEntity entityParams = new StringEntity(param, "utf-8");
			httpPost.setEntity(entityParams);
			httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
			CloseableHttpClient client = HttpClients.createDefault();
			CloseableHttpResponse response = client.execute(httpPost);
			if(response != null && response.getEntity() != null){
				res = new String(EntityUtils.toByteArray(response.getEntity()),"utf-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}*/

	public static String httpClientPostJson(String reqUrl,String param)
	{
		String res=null;
		try {
			URL url = new URL(reqUrl); //url地址
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Content-Type","application/json");
			connection.connect();

			try (OutputStream os = connection.getOutputStream()) {
				os.write(param.getBytes("UTF-8"));
			}

			try (BufferedReader reader = new BufferedReader(
					new InputStreamReader(connection.getInputStream()))) {
				String lines;
				StringBuffer sbf = new StringBuffer();
				while ((lines = reader.readLine()) != null) {
					lines = new String(lines.getBytes(), "utf-8");
					sbf.append(lines);
				}
				res = sbf.toString();

			}
			connection.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}finally{

		}
		return res;
	}

	/**
	 * 
	 * HTTP协议GET请求方法
	 */
	public static String httpMethodGet(String url, String gb) {
		if (null == gb || "".equals(gb)) {
			gb = "UTF-8";
		}
		StringBuffer sb = new StringBuffer();
		URL urls;
		HttpURLConnection uc = null;
		BufferedReader in = null;
		try {
			urls = new URL(url);
			uc = (HttpURLConnection) urls.openConnection();
			uc.setRequestMethod("GET");
			uc.connect();
			in = new BufferedReader(new InputStreamReader(uc.getInputStream(),
					gb));
			String readLine = "";
			while ((readLine = in.readLine()) != null) {
				sb.append(readLine);
			}
			if (in != null) {
				in.close();
			}
			if (uc != null) {
				uc.disconnect();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (uc != null) {
				uc.disconnect();
			}
		}
		return sb.toString();
	}

	/**
	 * 
	 * HTTP协议POST请求方法
	 */
	public static String httpMethodPost(String url, String params, String gb) {
		if (null == gb || "".equals(gb)) {
			gb = "UTF-8";
		}
		StringBuffer sb = new StringBuffer();
		URL urls;
		HttpURLConnection uc = null;
		BufferedReader in = null;
		try {
			urls = new URL(url);
			uc = (HttpURLConnection) urls.openConnection();
			uc.setRequestMethod("POST");
			uc.setDoOutput(true);
			uc.setDoInput(true);
			uc.setUseCaches(false);
			uc.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			uc.connect();
			DataOutputStream out = new DataOutputStream(uc.getOutputStream());
			out.write(params.getBytes(gb));
			out.flush();
			out.close();
			in = new BufferedReader(new InputStreamReader(uc.getInputStream(),
					gb));
			String readLine = "";
			while ((readLine = in.readLine()) != null) {
				sb.append(readLine);
			}
			if (in != null) {
				in.close();
			}
			if (uc != null) {
				uc.disconnect();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (uc != null) {
				uc.disconnect();
			}
		}
		return sb.toString();
	}

	/**
	 * 
	 * HTTP协议POST请求方法发送Json数据
	 */
	/*public static String httpMethodPostJson(String url,
                                        Map paramsMap, String gb) {
		if (null == gb || "".equals(gb)) {
			gb = "UTF-8";
		}
		String params = null;
		if (null != paramsMap) {
			params=Json.getJson().toJson(paramsMap);
		}
		StringBuffer sb = new StringBuffer();
		URL urls;
		HttpURLConnection uc = null;
		BufferedReader in = null;
		try {
			urls = new URL(url);
			uc = (HttpURLConnection) urls.openConnection();
			uc.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			uc.setDoOutput(true);
			uc.setDoInput(true);
			uc.setRequestMethod("POST");
			uc.setUseCaches(false);
			uc.connect();
			DataOutputStream out = new DataOutputStream(uc.getOutputStream());
			out.write(params.getBytes(gb));
			out.flush();
			out.close();
			in = new BufferedReader(new InputStreamReader(uc.getInputStream(),
					gb));
			String readLine = "";
			while ((readLine = in.readLine()) != null) {
				sb.append(readLine);
			}
			if (in != null) {
				in.close();
			}
			if (uc != null) {
				uc.disconnect();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (uc != null) {
				uc.disconnect();
			}
		}
		return sb.toString();
	}
*/
	/**
	 *
	 * HTTP协议POST请求方法
	 */
	public static String httpMethodPostMap(String url,
										  Map paramsMap, String gb) {
		if (null == gb || "".equals(gb)) {
			gb = "UTF-8";
		}
		String params = null;
		if (null != paramsMap) {
			params = getParamStr(paramsMap);
		}
		StringBuffer sb = new StringBuffer();
		URL urls;
		HttpURLConnection uc = null;
		BufferedReader in = null;
		try {
			urls = new URL(url);
			uc = (HttpURLConnection) urls.openConnection();
			uc.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			uc.setDoOutput(true);
			uc.setDoInput(true);
			uc.setRequestMethod("POST");
			uc.setUseCaches(false);
			uc.connect();
			DataOutputStream out = new DataOutputStream(uc.getOutputStream());
			out.write(params.getBytes(gb));
			out.flush();
			out.close();
			in = new BufferedReader(new InputStreamReader(uc.getInputStream(),
					gb));
			String readLine = "";
			while ((readLine = in.readLine()) != null) {
				sb.append(readLine);
			}
			if (in != null) {
				in.close();
			}
			if (uc != null) {
				uc.disconnect();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (uc != null) {
				uc.disconnect();
			}
		}
		return sb.toString();
	}

	/**
	 * 
	 * HTTP协议POST请求添加参数的封装方法
	 */
	public static String getParamStr(Map paramsMap) {
		StringBuilder param = new StringBuilder();
		Set<String> set = paramsMap.keySet();
		for (String string : set) {
			Object object = paramsMap.get(string);
			param.append("&").append(string).append("=").append(object);
		}
		System.out.println("参数："+param.toString().substring(1));
		return param.toString().substring(1);
	}

	public static Map<String,Object> getParams(Map<String, String[]> map, String jsonStr)
	{
		Map<String,Object> result =new HashMap<>();
		String requestDataStr = JsonKit.toJson(map);
		/***接收form-data格式的数据**/
		if (requestDataStr != null && !StringUtil.isBlank(requestDataStr) && !requestDataStr.equals("{}")) {
			for(Map.Entry<String, String[]> entry : map.entrySet())
			{
				result.put(entry.getKey(),entry.getValue());
			}
		} else {
			result = JsonUtils.parseJSON2Map(jsonStr);
		}
		return result;
	}
//	/**
//	 * 
//	 * Author：tingzw
//	 * 
//	 * @Description:微信摇一摇----上传图片素材
//	 * @param @param url
//	 * @param @param paramsMap
//	 * @param @param fileName
//	 * @param @param file
//	 * @param @return
//	 * @return String
//	 * @throws
//	 */
//	@SuppressWarnings("deprecation")
//	public static String httpMethodPost(String url,
//			TreeMap<String, String> paramsMap, String fileName, File file) {
//		try {
//			HttpPost post = new HttpPost(url);
//			HttpResponse httpResponse;
//			MultipartEntity reEntity = new MultipartEntity();
//			FileBody filebody = new FileBody(file);
//
//			reEntity.addPart(fileName, filebody);
//			for (Iterator<Map.Entry<String, String>> it = paramsMap.entrySet()
//					.iterator(); it.hasNext();) {
//				Map.Entry<String, String> e = it.next();
//				reEntity.addPart(e.getKey(), new StringBody(e.getValue()));
//			}
//
//			post.setEntity(reEntity);
//			httpResponse = new DefaultHttpClient().execute(post);
//			HttpEntity httpEntity = httpResponse.getEntity();
//			String ret = EntityUtils.toString(httpEntity, "UTF-8");
//			return ret;
//		} catch (ClientProtocolException e) {
//			log.error(e.getMessage(), e);
//		} catch (IOException e) {
//			log.error(e.getMessage(), e);
//		}
//		return "";
//	}
	/**
	 * 获取通知参数
	 */
	public static String getRequestParam(HttpServletRequest request, Map<String, String> map) {
		String notifyDataStr = HttpKit.readData(request);

		if (!StrKit.isBlank(notifyDataStr)) {
			try {
				JsonUtils.iteraJsonToMap(notifyDataStr, map);
			} catch (Exception e) {
				map = new HashMap();
			}
		}
		/**url传参*/
		if (map == null || map.size() < 1) {
			String[] params = notifyDataStr.split("&");
			for (int i = 0; i < params.length; i++) {
				String[] param = params[i].split("=");
				if (param.length >= 2) {
					String key = param[0];
					String value = param[1];
					for (int j = 2; j < param.length; j++) {
						value += "=" + param[j];
					}
					map.put(key, value);
				}
			}
		}
		if (map == null || map.size() < 1) {
			Enumeration<?> temp = request.getParameterNames();
			if (null != temp) {
				while (temp.hasMoreElements()) {
					String en = (String) temp.nextElement();
					String value = request.getParameter(en);
					map.put(en, value);
					//如果字段的值为空，判断若值为空，则删除这个字段>
					if (null == map.get(en) || "".equals(map.get(en))) {
						map.remove(en);
					}
				}
			}
		}

		return notifyDataStr;
	}
}
