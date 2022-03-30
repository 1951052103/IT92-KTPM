/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.nbl.app;

import com.nbl.pojo.Category;
import com.nbl.pojo.Question;
import com.nbl.pojo.Choice;
import com.nbl.services.CategoryServices;
import com.nbl.services.QuestionServices;
import com.nbl.utils.JdbcUtils;
import com.nbl.utils.Utils;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class QuestionController implements Initializable {

    @FXML
    private ComboBox<Category> cbCategories;
    @FXML
    private TableView<Question> tbQuestions;
    @FXML
    private TextField txtKeywords;
    @FXML
    private TextField txtContent;
    @FXML
    private RadioButton rdoA;
    @FXML
    private RadioButton rdoB;
    @FXML
    private RadioButton rdoC;
    @FXML
    private RadioButton rdoD;
    @FXML
    private TextField txtA;
    @FXML
    private TextField txtB;
    @FXML
    private TextField txtC;
    @FXML
    private TextField txtD;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CategoryServices s = new CategoryServices();
        try {
            cbCategories.setItems(FXCollections.observableList(s.getCategories())); //them category
        } catch (SQLException ex) {
            Logger.getLogger(QuestionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.loadColumns();
        this.loadTableData(null);
        txtKeywords.textProperty().addListener((evt) -> {
            this.loadTableData(txtKeywords.getText());
        });
    }
    
    private void loadTableData(String kw) { //them hang
        QuestionServices s = new QuestionServices();
        try {
            this.tbQuestions.setItems(FXCollections.observableList(s.getQuestions(kw)));
        } catch (SQLException ex) {
            Logger.getLogger(QuestionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void loadColumns() { //them ten cot
        TableColumn col1 = new TableColumn("Id");
        col1.setCellValueFactory(new PropertyValueFactory("id"));
        col1.setPrefWidth(300);
        
        TableColumn col2 = new TableColumn("Question content");
        col2.setCellValueFactory(new PropertyValueFactory("content"));
        col2.setPrefWidth(300);
        
        this.tbQuestions.getColumns().addAll(col1, col2);
    }
    
    public void addHandler123(ActionEvent evt) throws SQLException {
        Question q = new Question(UUID.randomUUID().toString(), txtContent.getText(), 
            cbCategories.getSelectionModel().getSelectedItem().getId());
        List<Choice> choices = new ArrayList<>();
        
        TextField[] t = new TextField[] {txtA, txtB, txtC, txtD};
        RadioButton[] r = new RadioButton[] {rdoA, rdoB, rdoC, rdoD};
        for(int i = 0; i < t.length; i++) {
            choices.add(new Choice(UUID.randomUUID().toString(), t[i].getText(), r[i].isSelected(), q.getId()));
        }
        
        QuestionServices s = new QuestionServices();
        if (s.addQuestion(q, choices) == true) {
            this.loadTableData(null);
            Utils.getBox("Add question successful", Alert.AlertType.INFORMATION).show();
        }
        else {
            Utils.getBox("Something wrong!", Alert.AlertType.WARNING).show();
        }
    }
}
