package com.selenium.sdjubbs.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class ResourceConfig extends WebMvcConfigurerAdapter {
    @Autowired
private SdjubbsSetting setting;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //前面是映射后的地址，后面是需要映射的地址
        //获取文件的真实路径
        String path = System.getProperty("user.dir") + setting.getBaseDirSavePath();
            System.out.println(path);
            registry.addResourceHandler("/common/**").
                    addResourceLocations("file:" + path);
        super.addResourceHandlers(registry);
    }
}
