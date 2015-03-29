package edu.uml.diet.gui;

import edu.uml.diet.logic.FoodService;
import edu.uml.diet.logic.FoodServiceException;
import edu.uml.diet.model.Day;
import edu.uml.diet.model.Meal;
import org.joda.time.DateTime;
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
        FoodService foodService = (FoodService) session.getAttribute("foodService");

        Day day = (Day) session.getAttribute("day");

        //get the user's email to use later
        String email = (String) session.getAttribute("email");
        DateTime dateTime = new DateTime(day.getDate());

        String daySelect;
        daySelect = request.getParameter("newDay");

        //make sure dayselect isn't null
        if(daySelect != null) {
            //show next day
            if (daySelect.equals("next")) {
                dateTime = dateTime.plusDays(1);
                try {
                    day = foodService.getDay(email, dateTime);
                    session.setAttribute("day", day);
                } catch (FoodServiceException e) {
                    throw new ServletException("Could not go to next day", e);
                }
            }
            //show previous day
            if (daySelect.equals("previous")) {
                dateTime = dateTime.minusDays(1);
                try {
                    day = foodService.getDay(email, dateTime);
                    session.setAttribute("day", day);
                } catch (FoodServiceException e) {
                    throw new ServletException("Could not go to next day", e);
                }
            }
            //show today
            if (daySelect.equals("today")) {
                try {
                    day = foodService.getDay(email, DateTime.now());
                } catch (FoodServiceException e) {
                    throw new ServletException("Could not get day ", e);
                }
                session.setAttribute("day", day);
            }
        }

        ArrayList<Meal> mealList = new ArrayList<>(day.getMeals());
        request.setAttribute("day", day);
        request.setAttribute("mealList", mealList);

        request.getRequestDispatcher("/WEB-INF/welcome.jsp").forward(request, response);
    }
}
