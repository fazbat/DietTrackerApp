package edu.uml.diet;


import edu.uml.diet.model.BasicFood;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Created by rgoolishian on 3/4/2015.
 */
public class DbFoodServiceTest {
    private DatabaseConnector databaseConnector;
    private BasicFood basicFood1;
    private BasicFood basicFood2;
    private BasicFood basicFood3;
    private BasicFood basicFood4;
    private List<BasicFood> basicFoodList;
    private DatabaseBuilder databaseBuilder;
    private Connection connection;
    private Session session;

    @Before
    public void setup()throws DatabaseConnectorException, PersistanceFoodServiceException, IOException{
        databaseBuilder = new DatabaseBuilder(databaseConnector,"DietTracker");
        databaseConnector = new DatabaseConnector();
        basicFood1 = new BasicFood("cheese1", 1, 2, 3, 4);
        basicFood2 = new BasicFood("cheese2", 1, 2, 3, 4);
        basicFood3 = new BasicFood("cheese3", 1, 2, 3, 4);
        if (!databaseBuilder.CheckIfDbExists()) {
            databaseBuilder.CreateDatabase();
        }
        if (!databaseBuilder.CheckIfTableExists("FOOD")) {
            databaseBuilder.CreateFoodTable();
        }
        connection = databaseConnector.getDatabaseConnection();
        session = databaseConnector.getSessionFactory().openSession();
    }

    @Test
    public void testCreateFood() throws PersistanceFoodServiceException,IOException, DuplicateFoodException, SQLException{
        DbFoodService dbFoodService = new DbFoodService();
        dbFoodService.createFood(basicFood1, connection, session);
        BasicFood basicFood = dbFoodService.searchForFood("cheese1");
        assertTrue(basicFood != null);
    }

    @Test
    public void testSearchForFood() throws PersistanceFoodServiceException, IOException, DuplicateFoodException, SQLException{
        DbFoodService dbFoodService = new DbFoodService();
        dbFoodService.createFood(basicFood1, connection, session);
        basicFood4 = dbFoodService.searchForFood("%");
        assertTrue(basicFood1.equals(basicFood4));
    }

    @Test
    public void testSearchForFoodList()throws PersistanceFoodServiceException, IOException, DuplicateFoodException, SQLException{
        DbFoodService dbFoodService = new DbFoodService();
        dbFoodService.createFood(basicFood1, connection, session);
        dbFoodService.createFood(basicFood2, connection, session);
        dbFoodService.createFood(basicFood3, connection, session);
        basicFoodList = dbFoodService.searchForFoodList("%");
        assertTrue(basicFoodList.size() == 3);
    }

    @Test
    public void testPopulateFoodDatabase()throws PersistanceFoodServiceException, IOException,
            SQLException, DatabaseConnectorException, DuplicateFoodException{
        DbFoodService dbFoodService = new DbFoodService();
        dbFoodService.populateFoodDatabase();
    }


    @After
    public void teardown() throws DatabaseConnectorException, SQLException{
        DatabaseBuilder databaseBuilder = new DatabaseBuilder(databaseConnector, "DietTracker");
        if(databaseBuilder.CheckIfDbExists()) {
            Statement statement = databaseConnector.getDatabaseConnection().createStatement();
            String sql = "DROP TABLE IF EXISTS FOOD";
            statement.executeUpdate(sql);
            sql = "DROP DATABASE IF EXISTS DietTracker";
            statement.executeUpdate(sql);
        }
        assertFalse(databaseBuilder.CheckIfDbExists());
    }

}
