/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Category class that represents the categroy table from the silmara database.
 *
 * @author Miguel Alvarado
 */
public class Category implements Serializable {

    private final IntegerProperty id;
    private final StringProperty name;

    private PropertyChangeSupport propertySupport;

    public Category(Integer idCategory, String nameCategory) {
        id = new SimpleIntegerProperty(idCategory);
        name = new SimpleStringProperty(nameCategory);
    }

    /*   public Category() {
     //   propertySupport = new PropertyChangeSupport(this);
    }*/
    public IntegerProperty idProperty() {
        return id;
    }

    public Integer getID() {
        return id.get();
    }

    public void setID(int idCat) {
        id.set(idCat);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String Name) {
        name.set(Name);
    }

    @Override
    public String toString() {
        return getName();
    }
}
