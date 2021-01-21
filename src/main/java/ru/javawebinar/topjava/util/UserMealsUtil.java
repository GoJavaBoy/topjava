package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        //   List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        // mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles
        List<UserMealWithExcess> userMealWithExcessList = new ArrayList<>();
        Map<Integer, Integer> dayCalories = new HashMap<>();
        for (UserMeal meal : meals) {
            if (dayCalories.get(meal.getDateTime().getDayOfMonth()) == null) {
                dayCalories.put(meal.getDateTime().getDayOfMonth(), meal.getCalories());
            } else dayCalories.merge(meal.getDateTime().getDayOfMonth(), meal.getCalories(), Integer::sum);
        }

        for (UserMeal meal : meals) {
            if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                {
                    int calories = dayCalories.get(meal.getDateTime().getDayOfMonth());
                    if (calories <= caloriesPerDay) {
                        userMealWithExcessList.add(new UserMealWithExcess(meal.getDateTime(),
                                meal.getDescription(), meal.getCalories(), false));
                    } else
                        userMealWithExcessList.add(new UserMealWithExcess(meal.getDateTime(),
                                meal.getDescription(), meal.getCalories(), true));
                }
            }
        }
        return userMealWithExcessList;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        List<UserMealWithExcess> userMealWithExcessList = new ArrayList<>();
        Map<Integer, Integer> dayCalories = new HashMap<>();
        for (UserMeal meal : meals) {
            if (dayCalories.get(meal.getDateTime().getDayOfMonth()) == null) {
                dayCalories.put(meal.getDateTime().getDayOfMonth(), meal.getCalories());
            } else dayCalories.merge(meal.getDateTime().getDayOfMonth(), meal.getCalories(), Integer::sum);
        }

        meals.stream()
                .filter(userMeal -> TimeUtil.isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(), startTime, endTime))
                .forEach(userMeal -> {
                    int calories = dayCalories.get(userMeal.getDateTime().getDayOfMonth());
                            if (calories <= caloriesPerDay) {
                                userMealWithExcessList.add(new UserMealWithExcess(userMeal.getDateTime(),
                                        userMeal.getDescription(), userMeal.getCalories(), false));
                            } else
                                userMealWithExcessList.add(new UserMealWithExcess(userMeal.getDateTime(),
                                        userMeal.getDescription(), userMeal.getCalories(), true));
                        }
                );
        return userMealWithExcessList;
    }

    public static List<UserMealWithExcess> filteredByStreamsOptional2(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        return meals.stream()
                .collect(Collector.of(
                        //Создаем экземпляр контейнера
                        () -> new AbstractMap.SimpleImmutableEntry<>(
                                new ArrayList<UserMeal>(), new HashMap<Integer, Integer>()),
                        //Кладем новые элементы в контейнер + доп. логика
                        (abstractEntry, userMeal) -> {
                            abstractEntry.getKey().add(userMeal);//(new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), false));
                            abstractEntry.getValue().merge(userMeal.getDateTime().getDayOfMonth(), userMeal.getCalories(), (v1, v2) -> (int) v1 + (int) v2);
                        },
                        //функция, которая объединяет два контейнера в один???
                        (map, list) -> null,
                        //преобразуем в конечный результат
                        (abstractEntry) -> {
                            List<UserMealWithExcess> result = new ArrayList<>();
                            Map map = abstractEntry.getValue();
                            List<UserMeal> userMeals = abstractEntry.getKey();

                            userMeals.forEach(userMeal -> {
                                if (TimeUtil.isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                                    if ((int) map.get(userMeal.getDateTime().getDayOfMonth()) > caloriesPerDay) {
                                        result.add(new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), true));
                                    } else
                                        result.add(new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), false));
                                }
                            });
                            return result;
                        }
                ));
    }
}
