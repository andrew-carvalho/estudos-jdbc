package application;

import domain.Producer;
import repository.ProducerRepository;

public class ProducerRepositoryTest {

    public static void main(String[] args) {
        Producer producer = Producer.builder().name("Studio Deen").build();
        ProducerRepository.save(producer);
    }

}
