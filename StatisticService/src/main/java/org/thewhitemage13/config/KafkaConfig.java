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
 * Configuration class for Apache Kafka.
 * <p>
 * This class defines beans for Kafka producers, consumers, and administrative operations.
 * It also configures error handling and serialization mechanisms for message processing.
 * </p>
 *
 * <h2>Responsibilities:</h2>
 * <ul>
 *     <li>Set up Kafka producers and consumers with necessary configurations.</li>
 *     <li>Define error handling strategies for Kafka listeners.</li>
 *     <li>Provide administrative configurations for managing Kafka topics.</li>
 * </ul>
 *
 * @see org.apache.kafka.clients.admin.AdminClientConfig
 * @see org.apache.kafka.clients.consumer.ConsumerConfig
 * @see org.apache.kafka.clients.producer.ProducerConfig
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Configuration
public class KafkaConfig {
    @Autowired
    Environment environment;

    /**
     * Configures and returns a KafkaAdmin bean for managing Kafka topics.
     *
     * @return the configured {@code KafkaAdmin} bean
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
     * Configures and returns a consumer factory for Kafka consumers.
     *
     * @return the configured {@code ConsumerFactory} instance
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
     * Configures and returns a Kafka listener container factory.
     *
     * @param consumerFactory the consumer factory to use
     * @param kafkaTemplate the Kafka template for publishing dead-letter messages
     * @return the configured {@code ConcurrentKafkaListenerContainerFactory}
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
     * Configures and returns a Kafka template for message publishing.
     *
     * @param producerFactory the producer factory to use
     * @return the configured {@code KafkaTemplate}
     */
    @Bean
    KafkaTemplate<Long, Object> kafkaTemplate(ProducerFactory<Long, Object> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    /**
     * Configures and returns a producer factory for Kafka producers.
     *
     * @return the configured {@code ProducerFactory} instance
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
