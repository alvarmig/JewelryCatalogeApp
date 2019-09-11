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
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class that handles all the functionality between the views
 * and the model for Cataloge application.
 *
 * @author Miguel
 */
public class SceneCatalogeOverviewController implements Initializable {

    ObservableList<Category> categoryData = FXCollections.observableArrayList();
    tblProductAdapter tblProduct = new tblProductAdapter();
    tblCategoryAdapter tblCategory = new tblCategoryAdapter();

    private Stage dialogStage;
    private String buttonState = "default";

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
    private TextField txtSearch;

    @FXML
    private Button btnSearch;

    /**
     * method: btnCancel_ActionPerformed. Handles the event for the CANCEL
     * button. When the event is triggered txtFields are disabled and "cancel"
     * state is passed to set the state of the different buttons.
     *
     * @param event
     */
    @FXML
    private void btnCancel_ActionPerformed(ActionEvent event) {
        disableFields();
        // set state of the different buttons
        enableDisableButtons("cancel");
    }

    /**
     * method: btnNewProduct_ActionPerformed. Handles the event for the NEW
     * button. When the event is triggered txtFields are enabled and cleared.
     * "new" state is passed to set the state of the different buttons.
     *
     * @param event
     */
    @FXML
    private void btnNewProduct_ActionPerformed(ActionEvent event) {
        enabledFields();
        clearFields();
        // set state of the different buttons
        enableDisableButtons("new");
        buttonState = "new";
    }

    /**
     * method: btnEdit_ActionPerformed. Handles the event for the EDIT button.
     * When the event is triggered txtFields are enabled. "edit" state is passed
     * to set the state of the different buttons.
     *
     * @param event
     */
    @FXML
    private void btnEdit_ActionPerformed(ActionEvent event) {
        enabledFields();
        buttonState = "edit";
        // set state of the different buttons
        enableDisableButtons("edit");
    }

    /**
     * method: btnSaveProduct_ActionPerformed. Handles the event for the SAVE
     * button. When the event is triggered a saveItem or editItem method will be
     * called. The action that is called depends on the state of the buttonState
     * variable. The buttons will be set to the "SAVE" state if the actions is
     * completed.
     *
     * @param event
     */
    @FXML
    private void btnSaveProduct_ActionPerformed(ActionEvent event) {
        // SAVE the new item created.
        if ("new".equals(buttonState)) {
            // if the item is saved, set the state of the buttons.
            if (saveItem()) {
                // set state of the different buttons
                enableDisableButtons("save");
            } else {
                enableDisableButtons("new");
            }
            // EDIT the current item selected.
        } else if ("edit".equals(buttonState)) {
            // if the item is edited, set the state of the buttons.
            if (editItem()) {
                // set state of the different buttons
                enableDisableButtons("save");
            } else {
                enableDisableButtons("edit");
            }
        }
    }

    /**
     * method: btnDelete_ActionPerformed. Handles the event for the DELETE
     * button. When the event is triggered the id from the item will be passed
     * to the Delete CRUD operation from the tblProductAdapter. First a
     * validation is required to ensure that there the user is selection an item
     * otherwise an alert will be displayed.
     *
     * @param event
     */
    @FXML
    private void btnDelete_ActionPerformed(ActionEvent event) {
        int selectedIndex = tbProductDetails.getSelectionModel().getSelectedIndex();
        int selectedID = 0;

        // Check if an item is selected from the table.
        if (selectedIndex >= 0) {
            // get the id from the item selected.
            selectedID = tbProductDetails.getSelectionModel().getSelectedItem().getProductId();
            // Dialog to confirm the item to be delted.
            if (confirmationDialog("Delete selected", selectedID)) {
                // DELTE CRUD operation.
                tblProduct.Delete(selectedID);
                // reload the table
                loadTableProducts();
                // set the state of the buttons.
                enableDisableButtons("delete");
            }

        } else {
            // If nothing selected, display an alert.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(dialogStage);
            alert.setTitle("No selection");
            alert.setHeaderText("No value selected");
            alert.setContentText("Please select an item in the table.");

            alert.showAndWait();
        }
    }

