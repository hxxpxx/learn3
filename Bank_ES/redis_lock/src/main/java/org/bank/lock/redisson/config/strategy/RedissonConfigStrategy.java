package org.bank.lock.redisson.config.strategy;

import org.bank.lock.redisson.config.RedissonProperties;
import org.redisson.config.Config;

/**
 * @desc Redisson配置构建接口
 */
public interface RedissonConfigStrategy {

    /**
     * 根据不同的Redis配置策略创建对应的Config
     * @param redissonProperties
     * @return Config
     */
    Config createRedissonConfig(RedissonProperties redissonProperties);
}
