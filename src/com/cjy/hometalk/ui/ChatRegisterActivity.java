package com.cjy.hometalk.ui;

import com.easemob.EMError;
import com.easemob.chat.EMChatManager;
import com.easemob.exceptions.EaseMobException;
import com.cjy.hometalk.BaseActivity;
import com.cjy.hometalk.R;
import com.cjy.hometalk.R.id;
import com.cjy.hometalk.R.layout;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class ChatRegisterActivity extends BaseActivity {

	private EditText mUsernameET;
	private EditText mPasswordET;
	private EditText mCodeET;
	private Button mSignupBtn;
	private Handler mHandler;
	private CheckBox mPasswordCB;
	private TextView mBackTV;
	private ImageView mCodeIV;
	private String currCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat_register);

		mHandler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {
				case 1000:
					Toast.makeText(getApplicationContext(), "注册成功",
							Toast.LENGTH_SHORT).show();
					finish();
					break;
				case 1001:
					Toast.makeText(getApplicationContext(), "网络异常，请检查网络！",
							Toast.LENGTH_SHORT).show();
					finish();
					break;
				case 1002:
					Toast.makeText(getApplicationContext(), "用户已存在！",
							Toast.LENGTH_SHORT).show();
					finish();
					break;
				case 1003:
					Toast.makeText(getApplicationContext(), "注册失败，无权限",
							Toast.LENGTH_SHORT).show();
					finish();
					break;
				case 1004:
					Toast.makeText(getApplicationContext(),
							"注册失败: " + (String) msg.obj, Toast.LENGTH_SHORT)
							.show();
					finish();
					break;

				default:
					break;
				}
			};
		};

		mUsernameET = (EditText) findViewById(R.id.chat_register_username);
		mPasswordET = (EditText) findViewById(R.id.chat_register_password);
		
		mSignupBtn = (Button) findViewById(R.id.chat_register_signup_btn);
		mPasswordCB = (CheckBox) findViewById(R.id.chat_register_password_checkbox);
		mBackTV = (TextView) findViewById(R.id.chat_register_back);
		

		

		

		mBackTV.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		mPasswordCB.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if (arg1) {
					mPasswordCB.setChecked(true);
					mPasswordET
							.setTransformationMethod(HideReturnsTransformationMethod
									.getInstance());
				} else {
					mPasswordCB.setChecked(false);
					mPasswordET
							.setTransformationMethod(PasswordTransformationMethod
									.getInstance());
				}
			}
		});
		mSignupBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final String userName = mUsernameET.getText().toString().trim();
				final String password = mPasswordET.getText().toString().trim();
				

				if (TextUtils.isEmpty(userName)) {
					Toast.makeText(getApplicationContext(), "请输入用户名",
							Toast.LENGTH_SHORT).show();
				} else if (TextUtils.isEmpty(password)) {
					Toast.makeText(getApplicationContext(), "请输入密码",
							Toast.LENGTH_SHORT).show();
				}   else {
					new Thread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							try {
								// 调用sdk注册方法
								EMChatManager.getInstance()
										.createAccountOnServer(userName,
												password);
								mHandler.sendEmptyMessage(1000);
							} catch (final EaseMobException e) {
								// 注册失败
								Log.i("TAG", "getErrorCode:" + e.getErrorCode());
								int errorCode = e.getErrorCode();
								if (errorCode == EMError.NONETWORK_ERROR) {
									mHandler.sendEmptyMessage(1001);
								} else if (errorCode == EMError.USER_ALREADY_EXISTS) {
									mHandler.sendEmptyMessage(1002);
								} else if (errorCode == EMError.UNAUTHORIZED) {
									mHandler.sendEmptyMessage(1003);
								} else {
									Message msg = Message.obtain();
									msg.what = 1004;
									msg.obj = e.getMessage();
									mHandler.sendMessage(msg);
								}
							}
						}
					}).start();
				}
			}
		});
	}
}
