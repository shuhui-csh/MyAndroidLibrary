package volley;

import com.android.volley.RequestQueue;
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

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 3.将StringRequest对象添加到RequestQueue里面
				//mQuene.add(stringRequest);
				mQuene.add(imageRequest);
			}
		});

	}

}
