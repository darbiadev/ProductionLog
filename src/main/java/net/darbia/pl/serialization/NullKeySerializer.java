package net.darbia.pl.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class NullKeySerializer extends StdSerializer<Object> {

  public NullKeySerializer() {
    this(null);
  }

  private NullKeySerializer(final Class<Object> t) {
    super(t);
  }

  @Override
  public void serialize(final Object value, final JsonGenerator gen,
      final SerializerProvider provider) throws IOException {
    gen.writeFieldName("");
  }
}
