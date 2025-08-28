/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nupea.reactordatabase.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author jmburu
 */
public class Reactor {
    private String name;
    private final List<CharacteristicCategory> categories;

    public Reactor(){
        name = "none";
        categories = new ArrayList<>();
    }
    public Reactor(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        this.categories = new ArrayList<>();
    }

    // --- name accessors ---
    public String getName() {
        return name;
    }

    public void setName(String name) {
        Objects.requireNonNull(name);
        this.name = name;
    }

    // --- categories mutators ---
    public List<CharacteristicCategory> getCategories() {
        return categories;
    }

    public void addCategory(CharacteristicCategory category) {
        categories.add(category);
    }

    public boolean removeCategory(CharacteristicCategory category) {
        return categories.remove(category);
    }

    @Override
    public String toString() {
        return name;
    }
}
