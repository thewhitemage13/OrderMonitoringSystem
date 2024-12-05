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
 * Configuration class for Redis caching.
 * <p>
 * This class sets up the Redis cache manager with custom serialization settings
 * and a default time-to-live (TTL) for cache entries.
 * It enables integration of Redis as a caching mechanism in the application, improving performance
 * by reducing database calls for frequently accessed data.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Custom serialization for cache keys and values.</li>
 *     <li>Default TTL for cache entries set to 10 minutes.</li>
 *     <li>Integration with Spring's {@link CacheManager} interface for transparent caching support.</li>
 * </ul>
 *
 * @see CacheManager
 * @see RedisCacheManager
 * @see RedisCacheConfiguration
 * @see RedisConnectionFactory
 * @see org.springframework.data.redis.serializer.StringRedisSerializer
 * @see org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Configuration
public class RedisConfig {

    /**
     * Configures and returns a {@link CacheManager} backed by Redis.
     * <p>
     * This method creates a {@link RedisCacheManager} with the following settings:
     * <ul>
     *     <li>Default TTL for cache entries: 10 minutes.</li>
     *     <li>Key serialization using {@link StringRedisSerializer}.</li>
     *     <li>Value serialization using {@link GenericJackson2JsonRedisSerializer}.</li>
     * </ul>
     * These settings ensure that cached data is efficiently stored and easily retrievable.
     *
     * @param redisConnectionFactory the factory for creating Redis connections
     * @return a configured {@link CacheManager} instance
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
