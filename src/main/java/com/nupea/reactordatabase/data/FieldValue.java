/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nupea.reactordatabase.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author user
 * @param <T>
 */
public class FieldValue<T> {
    private final Class<T> type;
    private T value;

    public FieldValue(Class<T> type, T value) {
        this.type = type;
        this.value = value;
    }

    // Serialize the type as fully-qualified name
    @JsonProperty("type")
    public String getTypeName() {
        return type.getName(); // e.g. "java.lang.Double", "com.nupea.ReactorStatus"
    }
    
    public Class<T> type(){
        return type;
    }

    public T getValue() { return value; }
    public void setValue(T value) { this.value = value; }

    // For Jackson deserialization
    @JsonCreator
    public static FieldValue<?> create(
            @JsonProperty("type") String typeName,
            @JsonProperty("value") Object value) {

        try {
            Class<?> clazz = Class.forName(typeName);

            // Special handling for enums
            if (clazz.isEnum()) {
                @SuppressWarnings("unchecked")
                Class<? extends Enum<?>> enumClass = (Class<? extends Enum<?>>) clazz;

                Object enumValue = Enum.valueOf((Class) enumClass, value.toString());

                // Cast clazz to Class<Object> so generics match
                @SuppressWarnings("unchecked")
                Class<Object> typedClass = (Class<Object>) clazz;

                return new FieldValue<>(typedClass, enumValue);
            }

            // Otherwise, trust Jackson’s coercion
            @SuppressWarnings("unchecked")
            Class<Object> typedClass = (Class<Object>) clazz;

            return new FieldValue<>(typedClass, value);

        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Unknown type: " + typeName, e);
        }
    }


    @Override
    public String toString() {
        return "FieldValue{" +
                "type = " + type.getName() +
                ", value = " + value +
                '}';
    }
}

