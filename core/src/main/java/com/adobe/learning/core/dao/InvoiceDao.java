package com.adobe.learning.core.dao;

import com.adobe.learning.core.models.Invoice;
import com.adobe.learning.core.models.Request;
import com.adobe.learning.core.models.User;

import java.util.List;

public interface InvoiceDao {

    void register(Invoice invoice);

    void update(Invoice invoice);

    void remove(int number);

    boolean search(int number);

    List<Invoice> list(User user);
}
