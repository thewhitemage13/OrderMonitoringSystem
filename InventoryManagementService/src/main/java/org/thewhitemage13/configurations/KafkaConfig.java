package org.thewhitemage13.configurations;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Configuration;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

import org.thewhitemage13.exception.NonRetryableException;
import org.thewhitemage13.exception.RetryableException;

/**
 * Configures Kafka integration for the application.
 * <p>
 * This class provides configuration for Kafka consumers, topics, and error handling mechanisms.
 * It uses Spring's Kafka integration to define beans for consumer factories, listener containers,
 * and topic creation.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Configures consumer factories with custom deserializers.</li>
 *     <li>Defines error handling logic with retry and dead-letter topic support.</li>
 *     <li>Creates Kafka topics dynamically during application startup.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Configuration
public class KafkaConfig {
    @Autowired
    Environment environment;

    /**
     * Configures a Kafka consumer factory.
     * <p>
     * This factory uses deserializers to process message keys and values. It reads configuration
     * properties such as bootstrap servers, group ID, and trusted packages from the application environment.
     * </p>
     *
     * @return a configured {@link ConsumerFactory} for Kafka consumers.
     */
    @Bean
    ConsumerFactory<Long, Object> consumerFactory() {
        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                environment.getProperty("spring.kafka.consumer.bootstrap-servers"));

        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);

        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);

        config.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);

        config.put(JsonDeserializer.TRUSTED_PACKAGES,
                environment.getProperty("spring.kafka.consumer.properties.spring.json.trusted.packages"));

        config.put(ConsumerConfig.GROUP_ID_CONFIG, environment.getProperty("spring.kafka.consumer.group-id"));

        return new DefaultKafkaConsumerFactory<>(config);
    }

    /**
     * Configures a Kafka listener container factory with error handling.
     * <p>
     * This factory wraps message listeners with retry logic and dead-letter queue support.
     * It also configures exception handling for retryable and non-retryable exceptions.
     * </p>
     *
     * @param consumerFactory the {@link ConsumerFactory} to use for message consumption.
     * @param kafkaTemplate   the {@link KafkaTemplate} used for sending dead-letter messages.
     * @return a configured {@link ConcurrentKafkaListenerContainerFactory}.
     */
    @Bean
    ConcurrentKafkaListenerContainerFactory<Long, Object> kafkaListenerContainerFactory(
            ConsumerFactory<Long, Object> consumerFactory, KafkaTemplate<Long, Object> kafkaTemplate) {

        DefaultErrorHandler errorHandler = new DefaultErrorHandler(new DeadLetterPublishingRecoverer(kafkaTemplate),
                new FixedBackOff(1000, 3));
        errorHandler.addNotRetryableExceptions(NonRetryableException.class);
        errorHandler.addRetryableExceptions(RetryableException.class);

        ConcurrentKafkaListenerContainerFactory<Long, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setCommonErrorHandler(errorHandler);

        return factory;
    }

    /**
     * Creates the "low.stock" Kafka topic.
     * <p>
     * This topic has 3 partitions and 1 replica, making it suitable for scalability
     * in handling low stock events.
     * </p>
     *
     * @return a {@link NewTopic} representing the "low.stock" topic.
     */
    @Bean
    NewTopic createTopic1() {
        return TopicBuilder
                .name("low.stock")
                .partitions(3)
                .replicas(1)
                .build();
    }

    /**
     * Creates the "update.stock" Kafka topic.
     * <p>
     * This topic has 3 partitions and 1 replica, ensuring efficient updates for stock-related events.
     * </p>
     *
     * @return a {@link NewTopic} representing the "update.stock" topic.
     */
    @Bean
    NewTopic createTopic2() {
        return TopicBuilder
                .name("update.stock")
                .partitions(3)
                .replicas(1)
                .build();
    }

    /**
     * Creates the "add.product" Kafka topic.
     * <p>
     * This topic has 3 partitions and 1 replica, supporting the addition of new product events.
     * </p>
     *
     * @return a {@link NewTopic} representing the "add.product" topic.
     */
    @Bean
    NewTopic createTopic3() {
        return TopicBuilder
                .name("add.product")
                .partitions(3)
                .replicas(1)
                .build();
    }
}
