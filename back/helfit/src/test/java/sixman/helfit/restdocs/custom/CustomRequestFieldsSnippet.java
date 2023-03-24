package sixman.helfit.restdocs.custom;

import org.springframework.http.MediaType;
import org.springframework.restdocs.operation.Operation;
import org.springframework.restdocs.payload.AbstractFieldsSnippet;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import sixman.helfit.restdocs.support.ConstrainedFields;

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

public class CustomRequestFieldsSnippet extends AbstractFieldsSnippet {
    public CustomRequestFieldsSnippet(
        String type,
        List<FieldDescriptor> descriptors,
        Map<String, Object> attributes,
        boolean ignoreUndocumentedFields
    ) {
        super(type, descriptors, attributes, ignoreUndocumentedFields);
    }

    @Override
    protected MediaType getContentType(Operation operation) {
        return operation.getRequest().getHeaders().getContentType();
    }

    @Override
    protected byte[] getContent(Operation operation) throws IOException {
        return operation.getRequest().getContent();
    }

    public static <T> CustomRequestFieldsSnippet customRequestFields(
        String type,
        Class<T> clazz,
        Map<String, String> attributes
    ) {
        FieldDescriptor[] fieldDescriptors = genCustomRequestFields(clazz, attributes);

        return new CustomRequestFieldsSnippet(type, Arrays.asList(fieldDescriptors), new HashMap<>(), true);
    }

    public static CustomRequestFieldsSnippet customRequestFields(
        String type,
        FieldDescriptor... descriptors
    ) {
        HashMap<String, Object> attributes = new HashMap<>();

        return new CustomRequestFieldsSnippet(type, Arrays.asList(descriptors), attributes, true);
    }

    public static CustomRequestFieldsSnippet customRequestFields(
        String type,
        Map<String, Object> attributes,
        FieldDescriptor... descriptors
    ) {
        return new CustomRequestFieldsSnippet(type, Arrays.asList(descriptors), attributes, true);
    }

    public static <T> FieldDescriptor[] genCustomRequestFields(
        Class<T> clazz,
        Map<String, String> attributes
    ) {
        ConstrainedFields constrainedFields = new ConstrainedFields(clazz);
        List<FieldDescriptor> attrList = new ArrayList<>();

        for (String key : attributes.keySet()) {
            FieldDescriptor fieldDescriptor = constrainedFields.withPath(key);
            String type = key.getClass().getSimpleName();
            String value = attributes.get(key);

            if (Pattern.compile(",\\s*Optional$").matcher(attributes.get(key)).find()) {
                value = value.replaceAll(",\\s*Optional$", "");

                fieldDescriptor.optional();
            }

            fieldDescriptor.description(value);

            Optional<JsonFieldType> field =
                Arrays.stream(JsonFieldType.values())
                    .filter(v -> v.name().equalsIgnoreCase(type))
                    .findAny();

            field.ifPresent(fieldDescriptor::type);

            attrList.add(fieldDescriptor);
        }

        return attrList.toArray(FieldDescriptor[]::new);
    }
}
