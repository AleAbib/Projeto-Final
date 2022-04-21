package com.adobe.learning.core.service;

import com.adobe.learning.core.models.Request;
import com.adobe.learning.core.models.User;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;

import java.io.IOException;
import java.util.List;

public interface RequestService {

    void register(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException;

    void update(Request request);

    boolean remove(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException;

    String list(User user);

    List<Integer> search(User user);
}