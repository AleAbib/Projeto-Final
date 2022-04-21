package com.adobe.learning.core.servlets;

import com.adobe.learning.core.models.User;
import com.adobe.learning.core.service.UserService;
import com.google.gson.Gson;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.Servlet;

import java.io.IOException;

import static org.apache.sling.api.servlets.ServletResolverConstants.*;

@Component(service = Servlet.class, property = {
        SLING_SERVLET_PATHS + "=" + "/bin/app/registeruser",
        SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
        SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_POST,
        SLING_SERVLET_EXTENSIONS + "=" + "json"
})
public class RegisterServlet extends SlingAllMethodsServlet {

    private static final long serialVersionUID = 1L;

    @Reference
    private UserService usuarioService;

    @Override
    protected void doPost(final SlingHttpServletRequest req, final SlingHttpServletResponse resp) throws IOException {

        usuarioService.register(req,resp);
    }

    @Override
    protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp) throws IOException {

        String name = req.getParameter("name");
        String json = usuarioService.list(name);
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }

    @Override
    protected void doDelete(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {

        usuarioService.remove(request,response);
    }

    @Override
    protected void doPut(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        if(usuarioService.search(id)) {

            String name = request.getParameter("name");
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            User user = new User(id, name, username, password);
            usuarioService.update(user);
            response.setContentType("application/json");

            response.getWriter().println("User updated with sucess! " + new Gson().toJson(user));
        }else {
            response.sendError(404, "User not found!");
        }
    }
}