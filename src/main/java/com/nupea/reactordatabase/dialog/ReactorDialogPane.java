/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nupea.reactordatabase.dialog;

import com.nupea.reactordatabase.data.CharacteristicCategory;
import com.nupea.reactordatabase.data.FieldValue;
import com.nupea.reactordatabase.data.Reactor;
import java.io.IO;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
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
        
        categoriesListView.getSelectionModel().selectedItemProperty().addListener((_, _ , nv)-> {
            if(Optional.ofNullable(nv).isPresent()){
                initGrid(nv);
                spreadSheet.getSelectionModel().clearSelection();
            }
        });
    }
    
    private void initGrid(){
        
        var reactor = reactorProperty.get();
        var category = reactor.getCategory("Plant");
        
        var grid = new GridBase(category.getInfo().size(), 2);
        
        double width = 20;
        
        ObservableList<ObservableList<SpreadsheetCell>> rows = FXCollections.observableArrayList();
        for (int row = 0; row < grid.getRowCount(); ++row) {
            final ObservableList<SpreadsheetCell> list = FXCollections.observableArrayList();
                        
            var key = (String) new ArrayList(category.getInfo().keySet()).get(row);
            
            var field = category.getInfo().get(key);
            
            var value0 = key;
            var value1 = field.getValue();
            
            var rowCell0 = SpreadsheetCellType.STRING.createCell(row, 0, 1, 1, value0);    
            var rowCell1 = createCell(row, 1, 1, 1, value1);    
            
            rowCell0.setEditable(false);
            rowCell1.setStyle("-fx-alignment: CENTER;");
            
            Text t = new Text(value0);
            t.setFont(new Label().getFont());
            double w = t.getBoundsInLocal().getWidth() + 10;
            width = Math.max(width, w);
            
            list.addAll(rowCell0, rowCell1);
            rows.add(list);
        }
        
        grid.setRows(rows);
        grid.setRowHeightCallback(_ -> 40.);
        spreadSheet.setGrid(grid);
        spreadSheet.getColumns().get(0).setPrefWidth(width);
        
        
    }
    
    private void initGrid(CharacteristicCategory category){                
        var grid = new GridBase(category.getInfo().size(), 2);
        
        double width = 20;
        
        ObservableList<ObservableList<SpreadsheetCell>> rows = FXCollections.observableArrayList();
        for (int row = 0; row < grid.getRowCount(); ++row) {
            final ObservableList<SpreadsheetCell> list = FXCollections.observableArrayList();
            var key = (String) new ArrayList(category.getInfo().keySet()).get(row);
            
            var field = category.getInfo().get(key);
            
            var value0 = key;
            var value1 = field.getValue();
            
            var rowCell0 = SpreadsheetCellType.STRING.createCell(row, 0, 1, 1, value0);    
            var rowCell1 = createCell(row, 1, 1, 1, value1);      
            
            rowCell0.setEditable(false);
            rowCell1.setStyle("-fx-alignment: CENTER;");

            
            Text t = new Text(value0);
            t.setFont(new Label().getFont());
            double w = t.getBoundsInLocal().getWidth() + 10;
            width = Math.max(width, w);
            
            list.addAll(rowCell0, rowCell1);
            rows.add(list);
        }
        
        grid.setRows(rows);
        grid.setRowHeightCallback(_ -> 40.);
        spreadSheet.setGrid(grid);
        spreadSheet.getColumns().get(0).setPrefWidth(width);
    }
    
    
    public void setReactor(Reactor reactor){
        var opt = Optional.of(reactor);
        switch(opt.isPresent()){
            case true-> this.reactorProperty.set(reactor);
            case false-> {}
        }   
        
        initGrid();
    }
        
    private SpreadsheetCell createCell(int row, int column, int rowSpan, int columnSpan, Object value){
        return switch(value.getClass()){
            case Class<?> c when c == String.class -> SpreadsheetCellType.STRING.createCell(row, column, rowSpan, columnSpan, (String) value);
            case Class<?> c when c == Integer.class -> SpreadsheetCellType.INTEGER.createCell(row, column, rowSpan, columnSpan, (Integer) value);
            case Class<?> c when c == Double.class -> SpreadsheetCellType.DOUBLE.createCell(row, column, rowSpan, columnSpan, (Double) value);
            default -> throw new UnsupportedOperationException();
        };
    }
}
