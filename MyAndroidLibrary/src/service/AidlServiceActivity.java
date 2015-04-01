package service;

import com.example.myandroidlibrary.R;
import com.example.myandroidlibrary.R.id;
import com.example.myandroidlibrary.R.layout;
import com.example.myandroidlibrary.R.menu;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * @author csh 这个activity假设为另一个应用的activity
 *
 */
public class AidlServiceActivity extends Activity {
	private ICat catService;
	private Button bindAidlService, getAidlState;

	private ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			catService = null;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			// 获取远程service的OnBind方法返回的对象的代理
			catService = ICat.Stub.asInterface(service);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aidl_service);

		bindAidlService = (Button) findViewById(R.id.bindAidlService);
		getAidlState = (Button) findViewById(R.id.getAidlState);

		final Intent intent = new Intent();
		intent.setAction("service.AIDL_SERVICE");

		bindAidlService.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 绑定远程service
				bindService(intent, conn, Service.BIND_AUTO_CREATE);
			}
		});

		getAidlState.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					Toast.makeText(
							getApplicationContext(),
							"猫的颜色为：" + catService.getColor() + "  体重为："
									+ catService.getWeight(),
							Toast.LENGTH_SHORT).show();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// activity销毁之前解除远程service绑定
		this.unbindService(conn);
		System.out.println("onDestroy..在这里解除了与service的绑定");
	}

}
