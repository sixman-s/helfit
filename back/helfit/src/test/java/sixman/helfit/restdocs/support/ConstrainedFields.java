package sixman.helfit.restdocs.support;

import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.constraints.ResourceBundleConstraintDescriptionResolver;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.snippet.Attributes;

import java.util.regex.Pattern;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

public class ConstrainedFields {
    private final ConstraintDescriptions constraintDescriptions;

    public ConstrainedFields(Class<?> clazz) {
        ResourceBundleConstraintDescriptionResolver fallback = new ResourceBundleConstraintDescriptionResolver();

        this.constraintDescriptions = new ConstraintDescriptions(clazz, constraint -> {
            String message = (String) constraint.getConfiguration().get("message");

            if (message != null && !Pattern.compile("\\{(.*?)}").matcher(message).matches())
                return message;

            return fallback.resolveDescription(constraint);
        });
    }

    public FieldDescriptor withPath(String path) {
        return fieldWithPath(path)
                   .attributes(
                       Attributes.key("constraints")
                           .value(this.constraintDescriptions.descriptionsForProperty(path))
                   );
    }
}
