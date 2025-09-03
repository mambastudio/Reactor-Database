/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nupea.reactordatabase.util;

import java.io.IO;
import java.util.function.UnaryOperator;
import javafx.application.Platform;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;
import org.controlsfx.control.spreadsheet.SpreadsheetCell;
import org.controlsfx.control.spreadsheet.SpreadsheetCellBase;
import org.controlsfx.control.spreadsheet.SpreadsheetCellEditor;
import org.controlsfx.control.spreadsheet.SpreadsheetCellType;
import org.controlsfx.control.spreadsheet.SpreadsheetView;

/**
 *
 * @author user
 */
public class StrictIntegerCellType extends SpreadsheetCellType<Integer> {
        
    public static final StrictIntegerCellType STRICT_INT = new StrictIntegerCellType();

    public StrictIntegerCellType() {
        super(new StringConverter<>() {
            @Override
            public String toString(Integer object) {
                return object == null ? "" : object.toString();
            }

            @Override
            public Integer fromString(String string) {
                if (string == null || string.isBlank() || "-".equals(string)) return null;
                return Integer.valueOf(string);
            }
        });
    }
    
    public SpreadsheetCell createCell(int row, int column, int rowSpan, int colSpan, Integer value) {
        SpreadsheetCellBase cell = new SpreadsheetCellBase(row, column, rowSpan, colSpan, this);
        cell.setItem(value);
        return cell;
    }

    @Override
    public SpreadsheetCellEditor createEditor(SpreadsheetView sv) {
        return new SpreadsheetCellEditor(sv) {
            private final NumericField field = new NumericField(Integer.class);
            
            
            
            @Override
            public void startEdit(Object value, String format, Object... options) {
                field.setText(value == null ? "0" : value.toString());
                
                // Commit on enter or focus lost
                field.setOnAction(e -> {
                    endEdit(true);
                });
                field.focusedProperty().addListener((obs, old, is) -> {
                    if (!is) endEdit(true);
                });
                
                // Cancel on ESC
                field.setOnKeyPressed(event -> {
                    switch (event.getCode()) {
                        case ENTER -> {
                            endEdit(true);
                            event.consume();
                        }
                        case ESCAPE -> {
                            endEdit(false); // restore old value
                            event.consume();
                        }
                    }
                });
            }

            @Override
            public Control getEditor() {
                Platform.runLater(()->field.requestFocus());
                return field;
            }

            @Override
            public String getControlValue() {
                return field.getText();
            }

            @Override
            public void end() {
                // cleanup if needed
            }
        };
    }

    @Override
    public String toString(Integer t) {
        return t == null ? "" : Integer.toString(t);
    }

    @Override
    public boolean match(Object o, Object... os) {
        // Accept Integer, or a String that is a valid integer per our strict rules (optional '-' + digits)
        if (o == null) return true; // allow empty cell
        if (o instanceof Integer) return true;
        if (o instanceof CharSequence cs) {
            String s = cs.toString();
            return s.isEmpty() || s.matches("-?\\d+");
        }
        return false;
    }

    @Override
    public Integer convertValue(Object o) {
        if (o == null) return null;
        if (o instanceof Integer i) return i;
        if (o instanceof CharSequence cs) {
            String s = cs.toString();
            if (s.isBlank() || "-".equals(s)) return null; // treat empty / lone '-' as no value
            try {
                return Integer.valueOf(s);
            } catch (NumberFormatException ignored) {
                // fall through
            }
        }
        // For anything else, reject by returning null; SpreadsheetView will keep old value on invalid commit
        return null;
    }
    
    @Override public String toString() { return "StrictInteger"; }
}
