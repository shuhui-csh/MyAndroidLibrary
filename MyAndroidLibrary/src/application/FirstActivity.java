package application;

import com.example.myandroidlibrary.R;

import com.example.myandroidlibrary.R.id;
import com.example.myandroidlibrary.R.layout;

import android.os.Bundle;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FirstActivity extends Activity {

	private Button appli_button;
	private MyApplication myapplication;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first);
		appli_button = (Button) findViewById(R.id.applic_button);
		appli_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				myapplication = (MyApplication) getApplication();
				myapplication.setName("陈树辉");
				Intent intent = new Intent(FirstActivity.this,
						SecondActivity.class);
				startActivity(intent);
			}
		});
	}
}
