package com.cjj.mousepaint;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;


import com.cjj.listener.CallbackListener;
import com.cjj.mousepaint.constants.Constant;
import com.cjj.mousepaint.constants.SaveInfo;
import com.cjj.mousepaint.dao.AppDao;
import com.cjj.mousepaint.fragment.MouseComicFragment;
import com.cjj.mousepaint.model.LoginModel;
import com.cjj.mousepaint.model.UserModel;
import com.cjj.mousepaint.utils.EmptyUtils;
import com.cjj.mousepaint.utils.SPUtils;
import com.umeng.analytics.MobclickAgent;

import java.util.logging.Handler;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.cjj.mousepaint.MosuePaintAppication.UserInfo;

/**
 * Created by Administrator on 2015/11/18.
 */
public class WelcomeActivity extends AppCompatActivity{
    @Bind(R.id.splash_image)
    ImageView iv_splash;

    private UserModel userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.splash);
        iv_splash.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                handLogin();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void handLogin() {

        userInfo = SPUtils.getObject(SaveInfo.KEY_LOGIN,UserModel.class);

       if(null!=userInfo)
       {
            login();
       }else
       {
           startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
       }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    public void toastMsg(String str){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    private void login() {
        AppDao.getInstance().userLogin(userInfo.email, userInfo.pass, new CallbackListener<LoginModel>() {

            @Override
            public void onStringResult(String result) {
                super.onStringResult(result);
                Log.i("cjj", "res----->" + result);
            }

            @Override
            public void onSuccess(LoginModel result) {
                super.onSuccess(result);
                int status = Integer.valueOf(result.ErrCode);
                if (Constant.success == status) {
//                    toastMsg("登录成功");

                    UserInfo = new UserModel();
                    UserInfo.email = result.Return.Email;
                    UserInfo.pass = userInfo.pass;

                    startActivity(new Intent(WelcomeActivity.this, HomeActivity.class));
                    WelcomeActivity.this.finish();

                } else {
                    toastMsg(result.ErrMsg);
                }

            }

            @Override
            public void onError(Exception e) {
                super.onError(e);
                Log.i("cjj", "Exception----->" + e);
                toastMsg(e.toString());
                toastMsg(Constant.NET_ERROR);
            }
        });
    }
}
