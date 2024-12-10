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
 * Configuration class for setting up Kafka components and properties.
 * <p>
 * This class configures Kafka producers, consumers, topics, and error handling
 * mechanisms for seamless communication with Kafka brokers.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Creates Kafka producers and consumers with specified configurations.</li>
 *     <li>Defines Kafka topics with partitions and replication factors.</li>
 *     <li>Implements error handling strategies using Dead Letter Queues (DLQs).</li>
 *     <li>Supports retries for specific exception types.</li>
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
     * Creates a KafkaAdmin bean for managing Kafka configurations.
     *
     * @return a {@link KafkaAdmin} instance
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
     *
     * @return a {@link ConsumerFactory} for Kafka consumers
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
     *
     * @param consumerFactory the consumer factory
     * @param kafkaTemplate   the Kafka template for publishing dead letter messages
     * @return a {@link ConcurrentKafkaListenerContainerFactory} instance
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
     * Configures the Kafka template for producing messages.
     *
     * @param producerFactory the producer factory
     * @return a {@link KafkaTemplate} instance
     */
    @Bean
    KafkaTemplate<Long, Object> kafkaTemplate(ProducerFactory<Long, Object> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    /**
     * Configures the Kafka producer factory.
     *
     * @return a {@link ProducerFactory} for Kafka producers
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
     * Defines the "post.created" topic.
     *
     * @return a {@link NewTopic} instance for the topic
     */
    @Bean
    NewTopic createTopic1() {
        return TopicBuilder
                .name("post.created")
                .partitions(3)
                .replicas(1)
                .build();
    };

    /**
     * Defines the "post.deleted" topic.
     *
     * @return a {@link NewTopic} instance for the topic
     */
    @Bean
    NewTopic createTopic2() {
        return TopicBuilder
                .name("post.deleted")
                .partitions(3)
                .replicas(1)
                .build();
    };

    /**
     * Defines the "post.updated" topic.
     *
     * @return a {@link NewTopic} instance for the topic
     */
    @Bean
    NewTopic createTopic3() {
        return TopicBuilder
                .name("post.updated")
                .partitions(3)
                .replicas(1)
                .build();
    };

}
