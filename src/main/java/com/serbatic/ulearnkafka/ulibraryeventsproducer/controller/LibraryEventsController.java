package com.serbatic.ulearnkafka.ulibraryeventsproducer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.serbatic.ulearnkafka.ulibraryeventsproducer.domain.LibraryEvent;
import com.serbatic.ulearnkafka.ulibraryeventsproducer.producer.LibraryEventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LibraryEventsController {
    @Autowired
    LibraryEventProducer libraryEventProducer;

    @PostMapping("/v1/library-event")
    public ResponseEntity<LibraryEvent> postLibraryEvent(@RequestBody LibraryEvent libraryEvent){
        // Invoke Kafka producer
        try {
            libraryEventProducer.sendLibraryEvent(libraryEvent);
            return  ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
