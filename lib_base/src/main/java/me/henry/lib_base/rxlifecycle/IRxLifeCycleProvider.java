package me.henry.lib_base.rxlifecycle;


import com.trello.rxlifecycle2.LifecycleTransformer;

public interface IRxLifeCycleProvider {
    <T> LifecycleTransformer<T> bindLifecycle();
}
