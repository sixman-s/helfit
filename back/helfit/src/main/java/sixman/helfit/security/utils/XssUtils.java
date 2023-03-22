package sixman.helfit.security.utils;

import org.apache.commons.text.StringEscapeUtils;

public class XssUtils {
    public static String charEscape(String value) {
        return value != null ? StringEscapeUtils.escapeHtml4(value) : null;
    }
}
