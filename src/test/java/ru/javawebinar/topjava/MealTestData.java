package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {
    public static final int NOT_FOUND = 10;

    public static final Meal mealUserDay30_1 = new Meal(100002, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal mealUserDay30_2 = new Meal(100003, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal mealUserDay30_3 = new Meal(100004, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal mealUserDay31_4 = new Meal(100005, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal mealAdminDay30_1 = new Meal(100006, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед для Админа", 1000);
    public static final Meal mealAdminDay30_2 = new Meal(100007, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин для Админа", 500);
    public static final Meal mealUserDay31_5 = new Meal(100008, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal mealUserDay31_6 = new Meal(100009, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
    public static final Meal mealUserDay31_7 = new Meal(100010, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);
    public static final Meal mealAdminDay31_3 = new Meal(100011, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед для Админа", 500);
    public static final Meal mealAdminDay31_4 = new Meal(100012, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин для Админа", 410);

    public static Meal getNew(){
        return new Meal(LocalDateTime.of(2022, Month.JANUARY, 22, 22, 22), "New", 222);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(mealUserDay30_1);
        updated.setCalories(123);
        updated.setDescription("UpdatedMeal");
        updated.setDateTime(LocalDateTime.of(2011, Month.JANUARY, 11, 11, 11));
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }
}
