package network;

import android.content.Context;
import android.os.AsyncTask;

public class AsyncTaskUtils extends AsyncTask<String, String, String> {

	private IAsyncTask ishowView = null;
	private Context context;

	public AsyncTaskUtils(Context context, IAsyncTask ishowView) {
		this.context = context;
		// this.ishowView = ishowView;

	}

	// 这一步是利用回调函数的思想，把实现类的一个对象作为参数传递给调用程序，调用程序通过这个参数来调用指定的函数ishowView.showView(result);
	public void setAsyncTaskLisner(IAsyncTask ishowView) {
		this.ishowView = ishowView;
	}

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		return HttpClient.HttpClientPost(context, params[0]);
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		ishowView.showView(result);
		super.onPostExecute(result);

	}

}
