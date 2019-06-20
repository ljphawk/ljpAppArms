package com.ljphawk.arms.base;



/*
 *@创建者       L_jp
 *@创建时间     2018/7/5 10:12.
 *@描述         ${""}
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述         ${""}
 */


import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public abstract class BasePresenter<V extends BaseView> implements InvocationHandler {
    //弱引用, 防止内存泄漏
    private WeakReference<V> weakReference;
    private V mProxyView;
    protected static final String TAG = "BasePresenter";

    void attach(V v) {
        this.weakReference = new WeakReference<>(v);
        /*
        使用动态代理，解决 getView 方法可能为空的问题
         V 层解绑了 P 层，那么 getView 就为空，调用 V 层就会发生空指针异常
         如果在 P 层的每个子类中都进行 getView() != null 防空判断会导致开发成本非常高，并且容易出现遗漏
         */
        mProxyView = (V) Proxy.newProxyInstance(v.getClass().getClassLoader(), v.getClass().getInterfaces(), this);
    }

    /**
     * 断开V层和P层
     */
    void detach() {
        if (isViewAttached()) {
            weakReference.clear();
            weakReference = null;
        }
    }

    /*
    这里拿到的是动态代理出来的view，不能强转成 子类对象
     */
    protected V getView() {
        return mProxyView;
    }

    /**
     * @return P层和V层是否关联.
     */
    private boolean isViewAttached() {
        return null != weakReference && null != weakReference.get();
    }

    /**
     *  动态代理接口，每次调用了代理对象的方法最终也会回到到这里
     *
     * {@link InvocationHandler}
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 如果当前还是绑定状态就执行 View 的方法，否则就不执行
        return isViewAttached() ? method.invoke(weakReference.get(), args) : null;
    }
}
