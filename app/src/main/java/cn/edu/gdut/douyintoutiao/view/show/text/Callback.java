package cn.edu.gdut.douyintoutiao.view.show.text;

import cn.edu.gdut.douyintoutiao.entity.Result;

public interface Callback<T> {
    void returnResult(T result);
}
