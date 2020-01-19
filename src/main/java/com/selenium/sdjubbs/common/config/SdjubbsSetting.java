package com.selenium.sdjubbs.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "sdjubbs")
@Data
public class SdjubbsSetting {
    @Value("${base-dir-save-path}")
    private String baseDirSavePath;
    @Value("${verify-code-save-path}")
    private String verifyCodeSavePath;
    @Value("${verify-code-request-path}")
    private String verifyCodeRequestPath;
    @Value("${avatar-save-path}")
    private String avatarSavePath;
}
