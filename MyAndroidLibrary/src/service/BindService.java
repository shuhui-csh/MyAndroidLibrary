/**
 * 
 */
package service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/**
 * @author csh 2015年4月1日
 */
public class BindService extends Service {
	private int count;
	private boolean quit = false;
	private MyBinder binder = new MyBinder();

	// binder类一般作为内部类存在，方便访问外部类service的各种变量
	public class MyBinder extends Binder {
		public int getCount() {
			// 返回service的运行状态
			return count;
		}
	}

	/*
	 * (non-Javadoc) 必须实现的方法，绑定该service时回调该方法
	 * 
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		System.out.println("Service is binded");
		return binder;
	}

	// service 被断开连接时回调该方法
	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		System.out.println("service is unbinded");
		return super.onUnbind(intent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onCreate()
	 */
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		System.out.println("Service is created");
		// 启动一条线程来动态地修改count状态值
		new Thread() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				while (!quit) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					count++;
				}
			}

		}.start();
	}

	/*
	 * (non-Javadoc) 如果是用bindService来启动service的话，这个方法不会被回调
	 * 
	 * @see android.app.Service#onStartCommand(android.content.Intent, int, int)
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		System.out.println("BindService is started");
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
		super.onDestroy();
		this.quit = true;
		this.binder = null;
		System.out.println("bindService is destroyed");
	}

}
