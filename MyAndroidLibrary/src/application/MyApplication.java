/**
 * 
 */
package application;

import android.app.Application;

/**
 * @author CSH注意事项：该MyApplication必须在清单文件中配置才能用，Application貌似利用了反射？
 * 
 */
public class MyApplication extends Application {
	private String name;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/*
	 * (non-Javadoc) 该方法是整个应用程序一运行就会首先加载的
	 * 
	 * @see android.app.Application#onCreate()
	 */
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		setName("林小妹");
	}

}
