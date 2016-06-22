package com.cjy.hometalk.discovery;



import com.cjy.hometalk.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

public class DiaryEdit extends Activity {

	private EditText mTitleText;
	private EditText mBodyText;
	private Long mRowId;
	private DiaryDbAdapter mDbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDbHelper = new DiaryDbAdapter(this);
		mDbHelper.open();
		setContentView(R.layout.discovery_edit);

		mTitleText = (EditText) findViewById(R.id.title);
		mBodyText = (EditText) findViewById(R.id.body);

		Button confirmButton = (Button) findViewById(R.id.confirm);
		Button cancelButton = (Button) findViewById(R.id.beiwang_cancel);

		mRowId = null;
		// ÿһ��intent�����һ��Bundle�͵�extras���ݡ�
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			String title = extras.getString(DiaryDbAdapter.KEY_TITLE);
			String body = extras.getString(DiaryDbAdapter.KEY_BODY);
			mRowId = extras.getLong(DiaryDbAdapter.KEY_ROWID);

			if (title != null) {
				mTitleText.setText(title);
			}
			if (body != null) {
				mBodyText.setText(body);
			}
		}
		final Toast t = Toast.makeText(this, "标题不能为空", Toast.LENGTH_LONG);
		confirmButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				String title = mTitleText.getText().toString();
				String body = mBodyText.getText().toString();
				if(title.isEmpty()){
					
                     t.show();
                     finish();
				}
				else{
				if (mRowId != null) {
					mDbHelper.updateDiary(mRowId, title, body);
				} else
					mDbHelper.createDiary(title, body);
				Intent mIntent = new Intent();
				setResult(RESULT_OK, mIntent);
				finish();
			}}
		});
		cancelButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				finish();
			}
		});
	}
}
