package com.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MySQL {
    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mawellaresort", "root", "123456");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getInstance(){
        return connection;
    }

    private static Statement Connection() throws Exception {
        Statement statement = connection.createStatement();
        return statement;
    }

    public static void Iud(String query){
        try {
            Connection().executeUpdate(query);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static ResultSet Search(String query)throws Exception{
        ResultSet resultSet = Connection().executeQuery(query);
        return resultSet;
    }
}
