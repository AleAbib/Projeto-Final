package com.adobe.learning.core.service.Impl;

import com.adobe.learning.core.dao.UserDao;
import com.adobe.learning.core.models.User;
import com.adobe.learning.core.service.DatabaseService;
import com.adobe.learning.core.service.UserService;
import com.google.gson.Gson;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component(immediate = true, service = UserService.class)
public class UserServiceImpl implements UserService {

    @Reference
    private UserDao userDao;

    @Reference
    private DatabaseService databaseService;

    public void register(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {

        String username = request.getParameter("username");

        if(userDao.searchByUsername(username) == true){
            response.sendError(406, "Username already registered!");
        }else {

            String name = request.getParameter("name");
            String password = request.getParameter("password");

            User user = new User(name, username.toLowerCase(), password);
            userDao.register(user);
            response.setContentType("application/json");

            response.getWriter().println("User registered sucessfully!");
        }
    }

    public void update(User user){
        this.userDao.update(user);
    }

    public void remove(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        String username = request.getParameter("username");

        if(userDao.searchByUsername(username)){
            userDao.remove(username);
            response.getWriter().println("User deleted sucessfully!");
        }else{
            response.sendError(406, "User doesn't exist!");
        }
    }

    public boolean search(int id){
        return userDao.search(id);
    }

    public User searchAccount(String username, String password){
        return userDao.searchAccount(username, password);
    }

    public String list(String name){
        List<User> userList = userDao.list();
        List<User> userTemp = new ArrayList<>();

        try {
            if (name == null || name.isEmpty() || name.isBlank()) {
                userTemp = userList;
            }
            else {
                for (User u: userList) {
                    if (u.getName().toLowerCase().contains(name.toLowerCase())) {
                        userTemp.add(u);
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