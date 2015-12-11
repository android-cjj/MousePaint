package com.cjj.mousepaint;

import android.app.Application;

import com.cjj.mousepaint.model.LoginModel;
import com.cjj.mousepaint.model.UserModel;
import com.cjj.mousepaint.utils.ScreenUtil;

/**
 * Created by Administrator on 2015/10/15.
 */
public class MosuePaintAppication extends Application {
    /**用户信息*/
    public static UserModel UserInfo;
    public static MosuePaintAppication appication;
    @Override
    public void onCreate() {
        super.onCreate();
        appication = this;
    }


}
