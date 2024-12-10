package org.thewhitemage13.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

/**
 * Configuration class for setting up Amazon S3 client.
 * <p>
 * This class configures the {@code S3Client} for interaction with Amazon S3
 * using AWS SDK for Java. It uses static credentials and a specified region
 * from the application's properties file.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Configures AWS S3 client with static credentials.</li>
 *     <li>Allows specification of AWS region and access keys via properties.</li>
 *     <li>Provides a bean for the {@code S3Client} to be used across the application.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Configuration
public class AmazonS3Config {

    /**
     * The AWS region where the S3 bucket is hosted.
     * <p>
     * This value is injected from the application's properties file using
     * the {@code amazon.s3.region} property.
     * </p>
     */
    @Value("${amazon.s3.region}")
    private String region;

    /**
     * The AWS access key ID for authentication.
     * <p>
     * This value is injected from the application's properties file using
     * the {@code aws.access.key.id} property.
     * </p>
     */
    @Value("${aws.access.key.id}")
    private String accessKeyId;

    /**
     * The AWS secret access key for authentication.
     * <p>
     * This value is injected from the application's properties file using
     * the {@code aws.secret.access.key} property.
     * </p>
     */
    @Value("${aws.secret.access.key}")
    private String secretAccessKey;

    /**
     * Configures and provides an {@code S3Client} bean.
     * <p>
     * The {@code S3Client} is configured with the specified AWS region and
     * static credentials for authentication.
     * </p>
     *
     * @return a configured instance of {@code S3Client}.
     */
    @Bean
    public S3Client amazonS3() {
        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKeyId, secretAccessKey)))
                .build();
    }
}
