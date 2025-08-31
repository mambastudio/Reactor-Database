/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nupea.reactordatabase.data;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jmburu
 */
public class CharacteristicCategory {
    private String category;
    private final Map<String, FieldValue<?>> info;

    public CharacteristicCategory() {
        this.category = "";
        this.info = new HashMap<>();
    }

    public CharacteristicCategory(String category) {
        this.category = category;
        this.info = new HashMap<>();
    }

    // --- accessors ---
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // --- mutators ---
    public void put(String key, FieldValue<?> fieldValue) {
        info.put(key, fieldValue);
    }

    public FieldValue<?> get(String key) {
        return info.get(key);
    }

    public FieldValue<?> remove(String key) {
        return info.remove(key);
    }

    public Map<String, FieldValue<?>> getInfo() {
        return info;
    }

    @Override
    public String toString() {
        return category;
    }
}
