package com.cjj.mousepaint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cjj.listener.CallbackListener;
import com.cjj.mousepaint.constants.Constant;
import com.cjj.mousepaint.constants.SaveInfo;
import com.cjj.mousepaint.dao.AppDao;
import com.cjj.mousepaint.events.RegisterEvent;
import com.cjj.mousepaint.model.LoginModel;
import com.cjj.mousepaint.model.RegisterModel;
import com.cjj.mousepaint.model.UserModel;
import com.cjj.mousepaint.utils.EmptyUtils;
import com.cjj.mousepaint.utils.SPUtils;
import com.cjj.mousepaint.utils.StatusBarCompat;
import static com.cjj.mousepaint.MosuePaintAppication.UserInfo;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by cjj on 2015/10/12.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener{
    private static final String Tag = LoginActivity.class.getSimpleName();
    @Bind(R.id.btn_register)Button btn_register;
    @Bind(R.id.et_user_email)EditText et_user_eamil;
    @Bind(R.id.et_user_password)EditText et_user_pass;
    @Bind(R.id.btn_login) Button btn_login;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        init();
    }

    private void init() {
        StatusBarCompat.compat(this, getResources().getColor(R.color.brownness));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("登录");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }

    private void toastMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(RegisterEvent event)
    {
        et_user_eamil.setText(event.email);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.btn_login:
                showProDialog("登录中...");
                login();
                break;
        }
    }

    private void login() {
        final String email = et_user_eamil.getText().toString().trim();
        final String pass = et_user_pass.getText().toString().trim();
        if(EmptyUtils.emptyOfString(email)||EmptyUtils.emptyOfString(pass))
        {
            toastMsg("邮箱或者密码不能为空");
            return;
        }
        AppDao.getInstance().userLogin(email,pass,new CallbackListener<LoginModel>()
        {

            @Override
            public void onStringResult(String result) {
                super.onStringResult(result);
                Log.i("cjj","res----->"+result);
            }

            @Override
            public void onSuccess(LoginModel result) {
                super.onSuccess(result);
                int status = Integer.valueOf(result.ErrCode);
                if(Constant.success == status)
                {
//                    toastMsg("登录成功");

                    UserInfo = new UserModel();
                    UserInfo.email = result.Return.Email;
                    UserInfo.pass = pass;

                    SPUtils.saveObject(SaveInfo.KEY_LOGIN, UserInfo);
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    LoginActivity.this.finish();
                }else
                {
                    toastMsg(result.ErrMsg);
                }

               closeProDialog();
            }

            @Override
            public void onError(Exception e) {
                super.onError(e);
                Log.i("cjj", "Exception----->" + e);
                toastMsg(e.toString());
                toastMsg(Constant.NET_ERROR);
                closeProDialog();
            }
        });
    }
}
