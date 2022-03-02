package com.nbl.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class PrimaryController {
    @FXML private TextField txtHeight;
    @FXML private TextField txtWeight;
    @FXML private Label lblResult;
    
    public void bmiHandler(ActionEvent event){
        double height = Double.parseDouble(this.txtHeight.getText());
        double weight = Double.parseDouble(this.txtWeight.getText());
        double bmi = weight/Math.pow(height, 2);
        
        this.lblResult.setTextFill(Color.DARKBLUE);
        
        if(bmi < 18.5){
            this.lblResult.setText("Gay");
        } else if (bmi < 25) {
            this.lblResult.setText("Binh thuong");
        } else {
            this.lblResult.setTextFill(Color.RED);
            this.lblResult.setText("Thua can");
        }
    }
}
