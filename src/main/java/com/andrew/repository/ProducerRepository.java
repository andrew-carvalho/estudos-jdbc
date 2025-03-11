package com.andrew.repository;

import com.andrew.domain.ConnectionFactory;
import com.andrew.domain.Producer;
import lombok.extern.log4j.Log4j2;

import java.sql.*;
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

    public static void showProducerMetaData() {
        String sqlQuery = "SELECT id, name FROM `anime_store`.`producer`";
        log.info("Displaying table meta data");

        try (Connection connection = ConnectionFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlQuery)) {
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                log.info("Table name: {}", resultSetMetaData.getTableName(i));
                log.info("Column position: {}", i);
                log.info("Column name: {}", resultSetMetaData.getColumnName(i));
                log.info("Column size: {}", resultSetMetaData.getColumnDisplaySize(i));
                log.info("Column type: {}", resultSetMetaData.getColumnTypeName(i));
            }
        } catch (SQLException e) {
            log.error("Error on retrieving producer meta data");
            throw new RuntimeException(e);
        }
    }

    public static void showDatabaseMetaData() {
        log.info("Displaying driver meta data");

        try (Connection connection = ConnectionFactory.getConnection()) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();

            if (databaseMetaData.supportsResultSetType(ResultSet.TYPE_FORWARD_ONLY)) {
                log.info("Supports TYPE_FORWARD_ONLY");
                if (databaseMetaData.supportsResultSetConcurrency(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE)) {
                    log.info("Supports TYPE_FORWARD_ONLY CONCUR_UPDATABLE");
                }
            }

            if (databaseMetaData.supportsResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE)) {
                log.info("Supports TYPE_SCROLL_INSENSITIVE");
                if (databaseMetaData.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
                    log.info("Supports TYPE_SCROLL_INSENSITIVE CONCUR_UPDATABLE");
                }
            }

            if (databaseMetaData.supportsResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE)) {
                log.info("Supports TYPE_SCROLL_SENSITIVE");
                if (databaseMetaData.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
                    log.info("Supports TYPE_SCROLL_SENSITIVE CONCUR_UPDATABLE");
                }
            }
        } catch (SQLException e) {
            log.error("Error on retrieving driver meta data");
            throw new RuntimeException(e);
        }
    }

    public static void showTypeScrollWorking() {
        String sqlQuery = "SELECT id, name FROM `anime_store`.`producer`";

        try (Connection connection = ConnectionFactory.getConnection();
             Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
             ResultSet resultSet = statement.executeQuery(sqlQuery)) {
            resultSet.last();
            log.info("Last line: {}", Producer.builder()
                    .id(resultSet.getInt("id"))
                    .name(resultSet.getString("name"))
                    .build());
            log.info("Row number: {}", resultSet.getRow());
            log.info("Row absolute? {}", resultSet.absolute(2));
            log.info("Row number: {}", resultSet.getRow());
            log.info("Row relative? {}", resultSet.relative(1));
            log.info("Row number: {}", resultSet.getRow());
            log.info("Is last? {}", resultSet.isLast());
            log.info("Is first? {}", resultSet.isFirst());
        } catch (SQLException e) {
            log.error("Error on showing Type Scroll");
            throw new RuntimeException(e);
        }
    }
}
