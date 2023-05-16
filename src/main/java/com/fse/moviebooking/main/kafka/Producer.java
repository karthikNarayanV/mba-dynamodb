package com.fse.moviebooking.main.kafka;

import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.fse.moviebooking.main.model.TicketDetail;


@Component
public class Producer {

	private  static final Logger log = LoggerFactory.getLogger(Producer.class);
	
	@Autowired
	private final KafkaTemplate<String, String> kafkaTemplate;
	
	@Autowired
    private ReplyingKafkaTemplate<String, TicketDetail, TicketDetail> template;

	public Producer(KafkaTemplate<String, String> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}
	
	public void sendMessage(String movieName, String theatreName,String status) {
		log.info("Start: Send Message");
		String topicName="set-status";
		String message=movieName+":"+theatreName+":"+status;
		ListenableFuture<SendResult<String,String>> future = kafkaTemplate.send(topicName, message);
		future.addCallback(new ListenableFutureCallback<SendResult<String,String>>() {

			@Override
			public void onSuccess(SendResult<String, String> result) {
				log.info("Sent Message Successfully");
			}

			@Override
			public void onFailure(Throwable ex) {
				log.error("failed to send message", ex);
				
			}
		});
		log.info("End: Send Message");
		
		
	}
	
	public TicketDetail kafkaRequestReply(String topicName,TicketDetail request) throws Exception {
		log.info("Start: Send and get Reply");
		String replyTopicName=topicName+"-redirect";
		ProducerRecord<String, TicketDetail> record = new ProducerRecord<String,TicketDetail>(topicName, request);
		record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, replyTopicName.getBytes()));
        RequestReplyFuture<String, TicketDetail, TicketDetail> replyFuture = template.sendAndReceive(record);
        SendResult<String, TicketDetail> sendResult = replyFuture.getSendFuture().get();
        sendResult.getProducerRecord().headers().forEach(header -> log.debug(header.key() + ":" + header.value().toString()));
        ConsumerRecord<String, TicketDetail> consumerRecord = replyFuture.get(100000,TimeUnit.SECONDS);
        TicketDetail reply = consumerRecord.value();
        log.info("End: Send and get Reply");
        return reply;
    }
	
	
	
}
