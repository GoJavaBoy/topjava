package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(100002, USER_ID);
        assertMatch(meal, mealUserDay30_1);
    }

    @Test
    public void getWrongUserMeal() {
        assertThrows(NotFoundException.class, () -> service.get(100003, ADMIN_ID));
    }

    @Test
    public void delete() {
        service.delete(100002, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(100002, USER_ID));
    }

    @Test
    public void deleteWrongUserMeal() {
        assertThrows(NotFoundException.class, () -> service.delete(100003, ADMIN_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> betweenUser = service.getBetweenInclusive(LocalDate.of(2020, 1, 31), LocalDate.of(2020, 1, 31), USER_ID);
        assertMatch(betweenUser, mealUserDay31_7, mealUserDay31_6, mealUserDay31_5, mealUserDay31_4);
    }

    @Test
    public void getAll() {
        List<Meal> allForUser = service.getAll(USER_ID);
        List<Meal> allForAdmin = service.getAll(ADMIN_ID);
        assertMatch(allForUser, mealUserDay31_7, mealUserDay31_6, mealUserDay31_5, mealUserDay31_4,
                mealUserDay30_3, mealUserDay30_2, mealUserDay30_1);
        assertMatch(allForAdmin, mealAdminDay31_4, mealAdminDay31_3, mealAdminDay30_2, mealAdminDay30_1);
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        MealTestData.assertMatch(service.get(100002, USER_ID), getUpdated());
    }

    @Test
    public void updateWrongUserMeal() {
        Meal updated = getUpdated();
        assertThrows(NotFoundException.class, () -> service.update(updated, ADMIN_ID));
    }

    @Test
    public void create() {
        Meal created = service.create(getNew(), USER_ID);
        Integer newId = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, USER_ID), newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Duplicate", 111), USER_ID));
    }
}