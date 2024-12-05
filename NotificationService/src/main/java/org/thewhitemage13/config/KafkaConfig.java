package org.thewhitemage13.config;

import org.springframework.context.annotation.Configuration;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

import org.thewhitemage13.exception.NonRetryableException;
import org.thewhitemage13.exception.RetryableException;

/**
 * Configuration class for Kafka integration in the application.
 * <p>
 * This class sets up the Kafka producer, consumer, and listener container factory.
 * It configures error handling, retry mechanisms, and dead-letter queue support for
 * better fault tolerance and resilience in Kafka-based messaging.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Configures Kafka consumer factory with deserialization settings and group ID.</li>
 *     <li>Sets up Kafka producer factory for serializing message keys and values.</li>
 *     <li>Defines a listener container factory with custom error handling and retry logic.</li>
 *     <li>Integrates Dead Letter Queue (DLQ) for failed message processing.</li>
 * </ul>
 *
 * @see org.springframework.kafka.core.ConsumerFactory
 * @see org.springframework.kafka.core.ProducerFactory
 * @see org.springframework.kafka.listener.DefaultErrorHandler
 * @see org.springframework.kafka.listener.DeadLetterPublishingRecoverer
 * @see org.springframework.kafka.support.serializer.JsonDeserializer
 * @see org.springframework.kafka.support.serializer.JsonSerializer
 * @see org.springframework.context.annotation.Bean
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Configuration
public class KafkaConfig {
    @Autowired
    Environment environment;

    /**
     * Configures the Kafka {@link ConsumerFactory}.
     * <p>
     * Sets up deserialization for keys and values, trusted packages for JSON deserialization,
     * and the consumer group ID.
     * </p>
     *
     * @return a configured {@link ConsumerFactory} instance
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
     * Configures the Kafka {@link ConcurrentKafkaListenerContainerFactory}.
     * <p>
     * Sets up error handling with retry mechanisms and Dead Letter Queue (DLQ) support
     * for unprocessable messages.
     * </p>
     *
     * @param consumerFactory the Kafka consumer factory
     * @param kafkaTemplate   the Kafka template for message publishing
     * @return a configured {@link ConcurrentKafkaListenerContainerFactory} instance
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
     * Configures the Kafka {@link KafkaTemplate} for message publishing.
     * <p>
     * The template is used for sending messages with serialized keys and values.
     * </p>
     *
     * @param producerFactory the Kafka producer factory
     * @return a configured {@link KafkaTemplate} instance
     */
    @Bean
    KafkaTemplate<Long, Object> kafkaTemplate(ProducerFactory<Long, Object> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    /**
     * Configures the Kafka {@link ProducerFactory}.
     * <p>
     * Sets up serialization for message keys and values.
     * </p>
     *
     * @return a configured {@link ProducerFactory} instance
     */
    @Bean
    ProducerFactory<Long, Object> producerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, environment.getProperty("spring.kafka.consumer.bootstrap-servers"));
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        return new DefaultKafkaProducerFactory<>(config);
    }
}
