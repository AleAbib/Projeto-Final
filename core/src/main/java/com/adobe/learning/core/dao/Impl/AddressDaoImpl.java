package com.adobe.learning.core.dao.Impl;

import com.adobe.learning.core.dao.AddressDao;
import com.adobe.learning.core.models.Address;
import com.adobe.learning.core.models.User;
import com.adobe.learning.core.service.DatabaseService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Component(immediate = true, service = AddressDao.class)
public class AddressDaoImpl implements AddressDao {

    @Reference
    private DatabaseService databaseService;

    @Override
    public void register(Address address) {
        try (Connection connection = databaseService.getConnection()) {

            String sql = "INSERT INTO address (CEP, street, number, city, state, district) VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, address.getCEP());
                ps.setString(2, address.getStreet());
                ps.setInt(3, address.getNumber());
                ps.setString(4, address.getCity());
                ps.setString(5, address.getState());
                ps.setString(6, address.getDistrict());
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

    @Override
    public void update(Address address) {
        try (Connection connection = databaseService.getConnection()) {

            String sql = "UPDATE address SET CEP = ?, street = ?, number = ?, city = ?, state = ?, district = ?  WHERE id_address = ?";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, address.getCEP());
                ps.setString(2, address.getStreet());
                ps.setInt(3, address.getNumber());
                ps.setString(4, address.getCity());
                ps.setString(5, address.getState());
                ps.setString(6, address.getDistrict());
                ps.setInt(7, address.getId_address());

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

    public boolean search(int id_address){
        try (Connection connection = databaseService.getConnection()) {
            String sql = "SELECT * FROM address WHERE id_address = ?";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1,id_address);
                ps.execute();
                try (ResultSet rs = ps.getResultSet()) {
                    User userTemp = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5));
                    if (userTemp.getId() == id_address){
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

    @Override
    public boolean remove(String CEP, int number) {
        try (Connection connection = databaseService.getConnection()) {

            String sql = "DELETE FROM address WHERE CEP = ? AND number = ?";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, CEP);
                ps.setInt(2, number);
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

    @Override
    public List<Address> list() {
        List<Address> ListAddress = new ArrayList<Address>();

        try (Connection connection = databaseService.getConnection()) {
            String sql = "SELECT * FROM address";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.execute();
                try (ResultSet rs = ps.getResultSet()) {
                    while (rs.next()) {
                        Address userTemp = new Address(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getString(6));
                        ListAddress.add(userTemp);
                    }
                    return ListAddress;
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
