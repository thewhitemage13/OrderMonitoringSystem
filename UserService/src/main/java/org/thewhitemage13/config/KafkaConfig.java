package org.thewhitemage13.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.backoff.FixedBackOff;
import org.thewhitemage13.exception.RetryableException;

import java.awt.geom.NoninvertibleTransformException;
import java.util.HashMap;
import java.util.Map;

/**
 * Kafka configuration class for setting up producer, consumer, and error handling.
 * <p>
 * This configuration enables integration with Kafka, including:
 * <ul>
 *     <li>Producer and consumer factories with JSON serialization/deserialization.</li>
 *     <li>Custom error handling with retry and dead-letter queue support.</li>
 *     <li>Dynamic creation of Kafka topics.</li>
 * </ul>
 * <p>
 * The class uses Spring Kafka and supports configuration through application properties.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Custom error handling via {@link DefaultErrorHandler} with retry and backoff strategies.</li>
 *     <li>JSON serialization and deserialization for Kafka messages.</li>
 *     <li>Dead-letter queue (DLQ) support for handling non-retryable exceptions.</li>
 *     <li>Dynamic topic creation for specified Kafka topics.</li>
 * </ul>
 *
 * @see org.apache.kafka.clients.producer.ProducerConfig
 * @see org.apache.kafka.clients.consumer.ConsumerConfig
 * @see org.springframework.kafka.listener.DefaultErrorHandler
 * @see org.springframework.kafka.core.KafkaTemplate
 * @see org.springframework.kafka.support.serializer.JsonDeserializer
 * @see org.springframework.kafka.config.TopicBuilder
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Configuration
public class KafkaConfig {

    /**
     * The Spring environment to load application properties.
     */
    @Autowired
    Environment environment;

    /**
     * Configures the Kafka consumer factory.
     * <p>
     * Sets up the configuration for Kafka consumers, including:
     * <ul>
     *     <li>Bootstrap servers.</li>
     *     <li>JSON deserialization with error handling.</li>
     *     <li>Consumer group ID and trusted packages for deserialization.</li>
     * </ul>
     *
     * @return a configured {@link ConsumerFactory} instance
     */
    @Bean
    ConsumerFactory<Long, Object> consumerFactory() {
        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, environment.getProperty("spring.kafka.consumer.bootstrap-servers"));
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);

        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);

        config.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);

        config.put(JsonDeserializer.TRUSTED_PACKAGES,
                environment.getProperty("spring.kafka.consumer.properties.spring.json.trusted.packages"));

        config.put(ConsumerConfig.GROUP_ID_CONFIG,
                environment.getProperty("spring.kafka.consumer.group-id"));

        return new DefaultKafkaConsumerFactory<>(config);
    }

    /**
     * Configures the Kafka listener container factory.
     * <p>
     * This method sets up error handling with retry and dead-letter queue support,
     * allowing retryable and non-retryable exceptions to be processed accordingly.
     * </p>
     *
     * @param consumerFactory the consumer factory used to create Kafka listeners
     * @param kafkaTemplate   the Kafka template used for publishing to the dead-letter queue
     * @return a configured {@link ConcurrentKafkaListenerContainerFactory} instance
     */
    @Bean
    ConcurrentKafkaListenerContainerFactory<Long, Object> kafkaListenerContainerFactory
            (ConsumerFactory<Long, Object> consumerFactory, KafkaTemplate kafkaTemplate) {

        DefaultErrorHandler errorHandler =
                new DefaultErrorHandler(new DeadLetterPublishingRecoverer(kafkaTemplate),
                        new FixedBackOff(3000, 3));

        errorHandler.addNotRetryableExceptions(NoninvertibleTransformException.class);

        errorHandler.addRetryableExceptions(RetryableException.class);

        ConcurrentKafkaListenerContainerFactory<Long, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory);

        factory.setCommonErrorHandler(errorHandler);
        return factory;
    }

    /**
     * Configures the Kafka template for message production.
     * <p>
     * This method provides a {@link KafkaTemplate} configured with the producer factory.
     * </p>
     *
     * @param producerFactory the producer factory used to create the Kafka template
     * @return a configured {@link KafkaTemplate} instance
     */
    @Bean
    KafkaTemplate<Long, Object> kafkaTemplate(ProducerFactory<Long, Object> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    /**
     * Configures the Kafka producer factory.
     * <p>
     * Sets up the configuration for Kafka producers, including bootstrap servers
     * and JSON serialization for message payloads.
     * </p>
     *
     * @return a configured {@link ProducerFactory} instance
     */
    @Bean
    ProducerFactory<Long, Object> producerFactory() {
        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, environment.getProperty("spring.kafka.consumer.bootstrap-servers"));

        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);

        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(config);
    }

    /**
     * Creates the Kafka topic "user.created".
     * <p>
     * This method dynamically creates a Kafka topic with 3 partitions and 1 replica.
     * </p>
     *
     * @return a configured {@link NewTopic} instance
     */
    @Bean
    NewTopic createTopic1() {
        return TopicBuilder
                .name("user.created")
                .partitions(3)
                .replicas(1)
                .build();
    };
}
