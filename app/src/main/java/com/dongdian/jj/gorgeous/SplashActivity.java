package com.dongdian.jj.gorgeous;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;

import com.tools.jj.tools.activity.permission.PermissionsActivity;
import com.tools.jj.tools.activity.permission.PermissionsChecker;
import com.tools.jj.tools.utils.LogUtil;


import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Jiajun he on 2018/4/8.
 * Email:1021661582@qq.com
 * des:
 * version: 1.0.0
 */

public class SplashActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 0; // 请求码

    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };
    private PermissionsChecker mPermissionsChecker; // 权限检测器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splsh);
        mPermissionsChecker = new PermissionsChecker(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 缺少权限时, 进入权限配置页面
        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            LogUtil.d("缺少权限");
            PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
        } else {
            //必须先处理完毕权限授权后才可以执行以下方法
            LogUtil.d("全部授权");
            startToActivity();
        }
    }

    private void startToActivity() {
        //延迟2秒跳转
        Observable.timer(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        Fade fade = new Fade();
                        fade.setDuration(getResources().getInteger(R.integer.splash_fade_animation_time));
                        getWindow().setExitTransition(fade);
                        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this);
                        startActivity(intent, activityOptions.toBundle());
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish();
        }
    }
}
