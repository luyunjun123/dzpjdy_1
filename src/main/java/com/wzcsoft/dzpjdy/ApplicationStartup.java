package com.wzcsoft.dzpjdy;

import com.wzcsoft.dzpjdy.service.AutoExecuteService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Created by Xingzhi on 2019/1/28.
 */
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        AutoExecuteService service = contextRefreshedEvent.getApplicationContext().getBean(AutoExecuteService.class);
        service.initApplication();
    }
}
