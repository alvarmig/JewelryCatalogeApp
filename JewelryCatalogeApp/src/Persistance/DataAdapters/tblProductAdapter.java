/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistance.DataAdapters;

import Beans.ProductDetail;
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
 * tblProductAdapter has the CRUD operations to the product table.
 *
 * @author Miguel
 */
public class tblProductAdapter {

    JDBCMySQL conn = new JDBCMySQL();
    Connection c = null;

    /**
     * Select will get the columns from the product and categories tables with a
     * JOIN. This method return an array list of ProductDetail objects.
     *
     * @return List
     */
    public List<ProductDetail> Select() {

        try {
            List<ProductDetail> rsProduct = new ArrayList<>();
            c = conn.connectionDB();
            // Set the prepared statement.
            PreparedStatement verificarStmt
                    = c.prepareStatement("SELECT"
                            + "   product_id  AS id                        "
                            + "   ,product_name AS name                    "
                            + "   ,product_description AS description      "
                            + "   ,product_price AS price                  "
                            + "   ,product_stock AS stock                  "
                            + "   ,category_id                             "
                            + "   ,category_name AS category               "
                            + "   FROM products p                          "
                            + "   JOIN product_categories pc               "
                            + "   ON p.product_category_id =pc.category_id "
                            + "   ORDER BY p.product_id ASC                ");

            ResultSet rs = verificarStmt.executeQuery();
            // process the result set into a list of ProductDetail objects.
            while (rs.next()) {
                ProductDetail prod = new ProductDetail(rs.getInt("id"), rs.getString("name"), rs.getString("description"),
                        rs.getDouble("price"), rs.getInt("stock"), rs.getInt("category_id"), rs.getString("category"));
                rsProduct.add(prod);
            }

            verificarStmt.close();
            rs.close();

            return rsProduct;
        } catch (SQLException ex) {
            Logger.getLogger(tblProductAdapter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Close the connection.
            conn.disconnection(c);
        }
        return null;
    }

    /**
     * Select will get the columns from the product and categories tables by
     * category with a JOIN. This method return an array list of ProductDetail
     * objects.
     *
     * @param searchFilter
     * @return List
     */
    public List<ProductDetail> SelectByCategory(String searchFilter) {

        try {
            List<ProductDetail> rsProduct = new ArrayList<>();
            c = conn.connectionDB();
            // Set the prepared statement.
            PreparedStatement verificarStmt
                    = c.prepareStatement("SELECT"
                            + "   product_id  AS id "
                            + "   ,product_name AS name "
                            + "   ,product_description AS description "
                            + "   ,product_price AS price "
                            + "   ,product_stock AS stock "
                            + "   ,category_id"
                            + "   ,category_name AS category "
                            + "   FROM products p "
                            + "   JOIN product_categories pc "
                            + "   ON p.product_category_id = pc.category_id "
                            + "   WHERE category_name LIKE ? "
                            + "   ORDER BY p.product_id ASC ");

            verificarStmt.setString(1, searchFilter);

            ResultSet rs = verificarStmt.executeQuery();
            // process the result set into a list of ProductDetail objects.
            while (rs.next()) {
                ProductDetail prod = new ProductDetail(rs.getInt("id"), rs.getString("name"), rs.getString("description"),
                        rs.getDouble("price"), rs.getInt("stock"), rs.getInt("category_id"), rs.getString("category"));
                rsProduct.add(prod);
            }

            verificarStmt.close();
            rs.close();

            return rsProduct;
        } catch (SQLException ex) {
            Logger.getLogger(tblProductAdapter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Close the connection.
            conn.disconnection(c);
        }
        return null;
    }

    /**
     * This method will delete a row with the DELETE CRUD statement using an id.
     *
     * @param index_id
     */
    public void Delete(int index_id) {

        try {
            c = conn.connectionDB();
            // Set the prepared statement.
            PreparedStatement verificarStmt
                    = c.prepareStatement("DELETE FROM products WHERE"
                            + "   product_id  "
                            + "   =    "
                            + index_id);

            verificarStmt.executeUpdate();

            verificarStmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(tblProductAdapter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Close the connection.
            conn.disconnection(c);
        }
    }

    /**
     * This method will INSERT a new item to the products table.
     *
     * @param name
     * @param desc
     * @param stock
     * @param category_id
     * @param price
     * @return
     */
    public int Insert(String name, String desc, Double price, int stock, int category_id) {
        try {
            int success = 0;
            c = conn.connectionDB();
            // Set the prepared statement.
            PreparedStatement verificarStmt;

            verificarStmt = c.prepareStatement("INSERT INTO products(product_name,product_description,product_price, product_stock, product_category_id) VALUES (?,?,?,?,?);");
            // Set values to insert.
            verificarStmt.setString(1, name);
            verificarStmt.setString(2, desc);
            verificarStmt.setDouble(3, price);
            verificarStmt.setInt(4, stock);
            verificarStmt.setInt(5, category_id);
            success = verificarStmt.executeUpdate();

            verificarStmt.close();
            return success;
        } catch (SQLException ex) {
            Logger.getLogger(tblProductAdapter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Close the connection.
            conn.disconnection(c);
        }
        return 0;
    }

    /**
     * This method will UPDATE an item from the products table based on the id
     * of the table.
     *
     * @param id
     * @param name
     * @param desc
     * @param stock
     * @param category_id
     * @param price
     * @return
     */
    public int Update(int id, String name, String desc, Double price, int stock, int category_id) {
        try {
            int success = 0;
            c = conn.connectionDB();
            // Set the prepared statement.
            PreparedStatement verificarStmt;

            verificarStmt = c.prepareStatement("UPDATE products SET product_name = ?, product_description = ?, product_price = ?, product_stock = ?, product_category_id = ? WHERE product_id = ?");
            // Set values to Update.
            verificarStmt.setString(1, name);
            verificarStmt.setString(2, desc);
            verificarStmt.setDouble(3, price);
            verificarStmt.setInt(4, stock);
            verificarStmt.setInt(5, category_id);
            verificarStmt.setInt(6, id);
            success = verificarStmt.executeUpdate();

            verificarStmt.close();
            return success;
        } catch (SQLException ex) {
            Logger.getLogger(tblProductAdapter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Close the connection.
            conn.disconnection(c);
        }
        return 0;
    }
}
