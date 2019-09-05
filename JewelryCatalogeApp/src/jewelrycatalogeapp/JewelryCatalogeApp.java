/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jewelrycatalogeapp;

import java.io.IOException;
import java.util.logging.Level;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author Miguel
 */
public class JewelryCatalogeApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {

        try {

            Parent root; //Defino padre o raiz del documento

            //Cargo ventana al escenario o raiz del documento
            root = FXMLLoader.load(JewelryCatalogeApp.class.getResource("/Resources/Views/sceneCatalogeOverview.fxml"));

            //Creo un nuevo escenario y le asigno la raiz
            Scene scene = new Scene(root);

            //A la ventana que estoy creando por parametros le asigno el escenario
            primaryStage.setScene(scene);

            //Asigno el titulo de la ventana
            primaryStage.setTitle("Product Cataloge");
            //TODO: Manejar tamanos de ventanas

            //Muestro la ventana
            primaryStage.show();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(JewelryCatalogeApp.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //JOptionPane.

        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);

    }

}
