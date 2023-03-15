package sixman.helfit.security.mail.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import sixman.helfit.exception.BusinessLogicException;
import sixman.helfit.exception.ExceptionCode;
import sixman.helfit.security.mail.entity.EmailConfirmToken;
import sixman.helfit.security.mail.repository.EmailConfirmTokenRepository;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@EnableAsync
public class EmailConfirmTokenService {
    @Value("${domain.back}")
    private String frontDomain;
    private final String SEND_MAIL_URI = "/api/v1/users/confirm-email?token-id=";

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final EmailConfirmTokenRepository emailConfirmTokenRepository;

    public EmailConfirmToken createEmailConfirmToken(Long userId) {
        EmailConfirmToken ect = new EmailConfirmToken();
        ect.setUserId(userId);
        ect.setExpired(false);

        return emailConfirmTokenRepository.save(ect);
    }

    public void updateEmailConfirmToken(String tokenId) {
        EmailConfirmToken verifiedConfirmToken = findVerifiedConfirmToken(tokenId);
        verifiedConfirmToken.setExpired(true);

        emailConfirmTokenRepository.save(verifiedConfirmToken);
    }

    public EmailConfirmToken findVerifiedConfirmToken(String tokenId) {
        Optional<EmailConfirmToken> emailConfirmTokenById =
            emailConfirmTokenRepository.findEmailConfirmTokenByTokenId(tokenId);

        return emailConfirmTokenById.orElseThrow(() -> new BusinessLogicException(ExceptionCode.EXPIRED_TOKEN));
    }

    @Async
    public void sendEmail(String receiverEmail, String tokenId) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        Context context = new Context();
        context.setVariable("link", frontDomain + SEND_MAIL_URI + tokenId);

        // # classpath:/mail-templates/email.html
        String template = templateEngine.process("email", context);

        helper.setSubject("[Helfit] 회원 가입 인증 메일");
        helper.setTo(receiverEmail);
        helper.setText(template, true);

        javaMailSender.send(message);
    }
}
