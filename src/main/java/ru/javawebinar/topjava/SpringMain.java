package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));
            adminUserController.create(new User(null, "Petr", "petulko@mail.ru", "password", Role.USER));
            adminUserController.create(new User(null, "Abragim", "abr1920@mail.ru", "password", Role.USER));
            adminUserController.create(new User(null, "Vasilij", "vasili9@mail.ru", "password", Role.USER));
            adminUserController.create(new User(null, "Gulaga", "gulaga@mail.ru", "password", Role.USER));
            adminUserController.create(new User(null, "Brasvisim", "bravisim@mail.ru", "password", Role.USER));
            adminUserController.create(new User(null, "Stepaska", "stepaska@mail.ru", "password", Role.USER));
            adminUserController.create(new User(null, "Stepaska", "astepaska@mail.ru", "password", Role.USER));
           // adminUserController.getAll().forEach(System.out::println);
            MealService ms = appCtx.getBean(MealService.class);
            MealRestController mc = appCtx.getBean(MealRestController.class);
         //   Collection<Meal> meal = ms.getAll(2);
            System.out.println(mc.get(1));
        }
    }
}
