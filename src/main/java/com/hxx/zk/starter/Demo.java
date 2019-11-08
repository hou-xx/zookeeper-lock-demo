package com.hxx.zk.starter;

import com.hxx.zk.service.WorkService;

/**
 * <ul>
 * <li>功能说明：启动测试</li>
 * <li>作者：tal on 2019/9/19 0019 16:09 </li>
 * <li>邮箱：hou_xiangxiang@126.com</li>
 * </ul>
 */
public class Demo {

    public static void main(String[] args) {

        final WorkService workService = new WorkService();
        int flag = 0;
        while (flag < 10) {
            //模拟业务操作
            new Thread(new Runnable() {
                public void run() {
                    workService.work(5000L);
                }
            }).start();
            flag++;
        }
    }
}
