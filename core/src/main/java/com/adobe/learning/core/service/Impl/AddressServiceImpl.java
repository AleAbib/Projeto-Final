package com.adobe.learning.core.service.Impl;

import com.adobe.learning.core.dao.AddressDao;
import com.adobe.learning.core.models.Address;
import com.adobe.learning.core.models.User;
import com.adobe.learning.core.service.AddressService;
import com.adobe.learning.core.service.DatabaseService;
import com.adobe.learning.core.service.UserService;
import com.google.gson.Gson;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component(immediate = true, service = AddressService.class)
public class AddressServiceImpl implements AddressService {

    @Reference
    private AddressDao addressDao;

    @Reference
    private UserService userService;

    @Reference
    private DatabaseService databaseService;

    public void register(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {

        String CEP = request.getParameter("CEP");
        String street = request.getParameter("street");
        int number = Integer.parseInt(request.getParameter("number"));
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String district = request.getParameter("district");

        HttpSession sessao = request.getSession();
        User user = (User) sessao.getAttribute("usuarioLogado");

        Address address = new Address(CEP, street, number, city, state, district);
        addressDao.register(address);
        //user.setAddress(address);
        userService.update(user);
        response.setContentType("application/json");

        response.getWriter().println("Usuario cadastrado com sucesso! " + new Gson().toJson(address));
    }

    public void update(Address address){

        this.addressDao.update(address);
    }

    public boolean search(int id){

        return addressDao.search(id);
    }

    public boolean remove(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        String CEP = request.getParameter("CEP");
        int number = Integer.parseInt(request.getParameter("number"));
        if (addressDao.remove(CEP, number)){
            return true;
        }else return false;
    }

    public String list(String CEP){
        List<Address> userList = addressDao.list();
        List<Address> userTemp = new ArrayList<>();

        try {
            if (CEP == null || CEP.isEmpty() || CEP.isBlank()) {
                userTemp = userList;
            }
            else {
                for (Address a: userList) {
                    if ((a.getCEP()).contains(CEP)) {
                        userTemp.add(a);
                        break;
                    }
                }
            }
        } catch(Exception e) {
            e.getStackTrace();
        }

        String json = new Gson().toJson(userTemp);
        return json;
    }
}