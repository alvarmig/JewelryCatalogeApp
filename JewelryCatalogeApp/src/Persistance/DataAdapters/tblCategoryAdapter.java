/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistance.DataAdapters;

import Persistance.JDBCMySQL;
import Beans.Category;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Miguel
 */
public class tblCategoryAdapter {

    JDBCMySQL conn = new JDBCMySQL();
    Connection c = null;

    public List<Category> Select() {

        try {
            List<Category> rsCategory = new ArrayList<>();
            c = conn.connectionDB();
            PreparedStatement verificarStmt
                    = c.prepareStatement("SELECT"
                            + "   category_id AS id "
                            + "   ,category_name AS name "
                            + "   FROM product_categories ");

            ResultSet rs = verificarStmt.executeQuery();

            while (rs.next()) {

                Category cat = new Category(rs.getInt("id"), rs.getString("name"));
                rsCategory.add(cat);
            }
            verificarStmt.close();
            rs.close();

            return rsCategory;
        } catch (SQLException ex) {
            Logger.getLogger(tblCategoryAdapter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            conn.disconnection(c);
        }
        return null;
    }
}
