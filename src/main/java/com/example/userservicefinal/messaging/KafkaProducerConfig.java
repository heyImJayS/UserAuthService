package com.example.userservicefinal.messaging;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class KafkaProducerConfig {
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    private KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerConfig(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
//        @Bean
//    public ProducerFactory<String, String> producerFactory() {
//        try {
//            Map<String, Object> configProps = new HashMap<>();
//            configProps.put(
//                    ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
//                    bootstrapAddress);
//            configProps.put(
//                    ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
//                    StringSerializer.class);
//            configProps.put(
//                    ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
//                    StringSerializer.class);
//            ProducerFactory<String, String> productFactory= new DefaultKafkaProducerFactory<>(configProps);
//            return productFactory;
//        }catch(Exception e){
//            System.out.println("Something wrong in Creating Kafka template");
//        }
//        return null;
//    }
//
//    @Bean
//    public KafkaTemplate<String, String> kafkaTemplate() {
//        return new KafkaTemplate<>(producerFactory());
//    }


    public void sendMessage(String topic, String message){
        kafkaTemplate.send(topic,message);
    }
}
