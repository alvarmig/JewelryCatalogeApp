/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistance.DataAdapters;

import Beans.Category;
import Persistance.JDBCMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * tblCategoryAdapter has the CRUD operations to the product_categories table.
 *
 * @author Miguel
 */
public class tblCategoryAdapter {

    JDBCMySQL conn = new JDBCMySQL();
    Connection c = null;

    /**
     * Select will get the columns id and name from the product_categories
     * table. This method return an array list of category objects.
     *
     * @return List
     */
    public List<Category> Select() {

        try {
            List<Category> rsCategory = new ArrayList<>();
            c = conn.connectionDB();
            // Set the prepared statement.
            PreparedStatement verificarStmt
                    = c.prepareStatement("SELECT"
                            + "   category_id AS id "
                            + "   ,category_name AS name "
                            + "   FROM product_categories ");

            ResultSet rs = verificarStmt.executeQuery();

            // process the result set into a list of Category objects.
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
            // Close the connection.
            conn.disconnection(c);
        }
        return null;
    }
}
