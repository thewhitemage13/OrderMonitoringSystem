package org.thewhitemage13.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;
import org.thewhitemage13.exception.NonRetryableException;
import org.thewhitemage13.exception.RetryableException;

import java.util.HashMap;
import java.util.Map;

/**
 * Kafka configuration class for setting up Kafka consumers and topics.
 * <p>
 * This class configures Kafka consumer factories, error handling strategies,
 * and creates Kafka topics. It sets up a custom deserialization mechanism
 * and configures the retry logic for Kafka consumers. The topics `order.created`
 * and `order.updated` are created with specific partition and replica settings.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Configures Kafka consumer factory with error handling and deserialization.</li>
 *     <li>Defines retry logic using {@link DefaultErrorHandler} and {@link DeadLetterPublishingRecoverer}.</li>
 *     <li>Creates Kafka topics `order.created` and `order.updated` with defined partition and replica settings.</li>
 * </ul>
 *
 * <h2>Usage:</h2>
 * <p>
 * This configuration class is typically used to set up Kafka consumers for processing events related to orders
 * and manage error handling, retries, and dead-letter topics. It is commonly used in Spring Boot applications
 * that integrate with Kafka for message-driven architectures.
 * </p>
 *
 * @see ConcurrentKafkaListenerContainerFactory
 * @see ConsumerFactory
 * @see DeadLetterPublishingRecoverer
 * @see DefaultErrorHandler
 * @see NewTopic
 * @see LongDeserializer
 * @see JsonDeserializer
 * @see RetryableException
 * @see NonRetryableException
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Configuration
public class KafkaConfig {
    @Autowired
    Environment environment;

    /**
     * Creates a {@link ConsumerFactory} for Kafka consumers.
     * <p>
     * Configures the Kafka consumer with bootstrap servers, deserializers for keys and values, and group ID.
     * Also configures error handling for deserialization using {@link ErrorHandlingDeserializer} and {@link JsonDeserializer}.
     * </p>
     *
     * @return a configured {@link ConsumerFactory} instance for Kafka consumers
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
     * Configures a {@link ConcurrentKafkaListenerContainerFactory} for Kafka consumers.
     * <p>
     * Sets the consumer factory and defines error handling with retry logic and dead-letter publishing.
     * The {@link DefaultErrorHandler} is used with retryable and non-retryable exceptions.
     * </p>
     *
     * @param consumerFactory the consumer factory used for creating Kafka consumers
     * @param kafkaTemplate   the Kafka template for sending messages to the dead-letter topic
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
     * Creates the {@link NewTopic} for the "order.created" Kafka topic.
     * <p>
     * This topic is created with 3 partitions and 1 replica.
     * </p>
     *
     * @return the created {@link NewTopic} instance for the "order.created" topic
     */
    @Bean
    NewTopic createTopic1() {
        return TopicBuilder
                .name("order.created")
                .partitions(3)
                .replicas(1)
                .build();
    };

    /**
     * Creates the {@link NewTopic} for the "order.updated" Kafka topic.
     * <p>
     * This topic is created with 3 partitions and 1 replica.
     * </p>
     *
     * @return the created {@link NewTopic} instance for the "order.updated" topic
     */
    @Bean
    NewTopic createTopic2() {
        return TopicBuilder
                .name("order.updated")
                .partitions(3)
                .replicas(1)
                .build();
    };
}
