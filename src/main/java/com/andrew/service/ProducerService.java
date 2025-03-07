package com.andrew.service;

import com.andrew.domain.Producer;
import com.andrew.repository.ProducerRepository;

public class ProducerService {

    public static void save(Producer producer) {
        ProducerRepository.save(producer);
    }

    public static void delete(int id) {
        validateId(id);
        ProducerRepository.delete(id);
    }

    public static void update(Producer producer) {
        validateId(producer.getId());
        ProducerRepository.update(producer);
    }

    private static void validateId(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid value for ID");
        }
    }

}
