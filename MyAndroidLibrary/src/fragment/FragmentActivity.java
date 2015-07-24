package fragment;

import com.example.myandroidlibrary.R;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class FragmentActivity extends Activity implements OnClickListener {

	/**
	 * 底部导航栏消息界面的布局
	 */
	private View message_layout;
	/**
	 * 底部导航栏联系人布局
	 */
	private View contacts_layout;
	/**
	 * 导航栏动态界面的布局
	 */
	private View news_layout;
	/**
	 * 消息图标
	 */
	private TextView messageImage;
	/**
	 * 联系人图标
	 */
	private TextView contactsImage;
	/**
	 * 动态图标
	 */
	private TextView newsImage;
	/**
	 * 消息图标对应的标题
	 */
	private TextView messageText;
	/**
	 * 联系人图标对应的标题
	 */
	private TextView contactsText;
	/**
	 * 动态图标对应的标题
	 */
	private TextView newsText;
	/**
	 * 用于对fragment进行管理的manager
	 */
	private FragmentManager fragmentManager;
	/**
	 * 消息界面fragment
	 * 
	 */
	private Fragment messageFragment;
	/**
	 * 联系人界面Fragment
	 */
	private Fragment contactsFragment;
	/**
	 * 动态界面Fragment
	 */
	private Fragment newsFragment;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment);
		initView();
		// 获取fragment管理器
		fragmentManager = getFragmentManager();
		// 初始界面选中第一个tab
		setTabSelection(0);
	}

	/**
	 * 初始化界面控件
	 */
	private void initView() {
		// TODO Auto-generated method stub
		message_layout = findViewById(R.id.message_layout);
		contacts_layout = findViewById(R.id.contacts_layout);
		news_layout = findViewById(R.id.news_layout);
		messageImage = (TextView) findViewById(R.id.message_imge);
		contactsImage = (TextView) findViewById(R.id.contacts_image);
		newsImage = (TextView) findViewById(R.id.news_image);
		messageText = (TextView) findViewById(R.id.message_text);
		contactsText = (TextView) findViewById(R.id.contacts_text);
		newsText = (TextView) findViewById(R.id.news_text);
		message_layout.setOnClickListener(this);
		contacts_layout.setOnClickListener(this);
		news_layout.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.message_layout:
			// 当点击了消息tab时，选中第1个tab
			setTabSelection(0);
			break;
		case R.id.contacts_layout:
			// 当点击了联系人tab时，选中第2个tab
			setTabSelection(1);
			break;
		case R.id.news_layout:
			// 当点击了动态tab时，选中第3个tab
			setTabSelection(2);
			break;

		default:
			break;

		}
	}

	/**
	 * @param i
	 */
	private void setTabSelection(int i) {
		// TODO Auto-generated method stub
		// 每次选中之前先清楚掉上次的选中状态
		clearSelection();
		// 开启一个Fragment事务
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
		hideFragments(transaction);
		switch (i) {
		case 0:
			// 当点击了消息tab时，改变控件的图片和文字颜色
			messageImage.setBackgroundResource(R.drawable.message_selected);
			messageText.setTextColor(Color.WHITE);
			if (messageFragment == null) {
				// 如果MessageFragment为空，则创建一个并添加到界面上
				messageFragment = new MessageFragment();
				transaction.add(R.id.content, messageFragment);
			} else {
				transaction.show(messageFragment);
			}
			break;
		case 1:
			// 当点击了联系人tab时，改变控件的图片和文字颜色
			contactsImage.setBackgroundResource(R.drawable.contacts_selected);
			contactsText.setTextColor(Color.WHITE);
			if (contactsFragment == null) {
				// 如果ContactsFragment为空，则创建一个并添加到界面上
				contactsFragment = new ContactsFragment();
				transaction.add(R.id.content, contactsFragment);
			} else {
				// 如果ContactsFragment不为空，则直接将它显示出来
				transaction.show(contactsFragment);
			}
			break;
		case 2:
			// 当点击了动态tab时，改变控件的图片和文字颜色
			newsImage.setBackgroundResource(R.drawable.news_selected);
			newsText.setTextColor(Color.WHITE);
			if (newsFragment == null) {
				// 如果NewsFragment为空，则创建一个并添加到界面上
				newsFragment = new NewsFragment();
				transaction.add(R.id.content, newsFragment);
			} else {
				// 如果NewsFragment不为空，则直接将它显示出来
				transaction.show(newsFragment);
			}
			break;
		default:
			break;
		}
		// 提交事务
		transaction.commit();
	}

	/**
	 * 将所有的Fragment都置为隐藏状态。
	 * 
	 * @param transaction
	 *            用于对Fragment执行操作的事务
	 */
	private void hideFragments(FragmentTransaction transaction) {
		// TODO Auto-generated method stub
		if (messageFragment != null) {
			transaction.hide(messageFragment);
		}
		if (contactsFragment != null) {
			transaction.hide(contactsFragment);
		}
		if (newsFragment != null) {
			transaction.hide(newsFragment);
		}
	}

	/**
	 * 清除掉所有的选中状态。
	 */
	private void clearSelection() {
		messageImage.setBackgroundResource(R.drawable.message_unselected);
		messageText.setTextColor(Color.parseColor("#82858b"));
		contactsImage.setBackgroundResource(R.drawable.contacts_unselected);
		contactsText.setTextColor(Color.parseColor("#82858b"));
		newsImage.setBackgroundResource(R.drawable.news_unselected);
		newsText.setTextColor(Color.parseColor("#82858b"));

	}

}
