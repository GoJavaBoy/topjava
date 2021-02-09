package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {
    void addMeal(Meal meal);
    void updateMeal(Meal meal, Long id);
    void deleteMeal(Long mealId);
    Meal getMealById(Long mealId);
    List<Meal> getAllMeal();
}
