package network;

import com.example.myandroidlibrary.R;

import com.example.myandroidlibrary.R.layout;
import com.example.myandroidlibrary.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements IAsyncTask {
	private Button button;
	private TextView textview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		button = (Button) findViewById(R.id.button1);
		textview = (TextView) findViewById(R.id.textView1);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// MainActivity.this向上转型为IShowView，不能new一个实例，如果是new一个实例该对象引用不是指向本activity了
				AsyncTaskUtils task = new AsyncTaskUtils(MainActivity.this,
						MainActivity.this);
				//这一步是利用回调函数的思想，把实现类的一个对象作为参数传递给调用程序，调用程序通过这个参数来调用指定的函数ishowView.showView(result);
				task.setAsyncTaskLisner(MainActivity.this);
				task.execute("http://www.baidu.com");
			}
		});

	}

	@Override
	public void showView(String str) {
		// TODO Auto-generated method stub
		textview.setText(str);
	}

}
