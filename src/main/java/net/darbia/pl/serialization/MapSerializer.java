// 
// Decompiled by Procyon v0.5.36
// 

package net.darbia.pl.serialization;

import java.io.IOException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.core.JsonGenerator;
import java.util.Map;
import com.fasterxml.jackson.databind.JsonSerializer;

public class MapSerializer extends JsonSerializer<Map>
{
    @Override
    public void serialize(final Map value, final JsonGenerator gen, final SerializerProvider serializers) throws IOException {
        gen.writeString(value.toString());
    }
}
