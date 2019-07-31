package com.ljphawk.arms.http;

import android.accounts.NetworkErrorException;

import com.ljphawk.arms.utils.NetworkUtil;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import io.reactivex.subscribers.DisposableSubscriber;
import retrofit2.HttpException;

/*
 *@创建者       L_jp
 *@创建时间     2019/7/29 15:48.
 *@描述
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述
 */

public abstract class NormalSubscriber<T> extends DisposableSubscriber<T> {


    /**
     *  网络请求错误类型
     *  DISCONNECT 网络未连接;
     *  TIMEOUT 请求超时;
     *  NETERROR 网络请求错误;
     *  UNKNOW 未知错误;
     *  JSON 数据解析错误;
     *  NOTDATA 没有数据;
     *  SERVERERROR 可能为服务端报错
     */
    public enum ErrorType {
        DISCONNECT, SERVERERROR, TIMEOUT, NETERROR, UNKNOW, JSON, NOTDATA
    }

    @Override
    public void onComplete() {
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();

        if (e instanceof TimeoutException || e instanceof SocketTimeoutException) {
            onError(ErrorType.TIMEOUT, "连接超时");
        } else if (e instanceof ConnectException || e instanceof NetworkErrorException || e instanceof HttpException) {
            onError(ErrorType.NETERROR, "网络请求错误");
        } else if (e instanceof IllegalStateException || e instanceof JSONException) {
            onError(ErrorType.JSON, "数据解析错误");
        } else if (e instanceof Exception) {
            onError(ErrorType.SERVERERROR, e.getMessage());
        } else {
            onError(ErrorType.UNKNOW, "数据错误");
        }
    }

    @Override
    public void onStart() {
        if (!NetworkUtil.isNetConnected()) {
            if (!isDisposed()) {
                dispose();
            }
            onError(ErrorType.DISCONNECT, "网络未连接");
            return;
        }
        super.onStart();
    }


    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    /**
     * 返回成功
     *
     * @param t
     */
    public abstract void onSuccess(T t);

    /**
     * 返回错误
     *
     * @param errorType
     * @param errorMsg
     */
    public abstract void onError(ErrorType errorType, String errorMsg);


}
