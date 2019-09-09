/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.io.Serializable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Category class that represents the JOIN between product and category tables
 * from the silmara database.
 *
 * @author Miguel Alvarado
 */
public class ProductDetail implements Serializable {

    private final IntegerProperty productId;
    private final StringProperty productName;
    private final StringProperty productDescription;
    private final DoubleProperty productPrice;
    private final IntegerProperty productStock;
    private final IntegerProperty productCategory_id;
    private final StringProperty productCategory;

    public ProductDetail(Integer id, String name, String desc, double price, int stock, int category_id, String category) {
        this.productId = new SimpleIntegerProperty(id);
        this.productName = new SimpleStringProperty(name);
        this.productDescription = new SimpleStringProperty(desc);
        this.productPrice = new SimpleDoubleProperty(price);
        this.productStock = new SimpleIntegerProperty(stock);
        this.productCategory_id = new SimpleIntegerProperty(category_id);
        this.productCategory = new SimpleStringProperty(category);
    }

    public IntegerProperty productIdProperty() {
        return productId;
    }

    public Integer getProductId() {
        return productId.get();
    }

    public void setProductId(int id) {
        productId.set(id);
    }

    public StringProperty productNameProperty() {
        return productName;
    }

    public String getProductName() {
        return productName.get();
    }

    public void setProductName(String productName) {
        this.productName.set(productName);
    }

    public StringProperty productDescriptionProperty() {
        return productDescription;
    }

    public String getProductDescription() {
        return productDescription.get();
    }

    public void setProductDescription(String description) {
        this.productDescription.set(description);
    }

    public DoubleProperty productPriceProperty() {
        return productPrice;
    }

    public Double getProductPrice() {
        return productPrice.get();
    }

    public void setProductPrice(double price) {
        this.productPrice.set(price);
    }

    public IntegerProperty productStockProperty() {
        return productStock;
    }

    public Integer getProductStock() {
        return productStock.get();
    }

    public Integer setProductStock() {
        return productStock.get();
    }

    public IntegerProperty productCategoryIdProperty() {
        return productCategory_id;
    }

    public Integer getProductCategoryId() {
        return productCategory_id.get();
    }

    public void setProductCategoryId(int categoryId) {
        this.productCategory_id.set(categoryId);
    }

    public StringProperty productCategoryProperty() {
        return productCategory;
    }

    public String getProductCategory() {
        return productCategory.get();
    }

    public void setProductCategory(String description) {
        this.productCategory.set(description);
    }
}
