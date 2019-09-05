/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Beans.Category;
import Beans.ProductDetail;
import Persistance.DataAdapters.tblCategoryAdapter;
import Persistance.DataAdapters.tblProductAdapter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author Miguel
 */
public class SceneCatalogeOverviewController implements Initializable {

    ObservableList<Category> categoryData = FXCollections.observableArrayList();
    tblProductAdapter tblProduct = new tblProductAdapter();
    tblCategoryAdapter tblCategory = new tblCategoryAdapter();
    Category currentCategory = new Category(1, "cadenas lentes");

    private Stage dialogStage;
    private String buttonState = "default";
    private String filter = "off";

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<ProductDetail> tbProductDetails;

    @FXML
    private TableColumn<ProductDetail, Number> colID;

    @FXML
    private TableColumn<ProductDetail, String> colName;

    @FXML
    private TableColumn<ProductDetail, String> colDescription;

    @FXML
    private TableColumn<ProductDetail, Number> colPrice;

    @FXML
    private TableColumn<ProductDetail, Number> colStock;

    @FXML
    private TableColumn<ProductDetail, String> colCategory;

    @FXML
    private TableColumn<ProductDetail, Number> colCategoryId;

    @FXML
    private TextField txtProductName;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtStock;

    @FXML
    private ChoiceBox<Category> choiceBoxCategory;

    @FXML
    private Button btnNewProduct;

    @FXML
    private Button btnSaveProduct;

    @FXML
    private Button btnDeleteProduct;

    @FXML
    private Button btnEditProduct;

    @FXML
    private Button btnCancelProduct;

    @FXML
    private AnchorPane filterArea;

    @FXML
    private Button btnFilterCategory;

    @FXML
    void Cancel_ActionPerformed(ActionEvent event) {
        disableFields();
        enableDisableButtons("cancel");
    }

    @FXML
    private void NewProduct_ActionPerformed(ActionEvent event) {
        enabledFields();
        clearFields();
        enableDisableButtons("new");
        buttonState = "new";
    }

    @FXML
    private void handleEdit(ActionEvent event) {
        enabledFields();
        buttonState = "edit";
        enableDisableButtons("edit");
    }

    @FXML
    private void handleSave(ActionEvent event) {

        if ("new".equals(buttonState)) {
            if (saveItem()) {
                enableDisableButtons("save");
            } else {
                enableDisableButtons("new");
            }

        } else if ("edit".equals(buttonState)) {
            if (editItem()) {
                enableDisableButtons("save");
            } else {
                enableDisableButtons("edit");
            }
        }
    }

