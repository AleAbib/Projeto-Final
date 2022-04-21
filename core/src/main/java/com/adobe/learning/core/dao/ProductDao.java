package com.adobe.learning.core.dao;

import com.adobe.learning.core.models.Product;
import com.adobe.learning.core.models.User;

import java.util.List;

public interface ProductDao {

    void register(Product product);

    void update(Product product);

    boolean remove(String name);

    boolean search(int idProduct);

    Product searchInvoice(int idProduct);

    List<Product> list();
}
