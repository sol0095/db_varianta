package cz.vsb.database;

import cz.vsb.application.files.PropertyLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static String username = PropertyLoader.loadProperty("username");
    private static String password = PropertyLoader.loadProperty("password");
    private static String url = PropertyLoader.loadProperty("url");
    private static String databaseName = PropertyLoader.loadProperty("databaseName");
    private static String connectionString;

    public static void setConnectionString(){
        connectionString =
                url + "database=" + databaseName + ";"
                        + "user=" + username + ";"
                        + "password=" + password + ";"
                        + "encrypt=true;"
                        + "trustServerCertificate=true;"
                        + "loginTimeout=3600;"
                        + "rewriteBatchedStatements=true;";
    }

    public static Connection getConnection(){
        Connection con = null;
        try {
            con = DriverManager.getConnection(connectionString);
        } catch (SQLException throwables) {
            System.out.println(throwables);
        }

        return con;
    }
}
