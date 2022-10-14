// 
// Decompiled by Procyon v0.5.36
// 

package net.darbia.pl.serialization;

import java.io.IOException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.core.JsonGenerator;
import net.darbia.pl.objects.Employee;
import com.fasterxml.jackson.databind.JsonSerializer;

public class EmployeeSerializer extends JsonSerializer<Employee>
{
    @Override
    public void serialize(final Employee value, final JsonGenerator gen, final SerializerProvider serializers) throws IOException {
        gen.writeNumber(value.getId());
    }
}
