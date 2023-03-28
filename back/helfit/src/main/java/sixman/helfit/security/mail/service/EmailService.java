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
import sixman.helfit.security.mail.entity.EmailConfirmRandomKey;
import sixman.helfit.security.mail.entity.EmailConfirmToken;
import sixman.helfit.security.mail.repository.EmailConfirmRandomKeyRedisRepository;
import sixman.helfit.security.mail.repository.EmailConfirmTokenRepository;
import sixman.helfit.utils.RandomUtil;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@EnableAsync
public class EmailService {
    @Value("${domain.back}")
    private String backDomain;
    private final String SEND_MAIL_URI = "/api/v1/users/confirm-email?token-id=";

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final EmailConfirmTokenRepository emailConfirmTokenRepository;
    private final EmailConfirmRandomKeyRedisRepository emailConfirmRandomKeyRedisRepository;

    public EmailConfirmToken createEmailConfirmToken(Long userId) {
        EmailConfirmToken ect = new EmailConfirmToken();
        ect.setUserId(userId);
        ect.setExpired(false);

        return emailConfirmTokenRepository.save(ect);
    }

    public EmailConfirmRandomKey createEmailConfirmRandomKey(String email) {
        String randomKey = RandomUtil.generateVerificationCode();

        EmailConfirmRandomKey emailConfirmRandomKey =
            EmailConfirmRandomKey.builder()
                .id(email)
                .randomKey(randomKey)
                .expiration(300L) // 5 Min
                .build();

       return emailConfirmRandomKeyRedisRepository.save(emailConfirmRandomKey);
    }

    public void updateEmailConfirmToken(String tokenId) {
        EmailConfirmToken verifiedConfirmToken = findVerifiedConfirmTokenByTokenId(tokenId);
        verifiedConfirmToken.setExpired(true);

        emailConfirmTokenRepository.save(verifiedConfirmToken);
    }

    public void updateEmailConfirmRandomKey(String randomKey) {
        EmailConfirmRandomKey verifiedConfirmRandomKeyByEmail = findVerifiedConfirmRandomKeyByRandomKey(randomKey);

        if (!verifiedConfirmRandomKeyByEmail.getRandomKey().equals(randomKey))
            throw new BusinessLogicException(ExceptionCode.EXPIRED_RANDOM_KEY);
        else
            emailConfirmRandomKeyRedisRepository.delete(verifiedConfirmRandomKeyByEmail);
    }

    public EmailConfirmToken findVerifiedConfirmTokenByTokenId(String tokenId) {
        Optional<EmailConfirmToken> emailConfirmTokenByTokenId =
            emailConfirmTokenRepository.findEmailConfirmTokenByTokenId(tokenId);

        return emailConfirmTokenByTokenId.orElseThrow(() -> new BusinessLogicException(ExceptionCode.EXPIRED_TOKEN));
    }

    public EmailConfirmToken findVerifiedConfirmTokenByUserId(Long userId) {
        Optional<EmailConfirmToken> emailConfirmTokenByUserId =
            emailConfirmTokenRepository.findEmailConfirmTokenByUserId(userId);

        return emailConfirmTokenByUserId.orElseThrow(() -> new BusinessLogicException(ExceptionCode.EXPIRED_TOKEN));
    }

    public EmailConfirmRandomKey findVerifiedConfirmRandomKeyByRandomKey(String randomKey) {
        Optional<EmailConfirmRandomKey> byId = emailConfirmRandomKeyRedisRepository.findByRandomKey(randomKey);

        return byId.orElseThrow(() -> new BusinessLogicException(ExceptionCode.EXPIRED_RANDOM_KEY));
    }

    @Async
    public void sendEmailWithToken(String receiverEmail, String tokenId) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        Context context = new Context();
        context.setVariable("link", backDomain + SEND_MAIL_URI + tokenId);

        // # classpath:/mail-templates/email_token.html
        String template = templateEngine.process("email_token", context);

        helper.setSubject("[Helfit] 회원 가입 인증 메일");
        helper.setTo(receiverEmail);
        helper.setText(template, true);

        javaMailSender.send(message);
    }

    @Async
    public void sendEmailWithRandomKey(String receiverEmail, String randomKey, String type) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        Context context = new Context();
        context.setVariable("type", type);
        context.setVariable("randomKey", randomKey);

        // # classpath:/mail-templates/email_token.html
        String template = templateEngine.process("email_random", context);

        helper.setSubject("[Helfit] 회원 " + type + "찾기 인증 메일");
        helper.setTo(receiverEmail);
        helper.setText(template, true);

        javaMailSender.send(message);
    }

    @Async
    public void sendEmailWithTempPassword(String receiverEmail, String tempPassword) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        Context context = new Context();
        context.setVariable("temp", tempPassword);

        // # classpath:/mail-templates/email_temp.html
        String template = templateEngine.process("email_temp", context);

        helper.setSubject("[Helfit] 회원 임시 비밀번호 발급 메일");
        helper.setTo(receiverEmail);
        helper.setText(template, true);

        javaMailSender.send(message);
    }
}
