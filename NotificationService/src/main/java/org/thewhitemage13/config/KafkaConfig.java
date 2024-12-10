package org.thewhitemage13.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.LongSerializer;
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
import org.thewhitemage13.exceptions.RetryableException;

import java.awt.geom.NoninvertibleTransformException;
import java.util.HashMap;
import java.util.Map;

/**
 * Configuration class for Kafka integration within the application.
 * <p>
 * This class defines Kafka producer, consumer, and admin settings, ensuring seamless communication
 * between application components using Kafka. It provides beans for KafkaTemplate, ProducerFactory,
 * ConsumerFactory, and more, allowing for customization of Kafka's behavior.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Configures Kafka producer and consumer factories with serializers and deserializers.</li>
 *     <li>Handles error scenarios with a {@link DefaultErrorHandler} and supports retry logic.</li>
 *     <li>Defines a {@link KafkaAdmin} for administrative operations.</li>
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
     * Configures the Kafka Admin client for administrative operations.
     *
     * @return a {@link KafkaAdmin} bean with custom configurations
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
     * Configures the Kafka ConsumerFactory with deserializers and group settings.
     *
     * @return a {@link ConsumerFactory} for consuming messages from Kafka topics
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
     * Configures the Kafka Listener Container Factory with custom error handling and retry logic.
     *
     * @param consumerFactory the {@link ConsumerFactory} for the Kafka listener
     * @param kafkaTemplate   the {@link KafkaTemplate} for sending messages to dead-letter topics
     * @return a {@link ConcurrentKafkaListenerContainerFactory} for Kafka listeners
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
     * Configures the KafkaTemplate for sending messages to Kafka topics.
     *
     * @param producerFactory the {@link ProducerFactory} for producing messages
     * @return a {@link KafkaTemplate} bean
     */
    @Bean
    KafkaTemplate<Long, Object> kafkaTemplate(ProducerFactory<Long, Object> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    /**
     * Configures the Kafka ProducerFactory with serializers and broker settings.
     *
     * @return a {@link ProducerFactory} for producing messages to Kafka topics
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
}
