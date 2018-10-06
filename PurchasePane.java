//  Description: PurchasePane displays a list of available laptops
//  from which a user can select to buy. It also shows how many
//  laptops are selected and what is the total amount.


import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.event.ActionEvent; 
import javafx.event.EventHandler;
import javafx.scene.layout.RowConstraints;

import java.util.ArrayList;
import java.text.DecimalFormat;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class PurchasePane extends VBox {
    private ArrayList<Laptop> laptopList, selectedList;
    private ListView<Laptop> laptopLV, selectedLV;
    
    Text availableLaptops = new Text("Available Laptops");
    Text selectedLaptops = new Text("Selected Laptops");
    int quantity = 0;
    double amount = 0;
    Text quantitySelected = new Text("Quantity Selected: " + quantity);
    Text totalAmount = new Text("Total Amount: $" + amount);
    Button pickLaptop = new Button("Pick a Laptop");
    Button removeLaptop = new Button("Remove a Laptop");
    ListView<Laptop> laptopListings;
    ListView<Laptop> selectedListings;
    
    /* PurchasePane() - Constructor for class PurchasePane.*/
    public PurchasePane(ArrayList<Laptop> list) {
        this.laptopList = list;
        selectedList = new ArrayList<Laptop>();
        
        this.laptopLV = new ListView<Laptop>();
        this.selectedLV = new ListView<Laptop>();
        
        // Creating section for listed laptops.
        VBox topHalf = new VBox();
        topHalf.setPadding(new Insets(5, 15, 5, 15));
        topHalf.setSpacing(10);
        availableLaptops.setStyle("-fx-font-size: 14;");
        topHalf.getChildren().addAll(availableLaptops, this.laptopLV);
        
        // Creating section for buttons.
        HBox control = new HBox();
        control.setAlignment(Pos.CENTER);
        control.setSpacing(5);
        control.setPadding(new Insets(15, 0, 15, 0));
        control.getChildren().addAll(pickLaptop, removeLaptop);
        
        // Creating section for selected laptops.
        VBox bottomHalf = new VBox();
        bottomHalf.setPadding(new Insets(5, 15, 5, 15));
        bottomHalf.setSpacing(10);
        selectedLaptops.setStyle("-fx-font-size: 14;");
        bottomHalf.getChildren().addAll(selectedLaptops, this.selectedLV);
        
        // Creating section for quantity and amount.
        HBox information = new HBox();
        quantitySelected.setStyle("-fx-font-size: 14;");
        totalAmount.setStyle("-fx-font-size: 14;");
        information.setAlignment(Pos.CENTER);
        information.setSpacing(5);
        information.setPadding(new Insets(15, 0, 15, 0));
        information.getChildren().addAll(quantitySelected, totalAmount);
        
        this.getChildren().addAll(topHalf, control, bottomHalf, information);
        
        // Register the button with its handler class
        ButtonHandler2 action = new ButtonHandler2();
        pickLaptop.setOnAction(action);
        removeLaptop.setOnAction(action);
        
    }
    
    /* updateLaptopList() - Updates list of available laptops on the top half.*/
    public void updateLaptopList() {
        ObservableList<Laptop> laptops = FXCollections.observableArrayList(this.laptopList);
        this.laptopLV.setItems(laptops);
        laptopListings = this.laptopLV;
    }
    
    /* updateSelectedList() - Updates list of selected laptops on the top half.*/
    public void updateSelectedList()
    {
        ObservableList<Laptop> laptops = FXCollections.observableArrayList(this.selectedList);
        this.selectedLV.setItems(laptops);
        selectedListings = this.selectedLV;
    }
    
//Step #2: Create a ButtonHandler class
    private class ButtonHandler2 implements EventHandler<ActionEvent>
    {   
        /* nullException() - Determines wether anything is selected in any of the ListViews.*/
        public boolean nullException(ListView<Laptop> list) {  
            boolean answer = true;
            try {
                list.getSelectionModel().getSelectedItem();
            } catch (NullPointerException e) {
                answer = false;
            }
            return answer;
        }
        
        // Override the abstact method handle()
        public void handle(ActionEvent e)
        {
            // If "Pick a Laptop" button is pressed AND you actually selected something from the top...
            if (e.getSource().equals(pickLaptop) && nullException(laptopListings)) {
                Laptop pickedLaptop1 = laptopListings.getSelectionModel().getSelectedItem();
                if (!selectedList.contains(pickedLaptop1)) {
                    
                    // Update quantity
                    selectedList.add(pickedLaptop1);
                    quantity++;
                    quantitySelected.setText("Quantity Selected: " + quantity);
                    
                    // Update amount
                    amount += pickedLaptop1.getPrice();
                    totalAmount.setText("Total Amount: $" + amount);
                    
                    updateSelectedList();
                }
            }
            
            // If "Remove a Laptop" button is pressed AND you actually selected something from the bottom...
            else if (e.getSource().equals(removeLaptop) && nullException(selectedListings)) {
                Laptop pickedLaptop2 = selectedListings.getSelectionModel().getSelectedItem();
                if (selectedList.contains(pickedLaptop2)) {
                    
                    // Update quantity
                    selectedList.remove(pickedLaptop2);
                    quantity--;
                    quantitySelected.setText("Quantity Selected: " + quantity);
                    
                    //Update amount
                    amount -= pickedLaptop2.getPrice();
                    totalAmount.setText("Total Amount: $" + amount);
                    
                    updateSelectedList();
                }
            } else {
                //All invalid action, do nothing here;
            }    
        }
    }
} 
