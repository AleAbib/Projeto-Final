package com.adobe.learning.core.service;

import com.adobe.learning.core.models.Address;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;

import java.io.IOException;

public interface AddressService {

    void register(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException;

    void update(Address address);

    boolean search(int id);

    boolean remove(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException;

    String list(String CEP);
}