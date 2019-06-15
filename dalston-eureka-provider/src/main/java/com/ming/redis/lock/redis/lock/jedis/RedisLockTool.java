package com.ming.redis.lock.redis.lock.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Collections;

public class RedisLockTool {

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final Long RELEASE_SUCCESS = 1L;

    /*
        NX: set if not exists
        XX: set if already exists
        EX: seconds
        PX: milliseconds
    */

    /*
        Lua Script
        eval "return redis.call('set', 'lockKey', 'lockValue')" 0
        eval "return redis.call('get', 'lockKey')" 0
        eval "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end" 1 lockKey lockValue
        eval "return redis.call('get', 'lockKey')" 0
     */

    /*public Jedis jedis;//非切片额客户端连接
    private JedisPool jedisPool;//非切片连接池

    public RedisLockTool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(10);
        config.setMaxIdle(5);
        config.setMaxWaitMillis(1000L);
        config.setTestOnBorrow(false);

        jedisPool = new JedisPool(config, "127.0.0.1", 6379, 1000, "password");
        jedis = jedisPool.getResource();
    }*/

    /**
     * Try to get the distributed lock
     *
     * @param jedis      Redis Client
     * @param lockKey
     * @param requestId
     * @param expireTime
     * @return Lock successfully or not
     */
    public static boolean tryGetDistributedLock(Jedis jedis, String lockKey, String requestId, int expireTime) {
        String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
        if (LOCK_SUCCESS.equals(result)) {
            System.out.println("Lock success");
            return true;
        } else {
            System.out.println("Lock failed");
            return false;
        }
    }

    /**
     * release the distributed lock
     *
     * @param jedis
     * @param lockKey
     * @param requestId
     * @return Release successfully or not
     */
    public static boolean releaseDistributedLock(Jedis jedis, String lockKey, String requestId) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
        if (RELEASE_SUCCESS.equals(result)) {
            System.out.println("Release success");
            return true;
        } else {
            System.out.println("Release failed");
            return false;
        }
    }

}
