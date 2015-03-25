package network;

import android.content.Context;
import android.os.AsyncTask;

public class AsyncTaskUtils extends AsyncTask<String, String, String> {

	private IAsyncTask ishowView = null;
	private Context context;

	public AsyncTaskUtils(Context context, IAsyncTask ishowView) {
		this.context = context;
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
