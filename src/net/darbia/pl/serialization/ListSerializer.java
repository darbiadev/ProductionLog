// 
// Decompiled by Procyon v0.5.36
// 

package net.darbia.pl.serialization;

import java.io.IOException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.core.JsonGenerator;
import java.util.List;
import com.fasterxml.jackson.databind.JsonSerializer;

public class ListSerializer extends JsonSerializer<List>
{
    @Override
    public void serialize(final List value, final JsonGenerator gen, final SerializerProvider serializers) throws IOException {
        gen.writeString(value.toString());
    }
}
