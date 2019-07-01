package com.ming.cache.memcached;

import net.spy.memcached.MemcachedClient;

import java.net.InetSocketAddress;
import java.util.concurrent.Future;

public class MemcachedTest {

    public static void main(String[] args) {
        try {
            // 连接本地的 Memcached 服务
            MemcachedClient memcachedClient = new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));
            System.out.println("Connection to server successfully.");
            // 添加数据
            Future future = memcachedClient.set("allen", 900, "Free Education");
            // 打印状态
            System.out.println("set status:" + future.get());
            // 输出
            System.out.println("allen value in cache - " + memcachedClient.get("allen"));
            // 添加
            future = memcachedClient.add("allen", 900, "memcached");
            // 打印状态
            System.out.println("add status:" + future.get());
            // 添加新key
            future = memcachedClient.add("codingground", 900, "All Free Compilers");
            // 打印状态
            System.out.println("add status:" + future.get());
            // 输出
            System.out.println("codingground value in cache - " + memcachedClient.get("codingground"));
            // 关闭连接
            memcachedClient.shutdown();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
