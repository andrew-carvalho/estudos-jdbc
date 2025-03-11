package com.andrew.repository;

import com.andrew.domain.ConnectionFactory;
import com.andrew.domain.Producer;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class ProducerRepository {

    public static void save(Producer producer) {
        String sqlQuery = String.format("INSERT INTO `anime_store`.`producer` (`name`) VALUES ('%s');", producer.getName());

        try (Connection connection = ConnectionFactory.getConnection(); Statement statement = connection.createStatement()) {
            int rowsAffected = statement.executeUpdate(sqlQuery);
            log.info("Inserted Producer {}, rows affected by statement: {}", producer.getName(), rowsAffected);
        } catch (SQLException e) {
            log.error("Error on inserting Producer {}", producer.getName());
            throw new RuntimeException(e);
        }
    }

    public static void delete(int id) {
        String sqlQuery = String.format("DELETE FROM `anime_store`.`producer` WHERE `id`='%d';", id);

        try (Connection connection = ConnectionFactory.getConnection(); Statement statement = connection.createStatement()) {
            int rowsAffected = statement.executeUpdate(sqlQuery);
            log.info("Producer {} deleted, rows affected by Statement: {}", id, rowsAffected);
        } catch (SQLException e) {
            log.error("Error on deleting Producer {}", id);
            throw new RuntimeException(e);
        }
    }

    public static void update(Producer producer) {
        String sqlQuery = String.format("UPDATE `anime_store`.`producer` SET `name` = '%s' WHERE `id` = %d", producer.getName(), producer.getId());

        try (Connection connection = ConnectionFactory.getConnection(); Statement statement = connection.createStatement()) {
            int rowsAffected = statement.executeUpdate(sqlQuery);
            log.info("Producer {} changed, rows affected by Statement: {}", producer.getId(), rowsAffected);
        } catch (SQLException e) {
            log.error("Error on updating Producer {}", producer.getId());
            throw new RuntimeException(e);
        }
    }

    public static List<Producer> findAll() {
        String sqlQuery = "SELECT id, name FROM `anime_store`.`producer`";
        List<Producer> producers = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlQuery)) {
            while (resultSet.next()) {
                Producer producer = Producer.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .build();
                producers.add(producer);
            }
        } catch (SQLException e) {
            log.error("Error on retrieving all producers");
            throw new RuntimeException(e);
        }

        return producers;
    }

    public static List<Producer> findByName(String name) {
        String sqlQuery = String.format("SELECT id, name FROM `anime_store`.`producer` WHERE `name` LIKE '%%%s%%'", name);
        List<Producer> producers = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlQuery)) {
            while (resultSet.next()) {
                Producer producer = Producer.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .build();
                producers.add(producer);
            }
        } catch (SQLException e) {
            log.error("Error on retrieving producer with name {}", name);
            throw new RuntimeException(e);
        }

        return producers;
    }

}
