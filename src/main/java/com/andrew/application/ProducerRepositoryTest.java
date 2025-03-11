package com.andrew.application;

import com.andrew.domain.Producer;
import com.andrew.service.ProducerService;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class ProducerRepositoryTest {

    public static void main(String[] args) {
        // Producer producer = Producer.builder().id().name("NHK").build();
        // Producer producer = Producer.builder().id(4).name("NHK").build();
        // ProducerService.save(producer);
        // ProducerService.delete(16);
        // ProducerService.update(producer);
        List<Producer> producers = ProducerService.findAll();
        log.info("Producers found {}", producers);
    }

}
