package com.adobe.learning.core.dao;

import com.adobe.learning.core.models.User;

import java.util.List;

public interface UserDao {

    void register(User usuario);

    void update(User usuario);

    void remove(String username);

    boolean search(int id);

    boolean searchByUsername(String username);

    User searchAccount(String username, String password);

    List<User> list();
}
