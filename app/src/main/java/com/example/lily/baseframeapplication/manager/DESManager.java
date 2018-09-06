package com.example.lily.baseframeapplication.manager;

import android.util.Log;
import com.example.lily.baseframeapplication.dao.exception.EncryptException;
import com.example.lily.baseframeapplication.model.AccessTokenInfo;
import com.example.lily.baseframeapplication.utils.DES;
import io.reactivex.Observable;
import java.util.List;

/**
 * Created by lily on 2017/11/14.
 * 加密字段管理类
 */

public class DESManager {
    public static Observable<List<String>> encryptStrings(String... strs) {
        return Observable.just(strs).flatMap(strings -> {
            int count = 0;
            for (String s : strings) {
                if (s != null) {
                    ++count;
                }
            }
            return    Observable.fromArray(strs)
                                .filter(s1 -> strs != null)
                                .flatMap(s -> encryptString(s))
                                .buffer(count);
        });
    }

    public static Observable<String> encryptString(String str) {
        return Observable.just(str).flatMap(s -> {
            if (s == null) {
                return TokenManager.getInstance()
                                   .getTokenInfo()
                                   .flatMap(tokenInfo -> Observable.just(null));
            }
            return encryptString(str, 9, 17);
        });
    }

    public static Observable<String> encryptString(String str, int sessionKeyPos, int sessionSecretPos) {
        return TokenManager.getInstance()
                           .getTokenInfo()
                           .flatMap(tokenInfo -> encrypt(tokenInfo, str,
                                   sessionKeyPos, sessionSecretPos));
    }

    //解密
    public static Observable<String> decryptString(AccessTokenInfo tokenInfo, String str, int sessionKeyPos, int sessionSecretPos) {
        return Observable.create(subscriber -> {
            try {
                subscriber.onNext(
                        DES.decryptDES(tokenInfo.sessionKey, sessionKeyPos, str,
                                tokenInfo.sessionSecret, sessionSecretPos));
                subscriber.onComplete();
            } catch (Exception e) {
                e.printStackTrace();
                subscriber.onError(new EncryptException());
            }
        });
    }

    //加密
    private static Observable<String> encrypt(AccessTokenInfo tokenInfo, String str, int sessionKeyPos, int sessionSecretPos) {
        Log.d("result",tokenInfo.sessionKey+"啊"+tokenInfo
                .sessionSecret+"哦"+sessionKeyPos+"额"+str);
        return Observable.create(subscriber -> {
            try {
                subscriber.onNext(
                        DES.encryptDES(tokenInfo.sessionKey, sessionKeyPos, str,
                                tokenInfo.sessionSecret, sessionSecretPos));
                subscriber.onComplete();
            } catch (Exception e) {
                e.printStackTrace();
                subscriber.onError(new EncryptException());
            }
        });
    }



}
