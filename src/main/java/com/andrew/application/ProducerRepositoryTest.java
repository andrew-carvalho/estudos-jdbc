package com.andrew.application;

import com.andrew.domain.Producer;
import com.andrew.repository.ProducerRepository;

public class ProducerRepositoryTest {

    public static void main(String[] args) {
        Producer producer = Producer.builder().name("NHK").build();
        ProducerRepository.save(producer);
    }

}
