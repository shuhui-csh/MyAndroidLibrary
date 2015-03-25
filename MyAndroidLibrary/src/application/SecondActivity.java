package application;

import com.example.myandroidlibrary.R;
import com.example.myandroidlibrary.R.id;
import com.example.myandroidlibrary.R.layout;

import android.os.Bundle;
import android.app.Activity;
import android.app.Application;
import android.view.Menu;
import android.widget.TextView;

public class SecondActivity extends Activity {
	private TextView appli_text;
	private MyApplication myapplication;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		appli_text = (TextView) findViewById(R.id.appli_text);
		myapplication = (MyApplication) getApplication();
		appli_text.setText(myapplication.getName());
	}

}
