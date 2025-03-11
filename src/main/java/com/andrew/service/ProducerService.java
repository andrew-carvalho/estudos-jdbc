package com.andrew.service;

import com.andrew.domain.Producer;
import com.andrew.repository.ProducerRepository;

import java.util.List;

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

    public static List<Producer> findAll() {
        return ProducerRepository.findAll();
    }

    public static List<Producer> findByName(String name) {
        return ProducerRepository.findByName(name);
    }

    public static void showProducerMetaData() {
        ProducerRepository.showProducerMetaData();
    }

    public static void showDatabaseMetaData() {
        ProducerRepository.showDatabaseMetaData();
    }

    public static void showTypeScrollWorking() {
        ProducerRepository.showTypeScrollWorking();
    }

    public static List<Producer> findByNameAndUpdateNameToUpperCase(String name) {
        return ProducerRepository.findByNameAndUpdateNameToUpperCase(name);
    }

    public static List<Producer> findByNameAndInsertIfNotFound(String name) {
        return ProducerRepository.findByNameAndInsertIfNotFound(name);
    }

    public static void findByNameAndDelete(String name) {
        ProducerRepository.findByNameAndDelete(name);
    }

    private static void validateId(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid value for ID");
        }
    }

}
