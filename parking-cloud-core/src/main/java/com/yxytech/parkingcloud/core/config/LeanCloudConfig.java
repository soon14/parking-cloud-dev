package com.yxytech.parkingcloud.core.config;

import com.avos.avoscloud.AVOSCloud;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LeanCloudConfig {

    public LeanCloudConfig() {
        AVOSCloud.initialize("jm7tOI7sufVpQSD1sMY95lEj-gzGzoHsz", "d7JKFyqz1a790kIm4mDMkFmK", "CgD6MU2mlVjOpNKBngQ1JVAq");
    }
}
