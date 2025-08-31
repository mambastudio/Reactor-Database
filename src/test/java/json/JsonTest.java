/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nupea.reactordatabase.data.CharacteristicCategory;
import com.nupea.reactordatabase.data.FieldValue;
import com.nupea.reactordatabase.data.Reactor;
import com.nupea.reactordatabase.data.Reactor.ReactorType;
import java.io.File;
import java.io.IO;
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
        test2();
    }
    
    public void test2() throws IOException{
        CharacteristicCategory specs = new CharacteristicCategory("Specs");

        specs.put("power", new FieldValue<>(Double.class, 1000.0));
        specs.put("coolant", new FieldValue<>(String.class, "Water"));
        specs.put("type", new FieldValue<>(ReactorType.class, ReactorType.ADS));

        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter()
              .writeValue(new File("C:\\Users\\user\\Documents\\File Examples\\specs.json"), specs);

        CharacteristicCategory readBack =
                mapper.readValue(new File("C:\\Users\\user\\Documents\\File Examples\\specs.json"), CharacteristicCategory.class);

        IO.println(readBack);
        IO.println(readBack.getInfo());
        
        var type = readBack.get("type");
        var val = type.getValue();
        
        IO.println((val instanceof String));
        
    }
}
