package com.adobe.learning.core.service.Impl;

import com.adobe.learning.core.dao.InvoiceDao;
import com.adobe.learning.core.dao.UserDao;
import com.adobe.learning.core.models.Invoice;
import com.adobe.learning.core.models.Product;
import com.adobe.learning.core.models.User;
import com.adobe.learning.core.service.*;
import com.google.gson.Gson;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component(immediate = true, service = InvoiceService.class)
public class InvoiceServiceImpl implements InvoiceService {

    @Reference
    private InvoiceDao invoiceDao;

    @Reference
    private RequestService requestService;

    @Reference
    private ProductService productService;

    @Reference
    private DatabaseService databaseService;

    public void register(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {

        int number = Integer.parseInt(request.getParameter("number"));

        if(invoiceDao.search(number) == true){
            response.sendError(406, "Invoice number already exists!");
        }else {
            HttpSession sessao = request.getSession();
            User user = (User) sessao.getAttribute("usuarioLogado");

            List<Integer> listIds = requestService.search(user);
            Invoice invoice = new Invoice(number, user.getId(), 0);

            for(int i=0; i < listIds.size();i++){
                Product productTemp = productService.searchInvoice(listIds.get(i));
                invoice.setValue(invoice.getValue() + productTemp.getPrice());
            }
            invoiceDao.register(invoice);

            response.setContentType("application/json");

            response.getWriter().println("Invoice registered sucessfully!");
        }
    }

    public void update(Invoice invoice){
        this.invoiceDao.update(invoice);
    }

    public void remove(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        int number = Integer.parseInt(request.getParameter("number"));
        invoiceDao.remove(number);
//        if(invoiceDao.search(number)){
//            invoiceDao.remove(number);
//            response.getWriter().println("Invoice deleted sucessfully!");
//        }else{
//            response.sendError(406, "Invoice doesn't exist!");
//        }
    }

    public boolean search(int id){
        return invoiceDao.search(id);
    }

    public String list(User user){

        List<Invoice> invoiceList = invoiceDao.list(user);

        String json = new Gson().toJson(invoiceList);
        return json;
    }
}