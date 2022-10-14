package net.darbia.pl.data;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import net.darbia.pl.objects.Employee;
import net.darbia.pl.objects.Order;
import net.darbia.pl.serialization.EmployeeMixIn;
import net.darbia.pl.serialization.NullKeySerializer;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class HTML {

  private static final Logger LOGGER;

  public static void putOrder(final Order order) {
    try {
      final ObjectMapper mapper = new ObjectMapper();
      mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
      mapper.registerModule(new JodaModule());
      mapper.getSerializerProvider().setNullKeySerializer(new NullKeySerializer());
      final String json = mapper.writeValueAsString(order);
      final CloseableHttpClient httpclient = HttpClients.createDefault();
      try {
        final HttpPut httpPut = new HttpPut("http://192.168.10.5:3000/put/order/json");
        httpPut.setHeader("Accept", "application/json");
        httpPut.setHeader("Content-type", "application/json");
        final StringEntity stringEntity = new StringEntity(json);
        httpPut.setEntity(stringEntity);
        final int status;
        HttpEntity entity;
        final ResponseHandler<String> responseHandler = response -> {
          status = response.getStatusLine().getStatusCode();
          if (status >= 200 && status < 300) {
            entity = response.getEntity();
            return (entity != null) ? EntityUtils.toString(entity) : null;
          } else {
            throw new ClientProtocolException(status);
          }
        };
        final String responseBody = httpclient.execute(httpPut,
            (ResponseHandler<? extends String>) responseHandler);
        HTML.LOGGER.debug("Response body: {}", responseBody);
        httpclient.close();
      } catch (Throwable t) {
        if (httpclient != null) {
          try {
            httpclient.close();
          } catch (Throwable exception) {
            t.addSuppressed(exception);
          }
        }
        throw t;
      }
    } catch (IOException ioe) {
      HTML.LOGGER.error("Submitting order JSON", ioe);
    }
  }

  public static List<Employee> getEmployees() {
    try {
      final CloseableHttpClient httpclient = HttpClients.createDefault();
      try {
        final HttpGet httpget = new HttpGet(
            "http://192.168.10.5:3000/get/employees/screen_printing");
        final int status;
        HttpEntity entity;
        final ResponseHandler<String> responseHandler = response -> {
          status = response.getStatusLine().getStatusCode();
          if (status >= 200 && status < 300) {
            entity = response.getEntity();
            return (entity != null) ? EntityUtils.toString(entity) : null;
          } else {
            throw new ClientProtocolException(status);
          }
        };
        final String responseBody = httpclient.execute(httpget,
            (ResponseHandler<? extends String>) responseHandler);
        HTML.LOGGER.debug("Response body: {}", responseBody);
        final ObjectMapper mapper = new ObjectMapper();
        mapper.enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES);
        mapper.addMixIn(Employee.class, EmployeeMixIn.class);
        final List<Employee> list = mapper.readValue(responseBody, new TypeReference<>() {
        });
        httpclient.close();
        return list;
      } catch (Throwable t) {
        if (httpclient != null) {
          try {
            httpclient.close();
          } catch (Throwable exception) {
            t.addSuppressed(exception);
          }
        }
        throw t;
      }
    } catch (IOException ioe) {
      HTML.LOGGER.error("Getting employees JSON", ioe);
      return null;
    }
  }

  static {
    LOGGER = LogManager.getLogger(HTML.class);
  }
}
