package com.adobe.learning.core.service.Impl;

import com.adobe.learning.core.dao.RequestDao;
import com.adobe.learning.core.models.Request;
import com.adobe.learning.core.models.User;
import com.adobe.learning.core.service.DatabaseService;
import com.adobe.learning.core.service.ProductService;
import com.adobe.learning.core.service.RequestService;
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

@Component(immediate = true, service = RequestService.class)
public class RequestServiceImpl implements RequestService {

    @Reference
    private RequestDao requestDao;

    public void register(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {

        int idProduct = Integer.parseInt(request.getParameter("idProduct"));

        HttpSession sessao = request.getSession();
        User user = (User) sessao.getAttribute("usuarioLogado");

        requestDao.register(idProduct, user.getId());
        response.setContentType("application/json");

        response.getWriter().println("Pedido cadastrado com sucesso! ");
    }

    public void update(Request request){
        this.requestDao.update(request);
    }

    public boolean remove(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        HttpSession sessao = request.getSession();
        User user = (User) sessao.getAttribute("usuarioLogado");
        return requestDao.remove(user.getId());
    }

    public String list(User user){

        List<Request> userList = requestDao.list(user);

        String json = new Gson().toJson(userList);
        return json;
    }

    public List<Integer> search(User user){

        return requestDao.search(user);
    }
}