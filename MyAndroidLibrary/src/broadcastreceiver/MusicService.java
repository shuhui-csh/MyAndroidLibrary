/**
 * 
 */
package broadcastreceiver;

import java.io.IOException;
import java.sql.PreparedStatement;

import service.BindServiceActivity;

import com.example.myandroidlibrary.R;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.IBinder;

/**
 * @author CSH 2015-4-3 该次学习的过程中主要出现以下的几点的问题：
 *         1.MusicService忘记在清单文件中注册了，可以分显式和隐式启动
 *         2、broadcastReceive如果在activity和service组件代码里面动态注册了就不在需要再在清单里面再注册了
 */
public class MusicService extends Service {
	ServiceReceive serviceReceive;
	AssetManager assetam;
	String[] musics = new String[] { "nw.mp3", "th.mp3", "hbp.mp3" };
	MediaPlayer mPlayer;
	int current = 0;
	// 当前的状态，0x11代表没有播放，0X12代表正在播放，0x13代表暂停
	private int status = 0x11;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub

		assetam = getAssets();
		serviceReceive = new ServiceReceive();
		IntentFilter filter = new IntentFilter();
		// 设置本service中广播接收器监听的action为：broadcast.action.UPDATE_ACTION
		filter.addAction(MusicServiceBroadReceiveActivity.CTL_ACTION);
		registerReceiver(serviceReceive, filter);
		// 创建mediaplayer
		mPlayer = new MediaPlayer();
		// 为mplayer播放完成事件绑定监听器
		mPlayer.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				current++;
				if (current >= 3) {
					current = 0;
				}
				// 发送广播通知activity更改文本框
				Intent uiIntent = new Intent(
						MusicServiceBroadReceiveActivity.UPDATE_ACTION);
				uiIntent.putExtra("current", current);
				// 发送广播，将被Activity组件中的Broadcastreceive接收到
				sendBroadcast(uiIntent);
				// 准备播放下一首歌曲
				prepareAndPlay(musics[current]);
				// 这时更改通知栏上的信息
				showInForeword(current);
			}
		});
		super.onCreate();
		System.out.println("service onCreat()......");
	}

	public class ServiceReceive extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			int control = intent.getIntExtra("control", -1);
			switch (control) {
			// 播放或暂停
			case 1:
				// 原来处于没有播放状态
				if (status == 0x11) {
					// 准备并播放音乐
					prepareAndPlay(musics[current]);
					status = 0x12;
				}
				// 原来处于播放状态
				else if (status == 0x12) {
					// 暂停
					mPlayer.pause();
					// 改为暂停状态
					status = 0x13;
				}
				// 原来处于暂停状态
				else if (status == 0x13) {
					// 播放
					mPlayer.start();
					// 改为播放状态
					status = 0x12;
				}
				break;
			case 2:
				// 如果原来正处在播放或暂停状态
				if (status == 0x12 || status == 0x13) {
					// 停止播放
					mPlayer.stop();
					status = 0x11;
				}
				break;
			case 3:
				current++;
				if (current >= 3) {
					current = 0;
				}
				// 先停止播放
				mPlayer.stop();
				// 准备并播放下一首音乐
				prepareAndPlay(musics[current]);
				status = 0x12;
			}
			Intent uiIntent = new Intent(
					MusicServiceBroadReceiveActivity.UPDATE_ACTION);
			uiIntent.putExtra("update", status);
			uiIntent.putExtra("current", current);
			// 发送广播，将被Activity组件中的Broadcastreceive接收到
			sendBroadcast(uiIntent);
			showInForeword(current);
		}
	}

	/**
	 * @param string
	 */
	private void prepareAndPlay(String music) {
		// TODO Auto-generated method stub
		try {
			// 打开assets文件中指定的文件
			AssetFileDescriptor afd = assetam.openFd(music);
			mPlayer.reset();
			// 使用MediaPlayer加载指定的音乐文件
			mPlayer.setDataSource(afd.getFileDescriptor(),
					afd.getStartOffset(), afd.getLength());
			// 准备声音
			mPlayer.prepare();
			// 播放
			mPlayer.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param currentt
	 *            使该服务成为前台服务，即在通知栏上显示出来
	 */
	public void showInForeword(int currentt) {
		Notification notification = new Notification(R.drawable.ic_launcher,
				"后台音乐", System.currentTimeMillis());
		Intent intent = new Intent(this, MusicServiceBroadReceiveActivity.class);
		PendingIntent pendIntent = PendingIntent
				.getActivity(this, 0, intent, 0);
		notification.setLatestEventInfo(this,
				MusicServiceBroadReceiveActivity.titles[currentt],
				MusicServiceBroadReceiveActivity.singers[currentt], pendIntent);
		startForeground(1, notification);
	}

}
