/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nupea.reactordatabase.util;

import java.math.BigInteger;
import java.util.function.UnaryOperator;
import javafx.application.Platform;
import javafx.beans.binding.NumberExpression;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.util.StringConverter;

/**
 *
 * @author user
 */
public class NumericField extends TextField
{
    private final NumberExpression value;
    
    public NumericField(Class<? extends Number> cls)
    {
        super();
        
        if (cls == byte.class || cls == Byte.class      || cls == short.class   || cls == Short.class ||
    	    cls == long.class    || cls == Long.class ||
    	    cls == BigInteger.class) 
        {           
            LongProperty prop = new SimpleLongProperty();  
            this.value = prop;
            initLongFormatter();
    	} 
        else if(cls == int.class || cls == Integer.class)
        {
            IntegerProperty prop = new SimpleIntegerProperty();              
            this.value = prop;
            initIntegerFormatter();
        }
        else 
        {           
            DoubleProperty prop = new SimpleDoubleProperty();              
            this.value = prop;
            initDoubleFormatter();
    	}
        
        focusedProperty().addListener((observable, oldValue, newValue) -> {   
            if(newValue)
                Platform.runLater(()->this.selectAll());
        });
        
        
        setStyle("-fx-control-inner-background: white;");
    }
    
    public final ObservableValue<Number> valueProperty() {
        return value;
    }
    
    public Number getValue()
    {
        return value.getValue();
    }
        
    private void initDoubleFormatter()
    {
        //unary filter 
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getControlNewText();

            //accept just in case user deletes everything
            if(text.equals(""))
                return change;

            //only double filtering
            //https://stackoverflow.com/questions/3543729/how-to-check-that-a-string-is-parseable-to-a-double
            try
            {
                Double.valueOf(text);
            }
            catch(NumberFormatException e)
            {
              //not a double
                return null;
            }
            return change;
        };
        //convert string to double when not in use
        StringConverter<Double> convert = new StringConverter<Double>() {
            @Override
            public Double fromString(String s) {    

                if (s.isEmpty() || "-".equals(s) || ".".equals(s) || "-.".equals(s)) {
                    return 0.0;
                } else {
                    
                    return Double.valueOf(s);
                }
            }

            @Override
            public String toString(Double d) {   
                
                return d.toString();
            }
        };

        TextFormatter textFormatter = new TextFormatter<>(convert, 0.0, filter);                                    
        setTextFormatter(textFormatter);
        //setMaxWidth(Double.MAX_VALUE);
        setOnKeyPressed(event -> 
        {
            if(event.getCode() == KeyCode.ENTER){
                DoubleProperty prop = (DoubleProperty)value;
                String text = getText();
                if(text != null && !text.isEmpty())
                {
                    prop.setValue(Double.valueOf(getText()));
                    this.positionCaret(text.length());
                    Platform.runLater(()->this.selectAll());
                    event.consume();
                }
            }
         }); 
    } 
    
    private void initLongFormatter()
    {
        //unary filter 
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getControlNewText();

            //accept just in case user deletes everything
            if(text.equals(""))
                return change;

            //only integer filtering
            //https://stackoverflow.com/questions/3543729/how-to-check-that-a-string-is-parseable-to-a-double
            try
            {
                Long.valueOf(text);
            }
            catch(NumberFormatException e)
            {
              //not a integer
                return null;
            }
            return change;
        };
        //convert string to double when not in use
        StringConverter<Long> convert = new StringConverter<Long>() {
            @Override
            public Long fromString(String s) {    

                if (s.isEmpty()) {
                    return 0L;
                } else {
                    
                    return Long.valueOf(s);
                }
            }

            @Override
            public String toString(Long f) {   
                
                return f.toString();
            }
        };

        TextFormatter textFormatter = new TextFormatter<>(convert, 0L, filter);                                    
        setTextFormatter(textFormatter);
        //setMaxWidth(Double.MAX_VALUE);
        setOnKeyPressed(event -> 
        {
            if(event.getCode() == KeyCode.ENTER){
                LongProperty prop = (LongProperty)value;
                String text = getText();
                if(text != null && !text.isEmpty())
                {
                    prop.setValue(Long.valueOf(getText()));
                    this.positionCaret(text.length());
                    Platform.runLater(()->this.selectAll());
                    event.consume();
                }
            }
         }); 
    }
    
    private void initIntegerFormatter()
    {
        //unary filter 
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getControlNewText();

            //accept just in case user deletes everything
            if(text.equals(""))
                return change;

            //only integer filtering
            //https://stackoverflow.com/questions/3543729/how-to-check-that-a-string-is-parseable-to-a-double
            try
            {
                Integer.valueOf(text);
            }
            catch(NumberFormatException e)
            {
              //not a integer
                return null;
            }
            return change;
        };
        //convert string to double when not in use
        StringConverter<Integer> convert = new StringConverter<Integer>() {
            @Override
            public Integer fromString(String s) { 
                if (s == null || s.isEmpty() || "-".equals(s))
                    return null; // instead of 0                
                return Integer.valueOf(s);
            }

            @Override
            public String toString(Integer f) {  
                
                return f.toString();
            }
        };

        TextFormatter textFormatter = new TextFormatter<>(convert, 0, filter);                                    
        setTextFormatter(textFormatter);
        //setMaxWidth(Double.MAX_VALUE);
        setOnKeyPressed(event -> 
        {
            if(event.getCode() == KeyCode.ENTER){
                IntegerProperty prop = (IntegerProperty)value;
                String text = getText();
                if(text != null && !text.isEmpty())
                {
                    prop.setValue(Integer.valueOf(getText()));
                    this.positionCaret(text.length());
                    Platform.runLater(()->this.selectAll());
                    event.consume();
                }
            }
         }); 
    }
}
