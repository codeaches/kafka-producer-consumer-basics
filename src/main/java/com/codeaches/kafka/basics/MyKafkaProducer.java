package com.codeaches.kafka.basics;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyKafkaProducer {

    Logger log = LoggerFactory.getLogger(MyKafkaProducer.class);

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("/send")
    public void sendDataToKafka(@RequestParam String data) {

	Message<String> dataToKafka = MessageBuilder.withPayload(data)
		.setHeader(KafkaHeaders.MESSAGE_KEY, UUID.randomUUID().toString()).build();

	ListenableFuture<SendResult<String, String>> listenableFuture = kafkaTemplate.send(dataToKafka);

	listenableFuture.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

	    @Override
	    public void onSuccess(SendResult<String, String> result) {

		log.info(String.format("Key       = %s", result.getProducerRecord().key()));
		log.info(String.format("Value     = %s", result.getProducerRecord().value()));
		log.info(String.format("Topic     = %s", result.getRecordMetadata().topic()));
		log.info(String.format("Partition = %s", result.getRecordMetadata().partition()));
		log.info(String.format("Offset    = %s", result.getRecordMetadata().offset()));
	    }

	    @Override
	    public void onFailure(Throwable ex) {
		log.error("Unable to send data to Kafka", ex);
	    }
	});
    }
}
