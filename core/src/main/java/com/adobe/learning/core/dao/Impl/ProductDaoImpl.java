package com.adobe.learning.core.dao.Impl;

import com.adobe.learning.core.dao.ProductDao;
import com.adobe.learning.core.dao.UserDao;
import com.adobe.learning.core.models.Product;
import com.adobe.learning.core.models.User;
import com.adobe.learning.core.service.DatabaseService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Component(immediate = true, service = ProductDao.class)
public class ProductDaoImpl implements ProductDao {

    @Reference
    private DatabaseService databaseService;

    public void register(Product product){
        try (Connection connection = databaseService.getConnection()) {

            String sql = "INSERT INTO product (name, category, price) VALUES (?, ?, ?)";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, product.getName());
                ps.setString(2, product.getCategory());
                ps.setDouble(3, product.getPrice());
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

    public void update(Product product){
        try (Connection connection = databaseService.getConnection()) {

            String sql = "UPDATE product SET name= ?, category= ?, price= ?  WHERE id = ?";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, product.getName());
                ps.setString(2, product.getCategory());
                ps.setDouble(3, product.getPrice());
                ps.setInt(4, product.getIdProduct());
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

    public boolean remove(String name){
        try (Connection connection = databaseService.getConnection()) {

            String sql = "DELETE FROM product WHERE name = ?";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, name);
                ps.execute();
                return true;
            }
            catch (Exception e) {
                return false;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean search(int idProduct){
        try (Connection connection = databaseService.getConnection()) {
            String sql = "SELECT * FROM product WHERE idProduct = ?";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1,idProduct);
                ps.execute();
                try (ResultSet rs = ps.getResultSet()) {
                    User userTemp = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5));
                    if (userTemp.getId() == idProduct){
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

    public Product searchInvoice(int idProduct){

        try (Connection connection = databaseService.getConnection()) {
            String sql = "SELECT * FROM product WHERE idProduct = ?";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1,idProduct);
                ps.execute();
                try (ResultSet rs = ps.getResultSet()) {
                    Product productTemp = null;
                    while(rs.next()) {
                        productTemp = new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4));
                    }
                    return productTemp;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Product> list(){

        List<Product> ListProducts = new ArrayList<Product>();

        try (Connection connection = databaseService.getConnection()) {
            String sql = "SELECT * FROM product";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.execute();
                try (ResultSet rs = ps.getResultSet()) {
                    while (rs.next()) {
                        Product userTemp = new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4));
                        ListProducts.add(userTemp);
                    }
                    return ListProducts;
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