    @FXML
    private void handleDeleteProduct(ActionEvent event) {
        int selectedIndex = tbProductDetails.getSelectionModel().getSelectedIndex();
        int selectedID = 0;

        if (selectedIndex >= 0) {
            selectedID = tbProductDetails.getSelectionModel().getSelectedItem().getProductId();
            if (confirmationDialog("Delete selected", selectedID)) {
                //tbProductDetails.getItems().remove(selectedIndex);
                tblProduct.Delete(selectedID);
                loadTableCurrency();
                enableDisableButtons("delete");
            }

        } else {
            //Nothing selected
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(dialogStage);
            alert.setTitle("No selection");
            alert.setHeaderText("No value selected");
            alert.setContentText("Please select an item in the table.");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleBtnFilterCategory(ActionEvent event) {
        filter = "on";

    }

    @FXML
    void initialize() {
        assert tbProductDetails != null : "fx:id=\"tbProductDetails\" was not injected: check your FXML file 'sceneCatalogeOverview.fxml'.";
        assert colID != null : "fx:id=\"colID\" was not injected: check your FXML file 'sceneCatalogeOverview.fxml'.";
        assert colName != null : "fx:id=\"colName\" was not injected: check your FXML file 'sceneCatalogeOverview.fxml'.";
        assert colDescription != null : "fx:id=\"colDescription\" was not injected: check your FXML file 'sceneCatalogeOverview.fxml'.";
        assert colPrice != null : "fx:id=\"colPrice\" was not injected: check your FXML file 'sceneCatalogeOverview.fxml'.";
        assert colStock != null : "fx:id=\"colStock\" was not injected: check your FXML file 'sceneCatalogeOverview.fxml'.";
        assert colCategory != null : "fx:id=\"colCategory\" was not injected: check your FXML file 'sceneCatalogeOverview.fxml'.";
        assert colCategoryId != null : "fx:id=\"colCategoryId\" was not injected: check your FXML file 'sceneCatalogeOverview.fxml'.";
        assert txtProductName != null : "fx:id=\"txtProductName\" was not injected: check your FXML file 'sceneCatalogeOverview.fxml'.";
        assert txtDescription != null : "fx:id=\"txtDescription\" was not injected: check your FXML file 'sceneCatalogeOverview.fxml'.";
        assert txtPrice != null : "fx:id=\"txtPrice\" was not injected: check your FXML file 'sceneCatalogeOverview.fxml'.";
        assert txtStock != null : "fx:id=\"txtStock\" was not injected: check your FXML file 'sceneCatalogeOverview.fxml'.";
        assert choiceBoxCategory != null : "fx:id=\"choiceBoxCategory\" was not injected: check your FXML file 'sceneCatalogeOverview.fxml'.";
        assert btnNewProduct != null : "fx:id=\"btnNewProduct\" was not injected: check your FXML file 'sceneCatalogeOverview.fxml'.";
        assert btnSaveProduct != null : "fx:id=\"btnSaveProduct\" was not injected: check your FXML file 'sceneCatalogeOverview.fxml'.";
        assert btnDeleteProduct != null : "fx:id=\"btnDeleteProduct\" was not injected: check your FXML file 'sceneCatalogeOverview.fxml'.";
        assert btnEditProduct != null : "fx:id=\"btnEditProduct\" was not injected: check your FXML file 'sceneCatalogeOverview.fxml'.";
        assert btnCancelProduct != null : "fx:id=\"btnCancelProduct\" was not injected: check your FXML file 'sceneCatalogeOverview.fxml'.";
        assert filterArea != null : "fx:id=\"filterArea\" was not injected: check your FXML file 'sceneCatalogeOverview.fxml'.";
        assert btnFilterCategory != null : "fx:id=\"btnFilterCategory\" was not injected: check your FXML file 'sceneCatalogeOverview.fxml'.";
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        colID.setCellValueFactory(cellData -> cellData.getValue().productIdProperty());
        colName.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
        colDescription.setCellValueFactory(cellData -> cellData.getValue().productDescriptionProperty());
        colPrice.setCellValueFactory(cellData -> cellData.getValue().productPriceProperty());
        colStock.setCellValueFactory(cellData -> cellData.getValue().productStockProperty());
        colCategory.setCellValueFactory(cellData -> cellData.getValue().productCategoryProperty());
        colCategoryId.setCellValueFactory(cellData -> cellData.getValue().productCategoryIdProperty());

        showProductDetails(null);

        loadTableCurrency();
        loadChoiceBoxCategory();
        loadCheckBoxCategory();
        enableDisableButtons("default");

        tbProductDetails.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showProductDetails(newValue));
    }

    private void loadTableCurrency() {
        tbProductDetails.getItems().clear();
        tbProductDetails.getColumns().clear();

        ObservableList<ProductDetail> productData = FXCollections.observableArrayList(tblProduct.Select());
        tbProductDetails.getColumns().addAll(colID);
        tbProductDetails.getColumns().addAll(colName);
        tbProductDetails.getColumns().addAll(colDescription);
        tbProductDetails.getColumns().addAll(colPrice);
        tbProductDetails.getColumns().addAll(colStock);
        tbProductDetails.getColumns().addAll(colCategoryId);
        tbProductDetails.getColumns().addAll(colCategory);

        tbProductDetails.setItems(productData);

        clearFields();
    }

    private void loadTableCurrency(List chkBox) {
        tbProductDetails.getItems().clear();
        tbProductDetails.getColumns().clear();

        ObservableList<ProductDetail> productData = FXCollections.observableArrayList(tblProduct.Select2(chkBox));
        tbProductDetails.getColumns().addAll(colID);
        tbProductDetails.getColumns().addAll(colName);
        tbProductDetails.getColumns().addAll(colDescription);
        tbProductDetails.getColumns().addAll(colPrice);
        tbProductDetails.getColumns().addAll(colStock);
        tbProductDetails.getColumns().addAll(colCategoryId);
        tbProductDetails.getColumns().addAll(colCategory);

        tbProductDetails.setItems(productData);

        clearFields();
    }

    private void showProductDetails(ProductDetail prod) {

        if (prod != null) {
            txtProductName.setText(prod.getProductName());
            txtDescription.setText(prod.getProductDescription());
            txtPrice.setText(Double.toString(prod.getProductPrice()));
            txtStock.setText(Integer.toString(prod.getProductStock()));
            // choiceBoxCategory.getSelectionModel().select((int) colCategoryId.getCellData(prod) - 1);
            Category cat = new Category(prod.getProductCategoryId(), prod.getProductCategory());
            //currentCategory.setID(prod.getProductCategoryId());
            //currentCategory.setName(prod.getProductCategory());
            System.out.println(currentCategory.getName());
            System.out.println(currentCategory.getID());
            //ObservableList<Category> categoryData = FXCollections.observableArrayList(cat);
            //choiceBoxCategory.getItems().addAll(categoryData);
            choiceBoxCategory.setValue(cat);

            System.out.println(choiceBoxCategory.getValue().getName());
            System.out.println(choiceBoxCategory.getValue().getID());

            buttonState = "listener";
            enableDisableButtons("listener");
            disableFields();
        } else {
            clearFields();
        }
    }

    private void loadChoiceBoxCategory() {
        choiceBoxCategory.getItems().clear();
        categoryData = FXCollections.observableArrayList(tblCategory.Select());
        choiceBoxCategory.getItems().addAll(categoryData);

        choiceBoxCategory.setConverter(new StringConverter<Category>() {
            @Override
            public String toString(Category object) {
                return object.getName();
            }

            @Override
            public Category fromString(String string) {
                return null;
            }
        });
    }

    private void loadCheckBoxCategory() {
        int j = 0;
        List<String> chBoxes = new ArrayList<String>();
        for (int i = 0; i < categoryData.size(); i++) {
            CheckBox chBox = new CheckBox(categoryData.get(i).getName());

            filterArea.getChildren().addAll(chBox);
            chBox.setLayoutY(10 + j);
            chBox.setLayoutX(20);
            j += 25;

            chBox.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {

                    if (chBox.isSelected()) {
                        System.out.println(chBox.getText());
                        chBoxes.add(chBox.getText());
                        loadTableCurrency();

                    } else if (!chBox.isSelected()) {
                        chBoxes.remove(chBox.getText());
                    }

                    if (chBoxes.isEmpty()) {
                        loadTableCurrency();
                    } else {
                        loadTableCurrency(chBoxes);
                    }

                    filter = "off";

                    System.out.println(chBoxes);
                }
            });

        }

    }

    private boolean saveItem() {
        if (isInputValid()) {
            if (confirmationDialog("Create new", 0)) {
                int idCategory = choiceBoxCategory.getValue().getID();
                if (tblProduct.Insert(txtProductName.getText(), txtDescription.getText(), Double.parseDouble(txtPrice.getText()), Integer.parseInt(txtStock.getText()), idCategory) == 1) {
                    loadTableCurrency();
                    disableFields();
                    return true;
                }
            }
        }
        return false;
    }

    private boolean editItem() {
        if (tbProductDetails.getSelectionModel().getSelectedItem() == null) {

            String errorMessage = "Please select an item or create a new product";

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(dialogStage);
            alert.setTitle("No item to edit");
            alert.setHeaderText("Please select an item from the list");
            alert.setContentText(errorMessage);

            alert.showAndWait();
            //enableDisableButtons("edit");

        } else {
            if (isInputValid()) {
                if (confirmationDialog("Edit selected", 0)) {
                    int id = tbProductDetails.getSelectionModel().getSelectedItem().getProductId();
                    int idCategory = choiceBoxCategory.getValue().getID();

                    if (tblProduct.Update(id, txtProductName.getText(), txtDescription.getText(), Double.parseDouble(txtPrice.getText()), Integer.parseInt(txtStock.getText()), idCategory) == 1) {
                        loadTableCurrency();
                        disableFields();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void enableDisableButtons(String state) {

        if ("new".equals(state) || "edit".equals(state)) {
            btnNewProduct.setDisable(true);
            btnSaveProduct.setDisable(false);
            btnDeleteProduct.setDisable(true);
            btnEditProduct.setDisable(true);
            btnCancelProduct.setDisable(false);
        } else if ("cancel".equals(state)) {
            if ("new".equals(buttonState)) {
                btnNewProduct.setDisable(false);
                btnSaveProduct.setDisable(true);
                btnDeleteProduct.setDisable(true);
                btnEditProduct.setDisable(true);
                btnCancelProduct.setDisable(true);
                clearFields();
            } else {
                btnNewProduct.setDisable(false);
                btnSaveProduct.setDisable(true);
                btnDeleteProduct.setDisable(false);
                btnEditProduct.setDisable(false);
                btnCancelProduct.setDisable(true);
            }
        } else if ("save".equals(state) || "delete".equals(state) || "default".equals(state)) {
            btnNewProduct.setDisable(false);
            btnSaveProduct.setDisable(true);
            btnDeleteProduct.setDisable(true);
            btnEditProduct.setDisable(true);
            btnCancelProduct.setDisable(true);
        } else if ("listener".equals(state)) {
            btnNewProduct.setDisable(false);
            btnSaveProduct.setDisable(true);
            btnDeleteProduct.setDisable(false);
            btnEditProduct.setDisable(false);
            btnCancelProduct.setDisable(true);
        }
    }

    private void clearFields() {
        txtProductName.setText("");
        txtDescription.setText("");
        txtPrice.setText("");
        txtStock.setText("");
        choiceBoxCategory.getSelectionModel().clearSelection();
    }

    private void disableFields() {
        txtProductName.setDisable(true);
        txtDescription.setDisable(true);
        txtPrice.setDisable(true);
        txtStock.setDisable(true);
        choiceBoxCategory.setDisable(true);
    }

    private void enabledFields() {
        txtProductName.setDisable(false);
        txtDescription.setDisable(false);
        txtPrice.setDisable(false);
        txtStock.setDisable(false);
        choiceBoxCategory.setDisable(false);
    }

    public boolean confirmationDialog(String type, int id) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        if (id != 0) {
            alert.setHeaderText(type + " item with ID: " + id + "?");
        } else {
            alert.setHeaderText(type + " item");
        }

        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.CANCEL) {
        }
        return result.get() == ButtonType.OK;
    }

    private boolean isInputValid() {
        StringBuilder errorMessage = new StringBuilder();

        if (txtProductName.getText().isEmpty()) {
            errorMessage.append("No valid product name!\n");
        }
        if (txtDescription.getText().isEmpty()) {
            errorMessage.append("No valid product description \n");
        }
        if (txtPrice.getText().isEmpty()) {
            errorMessage.append("No valid price value!\n");
        } else {
            // try to parse theprice into an int.
            try {
                Double.parseDouble(txtPrice.getText());
            } catch (NumberFormatException e) {
                errorMessage.append("No valid price (must be a number)!\n");
            }
        }
        if (txtStock.getText().isEmpty()) {
            errorMessage.append("No valid stock value!\n");
        } else {
            // try to parse the stock value into an int.
            try {
                Double.parseDouble(txtStock.getText());
            } catch (NumberFormatException e) {
                errorMessage.append("No valid price (must be a number)!\n");
            }
        }
        if (choiceBoxCategory.getValue() == null) {
            errorMessage.append("No category selected \n");
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage.toString());

            alert.showAndWait();

            return false;
        }
    }
}
