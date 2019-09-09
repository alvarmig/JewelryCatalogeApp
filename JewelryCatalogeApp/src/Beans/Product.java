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
 * Product class that represents the product table from the silmara database.
 *
 * @author Miguel Alvarado
 */
public class Product implements Serializable {

    private final IntegerProperty id;
    private final StringProperty prodcutName;
    private final StringProperty description;
    private final DoubleProperty price;
    private final IntegerProperty stock;

    public Product(Integer id, String name, String desc, double price, int stock) {
        this.id = new SimpleIntegerProperty(id);
        this.prodcutName = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(desc);
        this.price = new SimpleDoubleProperty(price);
        this.stock = new SimpleIntegerProperty(stock);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public Integer getID() {
        return id.get();
    }

    public StringProperty productNameProperty() {
        return prodcutName;
    }

    public String getProductName() {
        return prodcutName.get();
    }

    public void setProductName(String prodcutName) {
        this.prodcutName.set(prodcutName);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public Double getPrice() {
        return price.get();
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public IntegerProperty stock() {
        return stock;
    }

    public Integer getStock() {
        return stock.get();
    }

    public Integer setStock() {
        return stock.get();
    }

}
