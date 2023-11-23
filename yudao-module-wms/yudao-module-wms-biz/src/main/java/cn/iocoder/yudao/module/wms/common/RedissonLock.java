package cn.iocoder.yudao.module.wms.common;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author jiangfeng
 * @date 2023/7/10
 */
@Component
public class RedissonLock {

    private RedissonClient redissonClient;

    public RedissonLock(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    public RLock getLock(String key) {
        return redissonClient.getLock(key);
    }

    public boolean tryLock(RLock lock, long leaseTime, TimeUnit timeUnit) throws InterruptedException {
        return lock.tryLock(0, leaseTime, timeUnit);
    }

    public void unlock(RLock lock) {
        lock.unlock();
    }
}
