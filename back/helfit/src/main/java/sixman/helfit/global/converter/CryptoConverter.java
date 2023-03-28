package sixman.helfit.global.converter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import sixman.helfit.exception.BusinessLogicException;
import sixman.helfit.exception.ExceptionCode;
import sixman.helfit.utils.CryptoUtil;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
@Slf4j
public class CryptoConverter implements AttributeConverter<String, String> {
    @Value("${crypto.encrypt-key}")
    private String ENCRYPTION_KEY;

    private static final String ENCRYPT = "ENCRYPT";
    private static final String DECRYPT = "DECRYPT";
    private CryptoUtil cryptoUtil;

    @Override
    public String convertToDatabaseColumn(String sensitive) {
        if (sensitive == null) return null;

        return encryptDecryptString(sensitive, ENCRYPT);
    }

    @Override
    public String convertToEntityAttribute(String sensitive) {
        if (sensitive == null) return null;

        return encryptDecryptString(sensitive, DECRYPT);
    }

    private String encryptDecryptString(String sensitive, String encryptDecrypt) {
        try {
            if (cryptoUtil == null) cryptoUtil = new CryptoUtil(ENCRYPTION_KEY);

            if (StringUtils.equalsIgnoreCase(encryptDecrypt, ENCRYPT))
                return cryptoUtil.encrypt(sensitive);

            if (StringUtils.equalsIgnoreCase(encryptDecrypt, DECRYPT))
                return cryptoUtil.decrypt(sensitive);
        } catch (Exception e) {
            log.error("Exception: {}", e.getMessage());

            throw new BusinessLogicException(ExceptionCode.INTERNAL_SERVER_ERROR);
        }

        return null;
    }
}
