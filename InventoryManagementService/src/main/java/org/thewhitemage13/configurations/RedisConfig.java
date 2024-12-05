package org.thewhitemage13.configurations;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * Configures Redis cache integration for the application.
 * <p>
 * This class provides the configuration for caching using Redis. It defines a {@link CacheManager}
 * that is responsible for managing cache entries. The cache manager is configured to serialize the keys
 * and values of cache entries and set a default expiration time for cache entries.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Configures Redis cache with custom serialization for keys and values.</li>
 *     <li>Sets a time-to-live (TTL) of 10 minutes for all cache entries.</li>
 *     <li>Uses Jackson JSON serializer for cache values and String serializer for cache keys.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Configuration
public class RedisConfig {

    /**
     * Configures a {@link CacheManager} for Redis caching.
     * <p>
     * This method sets up the Redis cache manager with a default cache configuration that specifies:
     * <ul>
     *     <li>A time-to-live (TTL) of 10 minutes for cache entries.</li>
     *     <li>Serialization of cache keys using {@link StringRedisSerializer}.</li>
     *     <li>Serialization of cache values using {@link GenericJackson2JsonRedisSerializer}.</li>
     * </ul>
     *
     * @param redisConnectionFactory the Redis connection factory used for connecting to Redis.
     * @return a configured {@link CacheManager} instance for managing Redis caches.
     */
    @Bean
    public CacheManager cacheManager(
            RedisConnectionFactory redisConnectionFactory

    ) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration
                .defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10))
                .serializeKeysWith(RedisSerializationContext
                        .SerializationPair
                        .fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(
                        RedisSerializationContext
                                .SerializationPair
                                .fromSerializer(new GenericJackson2JsonRedisSerializer())
                );

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(redisCacheConfiguration)
                .build();
    }
}
