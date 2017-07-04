package com.example.mobilesafe.mobilesafe;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilesafe.mobilesafe.utils.StreamUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SplashActivity extends AppCompatActivity {

    //注解：方便大批量实例化控件。在使用注解时要在onCreate方法中通过 ViewUtils.inject(this);响应所有注解
    @ViewInject(R.id.tv_splash_downing)
    private TextView tv_splash_downing;
    private TextView tv_splash_version;

    private static final int MSG_SHOW_UPDATE = 10;
    String apkCode;
    String apkDesc;

    String apkUrl;
    /**
     * 子线程不能修改主线程，所以提供Handler，把所有子线程要做但不能做的任务放入handler中，由主线程来执行
     */
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SHOW_UPDATE:
                    showUpdateDialog();


            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ViewUtils.inject(this);
        tv_splash_version = (TextView) findViewById(R.id.tv_splash_version);
        tv_splash_version.setText("版本号:" + BuildConfig.VERSION_NAME);

        checkUpdate();

    }

    /**
     * 升级版本
     */
    private void checkUpdate() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    URL url = new URL(getResources().getString(R.string.apkUrl));
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(3000);
                    int code = conn.getResponseCode();
                    if (200 == code) {
                        //连接成功
                        InputStream is = conn.getInputStream();
                        String json = StreamUtils.parserInputStream(is);
                        JSONObject jsonObject = new JSONObject(json);
                        apkCode = jsonObject.getString("code");
                        apkDesc = jsonObject.getString("des");
                        apkUrl = jsonObject.getString("apkurl");


                        System.out.println(apkCode+"----------------------------"+apkUrl+"------------------------------------------");
                        //判断本地版本和服务器版本是否相同
                        if (!BuildConfig.VERSION_NAME.equals(apkCode)) {
                            //不相同，升级
                            Message message = Message.obtain();
                            message.what = MSG_SHOW_UPDATE;
                            handler.sendMessage(message);
                        }
                    } else {
                        //连接失败
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 升级对话框
     */
    private void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.luncher_bg);//设置对话框图标
        builder.setTitle("发现新版本" + apkCode);
        builder.setMessage(apkDesc);

        builder.setCancelable(false);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(SplashActivity.this,"开始下载",Toast.LENGTH_SHORT).show();
                download();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    /**
     * 下载新版本
     */
    private void download() {
        HttpUtils httputils = new HttpUtils();
        httputils.download(apkUrl, "/mnt/sdcard/mobilesafe2.0.apk", new RequestCallBack<File>() {
            @Override
            public void onSuccess(ResponseInfo<File> responseInfo) {
                Toast.makeText(SplashActivity.this,"下载成功",Toast.LENGTH_SHORT).show();
                //开始安装，通过调用系统安装程序来安装apk
              installApk();
            }

            @Override
            public void onLoading(long total, long current, boolean isUploading) {
                super.onLoading(total, current, isUploading);
                tv_splash_downing.setVisibility(View.VISIBLE);
                tv_splash_downing.setText(current + "/" + total);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(SplashActivity.this,"下载失败",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 调用系统功能安装程序
     * 打开系统的packageInstallerActivity
     */
    private void installApk() {
          /*
                系统包安装程序的应用
                <intent-filter>
                <action android:name="android.intent.action.VIEW" />
                <category android:name="android.intent.category.DEFAULT" />
                <data android:scheme="content" />       //通过内容提供者安装
                <data android:scheme="file" />          //通过文件安装    与内容提供者二选一
                <data android:mimeType="application/vnd.android.package-archive" />//数据类型
            </intent-filter>*/
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        //数据内容和数据类型会互相抵消，
//                intent.setType("application/vnd.android.package-archive");
//                intent.setData(Uri.fromFile(new File("/mnt/sdcard/mobilesafe2.0.apk")));
        //可以通过setdataandtype是两者同时存在
        intent.setDataAndType(Uri.fromFile(new File("/mnt/sdcard/mobilesafe2.0.apk")),"application/vnd.android.package-archive");
        startActivity(intent);
    }
}
