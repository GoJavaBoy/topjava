package ru.javawebinar.topjava;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.datajpa.DataJpaUserRepository;
import ru.javawebinar.topjava.web.Profiles;

import java.util.Arrays;
import java.util.List;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management (ARM)
        try (ClassPathXmlApplicationContext appCtx = new ClassPathXmlApplicationContext()) {
            appCtx.getEnvironment().setActiveProfiles(Profiles.REPOSITORY_IMPLEMENTATION, Profiles.getActiveDbProfile());
            appCtx.setConfigLocations("spring/spring-app.xml", "spring/spring-db.xml");
            appCtx.refresh();
//            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
//            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
//            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));
//            System.out.println();
//
//            MealRestController mealController = appCtx.getBean(MealRestController.class);
//            List<MealTo> filteredMealsWithExcess =
//                    mealController.getBetween(
//                            LocalDate.of(2020, Month.JANUARY, 30), LocalTime.of(7, 0),
//                            LocalDate.of(2020, Month.JANUARY, 31), LocalTime.of(11, 0));
//            filteredMealsWithExcess.forEach(System.out::println);
//            System.out.println();
//            System.out.println(mealController.getBetween(null, null, null, null));
            DataJpaUserRepository userRepo = appCtx.getBean(DataJpaUserRepository.class);
            User newUser = userRepo.get(UserTestData.USER_ID);
            List<Meal> newUserMeals = newUser.getMeals();
            System.out.println(Arrays.toString(newUserMeals.toArray()));
//            newUser.setMeals(MealTestData.meals);
//            newUserMeals = newUser.getMeals();
//            System.out.println();
//            System.out.println();
//            System.out.println(Arrays.toString(newUserMeals.toArray()));

        }
    }
}
