package com.cjj.mousepaint;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cjj.listener.CallbackListener;
import com.cjj.mousepaint.constants.Code;
import com.cjj.mousepaint.constants.Constant;
import com.cjj.mousepaint.dao.AppDao;
import com.cjj.mousepaint.events.RegisterEvent;
import com.cjj.mousepaint.model.RegisterModel;
import com.cjj.mousepaint.utils.EmptyUtils;
import com.cjj.mousepaint.utils.InputCheckUtils;
import com.cjj.mousepaint.utils.StatusBarCompat;
import com.cjj.mousepaint.utils.TintingDrawablesUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2015/10/10.
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String Tag = RegisterActivity.class.getSimpleName();
    @Bind(R.id.et_user_email)EditText et_user_email;
    @Bind(R.id.et_user_password)EditText et_user_password;
    @Bind(R.id.et_user_re_password)EditText et_user_password_re;
    @Bind(R.id.btn_sure)Button btn_sure;
    private String email = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        init();
    }


    private void init() {
        StatusBarCompat.compat(this, getResources().getColor(R.color.brownness));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("注册");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_sure.setOnClickListener(this);



    }

    private void toastMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    /**
     * 检测输入内容是否符合规范
     * @param password
     * @param comparePassword
     */
    private void checkInputFindPassword(final String account, final String password,final String comparePassword)
    {

        if(EmptyUtils.emptyOfString(account))
        {
            toastMsg("邮箱不能为空");
            return;
        }
        else if(!InputCheckUtils.isValidEmail(account))
        {
            toastMsg("请输入正确的邮箱");
            return;
        }
        else if(InputCheckUtils.isEmpty(password))
        {
            toastMsg("新密码不能为空");
            return;
        }
        else if(InputCheckUtils.isEmpty(comparePassword))
        {
            toastMsg("重复密码不能为空");
            return;
        }
        else if(!InputCheckUtils.compareIsEqual(password,comparePassword))
        {
            toastMsg("两次密码输入不一致");
            return;
        }
        else if(!InputCheckUtils.checkInputRangeIsConform(password,3,15) || !InputCheckUtils.checkInputRangeIsConform(comparePassword,3,15))
        {
            toastMsg("请填写3-15位密码");
            return;
        }

        email = account;

        /**
         * 验证通过
         */
        AppDao.getInstance().userRegister(account,password,new CallbackListener<RegisterModel>()
        {
            @Override
            public void onStringResult(String result) {
                super.onStringResult(result);
            }

            @Override
            public void onSuccess(RegisterModel result) {
                super.onSuccess(result);
                Log.i(Tag, "res--------->" + result.ErrCode);
                int status = Integer.valueOf(result.ErrCode);
                toastMsg(Code.getRegisterMsg(status));
                if(status == Constant.success)
                {
                    EventBus.getDefault().post(new RegisterEvent(email));
                    RegisterActivity.this.finish();
                }
            }
        });

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
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btn_sure:
                checkInputFindPassword(et_user_email.getText().toString().trim(),et_user_password.getText().toString().trim(),et_user_password_re.getText().toString().trim());
                break;

        }
    }
}
