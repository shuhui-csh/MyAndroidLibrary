package service;

import service.BindService.MyBinder;

import com.example.myandroidlibrary.R;
import com.example.myandroidlibrary.R.id;
import com.example.myandroidlibrary.R.menu;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class BindServiceActivity extends Activity {
	private Button bind, unbind, getServiceState;
	// 所启动的service的binder对象
	private BindService.MyBinder binder;

	private ServiceConnection conn = new ServiceConnection() {
		// 当该activity与service解除绑定成功时回调该方法
		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			System.out.println("service disconnected");
		}

		// 当该activity与service绑定成功时回调该方法
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			System.out.println("servoce is connected");
			binder = (BindService.MyBinder) service;
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bind_service);
		bind = (Button) findViewById(R.id.bindService);
		unbind = (Button) findViewById(R.id.unbind);
		getServiceState = (Button) findViewById(R.id.getState);

		final Intent intent = new Intent();
		intent.setAction("service.BIND_SERVICE");

		bind.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 绑定指定的service
				BindServiceActivity.this.bindService(intent, conn,
						Service.BIND_AUTO_CREATE);
			}
		});

		unbind.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 解除绑定的service
				unbindService(conn);
				// 解除绑定之后，binder对象仍有效哦,binder引用仍指向原来在service中就已经实例化的对象
				binder = null;
			}
		});

		getServiceState.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 通过binder对象访问service中的count值
				Toast.makeText(getApplicationContext(),
						"service的count值为" + binder.getCount(),
						Toast.LENGTH_LONG).show();
			}
		});
	}

}
