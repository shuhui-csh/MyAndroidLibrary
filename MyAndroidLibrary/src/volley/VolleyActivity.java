package volley;

import java.io.IOException;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import xmlparser.City;
import xmlparser.XmlPullParserTools;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myandroidlibrary.R;
import com.example.myandroidlibrary.R.layout;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class VolleyActivity extends Activity {
	private Button button;
	private TextView textview;
	private ImageView imageview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_volley);
		button = (Button) findViewById(R.id.volleybutton);
		textview = (TextView) findViewById(R.id.volletextView);
		imageview = (ImageView) findViewById(R.id.volleyimageView1);
		// 单线程下使用StringBuilder比StringBuffer性能会好些，因为StringBuilder不是线程安全的
		final StringBuilder responseStr = new StringBuilder();

		// 1.创建一个RequestQueue对象
		final RequestQueue mQuene = Volley.newRequestQueue(VolleyActivity.this);

		// 2.创建一个StringRequest对象。
		final StringRequest stringRequest = new StringRequest(
				"http://www.baidu.com", new Listener<String>() {

					@Override
					public void onResponse(String response) {
						// TODO Auto-generated method stub
						textview.setText(response);
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						textview.setText(error.getMessage());
					}
				});

		// 2.创建一个ImageRequest对象。
		final ImageRequest imageRequest = new ImageRequest(
				"http://developer.android.com/images/home/aw_dac.png",
				new Listener<Bitmap>() {

					@Override
					public void onResponse(Bitmap response) {
						// TODO Auto-generated method stub
						imageview.setImageBitmap(response);
					}
				}, 0, 0, Config.RGB_565, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						textview.setText(error.getMessage());
					}
				});

		final XMLRequest xmlRequest = new XMLRequest(
				"http://flash.weather.com.cn/wmaps/xml/china.xml",
				new Response.Listener<XmlPullParser>() {

					/**
					 * @param response
					 */
					@Override
					public void onResponse(XmlPullParser response) {
						List<City> list = XmlPullParserTools
								.ParserXmlSpecial(response);
						for (City city : list) {
							responseStr.append(city.toString());
						}
						textview.setText(responseStr);
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						Log.i("xmlError", error.getMessage());
					}
				});
		final GsonRequest<Weather> gsonRequest = new GsonRequest<Weather>(
				"http://www.weather.com.cn/data/sk/101010100.html",
				Weather.class, new Response.Listener<Weather>() {
					@Override
					public void onResponse(Weather response) {
						// TODO Auto-generated method stub
						WeatherInfo weatherinfo = response.getWeatherinfo();
						// StringBuffer info = new
						// StringBuffer("Gson解析出来的json:");
						responseStr.append("Gson解析出来的json:");
						responseStr.append(weatherinfo.getCity());
						responseStr.append(" ");
						responseStr.append(weatherinfo.getTemp());
						responseStr.append(" ");
						responseStr.append(weatherinfo.getTime());
						textview.setText(responseStr.append("\n"));
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub

					}
				});
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 3.将StringRequest对象添加到RequestQueue里面
				// mQuene.add(stringRequest);
				mQuene.add(imageRequest);
				mQuene.add(xmlRequest);
				mQuene.add(gsonRequest);
			}
		});

	}
}
