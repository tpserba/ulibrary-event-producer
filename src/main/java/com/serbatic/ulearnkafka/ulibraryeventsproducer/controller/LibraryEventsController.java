package com.serbatic.ulearnkafka.ulibraryeventsproducer.controller;

import com.serbatic.ulearnkafka.ulibraryeventsproducer.domain.LibraryEvent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LibraryEventsController {
    @PostMapping("/v1/library-event")
    public ResponseEntity<LibraryEvent> postLibraryEvent(@RequestBody LibraryEvent libraryEvent){
        // Invoke Kafka producer
        return  ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
    }
}
