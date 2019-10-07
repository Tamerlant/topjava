package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class MealsUtil {
    public static void main(String[] args) {
        List<Meal> mealList = Arrays.asList(
                new Meal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExcess(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);

    }

    public static List<MealTo>  getFilteredWithExcess(List<Meal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = mealList.stream()
                .collect(Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories)));
//              .collect(Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum);


        return mealList.stream()
                .filter(meal -> TimeUtil.isBetween(meal.getTime(), startTime, endTime))
                .map(meal -> new MealTo(meal.getDateTime(), meal.getDescription(),
                        meal.getCalories(),caloriesSumByDate.get(meal.getDate()) > caloriesPerDay ))
                .collect(toList());
    }
}