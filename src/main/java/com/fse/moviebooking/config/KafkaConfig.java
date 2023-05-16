package com.fse.moviebooking.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.fse.moviebooking.main.model.TicketDetail;

@Configuration
public class KafkaConfig {
	
	@Value(value="${kafka.bootstrapAddress}")
	private String bootstrapAddress;
	
	private static final Logger log=LoggerFactory.getLogger(KafkaConfig.class);
	
	@Bean
	public ProducerFactory<String, String> producerFactory(){
		log.info("In: Producer 1");
		Map<String,Object> properties = new HashMap<>();
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		return new DefaultKafkaProducerFactory<>(properties);
	}
	
	@Bean
	public ProducerFactory<String, TicketDetail> pf(){
		log.info("In: Producer 2");
		Map<String,Object> properties = new HashMap<>();
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return new DefaultKafkaProducerFactory<>(properties);
	}
	
	@Bean
	public KafkaTemplate<String, String> kafkaTemplate(){
		log.info("In: Template 1");
		return new KafkaTemplate<>(producerFactory());
	}
	
	@Bean
	public KafkaTemplate<String, TicketDetail> kt(){
		log.info("In: Template 2");
		return new KafkaTemplate<>(pf());
	}
	
	@Bean
	public ReplyingKafkaTemplate<String, TicketDetail,TicketDetail> replyKafkaTemplate(ProducerFactory<String, TicketDetail> pf,KafkaMessageListenerContainer<String,TicketDetail> lc){
		log.info("In: Reply Template");
		ReplyingKafkaTemplate<String, TicketDetail,TicketDetail> replyingKafkaTemplate = new ReplyingKafkaTemplate<>(pf,lc);
		replyingKafkaTemplate.setSharedReplyTopic(true);
		
		return replyingKafkaTemplate;
	}
	
	@Bean
	  public Map<String, Object> consumerConfigs() {
		log.info("In: Consumer Config");
	    Map<String, Object> props = new HashMap<>();
	    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapAddress);
	    props.put(ConsumerConfig.GROUP_ID_CONFIG, "mb-group");
	    return props;
	  }
	
	@Bean
	public ConsumerFactory<String, TicketDetail> consumerFactorySr(){
		log.info("In: Consumer Reply");
		return new DefaultKafkaConsumerFactory<>(consumerConfigs(),new StringDeserializer(),new JsonDeserializer<>(TicketDetail.class));
		
	}
	
	
	 @Bean
	  public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, TicketDetail>> replyKafkaListenerConatainerFactory() {
		 log.info("In: Reply Listener");
	    ConcurrentKafkaListenerContainerFactory<String, TicketDetail> factory = new ConcurrentKafkaListenerContainerFactory<>();
	    factory.setConsumerFactory(consumerFactorySr());
	    factory.setReplyTemplate(kt());
	    
	    return factory;
	  }
	
	@Bean
	  public KafkaMessageListenerContainer<String, TicketDetail> replyContainer(ConsumerFactory<String, TicketDetail> cf) {
			log.info("In: Reply Container");
	        ContainerProperties containerProperties = new ContainerProperties("read-available-tickets-redirect","read-booked-redirect");
	        return new KafkaMessageListenerContainer<>(cf, containerProperties);
	    }

}
