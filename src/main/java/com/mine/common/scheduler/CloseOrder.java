package com.mine.common.scheduler;

import com.mine.service.OrderService;
import com.mine.utils.DateUtils;
import com.mine.utils.PropertiesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author 杜晓鹏
 * @create 2019-01-18 20:33
 *
 * 定时关闭订单
 */
@Component
public class CloseOrder {

    @Autowired
    OrderService orderService;

    @Scheduled(cron = "0 0 */2 * * *")
    public void closeOrder(){
        //System.out.println("=================");

        int hour = Integer.parseInt(PropertiesUtils.getValue("close.order.time"));

        String date = DateUtils.dateToString(org.apache.commons.lang.time.DateUtils.addHours(new Date(),-hour));

        orderService.closeOrder(date);
    }
}
