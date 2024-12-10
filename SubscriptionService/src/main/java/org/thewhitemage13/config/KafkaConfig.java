package org.thewhitemage13.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
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
 * Kafka configuration for the application.
 * <p>
 * This class sets up the Kafka producer, consumer, error handling, and topic definitions.
 * It is designed to facilitate interaction with Kafka, including publishing and consuming messages,
 * handling errors, and configuring Dead Letter Topics (DLT) for failed messages.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Configuration of Kafka producer and consumer factories.</li>
 *     <li>Implementation of error handling with retries and dead-letter publishing.</li>
 *     <li>Creation of Kafka topics, including DLTs for failed message handling.</li>
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
     * Configures the Kafka admin client.
     * <p>
     * This client is used to manage Kafka resources, such as creating topics.
     * </p>
     *
     * @return the KafkaAdmin bean
     */
    @Bean
    KafkaAdmin kafkaAdmin() {
        Map<String, Object> config = new HashMap<>();
        config.put
                (
                        AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,
                        environment.getProperty("spring.kafka.producer.bootstrap-servers")
                );

        return new KafkaAdmin(config);
    }

    /**
     * Configures the Kafka consumer factory.
     * <p>
     * Sets up deserializers, trusted packages, and group ID for the consumer.
     * </p>
     *
     * @return the configured ConsumerFactory
     */
    @Bean
    ConsumerFactory<Long, Object> consumerFactory() {
        Map<String, Object> config = new HashMap<>();

        config.put
                (
                        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                        environment.getProperty("spring.kafka.consumer.bootstrap-servers")
                );

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
     * This factory manages consumer containers and error handling strategies,
     * including retries and Dead Letter Topic (DLT) publishing.
     * </p>
     *
     * @param consumerFactory the consumer factory to use
     * @param kafkaTemplate   the Kafka template for dead-letter publishing
     * @return the configured ConcurrentKafkaListenerContainerFactory
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
     * Configures the Kafka template for publishing messages.
     *
     * @param producerFactory the producer factory to use
     * @return the configured KafkaTemplate
     */
    @Bean
    KafkaTemplate<Long, Object> kafkaTemplate(ProducerFactory<Long, Object> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    /**
     * Configures the Kafka producer factory.
     * <p>
     * Sets up serializers for producing messages to Kafka.
     * </p>
     *
     * @return the configured ProducerFactory
     */
    @Bean
    ProducerFactory<Long, Object> producerFactory() {
        Map<String, Object> config = new HashMap<>();

        config.put
                (
                        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                        environment.getProperty("spring.kafka.consumer.bootstrap-servers")
                );

        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);

        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(config);
    }

    /**
     * Creates the "subscription.created" topic.
     *
     * @return the topic configuration
     */
    @Bean
    NewTopic createTopic1() {
        return TopicBuilder
                .name("subscription.created")
                .partitions(3)
                .replicas(1)
                .build();
    };

    /**
     * Creates the Dead Letter Topic (DLT) for the "subscription.created" topic.
     *
     * @return the DLT configuration
     */
    @Bean
    NewTopic createDLTTopic1() {
        return TopicBuilder
                .name("subscription.created.DLT")
                .partitions(3)
                .replicas(1)
                .build();
    }

    /**
     * Creates the "subscription.deleted" topic.
     *
     * @return the topic configuration
     */
    @Bean
    NewTopic createTopic2() {
        return TopicBuilder
                .name("subscription.deleted")
                .partitions(3)
                .replicas(1)
                .build();
    };

    /**
     * Creates the Dead Letter Topic (DLT) for the "subscription.deleted" topic.
     *
     * @return the DLT configuration
     */
    @Bean
    NewTopic createDeletedDLTTopic2() {
        return TopicBuilder
                .name("subscription.deleted.DLT")
                .partitions(3)
                .replicas(1)
                .build();
    }
}
