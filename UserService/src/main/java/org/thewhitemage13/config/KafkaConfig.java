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
 * Configuration class for Apache Kafka integration.
 * <p>
 * This class sets up Kafka Producer, Consumer, and Admin clients, along with
 * topics and error handling mechanisms. It enables robust message handling
 * with support for retry logic and dead-letter queues.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Producer and consumer factories configured for JSON serialization and deserialization.</li>
 *     <li>Custom error handling with retry and dead-letter support.</li>
 *     <li>Dynamic topic creation for user events such as creation, update, and deletion.</li>
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
     * Configures a KafkaAdmin bean for managing Kafka topics and metadata.
     *
     * @return a {@code KafkaAdmin} instance
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
     * Configures a consumer factory for handling messages with JSON deserialization.
     *
     * @return a {@code ConsumerFactory} instance
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
     * Configures a KafkaListenerContainerFactory with error handling.
     *
     * @param consumerFactory the consumer factory
     * @param kafkaTemplate   the Kafka template for publishing dead-letter messages
     * @return a {@code ConcurrentKafkaListenerContainerFactory} instance
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
     * Configures a KafkaTemplate for sending messages.
     *
     * @param producerFactory the producer factory
     * @return a {@code KafkaTemplate} instance
     */
    @Bean
    KafkaTemplate<Long, Object> kafkaTemplate(ProducerFactory<Long, Object> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    /**
     * Configures a producer factory for JSON serialization.
     *
     * @return a {@code ProducerFactory} instance
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
     * Creates a Kafka topic for user creation events.
     *
     * @return a {@code NewTopic} instance
     */
    @Bean
    NewTopic createTopic1() {
        return TopicBuilder
                .name("user.created")
                .partitions(3)
                .replicas(1)
                .build();
    };

    /**
     * Creates a Kafka topic for user update events.
     *
     * @return a {@code NewTopic} instance
     */
    @Bean
    NewTopic createTopic2() {
        return TopicBuilder
                .name("user.updated")
                .partitions(3)
                .replicas(1)
                .build();
    };

    /**
     * Creates a Kafka topic for user deletion events.
     *
     * @return a {@code NewTopic} instance
     */
    @Bean
    NewTopic createTopic3() {
        return TopicBuilder
                .name("user.deleted")
                .partitions(3)
                .replicas(1)
                .build();
    };

}
