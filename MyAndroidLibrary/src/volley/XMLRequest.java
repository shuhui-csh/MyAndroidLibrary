/**
 * 
 */
package volley;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

/**
 * @author CSH 2015-7-20
 */
public class XMLRequest extends Request<XmlPullParser> {
	private final Listener<XmlPullParser> mListener;

	/**
	 * Creates a new request with the given method.
	 * 
	 * @param method
	 *            the request {@link Method} to use
	 * @param url
	 *            URL to fetch the string at
	 * @param listener
	 *            Listener to receive the String response
	 * @param errorListener
	 *            Error listener, or null to ignore errors
	 */
	public XMLRequest(int method, String url, Listener<XmlPullParser> listener,
			ErrorListener errorListener) {
		super(method, url, errorListener);
		mListener = listener;
	}

	/**
	 * Creates a new GET request.
	 * 
	 * @param url
	 *            URL to fetch the string at
	 * @param listener
	 *            Listener to receive the String response
	 * @param errorListener
	 *            Error listener, or null to ignore errors
	 */
	public XMLRequest(String url, Listener<XmlPullParser> listener,
			ErrorListener errorListener) {
		this(Method.GET, url, listener, errorListener);
	}

	@Override
	protected Response<XmlPullParser> parseNetworkResponse(
			NetworkResponse response) {
		// TODO Auto-generated method stub
		try {
			String xmlString = new String(response.data,
					HttpHeaderParser.parseCharset(response.headers));
			XmlPullParserFactory xmlFactory = XmlPullParserFactory
					.newInstance();
			XmlPullParser xmlPullParser = xmlFactory.newPullParser();
			xmlPullParser.setInput(new StringReader(xmlString));
			return Response.success(xmlPullParser,
					HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.error(new ParseError(e));
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			return Response.error(new ParseError(e));
		}

	}

	@Override
	protected void deliverResponse(XmlPullParser response) {
		// TODO Auto-generated method stub
		mListener.onResponse(response);
	}

}
