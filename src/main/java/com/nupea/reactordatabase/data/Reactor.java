/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nupea.reactordatabase.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author jmburu
 */
public class Reactor {
    
    public enum ReactorType{PWR, BWR, HWR, SCWR, GCR, GFR, SFR, LFR, MSR, ADS, Others}
    
    private String acronym;
    private final List<CharacteristicCategory> categories;
    private final Map<String, CharacteristicCategory> categoryMap;

    public Reactor(){
        this("none");
    }
    public Reactor(String acronym) {
        Objects.requireNonNull(acronym);
        this.acronym = acronym;
        this.categories = new ArrayList<>();
        this.categoryMap = new LinkedHashMap<>();
        initDefault();
    }
    
    private void initDefault(){
        addCategory(new CharacteristicCategory("Plant"));
        addCategory(new CharacteristicCategory("Reactor Unit"));
        addCategory(new CharacteristicCategory("Nuclear Steam Supply System"));        
        addCategory(new CharacteristicCategory("Reactor Coolant System"));        
        addCategory(new CharacteristicCategory("Reactor Core"));
        addCategory(new CharacteristicCategory("Core Materials"));
        addCategory(new CharacteristicCategory("Reactor Pressure Vessel"));
        
        var plant = getCategory("Plant");
        plant.put("Full Name", FieldValue.of(String.class, "-"));
        plant.put("Vendor", FieldValue.of(String.class, "-"));
        plant.put("Country", FieldValue.of(String.class, "-"));
        plant.put("Type", FieldValue.of(String.class, 
                OptionSet.of("PWR", "BWR", "HWR", "SCWR", "GCR", "GFR", "SFR", "LFR", "MSR", "ADS", "Other", "-"),
                "-"));
        plant.put("Design Status", FieldValue.of(String.class, "-"));
        plant.put("Moderator", FieldValue.of(String.class, "-"));
        plant.put("Coolant", FieldValue.of(String.class, "-"));
        
        var reactorUnit = getCategory("Reactor Unit");
        reactorUnit.put("Neutron Spectrum", FieldValue.of(String.class, "_"));
        reactorUnit.put("Reactor Thermal Output (MWth)", FieldValue.of(Integer.class, 0));
        reactorUnit.put("Power Plant Output, Gross (MWe)", FieldValue.of(Integer.class, 0));
        reactorUnit.put("Power Plant Output, Net (MWe)", FieldValue.of(Integer.class, 0));
        
        var reactorCoolantSystem = getCategory("Reactor Coolant System");
        reactorCoolantSystem.put("Core Coolant", FieldValue.of(String.class, "_"));
        reactorCoolantSystem.put("Primary Coolant Flow Rate (kg/s)", FieldValue.of(String.class, "_"));
        reactorCoolantSystem.put("Reactor Operating Pressure (MPa)", FieldValue.of(String.class, "_"));
        reactorCoolantSystem.put("Coolant Inlet Temperature (°C", FieldValue.of(String.class, "_"));
        
        var nuclearSteamSupplySystem = getCategory("Nuclear Steam Supply System");
        nuclearSteamSupplySystem.put("Steam Flow Rate (kg/s)", FieldValue.of(String.class, "_"));
        nuclearSteamSupplySystem.put("Steam Pressure (MPa)", FieldValue.of(String.class, "_"));
        nuclearSteamSupplySystem.put("Steam Temperature (°C)", FieldValue.of(String.class, "_"));
        nuclearSteamSupplySystem.put("Feedwater Flow Rate (kg/s)", FieldValue.of(String.class, "_"));
        nuclearSteamSupplySystem.put("Feedwater Temperature (°C)", FieldValue.of(String.class, "_"));
        
        var reactorCore = getCategory("Reactor Core");
        reactorCore.put("Active Core Height (m)", FieldValue.of(String.class, "_"));
        reactorCore.put("Equivalent Core Diameeter (m)", FieldValue.of(String.class, "_"));
        reactorCore.put("Average Linear Rate (kW/kgU)", FieldValue.of(String.class, "_"));
        
        var coreMaterials = getCategory("Core Materials");
        coreMaterials.put("Fuel Material", FieldValue.of(String.class, "_"));
        coreMaterials.put("Cladding Material", FieldValue.of(String.class, "_"));
        coreMaterials.put("Enrichment Of Reload Fuel (wt%)", FieldValue.of(String.class, "_"));
        coreMaterials.put("Fuel Cycle Length (Months)", FieldValue.of(String.class, "_"));
        
        var reactorPressureVessel = getCategory("Reactor Pressure Vessel");
        reactorPressureVessel.put("Inner Diameter Of Cylindrical Shell (mm)", FieldValue.of(String.class, "_"));
        reactorPressureVessel.put("Wall Thickness Of Cylindrical Shell (mm)", FieldValue.of(String.class, "_"));
        reactorPressureVessel.put("Base Material", FieldValue.of(String.class, "_"));
        reactorPressureVessel.put("Total Height, Inside (mm)", FieldValue.of(String.class, "_"));
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

    // --- acronym accessors ---
    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        Objects.requireNonNull(acronym);
        this.acronym = acronym;
    }

    @Override
    public String toString() {
        return acronym;
    }
}
