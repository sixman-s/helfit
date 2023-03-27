package sixman.helfit.restdocs.support;

import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.constraints.ResourceBundleConstraintDescriptionResolver;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.snippet.Attributes.*;

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
                       key("constraints")
                           .value(
                               StringUtils
                                   .collectionToDelimitedString(
                                       this.constraintDescriptions.descriptionsForProperty(path),
                                       "\n",
                                       "- ",
                                       "\n"
                                   )
                           ));
    }
}
