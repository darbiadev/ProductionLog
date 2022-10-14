package net.darbia.pl.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.List;

public class ListSerializer extends JsonSerializer<List> {

  @Override
  public void serialize(final List value, final JsonGenerator gen,
      final SerializerProvider serializers) throws IOException {
    gen.writeString(value.toString());
  }
}
