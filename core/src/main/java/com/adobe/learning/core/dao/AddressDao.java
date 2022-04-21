package com.adobe.learning.core.dao;



import com.adobe.learning.core.models.Address;

import java.util.List;

public interface AddressDao {

    void register(Address address);

    void update(Address address);

    boolean search(int id_address);

    boolean remove(String CEP, int number);

    List<Address> list();
}
