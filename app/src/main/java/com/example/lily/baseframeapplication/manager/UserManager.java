package com.example.lily.baseframeapplication.manager;

import android.util.Log;
import com.example.lily.baseframeapplication.app.App;
import com.example.lily.baseframeapplication.app.Constant;
import com.example.lily.baseframeapplication.model.GlobalParams;
import com.example.lily.baseframeapplication.model.User;
import com.example.lily.baseframeapplication.model.event.LoginEvent;
import com.example.lily.baseframeapplication.utils.ACache;
import io.reactivex.Observable;
import org.greenrobot.eventbus.EventBus;

/**
 * Created by lily on 2017/11/13.
 * <用户管理类>
 */

public class UserManager {
    private static final String TAG = "UserManager";
    private static volatile UserManager mInstance;
    private User mUser;
    private ACache mACache;


    public static UserManager getInstance() {
        if (mInstance == null) {
            synchronized (UserManager.class) {
                if (mInstance == null) {
                    mInstance = new UserManager();
                }
            }
        }
        return mInstance;
    }

    private UserManager() {
        mACache = App.getACache();
    }

    public void fillUser() {
        User user = (User) mACache.getAsObject(Constant.KEY_USER);
        if (user != null) {
            this.mUser = user;
        }
    }

    public void setUser(User user) {
        try {
            this.mUser = user;
            if (user != null) {
                mACache.put(Constant.KEY_USER, user);
            }
            else {
                mACache.remove(Constant.KEY_USER);
            }
        } catch (Exception e) {
            Log.d(TAG, "setUserInfo=" + e);
        }
    }


    public User getUser() {
        return mUser;
    }


    public boolean isLogin() {
        return mUser != null;
    }


    //登录
    public Observable<User> login(String mobile, String smsCode) {
        return DataManager.login(mobile, smsCode).flatMap(user -> {
            setUser(user);
            return Observable.just(user);
        });
    }

    public void loginOut() {
        setUser(null);
        EventBus.getDefault().post(new LoginEvent());
    }

    private GlobalParams mGlobalParams;

    public Observable<GlobalParams> getGlobalParams() {
        if (mGlobalParams != null) {
            return Observable.just(mGlobalParams);
        }
        mGlobalParams = null;
        return DataManager.getGlobalParameters()
                          .doOnNext(g -> setGlobalParams(g));
    }

    public void setGlobalParams(GlobalParams globalParams) {
        try {
            //this.mMyInfo = null;
            this.mGlobalParams = globalParams;
            if (globalParams != null) {
                mACache.put(Constant.KEY_GLOBALPARAMS, globalParams);
            }
            else {
                mACache.remove(Constant.KEY_GLOBALPARAMS);
            }
        } catch (Exception e) {
            Log.d(TAG, "setGlobalParams=" + e);
        }
    }

    public static Observable<User> getRefreshLoginParams() {
        return Observable.create(subscriber -> {
            User user = UserManager.getInstance().getUser();
            String timestamp = String.valueOf(
                    System.currentTimeMillis() / 1000);
            subscriber.onNext(user.authenTicket);
            subscriber.onNext(timestamp);
            subscriber.onComplete();
        })
                         .map(o -> (String) o)
                         .buffer(2)
                         .flatMap(strings -> DataManager.refreshLogin(strings))
                         .flatMap(user -> {
                             Log.d("result",user.nickname+"getRefreshLoginParams()");
                             UserManager.getInstance().setUser(user);
                             return Observable.just(user);
                         });
    }





}
