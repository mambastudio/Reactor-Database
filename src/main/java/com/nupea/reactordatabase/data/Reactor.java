/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nupea.reactordatabase.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author jmburu
 */
public class Reactor {
    
    public enum ReactorType{PWR, BWR, HWR, SCWR, GCR, GFR, SFR, LFR, MSR, ADS, Others}
    
    private String name;
    private final List<CharacteristicCategory> categories;
    private final Map<String, CharacteristicCategory> categoryMap;

    public Reactor(){
        this("none");
    }
    public Reactor(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        this.categories = new ArrayList<>();
        this.categoryMap = new LinkedHashMap<>();
        initDefault();
    }
    
    private void initDefault(){
        addCategory(new CharacteristicCategory("Plant"));
        addCategory(new CharacteristicCategory("Reactor Unit"));
        addCategory(new CharacteristicCategory("Reactor Coolant System"));
        addCategory(new CharacteristicCategory("Reactor Core"));
        addCategory(new CharacteristicCategory("Nuclear Steam Supply System"));
        addCategory(new CharacteristicCategory("Core Materials"));
        addCategory(new CharacteristicCategory("Reactor Pressure Vessel"));
        
        var plant = getCategory("Plant");
        plant.put("Name", "-");
        plant.put("Vendor", "-");
        plant.put("Country", "");
        plant.put("Type", "-");
        plant.put("Design Status", "-");
        plant.put("Moderator", "-");
        plant.put("Coolant", "-");
    }
    
    // --- central helper to keep list + map in sync ---
    public void addCategory(CharacteristicCategory category) {
        categories.add(category);
        categoryMap.put(category.getCategory(), category);
    }

    public boolean removeCategory(String categoryName) {
        CharacteristicCategory removed = categoryMap.remove(categoryName);
        if (removed != null) {
            categories.remove(removed);
            return true;
        }
        return false;
    }

    // --- O(1) lookup ---
    public CharacteristicCategory getCategory(String name) {
        return categoryMap.get(name);
    }

    // --- getters for iteration/serialization ---
    public List<CharacteristicCategory> getCategories() {
        return Collections.unmodifiableList(categories);
    }

    public Map<String, CharacteristicCategory> getCategoryMap() {
        return Collections.unmodifiableMap(categoryMap);
    }
    
    public boolean isEmpty(){
        return categories.isEmpty();
    }
    
    public boolean hasCategories(){
        return !categories.isEmpty();
    }

    // --- name accessors ---
    public String getName() {
        return name;
    }

    public void setName(String name) {
        Objects.requireNonNull(name);
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
