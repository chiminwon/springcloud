package com.ming.rule;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

import java.util.List;


public class MingRule extends AbstractLoadBalancerRule {

    private Server chooseServer(ILoadBalancer lb, Object key) {
        if (lb == null) {
            return null;
        }
        Server server = null;
        while (server == null) {
            if (Thread.interrupted()) {
                return null;
            }

            // 可用的服务实例
            List<Server> upList = lb.getReachableServers();

            // 只获取端口为：2001 的服务实例
            for (Server tempServer : upList) {
                if (tempServer.getPort() == 2001) {
                    server = tempServer;
                }
            }
            if (server == null) {
                Thread.yield();
                continue;
            }
            System.out.println("Instance IP：" + server.getHost() + " Port：" + server.getPort());
            if (server.isAlive()) {
                return (server);
            }
            server = null;
            Thread.yield();
        }

        return server;
    }

    @Override
    public Server choose(Object key) {
        return this.chooseServer(getLoadBalancer(), key);
    }

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }
}
