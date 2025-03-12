package com.andrew.application;

import com.andrew.domain.Producer;
import com.andrew.service.ProducerService;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class ProducerRepositoryTest {

    public static void main(String[] args) {
        // Producer producer = Producer.builder().id().name("NHK").build();
        // ProducerService.save(producer);

        // ProducerService.delete(16);

        // Producer producerUpdate = Producer.builder().id(4).name("NHK").build();
        // ProducerService.update(producerUpdate);

        // List<Producer> producers = ProducerService.findAll();
        // log.info("Producers found {}", producers);

        // String producersName = "Mad";
        // List<Producer> producers = ProducerService.findByName(producersName);
        // log.info("Producers found with name '{}': {}", producersName, producers);

        // ProducerService.showProducerMetaData();

        // ProducerService.showDatabaseMetaData();

        // ProducerService.showTypeScrollWorking();

        // String producersName = "Mad";
        // List<Producer> producers = ProducerService.findByNameAndUpdateNameToUpperCase(producersName);
        // log.info("Producers found with name '{}': {}", producersName, producers);

        // String producersName = "A1-Studio";
        // List<Producer> producers = ProducerService.findByNameAndInsertIfNotFound(producersName);
        // log.info("Producers found with name '{}': {}", producersName, producers);

        // String producersName = "A1-Studio";
        // ProducerService.findByNameAndDelete(producersName);

        // String name = "N";
        // List<Producer> producers = ProducerService.findByNameWithPreparedStatement(name);
        // log.info("Producers found with name {}: {}", name, producers);

        Producer producerUpdate = Producer.builder().id(4).name("NHK").build();
        ProducerService.update(producerUpdate);
    }

}
