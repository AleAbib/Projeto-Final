package com.adobe.learning.core.service;

import com.adobe.learning.core.models.Product;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    void register(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException;

    void update(Product product);

    boolean remove(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException;

    boolean search(int id);

    Product searchInvoice(int id);

    String list(String name);
}