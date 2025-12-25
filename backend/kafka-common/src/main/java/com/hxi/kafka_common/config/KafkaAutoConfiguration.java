package com.hxi.kafka_common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@Configuration
@EnableKafka
public class KafkaAutoConfiguration {
    // Enables Kafka infra (@KafkaListener etc.)
}
