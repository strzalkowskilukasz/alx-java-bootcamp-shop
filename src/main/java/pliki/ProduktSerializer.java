package pliki;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import model.Produkt;

import java.io.IOException;

public class ProduktSerializer extends JsonSerializer<Produkt>
{
    static ObjectMapper mapper = new ObjectMapper();

    @Override
    public void serialize( Produkt value,
                           JsonGenerator jgen,
                           SerializerProvider provider )
            throws IOException
    {
        String json = mapper.writeValueAsString( value );
        jgen.writeFieldName(json);
    }
}