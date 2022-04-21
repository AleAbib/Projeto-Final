package com.adobe.learning.core.servlets;

import com.adobe.learning.core.models.Address;
import com.adobe.learning.core.models.User;
import com.adobe.learning.core.service.AddressService;
import com.adobe.learning.core.service.UserService;
import com.google.gson.Gson;
import org.apache.oltu.oauth2.common.OAuth;
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
        SLING_SERVLET_PATHS + "=" + "/bin/app/registeraddress",
        SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
        SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_POST,
        SLING_SERVLET_EXTENSIONS + "=" + "json"
})
public class RegisterAddressServlet extends SlingAllMethodsServlet {

    private static final long serialVersionUID = 1L;

    @Reference
    private AddressService addressService;

    @Override
    protected void doPost(final SlingHttpServletRequest req, final SlingHttpServletResponse resp) throws IOException {

        addressService.register(req,resp);
    }

    @Override
    protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp) throws IOException {

        String CEP = req.getParameter("CEP");
        String json = addressService.list(CEP);
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }

    @Override
    protected void doDelete(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {

        if(addressService.remove(request,response)){
            response.getWriter().write("Endereço removido com sucesso");
        } else{
            response.getWriter().write("Endereço nao existe em nosso sistema");
        }
    }

    @Override
    protected void doPut(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        int id_address = Integer.parseInt(request.getParameter("id_address"));

        if(addressService.search(id_address)) {

            String CEP = request.getParameter("CEP");
            String street = request.getParameter("street");
            int number = Integer.parseInt(request.getParameter("number"));
            String city = request.getParameter("city");
            String state = request.getParameter("state");
            String district = request.getParameter("district");

            Address address = new Address(id_address, CEP, street, number, city, state, district);
            addressService.update(address);
            response.setContentType("application/json");

            response.getWriter().println("Address updated with sucess! " + new Gson().toJson(address));
        }else {
            response.sendError(404, "Address not found!");
        }
    }
}