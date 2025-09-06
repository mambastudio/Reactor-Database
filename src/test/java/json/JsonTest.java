/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nupea.reactordatabase.data.CharacteristicCategory;
import com.nupea.reactordatabase.data.FieldValue;
import com.nupea.reactordatabase.data.Reactor;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author user
 */
public class JsonTest {
    void main() throws IOException{
        CharacteristicCategory specs = new CharacteristicCategory("Specs");

        specs.put("power", FieldValue.of(Double.class, 1000.0));
        specs.put("coolant", FieldValue.of(String.class, "Water"));
        specs.put("type", FieldValue.of(Reactor.ReactorType.class, Reactor.ReactorType.ADS));

        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter()
              .writeValue(new File("C:\\Users\\user\\Documents\\File Examples\\specs.json"), specs);

        CharacteristicCategory readBack =
                mapper.readValue(new File("C:\\Users\\user\\Documents\\File Examples\\specs.json"), CharacteristicCategory.class);

       //IO.println(readBack);
       // IO.println(readBack.getInfo());
        
        var type = readBack.get("type");
        var val = type.getValue();
        
        //IO.println((val instanceof String));
    }
}
