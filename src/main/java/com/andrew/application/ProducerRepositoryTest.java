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

        // Producer producerUpdate = Producer.builder().id(4).name("NHK").build();
        // ProducerService.update(producerUpdate);

        // String name = "N";
        // List<Producer> producers = ProducerService.findByNameWithCallableStatement(name);
        // log.info("Producers found with name {}: {}", name, producers);

        // String name = "N";
        // List<Producer> producers = ProducerService.findByNameWithJdbcRowSet(name);
        // log.info("Producers found with name {}: {}", name, producers);

        // Producer producerUpdate = Producer.builder().id(4).name("NHK").build();
        // ProducerService.updateWithJdbcRowSet(producerUpdate);

        // Producer producerUpdate = Producer.builder().id(4).name("nhk").build();
        // ProducerService.updateWithCachedRowSet(producerUpdate);

        Producer producer1 = Producer.builder().name("Andrew").build();
        Producer producer2 = Producer.builder().name("Teste").build();
        Producer producer3 = Producer.builder().name("Outro").build();
        ProducerService.saveTransaction(List.of(producer1, producer2, producer3));
    }

}
