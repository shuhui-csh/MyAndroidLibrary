/**
 * 
 */
package handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.TextView;

/**
 * @author CSH
 * 
 */
public class HandlerThread extends Thread {
	public Handler uihandler;
	public Handler mmHandler;

	/**
	 * @param textview
	 */
	public HandlerThread(Handler uihandler) {
		super();
		this.uihandler = uihandler;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		// 1.子线程中调用looper的静态方法创建一个looper对象，与此同时对应的messagequeue也相应地在looper的私有构造方法里创建了
		Looper.prepare();
		// 2.创建一个handler对象来接收处理消息，并改变ui
		mmHandler = new Handler() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see android.os.Handler#handleMessage(android.os.Message)
			 */
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				if (msg.what == 123) {
					Bundle bundle = msg.getData();
					String str = bundle.getString("handler");
					Message mmsg = new Message();
					mmsg.setData(bundle);
					mmsg.what = 321;
					uihandler.sendMessage(mmsg);
				}
			}

		};
		// 3.调用loop方法启动looper对象不断地取出messagequeue中的消息
		Looper.loop();
	}

}
