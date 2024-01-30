package ru.job4j.ref;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class UserCache {
    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger();

    public void add(User user) {
        id.incrementAndGet();
        User userNew = User.of(user.getName());
        userNew.setId(id.get());
        users.put(id.get(), userNew);
    }

    public User findById(int id) {
        User userNew = User.of(users.get(id).getName());
        userNew.setId(id);
        return userNew;
    }

    public List<User> findAll() {
        int id = 0;
        List<User> userList = new ArrayList<>();
        for (User user : users.values()) {
            User userNew = User.of(user.getName());
            userNew.setId(++id);
            userList.add(userNew);
        }
        return userList;
    }
}