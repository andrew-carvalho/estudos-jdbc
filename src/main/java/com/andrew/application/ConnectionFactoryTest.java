package com.andrew.application;

import com.andrew.domain.ConnectionFactory;

import java.sql.Connection;

public class ConnectionFactoryTest {

    public static void main(String[] args) {
        Connection connection = ConnectionFactory.getConnection();
        System.out.println(connection);
    }

}
