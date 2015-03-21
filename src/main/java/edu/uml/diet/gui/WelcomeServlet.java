package edu.uml.diet.gui;

import edu.uml.diet.model.Day;
import edu.uml.diet.model.Meal;
import edu.uml.diet.model.Portion;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by adil on 3/15/15.
 */
public class WelcomeServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //send the user straight on to the welcome page
        HttpSession session = request.getSession();

        //Day day = (Day) session.getAttribute("day");
        //ArrayList<Meal> meals = new ArrayList<Meal>(day.getMeals());
        //request.setAttribute("meals", meals);

        Day day = (Day) session.getAttribute("day");
        ArrayList<Meal> mealList = new ArrayList<>(day.getMeals());
        request.setAttribute("mealList", mealList);
        /*
        for (int j = 0; j < meals2.size(); j++) {
            ArrayList<Portion> newPortion = new ArrayList<>(meals2.get(j).getPortions());
            out.println(meals2.get(j).getName());
            for (int i = 0; i < newPortion.size(); i++) {
                out.println(newPortion.get(i).getFood().getName());
                out.println(newPortion.get(i).getCalories());
            }
        } */

        request.getRequestDispatcher("/WEB-INF/welcome.jsp").forward(request, response);
    }
}
