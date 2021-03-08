package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.TestMatcher;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles("datajpa")
public class DataJpaUserServiceTest extends UserServiceTest {

    @Autowired
    private UserService service;

    @Test
    public void get () {
        User user = service.get(USER_ID);
        List<Meal> userMeals = user.getMeals();
        userMeals.sort((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()));
        System.out.println(Arrays.toString(userMeals.toArray()));
        TestMatcher.usingIgnoringFieldsComparator("user").assertMatch(userMeals, MealTestData.meals);
    }


}
