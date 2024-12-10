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
 * Configuration class for Apache Kafka setup.
 * <p>
 * This class sets up the producer, consumer, and admin configurations for Kafka,
 * including serializers, deserializers, error handling, and the creation of topics.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Configures producer and consumer factories for Kafka communication.</li>
 *     <li>Sets up error handling mechanisms including retries and dead letter topics.</li>
 *     <li>Defines topics with specified partitions and replication factors.</li>
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
     * Configures the Kafka admin client with bootstrap server details.
     *
     * @return a KafkaAdmin instance for managing Kafka topics.
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
     * Sets up the deserializer configurations and group ID for the consumer.
     * </p>
     *
     * @return a configured {@code ConsumerFactory} for Kafka consumers.
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
     * Includes custom error handling logic with retry mechanisms and
     * dead letter topic handling.
     * </p>
     *
     * @param consumerFactory the Kafka consumer factory.
     * @param kafkaTemplate   the Kafka template for message handling.
     * @return a configured {@code ConcurrentKafkaListenerContainerFactory}.
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
     *
     * @param producerFactory the Kafka producer factory.
     * @return a configured {@code KafkaTemplate} for sending messages to Kafka.
     */
    @Bean
    KafkaTemplate<Long, Object> kafkaTemplate(ProducerFactory<Long, Object> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    /**
     * Configures the Kafka producer factory.
     * <p>
     * Sets up the serializer configurations for Kafka message production.
     * </p>
     *
     * @return a configured {@code ProducerFactory} for Kafka producers.
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
     * Creates the "media.upload" topic.
     *
     * @return a new Kafka topic named "media.upload" with three partitions and one replica.
     */
    @Bean
    NewTopic createTopic1() {
        return TopicBuilder
                .name("media.upload")
                .partitions(3)
                .replicas(1)
                .build();
    };

    /**
     * Creates the "media.deleted" topic.
     *
     * @return a new Kafka topic named "media.deleted" with three partitions and one replica.
     */
    @Bean
    NewTopic createTopic2() {
        return TopicBuilder
                .name("media.deleted")
                .partitions(3)
                .replicas(1)
                .build();
    };
}
