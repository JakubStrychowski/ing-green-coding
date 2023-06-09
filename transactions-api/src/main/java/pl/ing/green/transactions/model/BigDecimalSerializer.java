package pl.ing.green.transactions.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author Jakub Strychowski
 */
public class BigDecimalSerializer extends JsonSerializer<BigDecimal> {
    
    @Override
    public void serialize(BigDecimal value, JsonGenerator generator, SerializerProvider provider) 
            throws IOException,JsonProcessingException {
        generator.writeRawValue(value.setScale(2, RoundingMode.HALF_UP).toString());
    }    
    
}
