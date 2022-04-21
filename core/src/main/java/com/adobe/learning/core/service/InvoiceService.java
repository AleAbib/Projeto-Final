package com.adobe.learning.core.service;

import com.adobe.learning.core.models.Invoice;
import com.adobe.learning.core.models.User;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;

import java.io.IOException;

public interface InvoiceService {

    void register(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException;

    void update(Invoice invoice);

    void remove(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException;

    boolean search(int id);

    String list(User user);
}