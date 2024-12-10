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
import org.thewhitemage13.exceptions.RetryableException;

import java.awt.geom.NoninvertibleTransformException;
import java.util.HashMap;
import java.util.Map;

/**
 * Configuration class for Apache Kafka integration.
 * <p>
 * This class defines beans for configuring Kafka producers, consumers,
 * error handling, and topic creation. It leverages Spring's Kafka support to
 * simplify Kafka setup and management.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Dynamic creation of Kafka topics using {@link KafkaAdmin}.</li>
 *     <li>Custom consumer and producer factories with error handling.</li>
 *     <li>Dead letter queue integration for unprocessed messages.</li>
 *     <li>Retry mechanisms with customizable back-off strategy.</li>
 * </ul>
 *
 * <h2>Usage:</h2>
 * <p>
 * This configuration is automatically picked up by Spring Boot during application startup.
 * It sets up Kafka components for seamless integration with Kafka brokers.
 * </p>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Configuration
public class KafkaConfig {
    @Autowired
    Environment environment;

    /**
     * Configures the Kafka Admin client for managing topics and other Kafka resources.
     *
     * @return a {@link KafkaAdmin} bean for administrative operations
     */
    @Bean
    KafkaAdmin kafkaAdmin() {
        Map<String, Object> config = new HashMap<>();
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, environment.getProperty("spring.kafka.producer.bootstrap-servers"));
        return new KafkaAdmin(config);
    }

    /**
     * Configures a Kafka Consumer Factory with deserialization and error handling.
     *
     * @return a {@link ConsumerFactory} for consuming Kafka messages
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
     * Configures a Kafka Listener Container Factory with custom error handling.
     *
     * @param consumerFactory the {@link ConsumerFactory} for consuming messages
     * @param kafkaTemplate   the {@link KafkaTemplate} for sending messages to dead letter topics
     * @return a {@link ConcurrentKafkaListenerContainerFactory} bean
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
     * Configures a Kafka Template for sending messages.
     *
     * @param producerFactory the {@link ProducerFactory} for producing messages
     * @return a {@link KafkaTemplate} bean
     */
    @Bean
    KafkaTemplate<Long, Object> kafkaTemplate(ProducerFactory<Long, Object> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    /**
     * Configures a Kafka Producer Factory with serialization settings.
     *
     * @return a {@link ProducerFactory} for producing Kafka messages
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
     * Creates the "post.like.created" Kafka topic.
     *
     * @return a {@link NewTopic} bean
     */
    @Bean
    NewTopic createTopic1() {
        return TopicBuilder
                .name("post.like.created")
                .partitions(3)
                .replicas(1)
                .build();
    };

    /**
     * Creates the "post.like.deleted" Kafka topic.
     *
     * @return a {@link NewTopic} bean
     */
    @Bean
    NewTopic createTopic2() {
        return TopicBuilder
                .name("post.like.deleted")
                .partitions(3)
                .replicas(1)
                .build();
    };

    /**
     * Creates the "comment.like.created" Kafka topic.
     *
     * @return a {@link NewTopic} bean
     */
    @Bean
    NewTopic createTopic3() {
        return TopicBuilder
                .name("comment.like.created")
                .partitions(3)
                .replicas(1)
                .build();
    };

    /**
     * Creates the "comment.like.deleted" Kafka topic.
     *
     * @return a {@link NewTopic} bean
     */
    @Bean
    NewTopic createTopic4() {
        return TopicBuilder
                .name("comment.like.deleted")
                .partitions(3)
                .replicas(1)
                .build();
    };
}
