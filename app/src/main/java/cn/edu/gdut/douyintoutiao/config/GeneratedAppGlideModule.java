package cn.edu.gdut.douyintoutiao.config;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

/**
 * @author : cypang
 * @description ： 修复glide在控制台中报错的问题
 * @email : 516585610@qq.com
 * @date : 11/14/20 18:49
 */
@GlideModule
public class GeneratedAppGlideModule extends AppGlideModule {
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
