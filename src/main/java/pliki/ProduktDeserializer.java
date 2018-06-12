package pliki;

import com.fasterxml.jackson.databind.*;
import model.Produkt;

import java.io.IOException;


public class ProduktDeserializer extends KeyDeserializer {

    @Override
    public Object deserializeKey(
            String key,
            DeserializationContext context) throws IOException{

        return new ObjectMapper().readValue(key, Produkt.class);
    }
}
