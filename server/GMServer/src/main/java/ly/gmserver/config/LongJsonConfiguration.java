package ly.gmserver.config;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Snowflake IDs exceed JavaScript's exact integer range; transport longs as decimal strings. */
@Configuration
public class LongJsonConfiguration {
  @Bean public Jackson2ObjectMapperBuilderCustomizer exactLongValues() {
    return builder -> {
      builder.serializerByType(Long.class, ToStringSerializer.instance);
      builder.serializerByType(Long.TYPE, ToStringSerializer.instance);
    };
  }
}
