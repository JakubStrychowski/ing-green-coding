/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.ing.green.transactions.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 *
 * @author Jakub Strychowski
 */
public class CustomFloatSerializer extends JsonSerializer<Float>  {

    private final DecimalFormat decimalFormat;

    public CustomFloatSerializer() {
        this.decimalFormat = new DecimalFormat("0.00");
    }

    @Override
    public void serialize(Float value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String formattedValue = decimalFormat.format(value);
        jsonGenerator.writeString(formattedValue);
    }
    
}
