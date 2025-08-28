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
    private String category;                  // no final → mutable
    private final Map<String, Object> info;   // map contents still mutable

    public CharacteristicCategory() {
        this.category = "";
        this.info = new HashMap<>();
    }
    
    public CharacteristicCategory(String category) {
        this.category = category;
        this.info = new HashMap<>();
    }

    // --- category accessors ---
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // --- map mutators ---
    public void put(String key, Object value) {
        info.put(key, value);
    }

    public Object get(String key) {
        return info.get(key);
    }

    public Object remove(String key) {
        return info.remove(key);
    }

    public boolean containsKey(String key) {
        return info.containsKey(key);
    }

    public Map<String, Object> getInfo() {
        return info; // exposes live mutable map
    }

    @Override
    public String toString() {
        return category;
    }
}
