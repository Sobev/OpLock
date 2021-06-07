package com.sobev.OpLock.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class RedisSerializationConfig {
  /**
   * 自定义缓存序列化配置  使用json
   *
   * @return
   */
  @Bean
  RedisCacheConfiguration redisCacheConfiguration(CacheProperties cacheProperties) {
    CacheProperties.Redis redisProperties = cacheProperties.getRedis();
    RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
    config = config.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()));
    config = config.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericFastJsonRedisSerializer()));

    //照抄  org.springframework.boot.autoconfigure.cache
    if (redisProperties.getTimeToLive() != null) {
      config = config.entryTtl(redisProperties.getTimeToLive());
    }

    if (redisProperties.getKeyPrefix() != null) {
      config = config.prefixCacheNameWith(redisProperties.getKeyPrefix());
    }

    if (!redisProperties.isCacheNullValues()) {
      config = config.disableCachingNullValues();
    }

    if (!redisProperties.isUseKeyPrefix()) {
      config = config.disableKeyPrefix();
    }
    return config;
  }
}
