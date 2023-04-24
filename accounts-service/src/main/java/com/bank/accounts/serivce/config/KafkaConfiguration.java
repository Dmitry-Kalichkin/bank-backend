package com.bank.accounts.serivce.config;

import com.bank.accounts.serivce.data.dto.operation.OperationResponseDto;
import com.bank.accounts.serivce.data.enums.KafkaTopic;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfiguration {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public ProducerFactory<String, OperationResponseDto> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerProps());
    }

    @Bean
    public Map<String, Object> producerProps() {
        Map<String, Object> props = new HashMap<>();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return props;
    }

    @Bean
    public KafkaTemplate<String, OperationResponseDto> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public NewTopic operations() {
        return TopicBuilder
                .name(KafkaTopic.OPERATIONS_TOPIC_NAME.getValue())
                .replicas(2)
                .partitions(10)
                .build();
    }

    @Bean
    public NewTopic operationsWithReply() {
        return TopicBuilder
                .name(KafkaTopic.LOANS_PAYMENTS_OPERATIONS_TOPIC_NAME.getValue())
                .replicas(2)
                .partitions(10)
                .build();
    }

    @Bean
    public NewTopic paymentResponses() {
        return TopicBuilder
                .name(KafkaTopic.PAYMENT_RESPONSES_TOPIC_NAME.getValue())
                .replicas(2)
                .partitions(10)
                .build();
    }

    @Bean
    public StringJsonMessageConverter jsonConverter() {
        return new StringJsonMessageConverter();
    }
}
