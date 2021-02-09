package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoInMemoryImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private final MealDao mealDao;
    public static final int CALORIES_PER_DAY = 2000;

    public MealServlet() {
        super();
        this.mealDao = new MealDaoInMemoryImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (req.getParameterMap().isEmpty()) {
            List<MealTo> mealsTo = MealsUtil.filteredByStreams(mealDao.getAllMeal(), null, null, CALORIES_PER_DAY);
            req.setAttribute("mealsToList", mealsTo);
            log.debug("forward to meals.jsp from GET(empty params)");
            req.getRequestDispatcher("meals.jsp").forward(req, resp);
            return;
        }
        if (action.equalsIgnoreCase("delete")) {
            Long mealId = Long.parseLong(req.getParameter("id"));
            log.info("deleting meal");
            mealDao.deleteMeal(mealId);
        } else if (action.equalsIgnoreCase("update")) {
            Long mealId = Long.parseLong(req.getParameter("id"));
            Meal oldMeal = mealDao.getMealById(mealId);
            req.setAttribute("oldMeal", oldMeal);
            log.info("updating meal");
            req.getRequestDispatcher("addOrEditMeal.jsp").forward(req, resp);
            return;
        } else if (action.equalsIgnoreCase("create")) {
            log.info("creating meal");
            req.getRequestDispatcher("addOrEditMeal.jsp").forward(req, resp);
            return;
        }
        log.debug("redirect to meals from GET");
        resp.sendRedirect("meals");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        if (req.getParameter("id").isEmpty()) {
            LocalDateTime localDateTime = LocalDateTime.parse(req.getParameter("data"));
            String description = req.getParameter("description");
            int calories = Integer.parseInt(req.getParameter("calories"));
            mealDao.addMeal(new Meal(localDateTime, description, calories));
            log.info("meal created");
        } else {
            Long mealId = Long.parseLong(req.getParameter("id"));
            Meal mealForUpdate = mealDao.getMealById(mealId);
            LocalDateTime localDateTime = LocalDateTime.parse(req.getParameter("data"));
            String description = req.getParameter("description");
            int calories = Integer.parseInt(req.getParameter("calories"));
            mealForUpdate.setDateTime(localDateTime);
            mealForUpdate.setDescription(description);
            mealForUpdate.setCalories(calories);
            log.info("meal updated");
        }
        log.debug("redirect to meals from POST");
        resp.sendRedirect("meals");
    }
}
