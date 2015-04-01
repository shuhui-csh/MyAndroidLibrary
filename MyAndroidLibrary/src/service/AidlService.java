/**
 * 
 */
package service;

import java.util.Timer;
import java.util.TimerTask;

import service.ICat.Stub;
import android.R.string;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

/**
 * @author csh
 *
 */
public class AidlService extends Service {

	private CatBinder catBinder;
	Timer timer = new Timer();
	String[] colors = new String[] { "红色", "黄色", "黑色" };
	double[] weights = new double[] { 2.3, 3.1, 1.58 };
	private String color;
	private double weight;

	/**
	 * @author csh 这个类是ICat.java中的一个抽象类
	 *
	 */
	public class CatBinder extends Stub {

		@Override
		public String getColor() throws RemoteException {
			// TODO Auto-generated method stub
			return color;
		}

		@Override
		public double getWeight() throws RemoteException {
			// TODO Auto-generated method stub
			return weight;
		}

	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		// 创建一个binder对象，以给返回给远程调用绑定者
		catBinder = new CatBinder();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				// 随机地改变service组件内color,weight的属性值
				int rand = (int) (Math.random() * 3);
				color = colors[rand];
				weight = weights[rand];
				System.out.println("随机数字为：" + rand);
			}
		}, 0, 800);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		timer.cancel();
		System.out.println("远程service的onDestroy..执行了，计时器也停止了");
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return catBinder;
	}

}
