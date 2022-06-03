// 
// Decompiled by Procyon v0.5.36
// 

package net.darbia.pl.serialization;

import java.io.IOException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class NullKeySerializer extends StdSerializer<Object>
{
    public NullKeySerializer() {
        this((Class<Object>)null);
    }
    
    private NullKeySerializer(final Class<Object> t) {
        super(t);
    }
    
    @Override
    public void serialize(final Object value, final JsonGenerator gen, final SerializerProvider provider) throws IOException {
        gen.writeFieldName("");
    }
}
