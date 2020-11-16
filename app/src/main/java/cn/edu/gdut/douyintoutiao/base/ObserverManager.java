package cn.edu.gdut.douyintoutiao.base;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * @author : cypang
 * @description ： TODO:类的作用
 * @email : 516585610@qq.com
 * @date : 2020/11/12 11:14
 */
public abstract class ObserverManager<T> implements Observer<T> {
    @Override
    public void onSubscribe(@NonNull Disposable d) {
        onDisposable(d);
    }

    @Override
    public void onNext(@NonNull T t) {
        onSuccess(t);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        onFail(e);
    }

    @Override
    public void onComplete() {
        onFinish();
    }

    public abstract void onSuccess(T t);                        //调用成功
    public abstract void onFail(Throwable throwable);           //调用失败或者报错
    public abstract void onFinish();                            //调用完成
    public abstract void onDisposable(Disposable disposable);   //调用前准备工作
}
