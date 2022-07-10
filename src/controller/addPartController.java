package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Allows user to add a new part. Includes fields name, inventory, price, max, min, and machine id or company name.
 */
public class addPartController implements Initializable {
    public ToggleGroup addPartToggle;
    public TextField addPartMachineID;
    public Label addPartMachineIDLabel;
    public Button addPartCancelBtn;
    public Button addPartSaveBtn;
    public RadioButton addPartInHouseBtn;
    public RadioButton addPartOutsourcedBtn;
    public TextField addPartName;
    public TextField addPartStock;
    public TextField addPartPrice;
    public TextField addPartMax;
    public TextField addPartMin;

    /**
     * Empty string to hold validation errors from Part.validPart() in the
     * onAddPartSaveBtn method.
     */
    private String exception = "";
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Cancel button changes scene to Main Menu.
     */
    public void addPartCancelBtn(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Cancel");
        alert.setHeaderText("Confirm Cancel");
        alert.setContentText("Are you sure you want to cancel adding a new part?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1080, 400);
            stage.setTitle("Back to Main Screen");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * When the radio button "In House" is selected, the bottom label will say "Machine ID".
     */
    public void onAddPartInHouseBtn(ActionEvent actionEvent) {
        addPartMachineIDLabel.setText("Machine ID");
    }

    /**
     * When the radio button "Outsourced" is selected, the bottom label will say "Company Name".
     */
    public void onAddPartOutsourcedBtn(ActionEvent actionEvent) {
        addPartMachineIDLabel.setText("Company Name");
    }

    /**
     * Save button will take the data in the fields, validate them, and determine
     * if the part is In House or Outsourced. If all data is present and valid, a new part will be
     * made and added to the Inventory.allParts List. Otherwise, descriptive error messages will pop up.
     * After completion, scene transitions the Main screen.
     */
    public void onAddPartSaveBtn(ActionEvent actionEvent) throws IOException {
        int partID = Inventory.getAllParts().size() + 1;
        for (int i = 0; i < Inventory.getAllParts().size(); i++) {
            if (Inventory.getAllParts().get(i).getPartID() == partID) {
                partID++;
                i = 0;
            }
        }
        String name = addPartName.getText();
        String stock = addPartStock.getText();
        String price = addPartPrice.getText();
        String min = addPartMin.getText();
        String max = addPartMax.getText();
        String machOrComp = addPartMachineID.getText();
        boolean madeInHouse = true;
        if (addPartInHouseBtn.isSelected()) {
            madeInHouse = true;
        } else if (addPartOutsourcedBtn.isSelected()) {
            madeInHouse = false;
        }
        String errorMessages = "";
        Double validatedPrice;
        try {
            validatedPrice = Double.parseDouble(price);
        }
        catch (NumberFormatException e) {
            errorMessages += "Not a double. \n";
        }
        if (price < 0){
            //add text to alert area of screen (dont display just yet)
            //append to string to be displayed in alert area
            //errorMessages += "Price has to be positive";
        }
        if (name.isEmpty()){
            //another validation
            //append to string to be displayed in alert area
        }
        if (true){
            //another validation
            //append to string to be displayed in alert area
        }
        if (errorMessages.compareTo("")){ //alert area of the screen is not blank .empty()? .equals()?
            //display or refresh the mssage
        }
        else{ //alert area of the screen is blank
            //save
        }
                exception = Part.validPart(name, price, stock, min, max, machOrComp, madeInHouse);
                if (exception != "") {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error Adding Part");
                    alert.setContentText(exception);
                    alert.showAndWait();
                }
                else {
                    double roundedPrice = (Math.round(Double.parseDouble(price) * 100)) / 100.0;
                    if (addPartInHouseBtn.isSelected()) {
                        InHouse inHousePart = new InHouse(partID, name, roundedPrice, Integer.parseInt(stock), Integer.parseInt(min), Integer.parseInt(max), Integer.parseInt(addPartMachineID.getText()), true);
                        Inventory.addPart(inHousePart);


                    } else if (addPartOutsourcedBtn.isSelected()) {
                        Outsourced outsourcedPart = new Outsourced(partID, name, roundedPrice, Integer.parseInt(stock), Integer.parseInt(min), Integer.parseInt(max), addPartMachineID.getText(), false);
                        Inventory.addPart(outsourcedPart);
                    }

                    Parent root = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml"));
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root, 1080, 400);
                    stage.setTitle("Back to Main Screen");
                    stage.setScene(scene);
                    stage.show();
                }
            /*catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Error Adding Part");
                alert.setContentText("Form contains blank fields.");
                alert.showAndWait();
            }*/
    }
}
