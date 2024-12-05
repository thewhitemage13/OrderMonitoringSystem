package org.thewhitemage13.config;

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
 * Configuration class for Redis-based caching.
 * <p>
 * This class sets up a {@link CacheManager} for managing application-level caching using Redis.
 * It specifies caching behavior, including key and value serialization, and the time-to-live (TTL) for cached entries.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Integration with Redis as the caching backend.</li>
 *     <li>Configures key serialization using {@link StringRedisSerializer}.</li>
 *     <li>Configures value serialization using {@link GenericJackson2JsonRedisSerializer}.</li>
 *     <li>Sets a default TTL of 10 minutes for cached entries.</li>
 * </ul>
 *
 * <h2>Usage:</h2>
 * <p>
 * This configuration will be automatically picked up by Spring's application context. The
 * {@link CacheManager} bean can then be used to interact with the Redis cache in a
 * Spring-managed way.
 * </p>
 *
 * @see CacheManager
 * @see RedisCacheManager
 * @see RedisCacheConfiguration
 * @see RedisConnectionFactory
 * @see RedisSerializationContext
 * @see StringRedisSerializer
 * @see GenericJackson2JsonRedisSerializer
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Configuration
public class RedisConfig {

    /**
     * Creates and configures a {@link CacheManager} for Redis caching.
     * <p>
     * This method defines the caching configuration, including:
     * <ul>
     *     <li>Key serialization using {@link StringRedisSerializer}.</li>
     *     <li>Value serialization using {@link GenericJackson2JsonRedisSerializer}.</li>
     *     <li>A default TTL of 10 minutes for all cache entries.</li>
     * </ul>
     *
     * @param redisConnectionFactory the Redis connection factory used to connect to the Redis instance
     * @return a fully configured {@link CacheManager} for Redis
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
