package net.darbia.pl.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import net.darbia.pl.objects.Employee;

import java.io.IOException;

public class EmployeeSerializer extends JsonSerializer<Employee> {

  @Override
  public void serialize(final Employee value, final JsonGenerator gen,
      final SerializerProvider serializers) throws IOException {
    gen.writeNumber(value.getId());
  }
}
