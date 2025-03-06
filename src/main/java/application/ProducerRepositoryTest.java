package application;

import domain.Producer;
import repository.ProducerRepository;

public class ProducerRepositoryTest {

    public static void main(String[] args) {
        Producer producer = Producer.ProducerBuilder.builder().name("Mad House").build();
        ProducerRepository.save(producer);
    }

}
