package service;

import com.example.myandroidlibrary.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SimpleServiceActivity extends Activity {

	private Button start;
	private Button stop;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_service);
		System.out.println("ServiceActivity的主线程id为："
				+ Thread.currentThread().getId());
		start = (Button) findViewById(R.id.startService);
		stop = (Button) findViewById(R.id.stopService);
		// 创建启动service的intent
		final Intent intent = new Intent();
		intent.setAction("service.SIMPLE_SERVICE");
		start.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 启动指定的service
				System.out.println("ServiceActivity的主线程id为："
						+ Thread.currentThread().getId());
				SimpleServiceActivity.this.startService(intent);
			}
		});

		stop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 停止指定的service
				stopService(intent);
			}
		});
	}
}
