package com.cjy.hometalk.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.http.util.EncodingUtils;

import com.cjy.hometalk.R;
import com.cjy.hometalk.discovery.EX056;
import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMChatOptions;
import com.nineoldandroids.view.ViewPropertyAnimator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MeFragment extends Fragment  {
	private ImageView imageView;
	private Button clickBtnLocal;
	private Button clickBtnCamera;
	private Button viewshowiut;
	private LinearLayout updateBtn;
	private LinearLayout viewclickshow;
	
	private RelativeLayout viewshow;
	private File picFile;

	private Uri photoUri;

	private final int PIC_FROM_CAMERA = 1;
	private final int PIC_FROM＿LOCALPHOTO = 0;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_me, null);
		TextView name=(TextView)view.findViewById(R.id.name);
		imageView = (ImageView) view.findViewById(R.id.image);
		clickBtnLocal = (Button) view.findViewById(R.id.click_local);
		clickBtnCamera = (Button) view.findViewById(R.id.click_camera);
		viewclickshow=(LinearLayout) view.findViewById(R.id.ll_user);
		viewshow =(RelativeLayout) view.findViewById(R.id.camera_view);
		viewshowiut = (Button) view.findViewById(R.id.click_cancel);
		updateBtn = (LinearLayout) view.findViewById(R.id.me_update);
		name.setText(EMChatManager.getInstance().getCurrentUser());
		//打开时显示头像
		File pictureFileDir = new File(Environment.getExternalStorageDirectory(), "/upload");
		if (!pictureFileDir.exists()) {
			pictureFileDir.mkdirs();
		}
		picFile = new File(pictureFileDir, "upload.jpeg");
		if (!picFile.exists()) {
			try {
				picFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		photoUri = Uri.fromFile(picFile);
		if (photoUri != null) 
		{
			Bitmap bitmap = decodeUriAsBitmap(photoUri);
			imageView.setImageBitmap(bitmap);
		}
		
		
		
		final EMChatOptions chatOptions = EMChatManager.getInstance().getChatOptions();
		chatOptions.setUseSpeaker(true);

		 
		//登出操作
		Button me_lgout = (Button)view.findViewById(R.id.me_logout);
		me_lgout.setOnClickListener(new OnClickListener(){		  
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showLogoutDialog();
			}
		});
		//弹出窗口
		imageView.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{   
				ViewPropertyAnimator.animate(viewshow).translationY(50);
			}
		});
		//弹回窗口
		viewshowiut.setOnClickListener(new View.OnClickListener() 
				{
					@Override
					public void onClick(View v) 
					{   
						ViewPropertyAnimator.animate(viewshow).translationY(-300);
					}
				});
		//本地选择
		clickBtnLocal.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				doHandlerPhoto(PIC_FROM＿LOCALPHOTO);// 从相册中去获取
			}
		});

		//拍照
		clickBtnCamera.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				doHandlerPhoto(PIC_FROM_CAMERA);// 用户点击了从照相机获取
			}
		});
		//更新
		updateBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				Toast.makeText(getActivity(), R.string.soft_update_no, Toast.LENGTH_LONG).show();

			}
		});
		updateBtn.setOnLongClickListener(new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View arg0) {
				
				showNoticeDialog();
				return false;
			}
		});
	
		//是否启用新消息提醒
		ToggleButton mTogBtn = (ToggleButton)view.findViewById(R.id.toggle);
		mTogBtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {  
			  
		    @Override  
		    public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {  
		        // TODO Auto-generated method stub  
		        if(isChecked){  
		            //选中  
		        	chatOptions.setNotifyBySoundAndVibrate(true);
		        	System.out.println(1);
		        }else{  
		            //未选中 
		        	chatOptions.setNotifyBySoundAndVibrate(false);
		        	System.out.println(2);
		        }  
		    }  
		});
		//是否启用声音提醒
		ToggleButton mTogBtn1 = (ToggleButton)view.findViewById(R.id.toggle1);
		mTogBtn1.setOnCheckedChangeListener(new OnCheckedChangeListener() {  
			  
		    @Override  
		    public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {  
		        // TODO Auto-generated method stub  
		        if(isChecked){  
		            //选中  
		        	chatOptions.setNoticeBySound(true);
		        }else{  
		            //未选中 
		        	chatOptions.setNoticeBySound(false);
		        }  
		    }  
		});
		//是否启用震动提醒
		ToggleButton mTogBtn2 = (ToggleButton)view.findViewById(R.id.toggle2);
		mTogBtn2.setOnCheckedChangeListener(new OnCheckedChangeListener() {  
			  
		    @Override  
		    public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {  
		        // TODO Auto-generated method stub  
		        if(isChecked){  
		            //选中  
		        	chatOptions.setNoticedByVibrate(true);
		        }else{  
		            //未选中 
		        	chatOptions.setNoticedByVibrate(false);
		        }  
		    }  
		});
		//是否通过通知栏提示
		ToggleButton mTogBtn3 = (ToggleButton)view.findViewById(R.id.toggle3);
		mTogBtn3.setOnCheckedChangeListener(new OnCheckedChangeListener() {  
			  
		    @Override  
		    public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {  
		        // TODO Auto-generated method stub  
		        if(isChecked){  
		            //选中  
		        	chatOptions.setShowNotificationInBackgroud(true);
		        }else{  
		            //未选中 
		        	chatOptions.setShowNotificationInBackgroud(false);
		        }  
		    }  
		});
		return view; 
	}
	private void showLogoutDialog() {
		new AlertDialog.Builder(getActivity())
				.setTitle("应用提示")
				.setMessage(
						"确定要注销" + EMChatManager.getInstance().getCurrentUser()
								+ "用户吗？")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// EMChatManager.getInstance().logout();
						logout(new EMCallBack() {

							@Override
							public void onSuccess() {
								// TODO Auto-generated method stub
								startActivity(new Intent(getActivity(),
										ChatLoginActivity.class));
							}

							@Override
							public void onProgress(int arg0, String arg1) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onError(int arg0, String arg1) {
								// TODO Auto-generated method stub

							}
						});

					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
					}
				}).show();
	}
	public void logout(final EMCallBack callback) {
		// setPassword(null);
		EMChatManager.getInstance().logout(new EMCallBack() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				if (callback != null) {
					callback.onSuccess();
				}
			}

			@Override
			public void onError(int code, String message) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgress(int progress, String status) {
				// TODO Auto-generated method stub
				if (callback != null) {
					callback.onProgress(progress, status);
				}
			}

		});
	}
	/**
	 * 根据不同方式选择图片设置ImageView
	 * @param type 0-本地相册选择，非0为拍照
	 */
	private void doHandlerPhoto(int type)
	{
		try
		{
			//保存裁剪后的图片文件
			File pictureFileDir = new File(Environment.getExternalStorageDirectory(), "/upload");
			if (!pictureFileDir.exists()) {
				pictureFileDir.mkdirs();
			}
			picFile = new File(pictureFileDir, "upload.jpeg");
			if (!picFile.exists()) {
				picFile.createNewFile();
			}
			photoUri = Uri.fromFile(picFile);
			
			if (type==PIC_FROM＿LOCALPHOTO)
			{
				Intent intent = getCropImageIntent();
				startActivityForResult(intent, PIC_FROM＿LOCALPHOTO);
			}else
			{
				Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
				startActivityForResult(cameraIntent, PIC_FROM_CAMERA);
			}

		} catch (Exception e)
		{
			e.printStackTrace();
			Log.i("HandlerPicError", "处理图片出现错误");
		}
	}

	/**
	 * 调用图片剪辑程序
	 */
	public Intent getCropImageIntent() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.setType("image/*");
		setIntentParams(intent);
		return intent;
	}

	/**
	 * 启动裁剪
	 */
	private void cropImageUriByTakePhoto() {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(photoUri, "image/*");
		setIntentParams(intent);
		startActivityForResult(intent, PIC_FROM＿LOCALPHOTO);
	}

	/**
	 * 设置公用参数
	 */
	private void setIntentParams(Intent intent)
	{
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 600);
		intent.putExtra("outputY", 600);
		intent.putExtra("noFaceDetection", true); // no face detection
		intent.putExtra("scale", true);
		intent.putExtra("return-data", false);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		switch (requestCode)
		{
		case PIC_FROM_CAMERA: // 拍照
			try 
			{
				cropImageUriByTakePhoto();
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
			break;
		case PIC_FROM＿LOCALPHOTO:
			try
			{
				if (photoUri != null) 
				{
					Bitmap bitmap = decodeUriAsBitmap(photoUri);
					imageView.setImageBitmap(bitmap);
				}
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
			break;
		}
	}

	private Bitmap decodeUriAsBitmap(Uri uri)
	{
		Bitmap bitmap = null;
		try 
		{
			bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri));
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
			return null;
		}
		return bitmap;
	}
	public void update(){
		Uri uri =Uri.parse("http://1.hometalk1.applinzi.com/HomeTalk.apk");
		Intent it = new Intent(Intent.ACTION_VIEW,uri); 
		startActivity(it); 

	}
