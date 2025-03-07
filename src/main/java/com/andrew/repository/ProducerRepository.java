package com.andrew.repository;

import com.andrew.domain.ConnectionFactory;
import com.andrew.domain.Producer;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Log4j2
public class ProducerRepository {

    public static void save(Producer producer) {
        String sqlQuery = String.format("INSERT INTO `anime_store`.`producer` (`name`) VALUES ('%s');", producer.getName());

        try (Connection connection = ConnectionFactory.getConnection(); Statement statement = connection.createStatement()) {
            int rowsAffected = statement.executeUpdate(sqlQuery);
            log.info("Rows affected by statement: {}", rowsAffected);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
