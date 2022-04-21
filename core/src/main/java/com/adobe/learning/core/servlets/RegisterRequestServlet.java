package com.adobe.learning.core.servlets;

import com.adobe.learning.core.models.User;
import com.adobe.learning.core.service.RequestService;
import com.adobe.learning.core.service.UserService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.Servlet;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.apache.sling.api.servlets.ServletResolverConstants.*;

@Component(service = Servlet.class, property = {
        SLING_SERVLET_PATHS + "=" + "/bin/app/registerrequest",
        SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
        SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_POST,
        SLING_SERVLET_EXTENSIONS + "=" + "json"
})
public class RegisterRequestServlet extends SlingAllMethodsServlet {

    private static final long serialVersionUID = 1L;

    @Reference
    private RequestService requestService;

    @Override
    protected void doPost(final SlingHttpServletRequest req, final SlingHttpServletResponse resp) throws IOException {

        requestService.register(req,resp);
    }

    @Override
    protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp) throws IOException {

        HttpSession sessao = req.getSession();
        User user = (User) sessao.getAttribute("usuarioLogado");
        String json = requestService.list(user);
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }

    @Override
    protected void doDelete(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {

        if(requestService.remove(request,response)){
            response.getWriter().write("Pedido removido com sucesso");
        } else{
            response.getWriter().write("Pedido nao existe em nosso sistema");
        }
    }
//
//    @Override
//    protected void doPut(@org.jetbrains.annotations.NotNull SlingHttpServletRequest request, @org.jetbrains.annotations.NotNull SlingHttpServletResponse response) throws ServletException, IOException {
//
//    }
}