    /**
     * method: btnSearch_ActionPerformed. Handles the event for SEARCH button.
     * This method will load the table if no string is passed to the textSearch
     * field otherwise it will call the loadTableProductsByCategory if a string
     * is passed.
     *
     * @param event
     */
    @FXML
    private void btnSearch_ActionPerformed(ActionEvent event) {
        StringBuilder filter = new StringBuilder();

        // load the item table if no string is passed.
        if ("".equals(txtSearch.getText())) {
            loadTableProducts();
        } else {
            // append % to create the %String% format.
            filter.append('%');
            filter.append(txtSearch.getText().toLowerCase());
            filter.append('%');
            // load the table passing the string from the txtSearch field.
            loadTableProductsByCategory(filter.toString());
        }

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
        assert txtSearch != null : "fx:id=\"txtSearch\" was not injected: check your FXML file 'sceneCatalogeOverview.fxml'.";
        assert btnSearch != null : "fx:id=\"btnSearch\" was not injected: check your FXML file 'sceneCatalogeOverview.fxml'.";

    }

    /**
     * method: initialize. Set the property for each one of the columns of the
     * table view. Clears the text fields for the product details area. Loads
     * the table view and the category choiceBox. Add the listener to the table
     * view.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colID.setCellValueFactory(cellData -> cellData.getValue().productIdProperty());
        colName.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
        colDescription.setCellValueFactory(cellData -> cellData.getValue().productDescriptionProperty());
        colPrice.setCellValueFactory(cellData -> cellData.getValue().productPriceProperty());
        colStock.setCellValueFactory(cellData -> cellData.getValue().productStockProperty());
        colCategory.setCellValueFactory(cellData -> cellData.getValue().productCategoryProperty());

        loadTableProducts();
        loadChoiceBoxCategory();
        enableDisableButtons("default");

        // <editor-fold desc="Add lister with lamda function">
        //showProductDetails(null);
        /*tbProductDetails.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showProductDetails(newValue));*/
        // </editor-fold>
        /* Adds the listener to the selected item from the table view.
         * Each time an item from the table view is selected the text fields are filled.
         */
        tbProductDetails.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                // check that an item has been selected.
                if (tbProductDetails.getSelectionModel().getSelectedItem() != null) {
                    txtProductName.setText(tbProductDetails.getSelectionModel().getSelectedItem().getProductName());
                    txtDescription.setText(tbProductDetails.getSelectionModel().getSelectedItem().getProductDescription());
                    txtPrice.setText(Double.toString(tbProductDetails.getSelectionModel().getSelectedItem().getProductPrice()));
                    txtStock.setText(Integer.toString(tbProductDetails.getSelectionModel().getSelectedItem().getProductStock()));

                    // set the choiceBox to the value selected.
                    for (int i = 0; i < categoryData.size(); i++) {
                        if (categoryData.get(i).getName() == null ? tbProductDetails.getSelectionModel().getSelectedItem().getProductCategory() == null
                                : categoryData.get(i).getName().equals(tbProductDetails.getSelectionModel().getSelectedItem().getProductCategory())) {
                            choiceBoxCategory.setValue(categoryData.get(i));
                        }
                    }
                    buttonState = "listener";
                    enableDisableButtons("listener");
                    disableFields();
                } else {
                    clearFields();
                }
            }
        });
    }

    /**
     * method: loadTable. Gets the table columns from the tableview and add each
     * one of the columns.
     */
    private void loadTable() {
        tbProductDetails.getColumns().addAll(colID);
        tbProductDetails.getColumns().addAll(colName);
        tbProductDetails.getColumns().addAll(colDescription);
        tbProductDetails.getColumns().addAll(colPrice);
        tbProductDetails.getColumns().addAll(colStock);
        tbProductDetails.getColumns().addAll(colCategory);
    }

    /**
     * method: loadTableProducts. method that calls the SELECT CRUD operation to
     * get all the items. The result is passed to an observable list that is
     * added to the TableView.
     */
    private void loadTableProducts() {
        tbProductDetails.getItems().clear();
        tbProductDetails.getColumns().clear();

        // Create observable list from the result of the SELECT operation.
        ObservableList<ProductDetail> productData = FXCollections.observableArrayList(tblProduct.Select());
        loadTable();

        // Add the observable lis to the TableView
        tbProductDetails.setItems(productData);

        clearFields();
    }

    /**
     * method: loadTableProducts. method that calls the SELECT CRUD operation to
     * get all the items by category. The result is passed to an observable list
     * that is added to the TableView.
     */
    private void loadTableProductsByCategory(String searchFilter) {
        tbProductDetails.getItems().clear();
        tbProductDetails.getColumns().clear();

        // Create observable list from the result of the SELECT operation.
        ObservableList<ProductDetail> productData = FXCollections.observableArrayList(tblProduct.SelectByCategory(searchFilter));
        loadTable();

        // Add the observable lis to the TableView
        tbProductDetails.setItems(productData);

        clearFields();
    }

    /**
     * method: loadChoiceBoxCategory. Method to fill the choiceBox with the
     * values from the Category table from the db. A SELECT is used to get the
     * values from the table.
     */
    private void loadChoiceBoxCategory() {
        choiceBoxCategory.getItems().clear();
        // Create observable list from the result of the SELECT operation.
        categoryData = FXCollections.observableArrayList(tblCategory.Select());
        choiceBoxCategory.getItems().addAll(categoryData);
    }

    /**
     * method: saveItem. This method will call the INSERT CRUD operation to add
     * a new element to the product table. A confirmation dialog is displayed to
     * the user before confirmation.
     *
     * @return boolean
     */
    private boolean saveItem() {
        // check if the input values are valid.
        if (isInputValid()) {
            // send a confirmation dialog to the user.
            if (confirmationDialog("Create new", 0)) {
                int idCategory = choiceBoxCategory.getValue().getID();
                // Call the INSERT CRUD operation to the product table.
                if (tblProduct.Insert(txtProductName.getText(), txtDescription.getText(), Double.parseDouble(txtPrice.getText()), Integer.parseInt(txtStock.getText()), idCategory) == 1) {
                    // load the table view.
                    loadTableProducts();
                    //disable the text and and choicebox.
                    disableFields();
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * method: editItem. This method will call the UPDATE CRUD operation to
     * modify an element that has been selected by the user. A confirmation
     * dialog is displayed to the user before confirmation.
     *
     * @return boolean
     */
    private boolean editItem() {
        // A dialog wll display of the edit button is selected without an item available in the table view.
        if (tbProductDetails.getSelectionModel().getSelectedItem() == null) {

            String errorMessage = "Please select an item or create a new product";

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(dialogStage);
            alert.setTitle("No item to edit");
            alert.setHeaderText("Please select an item from the list");
            alert.setContentText(errorMessage);

            alert.showAndWait();

        } else {
            // ccheck that the fields are valid.
            if (isInputValid()) {
                // send a confirmation dialog to the user.
                if (confirmationDialog("Edit selected", 0)) {
                    int id = tbProductDetails.getSelectionModel().getSelectedItem().getProductId();
                    int idCategory = choiceBoxCategory.getValue().getID();
                    // Call the UPDATE CRUD operation to the product table.
                    if (tblProduct.Update(id, txtProductName.getText(), txtDescription.getText(), Double.parseDouble(txtPrice.getText()), Integer.parseInt(txtStock.getText()), idCategory) == 1) {
                        // load the table view.
                        loadTableProducts();
                        //disable the text and and choicebox.
                        disableFields();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * method: enableDisableButtons. Set the state of the SAVE, CANCEL, EDIT,
     * NEW buttons. Buttons can be enabled or disabled.
     *
     * @param state
     */
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

    /**
     * method: confirmationDialog. Displays a confirmation dialog depending on
     * the string passed to the method.
     *
     * @param type
     * @param id
     * @return boolean
     */
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

    /**
     * method: isInputValid. Method to verify the text fields and choice box
     * when a new item is created or when an item is edited.
     *
     * @return boolean
     */
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
            // try to parse the price into a Double.
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
                Integer.parseInt(txtStock.getText());
            } catch (NumberFormatException e) {
                errorMessage.append("No valid stock value (must be a round number)!\n");
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

    // <editor-fold desc="Add lister with lamda function, Called method">
    /**
     * method: showProductDetails. This method is called by the listener to
     * modify the textFileds when an item is selected by the user.
     *
     * @param item
     */
    private void showProductDetails(ProductDetail item) {

        // check that an item has been selected.
        if (item != null) {
            // set text fields to the value selected
            txtProductName.setText(item.getProductName());
            txtDescription.setText(item.getProductDescription());
            txtPrice.setText(Double.toString(item.getProductPrice()));
            txtStock.setText(Integer.toString(item.getProductStock()));

            // set the choiceBox to the value selected.
            for (int i = 0; i < categoryData.size(); i++) {
                if (categoryData.get(i).getName() == null ? item.getProductCategory() == null : categoryData.get(i).getName().equals(item.getProductCategory())) {
                    choiceBoxCategory.setValue(categoryData.get(i));
                }
            }

            buttonState = "listener";
            enableDisableButtons("listener");
            disableFields();
        } else {
            clearFields();
        }
    }
    // </editor-fold>
}
