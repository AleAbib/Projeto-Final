package com.adobe.learning.core.dao;

import com.adobe.learning.core.models.Request;
import com.adobe.learning.core.models.User;

import java.util.List;

public interface RequestDao {

    void register(int idProduct, int idUser);

    void update(Request request);

    boolean remove(int idUser);

    List<Request> list(User user);

    List<Integer> search(User user);
}
