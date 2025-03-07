package com.andrew.service;

import com.andrew.domain.Producer;
import com.andrew.repository.ProducerRepository;

public class ProducerService {

    public static void save(Producer producer) {
        ProducerRepository.save(producer);
    }

    public static void delete(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid value for ID");
        }
        ProducerRepository.delete(id);
    }

}
