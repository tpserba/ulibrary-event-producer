package com.serbatic.ulearnkafka.ulibraryeventsproducer.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serbatic.ulearnkafka.ulibraryeventsproducer.domain.LibraryEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
@Slf4j
public class LibraryEventProducer {
    @Autowired
    private KafkaTemplate<Integer, String> kafkaTemplate;
    @Autowired
    ObjectMapper objectMapper;

    public void sendLibraryEvent(LibraryEvent libraryEvent) throws JsonProcessingException {
        Integer key = libraryEvent.getLibraryEventId();
        String value = objectMapper.writeValueAsString(libraryEvent);
        ListenableFuture<SendResult<Integer, String>> listenableFuture = kafkaTemplate.sendDefault(key, value);
        listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                handleFailure(key, value, ex);
            }

            // Invoked if publish is successful
            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                handleSuccess(key, value, result);
            }
        });


    }

    private void handleFailure(Integer key, String value, Throwable ex) {
        log.error("Error while sending the Message. The exception is {}", ex.getMessage());
        try {
            throw ex;
        } catch (Throwable e) {
            log.error("Error in onFailure {}", e.getMessage());

        }

    }

    private void handleSuccess(Integer key, String value, SendResult<Integer, String> result) {
        log.info("Message sent successfully for the key: {} and the value is {} and partition is {}", key, value, result.getRecordMetadata().partition());
    }

}
