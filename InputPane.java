//  Description: InputPane generates a pane where a user can enter
//  a laptop information and create a list of available laptops.

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import javafx.geometry.HPos;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class InputPane extends HBox {
    private ArrayList<Laptop> laptopList;
    private PurchasePane purchasePane;
    
    // Instantiating UI elements for easier accessing.
    Label brandLabel;
    TextField brandTF;
    Label modelLabel;
    TextField modelTF;
    Label cpuLabel;
    TextField cpuTF;
    Label ramLabel;
    TextField ramTF;
    Label priceLabel;
    TextField priceTF;
    Text message = new Text("");
    TextArea area = new TextArea("No laptops.");
    
    /* InputPane() - Constructor for class InputPane */
    public InputPane(ArrayList<Laptop> list, PurchasePane pPane)
    {
        this.laptopList = list;
        this.purchasePane = pPane;
        
        // Instantiating of scene layouts.
        GridPane gridPane = new GridPane();
        GridPane leftHalf = new GridPane();
        GridPane textFields = new GridPane();
        
        // Left side of the application.
        textFields.getRowConstraints().add(new RowConstraints(35));
        textFields.getRowConstraints().add(new RowConstraints(35));
        textFields.getRowConstraints().add(new RowConstraints(35));
        textFields.getRowConstraints().add(new RowConstraints(35));
        textFields.getRowConstraints().add(new RowConstraints(35));
        textFields.getRowConstraints().add(new RowConstraints(35));
        
        textFields.add(brandLabel = new Label("Brand: "), 0, 0);
        textFields.add(brandTF = new TextField(), 1, 0);   
        textFields.add(modelLabel = new Label("Model: "), 0, 1);
        textFields.add(modelTF = new TextField(), 1, 1);
        textFields.add(cpuLabel = new Label("CPU (GHz): "), 0, 2);
        textFields.add(cpuTF = new TextField(), 1, 2);
        textFields.add(ramLabel = new Label("RAM (GB): "), 0, 3);
        textFields.add(ramTF = new TextField(), 1, 3);
        textFields.add(priceLabel = new Label("Price ($): "), 0, 4);
        textFields.add(priceTF = new TextField(), 1, 4);
        
        Button enter = new Button("Enter a Laptop Info.");
        
        leftHalf.add(message, 0, 0);
        leftHalf.add(textFields, 0, 1);
        leftHalf.add(enter, 0, 2);
        
        leftHalf.getRowConstraints().add(new RowConstraints(30));
        leftHalf.setHalignment(enter, HPos.CENTER);
        leftHalf.setPadding(new Insets(10, 10, 10, 30));
        
        // Right side of the application.
        VBox textArea = new VBox();
        area.setPrefHeight(370);
        textArea.getChildren().add(area);
        
        gridPane.getColumnConstraints().add(new ColumnConstraints(300));
        gridPane.getColumnConstraints().add(new ColumnConstraints(300));
        
        gridPane.add(leftHalf, 0, 0); 
        gridPane.add(textArea, 1, 0);
        
        // Add the left half and right half to the InputPane
        this.getChildren().add(gridPane);
        
        // Register source object with event handler
        ButtonHandler action = new ButtonHandler();
        enter.setOnAction(action);
        
    }
    
    /* updater() - Whenever a laptop has been successfully added, this method will
     * update the aggregated purchasePane. */
    public void updater() {
        this.purchasePane.updateLaptopList();
    }
    
    /* Step #2: Create a ButtonHandler class
    ButtonHandler listens to see if the buttont "Enter a Laptop Info." is pushed or not,
    When the event occurs, it get a laptop's brand, model, CPU, RAM and price
    information from the relevant text fields, then create a new Laptop object and add it inside
    the laptopList. Meanwhile it will display the laptop's information inside the text area.
    It also does error checking in case any of the textfields are empty or wrong data was entered. */
    private class ButtonHandler implements EventHandler<ActionEvent>
    {
        /* textFieldsEmpty() - Helper function to check if any text fields are empty. */
        public boolean textFieldsEmpty() {
            boolean textFieldsEmpty = false;
            
            if (brandTF.getText().isEmpty()) {
                textFieldsEmpty = true;
            } else if (modelTF.getText().isEmpty()) {
                textFieldsEmpty = true;
            } else if (cpuTF.getText().isEmpty()) {
                textFieldsEmpty = true;
            } else if (ramTF.getText().isEmpty()) {
                textFieldsEmpty = true;
            } else if (priceTF.getText().isEmpty()) {
                textFieldsEmpty = true;
            }
            
            return textFieldsEmpty;
        }
        
        /* textFieldsWrong() - Helper function to check if any text fields are incorrectly formatted.*/
        public boolean textFieldsWrong() {
            boolean textFieldsWrong = false;
            
            try {
                Double.parseDouble(cpuTF.getText());
            } catch (NumberFormatException e) {
                textFieldsWrong = true;
            }
            
            try {
                Double.parseDouble(ramTF.getText());
            } catch (NumberFormatException e) {
                textFieldsWrong = true;
            }
            
            try {
                Double.parseDouble(priceTF.getText());
            } catch (NumberFormatException e) {
                textFieldsWrong = true;
            }
            
            return textFieldsWrong;
        }
        
        /* inputPaneLaptops() - Helper function to traverses through the ArrayList of 
         * laptop and returns a String version of it.*/
        public String inputPaneLaptops(ArrayList list) {
            String stringList = "";
            for (int i = 0; i < list.size(); i++) {
                stringList += list.get(i).toString();
            }
            return stringList;
        }
        
        /* handle() - Overriding handle() abstract method so that it will check if textfields
         * are empty, wrong, or create a duplicate. If not, then update ArrayList and then 
         the right side of the application.*/
        public void handle(ActionEvent e) {
            if (textFieldsEmpty() || textFieldsWrong()) {
                if (textFieldsEmpty()) {
                    message.setText("Please fill all fields.");
                    message.setFill(Color.RED);
                } else {
                    message.setText("Please enter correct data format.");
                    message.setFill(Color.RED);
                }
            } else {
                String brand = brandTF.getText();
                String model = modelTF.getText();
                Double cpu = Double.parseDouble(cpuTF.getText());
                Double ram = Double.parseDouble(ramTF.getText());
                Double price = Double.parseDouble(priceTF.getText());
                
                Laptop enteredLaptop = new Laptop(brand, model, cpu, ram, price);
                
                // Checking if the laptop is a duplicate.
                boolean duplicateLaptop = false; 
                for (int i = 0; i < laptopList.size(); i++) {
                    if (laptopList.get(i).getBrand().equals(enteredLaptop.getBrand())
                            && laptopList.get(i).getModel().equals(enteredLaptop.getModel())
                            && laptopList.get(i).getCPU() == enteredLaptop.getCPU()
                            && laptopList.get(i).getRAM() == enteredLaptop.getRAM()) {
                        duplicateLaptop = true;
                    }
                }
                
                // Laptop is not a duplicate.
                if (duplicateLaptop != true) {
                    laptopList.add(enteredLaptop);
                    message.setText("Laptop entered.");
                    message.setFill(Color.BLACK);
                    area.setText(inputPaneLaptops(laptopList)); // Alter the list of laptops on input pane.
                    updater(); // Update the list of laptops on purchase pane.
                // Laptop was actually a duplicate.
                } else {
                    message.setText("Duplicate laptop not entered.");
                    message.setFill(Color.RED);
                }
            }
        }
    }   
}
