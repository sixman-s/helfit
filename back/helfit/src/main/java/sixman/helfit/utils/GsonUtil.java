package sixman.helfit.utils;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class GsonUtil {
    private static final String PATTERN_DATE = "yyyy-MM-dd";
    private static final String PATTERN_TIME = "HH:mm:ss";
    private static final String PATTERN_DATETIME = String.format("%s'T'%s", PATTERN_DATE, PATTERN_TIME);

    private static final Gson gson = new GsonBuilder()
                                   .disableHtmlEscaping()
                                   .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                                   .setDateFormat(PATTERN_DATETIME)
                                   .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter().nullSafe())
                                   .registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe())
                                   .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter().nullSafe())
                                   .create();

    public static String toJson(Object o) {
        String result = gson.toJson(o);

        if("null".equals(result))
            return null;

        return result;
    }

    public static String toJson(Object o, Class<?> clazz) {
        String result = gson.toJson(o, clazz);

        if("null".equals(result))
            return null;

        return result;
    }

    public static <T> T fromJson(String s, Class<T> clazz) {
        try {
            return gson.fromJson(s, clazz);
        } catch(JsonSyntaxException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    static class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
        DateTimeFormatter format = DateTimeFormatter.ofPattern(PATTERN_DATETIME);

        @Override
        public void write(JsonWriter out, LocalDateTime value) throws IOException {
            if(value != null)
                out.value(value.format(format));
        }

        @Override
        public LocalDateTime read(JsonReader in) throws IOException {
            return LocalDateTime.parse(in.nextString(), format);
        }
    }

    static class LocalDateAdapter extends TypeAdapter<LocalDate> {
        DateTimeFormatter format = DateTimeFormatter.ofPattern(PATTERN_DATE);

        @Override
        public void write(JsonWriter out, LocalDate value) throws IOException {
            out.value(value.format(format));
        }

        @Override
        public LocalDate read(JsonReader in) throws IOException {
            return LocalDate.parse(in.nextString(), format);
        }
    }

    static class LocalTimeAdapter extends TypeAdapter<LocalTime> {
        DateTimeFormatter format = DateTimeFormatter.ofPattern(PATTERN_TIME);
        @Override
        public void write(JsonWriter out, LocalTime value) throws IOException {
            out.value(value.format(format));
        }

        @Override
        public LocalTime read(JsonReader in) throws IOException {
            return LocalTime.parse(in.nextString(), format);
        }
    }
}
