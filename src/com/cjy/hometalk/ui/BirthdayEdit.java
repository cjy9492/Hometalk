package com.cjy.hometalk.ui;

import com.cjy.hometalk.R;
import com.cjy.hometalk.db.BirthdayDbAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BirthdayEdit extends Activity {
	private EditText mTitleText;
	private EditText mBodyText;
	private EditText mBodyDate;
	private Long mRowId;
	private BirthdayDbAdapter mDbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDbHelper = new BirthdayDbAdapter(this);
		mDbHelper.open();
		setContentView(R.layout.birthday_edit);

		mTitleText = (EditText) findViewById(R.id.birther_guanxi);
		mBodyText = (EditText) findViewById(R.id.birther_name);
		mBodyDate = (EditText) findViewById(R.id.birther_date);
		Button confirmButton = (Button) findViewById(R.id.birtherday_edit_add);
		Button cancelButton = (Button) findViewById(R.id.birtherday_edit_cancel);

		mRowId = null;

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			String title = extras.getString(BirthdayDbAdapter.KEY_TITLE);
			String body = extras.getString(BirthdayDbAdapter.KEY_BODY);
			String date = extras.getString(BirthdayDbAdapter.KEY_CREATED);
			mRowId = extras.getLong(BirthdayDbAdapter.KEY_ROWID);

			if (title != null) {
				mTitleText.setText(title);
			}
			if (body != null) {
				mBodyText.setText(body);
			}
			if (date != null) {
				mBodyDate.setText(date);
			}
		}
		final Toast t = Toast.makeText(this, "关系不能为空", Toast.LENGTH_LONG);
		final Intent i = new Intent(this, BirthdayActivity.class);
		confirmButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				String title = mTitleText.getText().toString();
				String body = mBodyText.getText().toString();
				String date = mBodyDate.getText().toString();
				if(title.isEmpty()){
					
                     t.show();
                     startActivity(i);
                     finish();
                     
				}
				else{
				if (mRowId != null) {
					mDbHelper.updateDiary(mRowId, title, body,date);
				} else
					mDbHelper.createDiary(title, body,date);
	
				startActivity(i);
				finish();
			}}
		});
		cancelButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				startActivity(i);
				finish();
			}
		});
	}
}
