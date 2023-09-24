package aaa;

import com.jshy.html.HTMLApplication;
import com.jshy.html.urils.HTMLUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = HTMLApplication.class)
@RunWith(SpringRunner.class)
public class aaa {
    @Test
    public void autoScanWmNews() {
//
//        wmNewsAutoScanService.autoScanWmNews(6233);

        String str="现在这篇文章将会针对性别不同的人群进行分类研究";
        System.out.println(HTMLUtils.judgeTag(str));
        System.out.println(HTMLUtils.deleteTag(str));
    }


}
