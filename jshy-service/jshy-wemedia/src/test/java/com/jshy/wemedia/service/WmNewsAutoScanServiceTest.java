package com.jshy.wemedia.service;

import com.jshy.check.service.SensitiveFilterService;
import com.jshy.wemedia.WemediaApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest(classes = WemediaApplication.class)
@RunWith(SpringRunner.class)
public class WmNewsAutoScanServiceTest {

    @Autowired
    private WmNewsAutoScanService wmNewsAutoScanService;

    @Autowired
    private SensitiveFilterService sensitiveFilterService;
    @Test
    public void autoScanWmNews() {
//
//        wmNewsAutoScanService.autoScanWmNews(6233);

        String str="草";
        System.out.println(sensitiveFilterService.judgeSensitive(str));
        System.out.println(sensitiveFilterService.filterSensitive(str));
    }
}