package com.codeaches.kafka.basics;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MyKafkaConsumer {

  Logger log = LoggerFactory.getLogger(MyKafkaConsumer.class);

  @KafkaListener(topics = "${kafka.consumer.topic}")
  public void listen(ConsumerRecord<String, String> kafkaMessage) {

    log.info(String.format("Key       = %s", kafkaMessage.key()));
    log.info(String.format("Value     = %s", kafkaMessage.value()));
    log.info(String.format("Topic     = %s", kafkaMessage.topic()));
    log.info(String.format("Partition = %s", kafkaMessage.partition()));
    log.info(String.format("Topic     = %s", kafkaMessage.topic()));
  }
}
