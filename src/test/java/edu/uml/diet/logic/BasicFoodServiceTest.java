package edu.uml.diet.logic;

import edu.uml.diet.logic.BasicFoodService;
import edu.uml.diet.model.BasicFood;
import edu.uml.diet.model.Portion;
import edu.uml.diet.persistence.PersistanceFoodService;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class BasicFoodServiceTest extends TestCase {

    BasicFood basicfood;
    Double portionSize;
    Portion portion;
    String foodName;
    PersistanceFoodService persistanceFoodService_MOCK;

    @Before
    public void setUp() {
        foodName = "Apple";
        persistanceFoodService_MOCK =   Mockito.mock(PersistanceFoodService.class);
    }

    /**
     * test to verify foodSearch() method
     */
    @Test
    public void testFoodSearch() throws Exception{
        when(persistanceFoodService_MOCK.searchForFood(any(String.class))).thenReturn(new BasicFood(foodName,1,1,1,1,1,foodName));
        BasicFoodService basicFoodService = new BasicFoodService(persistanceFoodService_MOCK);
        Portion portion = basicFoodService.foodSearch(foodName);
        assertEquals("testFoodSearch: Positive", portion.getFood().getName(), foodName);

    }

    /**
     *test to verify foodListSearch. Only tests length for list not empty
     */
    @Test
    public void testFoodListSearch()throws Exception{
        BasicFoodService basicFoodService = new BasicFoodService(persistanceFoodService_MOCK);
        List<BasicFood> basicFoodList= new ArrayList<>();
        basicFoodList.add(new BasicFood(foodName, 1, 1, 1, 1, 1, foodName));
        when(persistanceFoodService_MOCK.searchForFoodList(any(String.class))).thenReturn(basicFoodList);
        List<Portion> portionList = basicFoodService.foodListSearch(foodName);
        assertFalse("testFoodListSearch: positive",portionList.isEmpty());
    }
}