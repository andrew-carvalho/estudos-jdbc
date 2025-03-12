package com.andrew.repository;

import com.andrew.domain.ConnectionFactory;
import com.andrew.domain.Producer;
import com.andrew.listener.JdbcRowSetListener;
import lombok.extern.log4j.Log4j2;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.JdbcRowSet;
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

    public static List<Producer> findByNameAndUpdateNameToUpperCase(String name) {
        String sqlQuery = String.format("SELECT id, name FROM `anime_store`.`producer` WHERE `name` LIKE '%%%s%%'", name);
        List<Producer> producers = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
             Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
             ResultSet resultSet = statement.executeQuery(sqlQuery)) {
            while (resultSet.next()) {
                resultSet.updateString("name", resultSet.getString("name").toUpperCase());
                resultSet.updateRow();

                Producer producer = Producer.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .build();
                producers.add(producer);
            }
        } catch (SQLException e) {
            log.error("Error on retrieving producer {}", name);
            throw new RuntimeException(e);
        }

        return producers;
    }

    public static List<Producer> findByNameAndInsertIfNotFound(String name) {
        String sqlQuery = String.format("SELECT id, name FROM `anime_store`.`producer` WHERE `name` LIKE '%%%s%%'", name);
        List<Producer> producers = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
             Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
             ResultSet resultSet = statement.executeQuery(sqlQuery)) {
            if (!resultSet.next()) {
                resultSet.moveToInsertRow();
                resultSet.updateString("name", name);
                resultSet.insertRow();
            }

            resultSet.beforeFirst();

            while (resultSet.next()) {
                Producer producer = Producer.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .build();
                producers.add(producer);
            }
        } catch (SQLException e) {
            log.error("Error on retrieving or creating producer {}", name);
            throw new RuntimeException(e);
        }

        return producers;
    }

    public static void findByNameAndDelete(String name) {
        String sqlQuery = String.format("SELECT id, name FROM `anime_store`.`producer` WHERE `name` LIKE '%%%s%%'", name);

        try (Connection connection = ConnectionFactory.getConnection();
             Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
             ResultSet resultSet = statement.executeQuery(sqlQuery)) {
            while (resultSet.next()) {
                log.info("Deleting {}", resultSet.getString("name"));
                resultSet.deleteRow();
            }
        } catch (SQLException e) {
            log.error("Error on retrieving or deleting producer {}", name);
            throw new RuntimeException(e);
        }
    }

    public static List<Producer> findByNameWithPreparedStatement(String name) {
        List<Producer> producers = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = createPreparedStatementForFindByName(connection, name);
             ResultSet resultSet = statement.executeQuery()) {
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

    public static PreparedStatement createPreparedStatementForFindByName(Connection connection, String name) throws SQLException {
        String sqlQuery = "SELECT id, name FROM `anime_store`.`producer` WHERE `name` LIKE ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setString(1, String.format("%%%s%%", name));
        return preparedStatement;
    }

    public static void updateWithPreparedStatement(Producer producer) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = createPreparedStatementForUpdate(connection, producer)) {
            int rowsAffected = statement.executeUpdate();
            log.info("Producer {} changed, rows affected by Statement: {}", producer.getId(), rowsAffected);
        } catch (SQLException e) {
            log.error("Error on updating Producer {}", producer.getId());
            throw new RuntimeException(e);
        }
    }

    private static PreparedStatement createPreparedStatementForUpdate(Connection connection, Producer producer) throws SQLException {
        String sqlQuery = "UPDATE `anime_store`.`producer` SET `name` = ? WHERE `id` = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setString(1, producer.getName());
        preparedStatement.setInt(2, producer.getId());
        return preparedStatement;
    }

    public static List<Producer> findByNameWithCallableStatement(String name) {
        List<Producer> producers = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
             CallableStatement callableStatement = createCallableStatementForFindByName(connection, name);
             ResultSet resultSet = callableStatement.executeQuery()) {
            while (resultSet.next()) {
                producers.add(Producer.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .build());
            }
        } catch (SQLException e) {
            log.error("Error on retrieving producer {}", name);
            throw new RuntimeException(e);
        }

        return producers;
    }

    private static CallableStatement createCallableStatementForFindByName(Connection connection, String name) throws SQLException {
        String sqlQuery = "CALL sp_get_producer_by_name(?)";
        CallableStatement callableStatement = connection.prepareCall(sqlQuery);
        callableStatement.setString(1, String.format("%%%s%%", name));
        return callableStatement;
    }

    public static List<Producer> findByNameWithJdbcRowSet(String name) {
        String sqlQuery = "SELECT id, name FROM `anime_store`.`producer` WHERE `name` LIKE ?";
        List<Producer> producers = new ArrayList<>();

        try (JdbcRowSet jdbcRowSet = ConnectionFactory.getJdbcRowSet()) {
            jdbcRowSet.setCommand(sqlQuery);
            jdbcRowSet.setString(1, String.format("%%%s%%", name));
            jdbcRowSet.execute();
            while (jdbcRowSet.next()) {
                Producer producer = Producer.builder()
                        .id(jdbcRowSet.getInt("id"))
                        .name(jdbcRowSet.getString("name"))
                        .build();
                producers.add(producer);
            }
        } catch (SQLException e) {
            log.error("Error on retrieving producer {}", name);
            throw new RuntimeException(e);
        }

        return producers;
    }

    public static void updateWithJdbcRowSet(Producer producer) {
        String sqlQuery = "SELECT id, name FROM `anime_store`.`producer` WHERE `id` = ?";

        try (JdbcRowSet jdbcRowSet = ConnectionFactory.getJdbcRowSet()) {
            jdbcRowSet.addRowSetListener(new JdbcRowSetListener());
            jdbcRowSet.setCommand(sqlQuery);
            jdbcRowSet.setInt(1, producer.getId());
            jdbcRowSet.execute();

            if (!jdbcRowSet.next()) {
                return;
            }

            jdbcRowSet.updateString("name", producer.getName());
            jdbcRowSet.updateRow();
        } catch (SQLException e) {
            log.error("Error on updating Producer {}", producer.getId());
            throw new RuntimeException(e);
        }
    }

    public static void updateWithCachedRowSet(Producer producer) {
        String sqlQuery = "SELECT id, name FROM `producer` WHERE `id` = ?";

        try (Connection connection = ConnectionFactory.getConnection();
             CachedRowSet cachedRowSet = ConnectionFactory.getCachedRowSet()) {
            connection.setAutoCommit(false);
            cachedRowSet.setCommand(sqlQuery);
            cachedRowSet.setInt(1, producer.getId());
            cachedRowSet.execute(connection);

            if (!cachedRowSet.next()) {
                return;
            }

            cachedRowSet.updateString("name", producer.getName());
            cachedRowSet.updateRow();
            cachedRowSet.acceptChanges();
        } catch (SQLException e) {
            log.error("Error on updating Producer {}", producer.getId());
            throw new RuntimeException(e);
        }
    }

    public static void saveTransaction(List<Producer> producers) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            connection.setAutoCommit(false);
            createPreparedStatementForTransaction(connection, producers);
        } catch (SQLException e) {
            log.error("Error on saving transaction");
            throw new RuntimeException(e);
        }
    }

    public static void createPreparedStatementForTransaction(Connection connection, List<Producer> producers) throws SQLException {
        String sqlQuery = "INSERT INTO `anime_store`.`producer` (`name`) VALUES (?)";
        boolean hasError = false;

        for (Producer producer : producers) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
                if (producer.getName().equals("Teste")) {
                    throw new SQLException("Error on adding teste");
                }
                preparedStatement.setString(1, producer.getName());
                preparedStatement.execute();
            } catch (SQLException e) {
                hasError = true;
                throw new RuntimeException(e);
            }
        }

        if (hasError) {
            log.error("An error has occurred, rollback transaction");
            connection.rollback();
            return;
        }

        connection.commit();
        log.info("All rows inserted");
    }
}
