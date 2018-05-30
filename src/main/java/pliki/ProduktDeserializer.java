package pliki;

import com.fasterxml.jackson.databind.*;
import model.Produkt;

import java.io.IOException;


public class ProduktDeserializer extends KeyDeserializer {
    static ObjectMapper mapper = new ObjectMapper();

    @Override
    public Object deserializeKey(
            String key,
            DeserializationContext ctxt) throws IOException{

        return mapper.readValue(key, Produkt.class);
    }
}
