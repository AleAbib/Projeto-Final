package com.adobe.learning.core.dao.Impl;

import com.adobe.learning.core.dao.RequestDao;
import com.adobe.learning.core.dao.UserDao;
import com.adobe.learning.core.models.Request;
import com.adobe.learning.core.models.User;
import com.adobe.learning.core.service.DatabaseService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Component(immediate = true, service = RequestDao.class)
public class RequestDaoImpl implements RequestDao {

    @Reference
    private DatabaseService databaseService;

    public void register(int idProduct, int idUser){
        try (Connection connection = databaseService.getConnection()) {

            String sql = "INSERT INTO request (idProduct, idUser) VALUES (?, ?)";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, idProduct);
                ps.setInt(2, idUser);
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

    public void update(Request request){

    }

    public boolean remove(int idUser){
        try (Connection connection = databaseService.getConnection()) {

            String sql = "DELETE FROM request WHERE idUser = ?";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, idUser);
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

    public List<Request> list(User user){

        List<Request> ListRequests = new ArrayList<Request>();

        try (Connection connection = databaseService.getConnection()) {
            String sql = "SELECT * FROM request WHERE idUser = ?";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, user.getId());
                ps.execute();
                try (ResultSet rs = ps.getResultSet()) {
                    while (rs.next()) {
                        Request userTemp = new Request(rs.getInt(1), rs.getInt(2));
                        ListRequests.add(userTemp);
                    }
                    return ListRequests;
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

    public List<Integer> search(User user){
        List<Integer> ListIds = new ArrayList<Integer>();

        try (Connection connection = databaseService.getConnection()) {
            String sql = "SELECT * FROM request WHERE idUser = ?";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, user.getId());
                ps.execute();
                try (ResultSet rs = ps.getResultSet()) {
                    while (rs.next()) {
                        ListIds.add(rs.getInt(1));
                    }
                    return ListIds;
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
