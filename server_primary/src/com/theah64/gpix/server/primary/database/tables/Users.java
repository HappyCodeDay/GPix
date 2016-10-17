package com.theah64.gpix.server.primary.database.tables;

import com.theah64.gpix.server.primary.database.Connection;
import com.theah64.gpix.server.primary.models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by theapache64 on 15/10/16.
 */
public class Users extends BaseTable<User> {

    private static final Users instance = new Users();
    public static final String COLUMN_API_KEY = "api_key";
    public static final String COLUMN_EMAIL = "email";

    private Users() {
        super("users");
    }

    public static Users getInstance() {
        return instance;
    }

    @Override
    public boolean add(User newUser) {
        boolean isUserAdded = false;
        final String query = "INSERT INTO users (email,api_key) VALUES (?,?);";
        final java.sql.Connection con = Connection.getConnection();
        try {

            final PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, newUser.getEmail());
            ps.setString(2, newUser.getApiKey());

            isUserAdded = ps.executeUpdate() == 1;

            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isUserAdded;
    }

    @Override
    public User get(String column, String value) {

        User user = null;

        final String query = String.format("SELECT id, email,api_key FROM users WHERE %s = ?", column);

        final java.sql.Connection con = Connection.getConnection();
        try {

            final PreparedStatement ps = con.prepareStatement(query);
            final ResultSet rs = ps.executeQuery();

            final String id = rs.getString(COLUMN_ID);
            final String name = rs.getString(COLUMN_NAME);
            final String apiKey = rs.getString(COLUMN_API_KEY);

            user = new User(id, name, apiKey);

            rs.close();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return user;
    }
}