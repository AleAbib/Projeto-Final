package com.adobe.learning.core.service.Impl;

import com.adobe.learning.core.dao.ProductDao;
import com.adobe.learning.core.dao.UserDao;
import com.adobe.learning.core.models.Product;
import com.adobe.learning.core.service.DatabaseService;
import com.adobe.learning.core.service.ProductService;
import com.google.gson.Gson;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component(immediate = true, service = ProductService.class)
public class ProcutServiceImpl implements ProductService {

    @Reference
    private ProductDao productDao;

    @Reference
    private DatabaseService databaseService;

    public void register(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String category = request.getParameter("category");
        int price = Integer.parseInt(request.getParameter("price"));

        Product product = new Product(name, category, price);
        productDao.register(product);
        response.setContentType("application/json");

        response.getWriter().println("Usuario cadastrado com sucesso! " + new Gson().toJson(product));
    }

    public void update(Product product){
        this.productDao.update(product);
    }

    public boolean remove(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        String nome = request.getParameter("name");
        return productDao.remove(nome);
    }

    public boolean search(int id){

        return productDao.search(id);
    }

    public Product searchInvoice(int id){

        return productDao.searchInvoice(id);
    }

    public String list(String name){
        List<Product> userList = productDao.list();
        List<Product> userTemp = new ArrayList<>();

        try {
            if (name == null || name.isEmpty() || name.isBlank()) {
                userTemp = userList;
            }
            else {
                for (Product p: userList) {
                    if (p.getName().toLowerCase().contains(name.toLowerCase())) {
                        userTemp.add(p);
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