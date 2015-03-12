package edu.uml.diet.logic;

import edu.uml.diet.model.Portion;

import java.util.List;

/**
 * Interface for searching getting food information from persistence Layer
 */
public interface FoodService {

    //method to return SINGLE portion information to UI Layer
    Portion foodSearch(String food) throws FoodServiceException;

    //method to return LIST of portions information to UI Layer
    List<Portion> foodListSearch(String food) throws FoodServiceException;
}