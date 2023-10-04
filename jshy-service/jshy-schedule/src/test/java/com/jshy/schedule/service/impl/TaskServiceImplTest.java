package com.jshy.schedule.service.impl;

import com.jshy.common.redis.CacheService;
import com.jshy.model.schedule.dtos.Task;
import com.jshy.schedule.ScheduleApplication;
import com.jshy.schedule.service.TaskService;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

@SpringBootTest(classes = ScheduleApplication.class)
@RunWith(SpringRunner.class)
public class TaskServiceImplTest {
    @Autowired
    TaskService taskService;

    /**
     * 添加
     */
    @Test
    public void addTask() {
        Task task = new Task();
        task.setTaskType(100);
        task.setPriority(50);
        task.setParameters("task test".getBytes());

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 1);
        long nextScheduleTime = calendar.getTimeInMillis();

        task.setExecuteTime(nextScheduleTime);
        Long taskId = taskService.addTask(task);
        System.out.println(taskId);
    }

    /**
     * 删除
     */

    @Test
    public void cancelTask() {
        taskService.cancelTask(1709106852015841281L);
    }

    /**
     * 拉取
     */
    @Test
    public void pollTask() {
        Task task = taskService.poll(100, 50);
        System.out.println(task);
    }

}
