package handler;

import com.example.myandroidlibrary.R;
import com.example.myandroidlibrary.R.layout;
import com.example.myandroidlibrary.R.menu;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class HandlerActivity extends Activity {
	public Button button;
	public TextView textview;
	public EditText edittext;
	private Handler mHandler;
	HandlerThread handlerthread;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_handler);
		edittext = (EditText) findViewById(R.id.handlereditText1);
		textview = (TextView) findViewById(R.id.handlertextView);
		button = (Button) findViewById(R.id.handlerbutton);
		// 1.主线程已经初始化了一个looper对象了，因此直接创建handler即可
		// 2.创建一个handler对象来接收处理消息，并改变ui，要改变ui,handler必须在ui主线程中创建
		mHandler = new Handler() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see android.os.Handler#handleMessage(android.os.Message)
			 */
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				if (msg.what == 321) {
					Bundle bundle = msg.getData();
					String str = bundle.getString("handler");
					textview.setText(str);
				}
			}

		};
		 handlerthread = new HandlerThread(mHandler);
		handlerthread.start();

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 这里的发消息部分可新建一个线程不停地发
				
				Message msg = new Message();
				msg.what = 123;
				Bundle bundle = new Bundle();
				bundle.putString("handler", edittext.getText().toString());
				msg.setData(bundle);
				// 发送消息的handler必须和接收消息的同一个
				handlerthread.mmHandler.sendMessage(msg);
			}
		});
	}

}
