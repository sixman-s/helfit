package sixman.helfit.security.support;

import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.SerializedString;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.commons.text.translate.AggregateTranslator;
import org.apache.commons.text.translate.CharSequenceTranslator;
import org.apache.commons.text.translate.EntityArrays;
import org.apache.commons.text.translate.LookupTranslator;

import java.util.HashMap;


public class XssProtectionSupport extends CharacterEscapes {
    private final int[] asciiEscapes;
    private final CharSequenceTranslator translator;

    public XssProtectionSupport() {
        asciiEscapes = CharacterEscapes.standardAsciiEscapesForJSON();
        asciiEscapes['<'] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes['>'] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes['('] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes[')'] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes['#'] = CharacterEscapes.ESCAPE_CUSTOM;

        translator = new AggregateTranslator(
            new LookupTranslator(EntityArrays.BASIC_UNESCAPE),
            new LookupTranslator(EntityArrays.ISO8859_1_ESCAPE),
            new LookupTranslator(EntityArrays.HTML40_EXTENDED_ESCAPE),
            new LookupTranslator(
                new HashMap<>() {{
                    put("<", "&#12296;");
                    put(">", "&#12297;");
                    put("(",  "&#40;");
                    put(")",  "&#41;");
                    put("#",  "&#35;");
                }}
            )
        );
    }

    @Override
    public int[] getEscapeCodesForAscii() {
        return asciiEscapes;
    }

    @Override
    public SerializableString getEscapeSequence(int ch) {
        SerializedString serializedString = null;

        char charAt = (char) ch;

        if (Character.isHighSurrogate(charAt) || Character.isLowSurrogate(charAt)) {
            String emoji = "\\u" + String.format("%04x", ch);

            serializedString = new SerializedString(emoji);
        } else {
            // serializedString = new SerializedString(translator.translate(Character.toString((char) ch)));
            serializedString = new SerializedString(StringEscapeUtils.escapeHtml4(Character.toString(charAt)));
        }

        return serializedString;
    }
}
