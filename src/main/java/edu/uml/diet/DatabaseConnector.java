package edu.uml.diet;

import java.sql.*;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * Created by Raymond on 2/22/2015.
 */
public class DatabaseConnector {
    private static Configuration configuration;

    /**
     *
     * @return server connection configuration
     */
    private static Configuration getServerConfiguration(){
        configuration = new Configuration();
        configuration.configure("hibernateServer.cfg.xml");
        return configuration;
    }

    /**
     *
     * @return database connection configuration
     */
    private static Configuration getDatabaseConfiguration(){
        configuration = new Configuration();
        configuration.configure("hibernateDatabase.cfg.xml");
        return configuration;
    }

    /**
     * Method used to open database server connection
     *
     * @return opened database connection
     */
    public static Connection getServerConnection() throws DatabaseConnectorException {
        Connection connection = null;
        Configuration configuration = getServerConfiguration();
        try {
            Class.forName(configuration.getProperty("hibernate.connection.driver_class"));
            String databaseUrl = configuration.getProperty("hibernate.connection.url");
            String username = configuration.getProperty("hibernate.connection.username");
            String password = configuration.getProperty("hibernate.connection.password");
            connection = DriverManager.getConnection(databaseUrl, username, password);
        }
        catch(ClassNotFoundException | SQLException e){
            throw new DatabaseConnectorException("Could not connect to database." + e.getMessage(), e);
        }
        return connection;
    }

    /**
     * Method used to open database server connection
     *
     * @return opened database connection
     */
    public static Connection getDatabaseConnection() throws DatabaseConnectorException {
        Connection connection = null;
        Configuration configuration = getDatabaseConfiguration();
        try {
            Class.forName(configuration.getProperty("hibernate.connection.driver_class"));
            String databaseUrl = configuration.getProperty("hibernate.connection.url");
            String username = configuration.getProperty("hibernate.connection.username");
            String password = configuration.getProperty("hibernate.connection.password");
            connection = DriverManager.getConnection(databaseUrl, username, password);
        }
        catch(ClassNotFoundException | SQLException e){
            throw new DatabaseConnectorException("Could not connect to database." + e.getMessage(), e);
        }
        return connection;
    }

    /*
  * @return SessionFactory for use with database transactions
  */
    public static SessionFactory getSessionFactory() {
        SessionFactory sessionFactory = null;

        if (sessionFactory == null) {

            Configuration configuration = getDatabaseConfiguration();

            ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .buildServiceRegistry();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        return sessionFactory;
    }
}
