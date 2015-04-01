/**
 * 
 */
package service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * @author csh 2015年3月31日
 */
public class SimpleService extends Service {

	/*
	 * (non-Javadoc) 必须实现的方法
	 * 
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)service被创建时回调该方法
	 * 
	 * @see android.app.Service#onCreate()
	 */
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		System.out.println("调用onCreate();Service is created....");
		System.out.println("SimpleService的线程ID："
				+ Thread.currentThread().getId());
		super.onCreate();
	}

	/*
	 * (non-Javadoc)service 被启动时回调该方法
	 * 
	 * @see android.app.Service#onStartCommand(android.content.Intent, int, int)
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		System.out.println("调用onStartCommand();  service is started....");
		return super.onStartCommand(intent, flags, startId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onDestroy()
	 */
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		System.out.println("service is destoryed....,释放资源也是在ondestroy()方法里进行");
		super.onDestroy();
	}

}