/*	private void showNoticeDialog()
	{
		// 构造对话框
		AlertDialog.Builder builder = new Builder(getActivity());
		builder.setTitle(R.string.soft_update_title);
		builder.setMessage(R.string.soft_update_info);
		// 更新.
		builder.setPositiveButton(R.string.soft_update_updatebtn, this);
		// 稍后更新
		builder.setNegativeButton(R.string.soft_update_later, this);
		Dialog noticeDialog = builder.create();
		noticeDialog.show();
	}
	@Override
	public void onClick(DialogInterface dialog, int arg1) {
		
		dialog.dismiss();
		update();
	} */
	private void showNoticeDialog() {
		new AlertDialog.Builder(getActivity())
				.setTitle("应用提示")
				.setMessage(
						"检测到新版本是否更新？")
				.setPositiveButton("更新", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// EMChatManager.getInstance().logout();
						//isUpdate();
						update();
				

					}
				})
				.setNegativeButton("稍后更新", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
					}
				}).show();
	}
	/*
	private boolean isUpdate()
	{
		// 获取当前软件版本
		int versionCode = getVersionCode(mContext);
		// 把version.xml放到网络上，然后获取文件信息
		InputStream inStream = ParseXmlService.class.getClassLoader().getResourceAsStream("version.xml");
		// 解析XML文件。 由于XML文件比较小，因此使用DOM方式进行解析
		ParseXmlService service = new ParseXmlService();
		try
		{
			mHashMap = service.parseXml(inStream);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		if (null != mHashMap)
		{
			int serviceCode = Integer.valueOf(mHashMap.get("version"));
			// 版本判断
			if (serviceCode > versionCode)
			{
				return true;
			}
		}
		return false;
	}
	*/
/*//	写文件
	public void writeSDFile(String fileName, String write_str) throws IOException{    
		  
        File file = new File(fileName);    
  
        FileOutputStream fos = new FileOutputStream(file);    
  
        byte [] bytes = write_str.getBytes();   
  
        fos.write(bytes);   
  
        fos.close();   
} 
	//读文件
	public String readSDFile(String fileName) throws IOException {    
		  
        File file = new File(fileName);    
  
        FileInputStream fis = new FileInputStream(file);    
  
        int length = fis.available();   
  
         byte [] buffer = new byte[length];   
         fis.read(buffer);       
  
         String res = EncodingUtils.getString(buffer, "UTF-8");   
  
         fis.close();       
         return res;    
}    */
}
