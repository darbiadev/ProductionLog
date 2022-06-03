// 
// Decompiled by Procyon v0.5.36
// 

package net.darbia.pl.serialization;

import org.joda.time.format.ISODateTimeFormat;
import java.io.IOException;
import org.joda.time.ReadableInstant;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.core.JsonGenerator;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.DateTime;
import com.fasterxml.jackson.databind.JsonSerializer;

public class JsonJodaDateTimeSerializer extends JsonSerializer<DateTime>
{
    private static DateTimeFormatter formatter;
    
    @Override
    public void serialize(final DateTime value, final JsonGenerator gen, final SerializerProvider arg2) throws IOException {
        gen.writeString(JsonJodaDateTimeSerializer.formatter.print(value));
    }
    
    static {
        JsonJodaDateTimeSerializer.formatter = ISODateTimeFormat.dateTime();
    }
}
