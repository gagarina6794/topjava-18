package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MapMealStorage implements MealStorage {
    private Map<Integer, Meal> map = new ConcurrentHashMap<>();
    private AtomicInteger atomicId = new AtomicInteger(0);

    @Override
    public Meal save(Meal meal) {
        if (meal.getId() == null) {
            meal.setId(atomicId.getAndIncrement());
        }
        return map.put(meal.getId(), meal);
    }

    @Override
    public Meal get(Integer id) {
        Meal meal = (id != null ? map.get(id) : null);
        return meal != null ? meal : new Meal();
    }

    @Override
    public void delete(Integer id) {
        if (id != null) map.remove(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public void clean() {
        map.clear();
    }
}
