package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        return service.create(meal);
    }

    public void delete(int id) {
        log.info("delete meal {} from user {}", id, SecurityUtil.authUserId());
        service.delete(id, SecurityUtil.authUserId());
    }

    public Meal get(int id) {
        log.info("get meal {}, from user {}", id, SecurityUtil.authUserId());
        return service.get(id, SecurityUtil.authUserId());
    }

    public Collection<MealTo> getAll() {
        log.info("get all meals from user {}", SecurityUtil.authUserId());
        return MealsUtil.getTos(service.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay());
    }

    public Collection<MealTo> getFilteredAll(LocalDate startDate, LocalDate endDate,
                                             LocalTime startTime, LocalTime endTime) {
        log.info("get all filtered meals from user {}", SecurityUtil.authUserId());
        return getAll().stream()
                .filter(mealTo -> DateTimeUtil.isBetweenHalfOpen(mealTo.getDateTime().toLocalDate(),
                        startDate != null ? startDate : LocalDate.MIN,
                        endDate != null ? endDate.plusDays(1) : LocalDate.MAX))
                .filter(mealTo -> DateTimeUtil.isBetweenHalfOpen(mealTo.getDateTime().toLocalTime(),
                        startTime != null ? startTime : LocalTime.MIN,
                        endTime != null ? endTime : LocalTime.MAX))
                .collect(Collectors.toList());
    }

    public void update(Meal meal, int id) {
        log.info("update meal {} with id={}", meal, id);
        service.update(meal);
    }

}