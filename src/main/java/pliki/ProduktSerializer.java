package pliki;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import model.Produkt;

import java.io.IOException;

public class ProduktSerializer extends JsonSerializer<Produkt> {

    @Override
    public void serialize(Produkt produkt, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String string = new ObjectMapper().writeValueAsString(produkt);
        jsonGenerator.writeFieldName(string);
    }
}