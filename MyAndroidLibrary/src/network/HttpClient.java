package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.net.ParseException;
import android.util.Log;
import android.widget.Toast;

public class HttpClient {
	private static DefaultHttpClient HttpClient = null;
	private static HttpEntity urlEncoded;
	private static String tag = "HttpClient";
	private static HttpEntity entity;
	private static InputStream in = null;
	private static String resul = null;

	private HttpClient() {
	};

	/**
	 * 以下有两个同名的静态方法HttpClientPost();参数列表不同。这是方法重载的一种。
	 */
	/**
	 * HttpClient post方法
	 * 
	 * @param url
	 * @param List
	 *            <NameValuePair>
	 * @return
	 */

	public static String HttpClientPost(Context context, String url_path,
			List<NameValuePair> params) {

		try {

			/* 创建一个HttpPost对象 */
			HttpPost httpPost = new HttpPost(url_path);
			/* 设置请求头，服务器接收的内容类型Content-Type,经测试登录可以不用设置 */

			httpPost.setHeader("Content-Type",
					"application/x-www-form-urlencoded");

			/* 为此params请求参数设置编码格式 */
			urlEncoded = new UrlEncodedFormEntity(params, HTTP.UTF_8);
			/* 为HttpPost对象设置请求参数urlEncoded */
			httpPost.setEntity(urlEncoded);
			/* 创建一个自己设置各种参数的DefaultHttpClient实例 */
			DefaultHttpClient client = getHttpClient(context);
			Log.i(tag, "有没有进行网络连接啊0");
			/* DefaultHttpClient实例调用execute()方法执行连接请求操作 */
			HttpResponse response = client.execute(httpPost);
			entity = response.getEntity();
			if (entity != null) {
				in = entity.getContent(); // 之前没使用这个造成了大量异常抛出，只要是
			} 
			/* HttpStatus.SC_OK代表http请求码200，代表请求成功的状态码 */
			Log.i(tag, "有没有进行网络连接啊1");

			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				Log.i(tag, "有没有进行网络连接啊2");

				return String.valueOf(response.getStatusLine().getStatusCode());

			} else {
				/* 如果请求成功，即状态码为HttpStatus.SC_OK（200），则返回应答里面的内容 */
				Log.i(tag, "有没有进行网络连接啊3");
				/*
				 * return EntityUtils.toString( entity, HTTP.UTF_8);
				 */
				InputStreamReader inReader = new InputStreamReader(in);
				BufferedReader buffer = new BufferedReader(inReader);
				StringBuffer stringBuffer = new StringBuffer();
				String strLine = "";
				Log.i(tag, "我有没有转换流啊");
				while ((strLine = buffer.readLine()) != null) {
					stringBuffer.append(strLine);
				}
				/* inReader.close(); */
				return stringBuffer.toString();
				/* return in.toString(); */
			}

		} catch (ConnectTimeoutException e) {
			e.printStackTrace();
			return "连接超时";
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
			return "请求超时";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "异常：不支持的编码";
		} catch (ClientProtocolException e) {
			/* 客户端提交给服务器的请求，不符合HTTP协议 */
			e.printStackTrace();
			return null;
		} catch (ParseException e) {
			/* 语法、格式方面上的异常 */
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (entity != null) {
				try {
					in.close();
				} catch (IOException e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}

		}
	}

	/**
	 * HttpClient post方法
	 * 
	 * @param url
	 * @return
	 */
	public static String HttpClientPost(Context context, String url_path) {
		try {
			/* 创建一个HttpPost对象 */
			HttpPost httpPost = new HttpPost(url_path);
			/* 设置请求头，服务器接收的内容类型Content-Type */
			/*
			 * httpPost.setHeader("Content-Type",
			 * "application/json;charset=UTF-8");
			 */
			/* 创建一个自己设置各种参数的DefaultHttpClient实例 */
			DefaultHttpClient client = getHttpClient(context);
			/* DefaultHttpClient实例调用execute()方法执行连接请求操作 */
			HttpResponse response = client.execute(httpPost);
			entity = response.getEntity();
			if (entity != null) {
				in = entity.getContent(); // 之前没使用这个造成了大量异常抛出，只要是
			}
			/* HttpStatus.SC_OK代表http请求码200，代表请求成功的状态码 */
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				return String.valueOf(response.getStatusLine().getStatusCode());
			} else {
				/*
				 * 如果请求成功，则返回应答里面的内容
				 * 这里请求成功不应该返回服务器给我的jason数据吗？，应答里面的内容是String类型的？
				 */
				/*
				 * HttpEntity entity = response.getEntity(); return (entity ==
				 * null) ? "没有返回数据啊！！" : EntityUtils.toString( entity,
				 * HTTP.UTF_8);
				 */
				InputStreamReader inReader = new InputStreamReader(in);
				BufferedReader buffer = new BufferedReader(inReader);
				StringBuffer stringBuffer = new StringBuffer();
				String strLine = "";
				Log.i(tag, "我有没有转换流啊");
				while ((strLine = buffer.readLine()) != null) {
					stringBuffer.append(strLine);
				}
				/* inReader.close(); */
				return stringBuffer.toString();
			}

		} catch (ConnectTimeoutException e) {
			e.printStackTrace();
			return "连接超时";
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
			return "请求超时";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (entity != null) {
				try {
					in.close();
				} catch (IOException e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}

		}

	}

