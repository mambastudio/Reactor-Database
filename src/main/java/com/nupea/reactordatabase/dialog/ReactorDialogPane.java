/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nupea.reactordatabase.dialog;

import com.nupea.reactordatabase.data.CharacteristicCategory;
import com.nupea.reactordatabase.data.Reactor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import org.controlsfx.control.spreadsheet.GridBase;
import org.controlsfx.control.spreadsheet.SpreadsheetCell;
import org.controlsfx.control.spreadsheet.SpreadsheetCellType;
import org.controlsfx.control.spreadsheet.SpreadsheetView;

/**
 *
 * @author user
 */
public class ReactorDialogPane extends BorderPane{
    private final ObjectProperty<Reactor> reactorProperty = new SimpleObjectProperty();
    private final SpreadsheetView spreadSheet = new SpreadsheetView();
    
    @FXML
    ListView<CharacteristicCategory> categoriesListView;
    @FXML
    StackPane viewPort;
    
    public ReactorDialogPane() {
       
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "ReactorDialog.fxml"));
        fxmlLoader.setRoot(ReactorDialogPane.this);
        fxmlLoader.setController(ReactorDialogPane.this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        viewPort.getChildren().add(spreadSheet);
        
        reactorProperty.addListener((_, _, nv)->{
            categoriesListView.getItems().setAll(reactorProperty.get().getCategories());
            categoriesListView.getSelectionModel().selectFirst();
            Platform.runLater(()-> categoriesListView.requestFocus());
        });
    }
    
    private void initGrid(){
        
        var reactor = reactorProperty.get();
        var category = reactor.getCategory("Plant");
        
        var grid = new GridBase(category.getInfo().size(), 2);
        
        ObservableList<ObservableList<SpreadsheetCell>> rows = FXCollections.observableArrayList();
        for (int row = 0; row < grid.getRowCount(); ++row) {
            final ObservableList<SpreadsheetCell> list = FXCollections.observableArrayList();
            var rowCell = SpreadsheetCellType.STRING.createCell(row, 0, 1, 1, (String) new ArrayList(category.getInfo().keySet()).get(row));    
            rowCell.setEditable(false);
            list.add(rowCell);
            rows.add(list);
        }
        
        grid.setRows(rows);
        grid.setRowHeightCallback(_ -> 40.);
        spreadSheet.setGrid(grid);
    }
    public void setReactor(Reactor reactor){
        var opt = Optional.of(reactor);
        switch(opt.isPresent()){
            case true-> this.reactorProperty.set(reactor);
            case false-> {}
        }   
        
        initGrid();
    }
}
