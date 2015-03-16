package edu.uml.diet.gui;

import edu.uml.diet.logic.ServiceFactory;
import edu.uml.diet.logic.UserService;
import edu.uml.diet.logic.UserServiceException;
import edu.uml.diet.model.Day;
import edu.uml.diet.model.Meal;
import edu.uml.diet.model.Portion;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Servlet for handling user login attempts
 *
 * Created by adil on 3/1/15.
 */
public class LoginServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //create session
        HttpSession session = request.getSession(true);
        session.setAttribute("loggedIn", false);

        //TODO may take this out
        //SESSION INITIALIZATION
        ArrayList<Portion> userPortionList =  new ArrayList<>();
        session.setAttribute("userPortionList", userPortionList);
        Day day = new Day(); //day holds meals
        ArrayList<Meal>  meals = new ArrayList<>();
        //create meals
        Meal breakfast = new Meal();
        Meal lunch = new Meal();
        Meal dinner = new Meal();
        Meal snack = new Meal();

        //set names
        breakfast.setName("breakfast");
        lunch.setName("lunch");
        dinner.setName("dinner");
        snack.setName("snack");

        //add them to days
        meals.add(breakfast);
        meals.add(lunch);
        meals.add(dinner);
        meals.add(snack);
        day.setMeals(meals);

        //store them in the session
        session.setAttribute("day", day);

        //nothing else to do, send user to login page
        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //get session
        //if no session exists, create one
        HttpSession session = request.getSession(true);

        //get user inputted info
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserService userService = null;
        try {
            userService = ServiceFactory.getUserServiceInstance();
        } catch (UserServiceException e) {
            throw new ServletException("Error creating userService", e);
        }
        boolean authenticated = false;
        try {
            authenticated = userService.verifyUser(email, password);
            session.setAttribute("loggedIn", authenticated);
        } catch (UserServiceException e) {
            throw new ServletException("Error authenticating", e);
        }

        if (authenticated) {
            response.sendRedirect("welcome");
        } else {
            request.setAttribute("error", "Username not found. Do you want to <a href = \"register.html\"> register</a>?");
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
    }
}
