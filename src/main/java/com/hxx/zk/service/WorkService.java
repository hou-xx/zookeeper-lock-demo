package com.hxx.zk.service;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * <ul>
 * <li>功能说明：模拟业务</li>
 * <li>作者：tal on 2019/9/19 0019 16:19 </li>
 * <li>邮箱：hou_xiangxiang@126.com</li>
 * </ul>
 */
public class WorkService {

    private static String connectUrl = "10.21.41.181:2181,10.21.42.47:2181,10.21.49.252:2181";

    public void work(Long time) {
        //创建zookeeper的客户端
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(connectUrl, retryPolicy);
        client.start();
        //创建分布式锁, 锁空间的根节点路径为/curator-test/lock
        InterProcessMutex mutex = new InterProcessMutex(client, "/curator-test/lock");
        try {
            mutex.acquire();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("尝试获取锁发生异常！");
            return;
        }
        //获得了锁, 进行业务流程
        System.out.println(Thread.currentThread().getName() + " --> Enter mutex");
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //完成业务流程, 释放锁
        try {
            mutex.release();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("尝试获取锁发生异常！");
        }
        //关闭客户端
        client.close();
    }
}
