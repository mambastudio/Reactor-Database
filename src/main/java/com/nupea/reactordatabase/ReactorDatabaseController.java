/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nupea.reactordatabase;

import com.nupea.reactordatabase.data.CharacteristicCategory;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.BreadCrumbBar;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.spreadsheet.GridBase;
import org.controlsfx.control.spreadsheet.SpreadsheetCell;
import org.controlsfx.control.spreadsheet.SpreadsheetCellType;
import org.controlsfx.control.spreadsheet.SpreadsheetView;

/**
 *
 * @author jmburu
 */
public class ReactorDatabaseController implements Initializable {
    @FXML
    VBox selectionBox;
    @FXML
    CheckComboBox<String> sizeComboBox;
    @FXML
    Button clearSizeButton;
    @FXML
    CheckComboBox<String> coreSizeComboBox;  
    @FXML
    Button clearCoreSizeButton;
    @FXML
    CheckComboBox<String> typeComboBox;
    @FXML
    Button clearTypeButton;
    @FXML
    BreadCrumbBar<String> breadCrumbBar;
    //Ordered Selection
    ObservableList<String> selectedTechnology = FXCollections.observableArrayList();
    
    ObservableList<CharacteristicCategory> categories = FXCollections.observableArrayList();
    @FXML
    ListView<CharacteristicCategory> categoryList;
    
    @FXML
    StackPane sheetPane;
    SpreadsheetView spreadSheet;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sizeComboBox.getItems().setAll("Large >900MWe", "Medium 300MWe - 900MWe", "SMR 30MWe - 300MWe", "Microreactor <30MWe");
        coreSizeComboBox.getItems().setAll("30MWth", "30MWth> - <1000MWth", "1000MWth - 3000MWth", ">3000MWth");
        typeComboBox.getItems().setAll("PWR", "BWR", "HWR", "SCWR", "GCR", "GFR", "SFR", "LFR", "MSR", "ADS", "Others");
       
        breadCrumbBar.setSelectedCrumb(new TreeItem("All"));
        breadCrumbBar.setAutoNavigationEnabled(false);
        // and listen to the relevant events (e.g. when the selected indices or 
        // selected items change).
        sizeComboBox.getCheckModel().getCheckedItems().addListener((ListChangeListener.Change<? extends String> c) -> {
            applyAllSelectedTechnology();
            if(sizeComboBox.getCheckModel().getCheckedItems().isEmpty())
                clearSizeButton.setDisable(true);
            else
                clearSizeButton.setDisable(false);
        });
        coreSizeComboBox.getCheckModel().getCheckedItems().addListener((ListChangeListener.Change<? extends String> c) -> {
            applyAllSelectedTechnology();
            if(coreSizeComboBox.getCheckModel().getCheckedItems().isEmpty())
                clearCoreSizeButton.setDisable(true);
            else
                clearCoreSizeButton.setDisable(false);
        });
        typeComboBox.getCheckModel().getCheckedItems().addListener((ListChangeListener.Change<? extends String> c) -> {
            applyAllSelectedTechnology();
            if(typeComboBox.getCheckModel().getCheckedItems().isEmpty())
                clearTypeButton.setDisable(true);
            else
                clearTypeButton.setDisable(false);
        });
        
        selectedTechnology.addListener((ListChangeListener.Change<? extends String> c) -> {
            if(c.getList().isEmpty()){
                breadCrumbBar.setSelectedCrumb(new TreeItem("All"));
            }
            else
                breadCrumbBar.setSelectedCrumb(BreadCrumbBar.<String>buildTreeModel((String[]) selectedTechnology.toArray(String[]::new)));
        });
        
        categories.addAll(
                new CharacteristicCategory("Plant"),
                new CharacteristicCategory("Reactor Unit"),
                new CharacteristicCategory("Reactor Coolant System"),
                new CharacteristicCategory("Reactor Core"),
                new CharacteristicCategory("Nuclear Steam Supply System"),
                new CharacteristicCategory("Core Materials"),
                new CharacteristicCategory("Reactor Pressure Vessel")
        );
        categoryList.getItems().setAll(categories);
        categoryList.setFixedCellSize(40);
        
        spreadSheet = new SpreadsheetView();
        spreadSheet.setEditable(false);
        initGrid();
        sheetPane.getChildren().add(spreadSheet);
    }
    
    private void applyAllSelectedTechnology(){
        var list = FXCollections.<String>observableArrayList();
        for(int i : sizeComboBox.getCheckModel().getCheckedIndices())
            list.add(sizeComboBox.getItems().get(i));
        for(int i : coreSizeComboBox.getCheckModel().getCheckedIndices())
            list.add(coreSizeComboBox.getItems().get(i));
        for(int i : typeComboBox.getCheckModel().getCheckedIndices())
            list.add(typeComboBox.getItems().get(i));
        
        if(list.isEmpty())
            selectedTechnology.removeAll(selectedTechnology);
        else
            selectedTechnology.setAll(list);
    }
    
    public void clearSizeChecks(ActionEvent e){
        sizeComboBox.getCheckModel().clearChecks();
    }
    
    public void clearCoreSizeChecks(ActionEvent e){
        coreSizeComboBox.getCheckModel().clearChecks();
    }   
    
    public void clearTypeChecks(ActionEvent e){
        typeComboBox.getCheckModel().clearChecks();
    }
    
    public void initGrid(){
        int rowCount = 15;
        int columnCount = 4;
        GridBase grid = new GridBase(rowCount, columnCount);
        
        ObservableList<ObservableList<SpreadsheetCell>> rows = FXCollections.observableArrayList();
        //for (int row = 0; row < grid.getRowCount(); ++row) {
            final ObservableList<SpreadsheetCell> list = FXCollections.observableArrayList();
            //for (int column = 0; column < grid.getColumnCount(); ++column) {
                list.add(SpreadsheetCellType.STRING.createCell(0, 0, 1, 1,"Reactor Type"));
            //}
            rows.add(list);
        //}
        grid.setRows(rows);
        spreadSheet.setGrid(grid);
    }
    
}
