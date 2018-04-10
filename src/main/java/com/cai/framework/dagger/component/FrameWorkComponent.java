package com.cai.framework.dagger.component;

import com.cai.framework.base.GodBaseApplication;
import com.cai.framework.dagger.module.FrameWorkModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by clarence on 2018/3/26.
 */
@Singleton
@Component(modules = FrameWorkModule.class)
public interface FrameWorkComponent {

    void inject(GodBaseApplication application);
}
