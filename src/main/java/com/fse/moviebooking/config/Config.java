package com.fse.moviebooking.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;




@EnableKafka
@Configuration
public class Config {
	
	@Value(value="${kafka.bootstrapAddress}")
	private String bootstrapAddress;
	
	@Value(value="${group.id}")
	private String groupId;
	
	private static final Logger log=LoggerFactory.getLogger(Config.class);
	
	
	
	
	@Bean
	public ConsumerFactory<String, String> consumerFactory(){
		log.info("In: Consumer Factory");
		Map<String,Object> properties = new HashMap<>();
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, "mba-group");
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		return new DefaultKafkaConsumerFactory<>(properties);
	}
	
	
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerConatainerFactory(){
		log.info("In: Listener Container Factory");
		ConcurrentKafkaListenerContainerFactory<String , String> factory= new ConcurrentKafkaListenerContainerFactory<>() ;
		factory.setConsumerFactory(consumerFactory());
		
		return factory;
	}
	
	
	
	
}
