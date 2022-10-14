package net.darbia.pl.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Map;

public class MapSerializer extends JsonSerializer<Map> {

  @Override
  public void serialize(final Map value, final JsonGenerator gen,
      final SerializerProvider serializers) throws IOException {
    gen.writeString(value.toString());
  }
}
