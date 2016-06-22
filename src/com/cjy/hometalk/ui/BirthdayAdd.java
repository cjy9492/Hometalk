package com.cjy.hometalk.ui;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cjy.hometalk.R;
import com.cjy.hometalk.discovery.DiaryDbAdapter;

public class BirthdayAdd extends Activity{
	private EditText mombirthday;
	private EditText dadbirthday;
	private EditText medbirthday;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.birthday_add);

		mombirthday = (EditText) findViewById(R.id.mom_birther);
		dadbirthday = (EditText) findViewById(R.id.dad_birther);
		medbirthday = (EditText) findViewById(R.id.me_birther);	

		Button confirmButton = (Button) findViewById(R.id.birtherday_add_add);
		Button cancelButton = (Button) findViewById(R.id.birtherday_add_cancel);
		SharedPreferences sharedPreferences = this.getSharedPreferences("userinfo.txt", Context.MODE_PRIVATE);
		 String mom = sharedPreferences.getString("mombirthday", "未设置");
        String dad = sharedPreferences.getString("dadbirthday", "未设置");
        String me = sharedPreferences.getString("medbirthday", "未设置");
		final Intent intent = new Intent(this,BirthdayActivity.class);
		mombirthday.setText(mom);
		dadbirthday.setText(dad);
		medbirthday.setText(me);
		confirmButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				String mom = mombirthday.getText().toString();
				String dad = dadbirthday.getText().toString();
				String me = medbirthday.getText().toString();
				save(mom,dad,me);
				startActivity(intent);
				finish();
			}
		});

		cancelButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				startActivity(intent);
				finish();
			}
		});
	}
	public void save(String mom,String dad,String me){
		SharedPreferences sharedPreferences = this.getSharedPreferences("userinfo.txt", Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
        editor.putString("mombirthday", mom);
        editor.putString("dadbirthday", dad);
        editor.putString("medbirthday", me);
        editor.commit();
		
	}
	
}
