package broadcastreceiver;

import com.example.myandroidlibrary.R;
import com.example.myandroidlibrary.R.layout;
import com.example.myandroidlibrary.R.menu;

import android.R.string;
import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author CSH 2015-4-3
 */
public class MusicServiceBroadReceiveActivity extends Activity implements
		OnClickListener {
	// 歌曲标题、歌手文本框
	private TextView title, singer;
	// 播放、暂停按钮，
	private Button stop, play, next;
	// 定义音乐的播放状态，0x11代表没有播放，0X12代表正在播放，0x13代表暂停
	private int status = 0x11;
	private BroadcastReceiveService receiver;
	public static String[] titles = new String[] { "你我", "桐花", "黑白配" };
	public static String[] singers = new String[] { "陈晓、妍希", "钟欣桐", "林小妹" };
	// 这是后台service中的广播接收器所监听的action
	public static final String CTL_ACTION = "broadcast.action.CTL_ACTION";
	// 这是前台activity中的广播接收器所监听的action
	public static final String UPDATE_ACTION = "broadcast.action.UPDATE_ACTION";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_music_service_broad_receive);
		title = (TextView) findViewById(R.id.title);
		singer = (TextView) findViewById(R.id.singer);
		stop = (Button) findViewById(R.id.stop);
		play = (Button) findViewById(R.id.play);
		next = (Button) findViewById(R.id.next);
		play.setOnClickListener(this);
		stop.setOnClickListener(this);
		next.setOnClickListener(this);

		receiver = new BroadcastReceiveService();
		IntentFilter filter = new IntentFilter();
		// 设置本activity中广播接收器监听的action为：broadcast.action.UPDATE_ACTION
		filter.addAction(UPDATE_ACTION);
		// 为本activity 动态注册下面内部类的BroadcastReceiveService
		registerReceiver(receiver, filter);
		System.out.println("activity  oncreat()...1");
		// 启动后台音乐服务
		Intent intent = new Intent(this, MusicService.class);
		startService(intent);
		System.out.println("activity  oncreat()...2");
	}

	public class BroadcastReceiveService extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			// 获取intent中的update消息，update代表播放状态
			int update = intent.getIntExtra("update", -1);
			// 获取intent中的current消息，current代表当前正在播放的歌曲
			int current = intent.getIntExtra("current", -1);
			if (current >= 0) {
				title.setText(titles[current]);
				singer.setText(singers[current]);
			}
			switch (update) {
			case 0x11:
				// 没有播放的状态按钮设置为：播放
				status = 0x11;
				play.setText("播放");
				break;
			case 0x12:
				// 播放状态中按钮设置为：暂停
				play.setText("暂停");
				status = 0x12;
				break;
			case 0X13:
				// 暂停状态中按钮设置为：播放
				play.setText("播放");
				status = 0x13;
				break;
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent("broadcast.action.CTL_ACTION");
		switch (v.getId()) {
		case R.id.play:
			intent.putExtra("control", 1);
			System.out.println("单击播放按钮了");
			break;
		case R.id.stop:
			intent.putExtra("control", 2);
			break;
		case R.id.next:
			intent.putExtra("control", 3);
		}
		// 发送广播，将被Service组件中的BroadcastReceive接收到
		sendBroadcast(intent);
	}
}
