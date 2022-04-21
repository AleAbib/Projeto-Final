package com.adobe.learning.core.servlets;

import com.adobe.learning.core.models.Product;
import com.adobe.learning.core.models.User;
import com.adobe.learning.core.service.ProductService;
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
        SLING_SERVLET_PATHS + "=" + "/bin/app/registerproduct",
        SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
        SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_POST,
        SLING_SERVLET_EXTENSIONS + "=" + "json"
})
public class RegisterProductServlet extends SlingAllMethodsServlet {

    private static final long serialVersionUID = 1L;

    @Reference
    private ProductService productService;

    @Override
    protected void doPost(final SlingHttpServletRequest req, final SlingHttpServletResponse resp) throws IOException {

        productService.register(req,resp);
    }

    @Override
    protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp) throws IOException {

        String name = req.getParameter("name");
        String json = productService.list(name);
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }

    @Override
    protected void doDelete(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {

        if(productService.remove(request,response)){
            response.getWriter().write("Produto removido com sucesso");
        } else{
            response.getWriter().write("Produto nao existe em nosso sistema");
        }
    }

    @Override
    protected void doPut(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        int idProduct = Integer.parseInt(request.getParameter("idProduct"));

        if(productService.search(idProduct)) {

            String name = request.getParameter("name");
            String category = request.getParameter("category");
            double price = Double.parseDouble(request.getParameter("price"));

            Product product = new Product(idProduct, name, category, price);
            productService.update(product);
            response.setContentType("application/json");

            response.getWriter().println("Product updated with sucess! " + new Gson().toJson(product));
        }else {
            response.sendError(404, "Product not found!");
        }
    }
}