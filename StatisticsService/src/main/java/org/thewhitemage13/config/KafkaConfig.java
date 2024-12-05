package org.thewhitemage13.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.backoff.FixedBackOff;
import org.thewhitemage13.exception.NonRetryableException;
import org.thewhitemage13.exception.RetryableException;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration class for Kafka integration.
 * <p>
 * This class sets up the Kafka producer, consumer, and listener configurations
 * with error handling, retry mechanisms, and dead-letter queue support.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Configures Kafka consumer and producer factories.</li>
 *     <li>Sets up error handling with retry and dead-letter queue mechanisms.</li>
 *     <li>Utilizes environment properties for dynamic configurations.</li>
 * </ul>
 *
 * <h2>Error Handling:</h2>
 * <p>
 * The error handling is configured to:
 * <ul>
 *     <li>Retry specific exceptions ({@link RetryableException}) up to 3 times with a fixed back-off of 1 second.</li>
 *     <li>Mark certain exceptions ({@link NonRetryableException}) as non-retryable.</li>
 *     <li>Send unprocessed messages to a dead-letter queue using a {@link DeadLetterPublishingRecoverer}.</li>
 * </ul>
 *
 * @see ConcurrentKafkaListenerContainerFactory
 * @see KafkaTemplate
 * @see DeadLetterPublishingRecoverer
 * @see DefaultErrorHandler
 * @see ProducerFactory
 * @see ConsumerFactory
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Configuration
public class KafkaConfig {
    @Autowired
    Environment environment;

    /**
     * Creates a Kafka consumer factory with error-handling deserialization.
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
     * Configures a Kafka listener container factory with custom error handling.
     *
     * @param consumerFactory the consumer factory to be used for Kafka listeners
     * @param kafkaTemplate   the Kafka template for dead-letter recovery
     * @return a {@link ConcurrentKafkaListenerContainerFactory} instance
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
     * Creates a Kafka template for sending messages.
     *
     * @param producerFactory the producer factory to be used
     * @return a {@link KafkaTemplate} instance
     */
    @Bean
    KafkaTemplate<Long, Object> kafkaTemplate(ProducerFactory<Long, Object> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    /**
     * Creates a Kafka producer factory with JSON serialization.
     *
     * @return a configured {@link ProducerFactory} instance for Kafka producers
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
