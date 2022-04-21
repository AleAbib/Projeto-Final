package com.adobe.learning.core.servlets;

import com.adobe.learning.core.models.Invoice;
import com.adobe.learning.core.models.User;
import com.adobe.learning.core.service.InvoiceService;
import com.adobe.learning.core.service.UserService;
import com.google.gson.Gson;
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
        SLING_SERVLET_PATHS + "=" + "/bin/app/registerinvoice",
        SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
        SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_POST,
        SLING_SERVLET_EXTENSIONS + "=" + "json"
})
public class RegisterInvoiceServlet extends SlingAllMethodsServlet {

    private static final long serialVersionUID = 1L;

    @Reference
    private InvoiceService invoiceService;

    @Override
    protected void doPost(final SlingHttpServletRequest req, final SlingHttpServletResponse resp) throws IOException {

        invoiceService.register(req,resp);
    }

    @Override
    protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp) throws IOException {

        HttpSession sessao = req.getSession();
        User user = (User) sessao.getAttribute("usuarioLogado");
        String json = invoiceService.list(user);
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }

    @Override
    protected void doDelete(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {

        invoiceService.remove(request,response);
    }

    @Override
    protected void doPut(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        if(invoiceService.search(id)) {

            int number = Integer.parseInt(request.getParameter("number"));
            int idUser = Integer.parseInt(request.getParameter("idUser"));
            Double value = Double.parseDouble(request.getParameter("value"));

            Invoice invoice = new Invoice(number, idUser, value);
            invoiceService.update(invoice);
            response.setContentType("application/json");

            response.getWriter().println("User updated with sucess! " + new Gson().toJson(invoice));
        }else {
            response.sendError(404, "User not found!");
        }
    }
}