/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nupea.reactordatabase.data;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author user
 * @param <E>
 */
public class OptionSet<E> {
    public static<E> Set<E> of(E... elements){
        var hashset = new LinkedHashSet<E>();
        hashset.addAll(Arrays.asList(elements));
        return hashset;
    }
    
    public static<E> Set<E> of(Collection<? extends E> collection){
        return new LinkedHashSet<>(collection);
    }
}
