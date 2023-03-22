package sixman.helfit.restdocs.custom;

import org.springframework.http.MediaType;
import org.springframework.restdocs.operation.Operation;
import org.springframework.restdocs.payload.AbstractFieldsSnippet;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import sixman.helfit.restdocs.support.ConstrainedFields;

import java.io.IOException;
import java.util.*;

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

    public static CustomRequestFieldsSnippet customRequestFields(
        String type,
        Map<String, Object> attributes,
        FieldDescriptor... descriptors
    ) {
        return new CustomRequestFieldsSnippet(type, Arrays.asList(descriptors), attributes, true);
    }

    public static CustomRequestFieldsSnippet customRequestFields(
        String type,
        FieldDescriptor... descriptors
    ) {
        HashMap<String, Object> attributes = new HashMap<>();
        return new CustomRequestFieldsSnippet(type, Arrays.asList(descriptors), attributes, true);
    }

    public static <T> FieldDescriptor[] genCustomRequestFields(Class<T> clazz, Map<String, String> attributes) {
        ConstrainedFields constrainedFields = new ConstrainedFields(clazz);
        List<FieldDescriptor> attrList = new ArrayList<>();

        for (String key : attributes.keySet()) {
            FieldDescriptor fieldDescriptor = constrainedFields.withPath(key).description(attributes.get(key));
            String type = key.getClass().getSimpleName();

            Optional<JsonFieldType> field = Arrays.stream(JsonFieldType.values())
                                                .filter(v -> v.name().equalsIgnoreCase(type))
                                                .findAny();

            if (field.isPresent()) fieldDescriptor.type(field);

            attrList.add(fieldDescriptor);
        }

        return attrList.toArray(FieldDescriptor[]::new);
    }
}
