package com.adobe.learning.core.dao.Impl;

import com.adobe.learning.core.dao.InvoiceDao;
import com.adobe.learning.core.dao.UserDao;
import com.adobe.learning.core.models.Invoice;
import com.adobe.learning.core.models.User;
import com.adobe.learning.core.service.DatabaseService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Component(immediate = true, service = InvoiceDao.class)
public class InvoiceDaoImpl implements InvoiceDao {

    @Reference
    private DatabaseService databaseService;

    public void register(Invoice invoice){
        try (Connection connection = databaseService.getConnection()) {

            String sql = "INSERT INTO invoice (number, idUser, value) VALUES (?, ?, ?)";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, invoice.getNumber());
                ps.setInt(2, invoice.getIdUser());
                ps.setDouble(3, invoice.getValue());
                ps.execute();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Invoice invoice){
        try (Connection connection = databaseService.getConnection()) {

            String sql = "UPDATE invoice SET idUser= ?, value= ?  WHERE number = ?";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, invoice.getIdUser());
                ps.setDouble(2, invoice.getValue());
                ps.setInt(3, invoice.getNumber());
                ps.execute();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void remove(int number){
        try (Connection connection = databaseService.getConnection()) {

            String sql = "DELETE FROM invoice WHERE number = ?";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, number);
                ps.execute();
            }
            catch (Exception e) {
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean search(int number){
        try (Connection connection = databaseService.getConnection()) {
            String sql = "SELECT * FROM invoice WHERE number = ?";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1,number);
                ps.execute();
                try (ResultSet rs = ps.getResultSet()) {
                    Invoice invoiceTemp = new Invoice(rs.getInt(1), rs.getInt(2), rs.getDouble(3));
                    if (invoiceTemp.getNumber() == number){
                        return true;
                    }else return false;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<Invoice> list(User user){

        List<Invoice> ListInvoices = new ArrayList<Invoice>();

        try (Connection connection = databaseService.getConnection()) {
            String sql = "SELECT * FROM invoice WHERE idUser = ?";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1,user.getId());
                ps.execute();
                try (ResultSet rs = ps.getResultSet()) {
                    while (rs.next()) {
                        Invoice invoiceTemp = new Invoice(rs.getInt(1), rs.getInt(2), rs.getDouble(3));
                        ListInvoices.add(invoiceTemp);
                    }
                    return ListInvoices;
                }
                catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
