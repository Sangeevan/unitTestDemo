package com.example.unitTestDemo.util;

import com.example.unitTestDemo.model.User;

public class UserUtils {
    public static boolean isAdult(User user) {
        return user.getAge() >= 18;
    }
}
