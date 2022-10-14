package net.darbia.pl.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.io.IOException;

public class JsonJodaDateTimeSerializer extends JsonSerializer<DateTime> {

  private static DateTimeFormatter formatter;

  @Override
  public void serialize(final DateTime value, final JsonGenerator gen,
      final SerializerProvider arg2) throws IOException {
    gen.writeString(JsonJodaDateTimeSerializer.formatter.print(value));
  }

  static {
    JsonJodaDateTimeSerializer.formatter = ISODateTimeFormat.dateTime();
  }
}
