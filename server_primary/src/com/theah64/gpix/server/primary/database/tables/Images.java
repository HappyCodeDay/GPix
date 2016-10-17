package com.theah64.gpix.server.primary.database.tables;

import com.theah64.gpix.server.primary.core.Image;
import com.theah64.gpix.server.primary.database.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by theapache64 on 17/10/16,2:38 PM.
 */
public class Images extends BaseTable<Image> {


    public static final String COLUMN_IMAGE_URL = "image_url";
    public static final String COLUMN_THUMB_URL = "thumb_url";
    public static final String COLUMN_HEIGHT = "height";
    public static final String COLUMN_WIDTH = "width";
    public static final String TABLE_NAME_IMAGES = "images";

    private static final Images instance = new Images();
    private static final int MAX_RESULT_VALIDITY_IN_DAYS = 5;

    private Images() {
        super(TABLE_NAME_IMAGES);
    }

    public static Images getInstance() {
        return instance;
    }

    public List<Image> getAll(String keyword, final int limit) {
        List<Image> images = null;

        final String query = String.format("SELECT i.image_url, i.thumb_url, i.height, i.width FROM images i INNER JOIN requests r ON r.id = i.request_id WHERE r.keyword = ? AND IFNULL(DATEDIFF(NOW(),r.created_at),0) <= %d LIMIT %d;", MAX_RESULT_VALIDITY_IN_DAYS, limit);
        final java.sql.Connection con = Connection.getConnection();
        try {
            final PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, keyword);

            final ResultSet rs = ps.executeQuery();

            if (rs.first()) {
                images = new ArrayList<>();

                do {
                    final String imageUrl = rs.getString(COLUMN_IMAGE_URL);
                    final String thumbUrl = rs.getString(COLUMN_THUMB_URL);
                    final int height = rs.getInt(COLUMN_HEIGHT);
                    final int width = rs.getInt(COLUMN_WIDTH);

                    images.add(new Image(thumbUrl, imageUrl, height, width));
                } while (rs.next());

            }

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
        return images;
    }
}

