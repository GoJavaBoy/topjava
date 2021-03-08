package ru.javawebinar.topjava.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;

@ActiveProfiles("datajpa")
public class DataJpaMealServiceTest extends MealServiceTest {

    @Autowired
    private MealService service;

    @Test
    public void get () {
        Meal meal = service.get(100002, 100000);
        int id = meal.getUser().getId();
       // USER_MATCHER.assertMatch(user, UserTestData.user);
        Assert.assertEquals(id, UserTestData.user.id());
    }
}
