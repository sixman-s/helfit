package sixman.helfit.restdocs.custom;

import org.springframework.http.MediaType;
import org.springframework.restdocs.operation.Operation;
import org.springframework.restdocs.payload.AbstractFieldsSnippet;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.FieldTypeResolver;
import org.springframework.restdocs.payload.JsonFieldType;
import sixman.helfit.restdocs.support.ConstrainedFields;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
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
        Class<T> clazz,
        Map<String, String> attributes
    ) {
        FieldDescriptor[] fieldDescriptors = genCustomRequestFields(clazz, attributes);

        return new CustomRequestFieldsSnippet("custom-request", Arrays.asList(fieldDescriptors), new HashMap<>(), true);
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
            String values = attributes.get(key);

            String[] regexes = {",\\s*Optional$", ",\\s*\\w+$"};

            for (int i = 0; i < regexes.length; i++) {
                Pattern pattern = Pattern.compile(regexes[i]);
                Matcher matcher = pattern.matcher(values);

                switch (i) {
                    case 0:
                        if (matcher.find()) {
                            fieldDescriptor.optional();
                            values = values.replaceAll(regexes[i], "");
                        }
                    case 1:
                        if (matcher.find()) {
                            fieldDescriptor.type(matcher.group().replaceAll(",\\s*", ""));
                            values = values.replaceAll(regexes[i], "");
                        } else {
                            fieldDescriptor.type(JsonFieldType.OBJECT);
                        }
                    default:
                        break;
                }
            }

            attrList.add(fieldDescriptor.description(values));
        }

        return attrList.toArray(FieldDescriptor[]::new);
    }
}