	/**
	 * Get请求
	 * 
	 * @param url
	 * @param List
	 *            <NameValuePair> params
	 * @return
	 */

	public static String HttpClientGet(Context context, String url_path,
			List<NameValuePair> params) {
		try {
			/* URL url = new URL(url_path); */
			/* 创建一个HttpGet对象 */
			HttpGet httpget = new HttpGet(url_path);
			/* 设置请求头，服务器接收的内容类型Content-Type */
			httpget.setHeader("Content-Type",
					"application/x-www-form-urlencoded");
			// 设置请求参数params的编码方式，get方法的请求参数是跟在url后面
			String str = EntityUtils.toString(new UrlEncodedFormEntity(params,
					HTTP.UTF_8));
			httpget.setURI(new URI(httpget.getURI().toString() + "?" + str));
			/* 创建一个自己设置各种参数的DefaultHttpClient实例 */
			DefaultHttpClient httpclient = getHttpClient(context);
			/* DefaultHttpClient实例调用execute()方法执行连接请求操作 */
			HttpResponse httpresponse = httpclient.execute(httpget);
			/* 如果请求不成功 */
			if (httpresponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				throw new RuntimeException("HttpClientGet可以操作，但连接失败");
			}
			/* 如果请求成功，则返回应答里面的内容 */
			HttpEntity entity = httpresponse.getEntity();
			return EntityUtils.toString(entity);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 创建自己设置一些参数的httpClient实例
	 * 
	 * @return DefaultHttpClient
	 * @throws Exception
	 */
	private static synchronized DefaultHttpClient getHttpClient(Context context) {
		/* 这里也运用了单例模式，我们的应用程序使用同一个HttpClient来管理所有的Http请求 */
		if (null == HttpClient) {
			HttpParams params = new BasicHttpParams();
			// 设置一些基本参数
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
			HttpProtocolParams.setUseExpectContinue(params, true);
			HttpProtocolParams
					.setUserAgent(
							params,
							"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
									+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
			// 设置最大连接数
			ConnManagerParams.setMaxTotalConnections(params, 600);
			// 超时设置
			/* 这定义了从ConnectionManager管理的连接池中取出连接的超时时间，此处设置为1秒 */
			ConnManagerParams.setTimeout(params, 1000);
			/*
			 * 这定义了通过网络与服务器建立连接的超时时间。Httpclient包中通过一个异步线程去创建与服务器的socket连接，
			 * 这就是该socket连接的超时时间，此处设置为2秒。
			 */
			int ConnectionTimeOut = 2000;
			/*
			 * if (!HttpUtils.isWifiDataEnable(context)) { ConnectionTimeOut =
			 * 10000; }
			 */
			HttpConnectionParams
					.setConnectionTimeout(params, ConnectionTimeOut);
			/* 这定义了Socket读数据的超时时间，即从服务器获取响应数据需要等待的时间，此处设置为4秒。 */
			HttpConnectionParams.setSoTimeout(params, 4000);
			/*
			 * 以上3种超时分别会抛出ConnectionPoolTimeoutException,
			 * ConnectionTimeoutException与SocketTimeoutException
			 */
			// 设置我们的HttpClient支持HTTP和HTTPS两种模式
			SchemeRegistry schReg = new SchemeRegistry();
			schReg.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			schReg.register(new Scheme("https", SSLSocketFactory
					.getSocketFactory(), 443));

			// 使用线程安全的连接管理来创建HttpClient
			ClientConnectionManager conMgr = new ThreadSafeClientConnManager(
					params, schReg);
			HttpClient = new DefaultHttpClient(conMgr, params);
		}
		return HttpClient;
	}

}
