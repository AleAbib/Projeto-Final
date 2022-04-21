package com.adobe.learning.core.dao.Impl;

import com.adobe.learning.core.dao.UserDao;
import com.adobe.learning.core.models.User;
import com.adobe.learning.core.service.DatabaseService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Component(immediate = true, service = UserDao.class)
public class UserDaoImpl implements UserDao {

    @Reference
    private DatabaseService databaseService;

    public void register(User user){
        try (Connection connection = databaseService.getConnection()) {

            String sql = "INSERT INTO user (name, username, password) VALUES (?, ?, ?)";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, user.getName());
                ps.setString(2, user.getUsername());
                ps.setString(3, user.getPassword());
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

    public void update(User user){
        try (Connection connection = databaseService.getConnection()) {

            String sql = "UPDATE user SET name= ?, username= ?, password= ?  WHERE id = ?";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, user.getName());
                ps.setString(2, user.getUsername());
                ps.setString(3, user.getPassword());
                ps.setInt(4, user.getId());
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

    public void remove(String username){
        try (Connection connection = databaseService.getConnection()) {

            String sql = "DELETE FROM user WHERE username = ?";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, username);
                ps.execute();
            }
            catch (Exception e) {
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean search(int id){
        try (Connection connection = databaseService.getConnection()) {
            String sql = "SELECT * FROM user WHERE id = ?";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1,id);
                ps.execute();
                try (ResultSet rs = ps.getResultSet()) {
                    User userTemp = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5));
                    if (userTemp.getId() == id){
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

    public User searchAccount(String username, String password){
        try (Connection connection = databaseService.getConnection()) {
            String sql = "SELECT * FROM user WHERE username = ? AND password = ?";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1,username);
                ps.setString(2, password);
                ps.execute();
                try (ResultSet rs = ps.getResultSet()) {
                    User userTemp = null;
                    while (rs.next()) {
                        userTemp = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5));
                    }
                    return userTemp;
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

    public boolean searchByUsername(String username){

        List<User> ListUsers = new ArrayList<User>();

        try (Connection connection = databaseService.getConnection()) {
            String sql = "SELECT * FROM user WHERE username = ?";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1,username);
                ps.execute();
                try (ResultSet rs = ps.getResultSet()) {
                    while (rs.next()) {
                        User userTemp = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5));
                        ListUsers.add(userTemp);
                    }
                    if(ListUsers.isEmpty()) return false;
                    else return true;
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

    public List<User> list(){

        List<User> ListUsers = new ArrayList<User>();

        try (Connection connection = databaseService.getConnection()) {
            String sql = "SELECT * FROM user";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.execute();
                try (ResultSet rs = ps.getResultSet()) {
                    while (rs.next()) {
                        User userTemp = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5));
                        ListUsers.add(userTemp);
                    }
                    return ListUsers;
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
