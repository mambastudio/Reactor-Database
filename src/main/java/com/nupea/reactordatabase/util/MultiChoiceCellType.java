package com.nupea.reactordatabase.util;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.Control;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.spreadsheet.SpreadsheetCell;
import org.controlsfx.control.spreadsheet.SpreadsheetCellBase;
import org.controlsfx.control.spreadsheet.SpreadsheetCellEditor;
import org.controlsfx.control.spreadsheet.SpreadsheetCellType;
import org.controlsfx.control.spreadsheet.SpreadsheetView;

import java.util.ArrayList;
import java.util.List;

public class MultiChoiceCellType<T> extends SpreadsheetCellType<List<T>> {

    private final List<T> options;

    public MultiChoiceCellType(List<T> options) {
        super(null); // no need for StringConverter, we override toString()
        this.options = new ArrayList<>(options);
    }

    public SpreadsheetCell createCell(int row, int column, int rowSpan, int colSpan, List<T> values) {
        SpreadsheetCellBase cell = new SpreadsheetCellBase(row, column, rowSpan, colSpan, this);
        cell.setItem(values == null ? new ArrayList<>() : new ArrayList<>(values));
        return cell;
    }

    @Override
    public SpreadsheetCellEditor createEditor(SpreadsheetView sv) {
        return new SpreadsheetCellEditor(sv) {
            private final CheckComboBox<T> checkCombo = new CheckComboBox<>(FXCollections.observableArrayList(options));

            @Override
            public void startEdit(Object value, String format, Object... opts) {
                checkCombo.getCheckModel().clearChecks();
                if (value instanceof List<?> list) {
                    for (Object v : list) {
                        @SuppressWarnings("unchecked")
                        T casted = (T) v;
                        checkCombo.getCheckModel().check(casted);
                    }
                }
            }

            @Override
            public Control getEditor() {
                Platform.runLater(() -> checkCombo.requestFocus());
                return checkCombo;
            }

            @Override
            public String getControlValue() {
                return checkCombo.getCheckModel().getCheckedItems().toString();
            }

            @Override
            public void end() {
                // cleanup if needed
            }
        };
    }

    @Override
    public String toString(List<T> values) {
        if (values == null || values.isEmpty()) return "";
        return String.join(", ", values.stream().map(Object::toString).toList());
    }

    @Override
    public boolean match(Object o, Object... os) {
        return o == null || o instanceof List<?>;
    }

    @Override
    public List<T> convertValue(Object o) {
        if (o == null) return new ArrayList<>();
        if (o instanceof List<?> list) {
            @SuppressWarnings("unchecked")
            List<T> typedList = (List<T>) list;
            return typedList;
        }
        return new ArrayList<>();
    }

    @Override
    public String toString() {
        return "MultiChoice";
    }
}
