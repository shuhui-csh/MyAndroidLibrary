/**
 * 
 */
package service;

import com.example.myandroidlibrary.R;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * @author CSH 前台Service，在通知栏上显示消息
 */
public class ForegroundService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Notification notification = new Notification(R.drawable.ic_launcher,
				"前台服务有通知到来", System.currentTimeMillis());
		Intent intent = new Intent(this, BindServiceActivity.class);
		PendingIntent pendIntent = PendingIntent
				.getActivity(this, 0, intent, 0);
		notification.setLatestEventInfo(this, "前台Service通知", "前天Service通知内容",
				pendIntent);
		startForeground(1, notification);
		System.out.println("ForegroundService的onCreat()......");
	}
}
