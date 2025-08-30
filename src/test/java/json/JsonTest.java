/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nupea.reactordatabase.data.CharacteristicCategory;
import com.nupea.reactordatabase.data.Reactor;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author jmburu
 */
public class JsonTest {
    public void main() throws IOException{
        /*
        // --- Create reactor ---
        Reactor reactor = new Reactor("BWR-1");

        // --- Create category: Specs ---
        CharacteristicCategory specs = new CharacteristicCategory("Specs");
        specs.put("power", "1000MW");
        specs.put("coolant", "Water");

        // --- Create category: Person ---
        CharacteristicCategory person = new CharacteristicCategory("Person");
        person.put("age", 30);
        person.put("hobby", "JavaFX");
        person.put("skills", new String[]{"Photography", "Programming"});

        // --- Add categories to reactor ---
        reactor.addCategory(specs);
        reactor.addCategory(person);
        
        //JsonFactory d
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter()
              .writeValue(new File("C:\\Users\\jmburu\\Documents\\NuPEA Work\\category.json"), reactor);
             
        // --- Read the JSON back into a Reactor object ---
        ObjectMapper mapper = new ObjectMapper();
        Reactor reactorInfo = mapper.readValue(new File("C:\\Users\\jmburu\\Documents\\NuPEA Work\\category.json"), Reactor.class);
        
        // --- Use it like normal Java objects ---
        System.out.println("Reactor name: " + reactorInfo.getName());
        reactorInfo.getCategories().forEach(cat -> {
            System.out.println("  Category: " + cat.getCategory());
            cat.getInfo().forEach((k, v) ->
                System.out.println("    " + k + " = " + v)
            );
        });
 */
    }
}
