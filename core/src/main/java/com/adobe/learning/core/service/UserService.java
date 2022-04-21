package com.adobe.learning.core.service;

import com.adobe.learning.core.models.User;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;

import java.io.IOException;

public interface UserService {

    void register(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException;

    void update(User user);

    void remove(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException;

    boolean search(int id);

    User searchAccount(String username, String password);

    String list(String name);
}