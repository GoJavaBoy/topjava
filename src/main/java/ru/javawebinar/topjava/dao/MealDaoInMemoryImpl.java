package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class MealDaoInMemoryImpl implements MealDao {
    private static final AtomicLong id = new AtomicLong(0);
    private static final Map<Long, Meal> MEALS_DATA = Collections.synchronizedMap(new HashMap<>());
    static {
        MEALS_DATA.put(id.incrementAndGet(), new Meal(id.get(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        MEALS_DATA.put(id.incrementAndGet(), new Meal(id.get(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        MEALS_DATA.put(id.incrementAndGet(), new Meal(id.get(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        MEALS_DATA.put(id.incrementAndGet(), new Meal(id.get(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        MEALS_DATA.put(id.incrementAndGet(), new Meal(id.get(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        MEALS_DATA.put(id.incrementAndGet(), new Meal(id.get(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        MEALS_DATA.put(id.incrementAndGet(), new Meal(id.get(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    @Override
    public void addMeal(Meal meal) {
        meal.setMealId(id.incrementAndGet());
        MEALS_DATA.put(id.get(), meal);
    }

    @Override
    public void updateMeal(Meal meal, Long id) throws NoSuchElementException {
        Meal mealForUpdate = getMealById(id);
        if (mealForUpdate==null) throw new NoSuchElementException();
        mealForUpdate.setCalories(meal.getCalories());
        mealForUpdate.setDescription(meal.getDescription());
        mealForUpdate.setDateTime(meal.getDateTime());
    }

    @Override
    public void deleteMeal(Long mealId) throws NoSuchElementException {
        if (MEALS_DATA.get(mealId)==null) throw new NoSuchElementException();
        MEALS_DATA.remove(mealId);
    }

    @Override
    public Meal getMealById(Long mealId) throws NoSuchElementException {
        if (MEALS_DATA.get(mealId)==null) throw new NoSuchElementException();
        return MEALS_DATA.get(mealId);
    }

    @Override
    public List<Meal> getAllMeal() {
        return new ArrayList<>(MEALS_DATA.values());
    }
}
