package com.kostserver.utils;

import com.github.javafaker.Faker;

public class DatabaseSeeder {

    String[] emails = {"admin@mail.com", "user1@mail.com", "user2@mail.com"};
    String[] roles = {"ROLE_ADMIN","ROLE_USER_PENYEWA", "ROLE_USER_PENYEDIA"};
    String defaultPassword = "password";

    Faker faker = new Faker();


}
