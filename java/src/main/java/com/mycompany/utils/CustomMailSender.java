package org.mdback.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class CustomMailSender {
    private final JavaMailSender javaMailSender;
    private static final Logger logger = LoggerFactory.getLogger(CustomMailSender.class);

    @Value("${platform.name}")
    private String PLATFORM_NAME;

    public CustomMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    @Async
    public void sendTemporaryPassword(String to, String tempPwd,String language) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        // 이메일 내용 설정
        String subject = getSubject(language);
        String text = getText(tempPwd, language);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, true); // HTML 지원: true
        javaMailSender.send(message);
    }
    private String getSubject(String language) {
        return switch (language) {
            case "ko" -> "["+ PLATFORM_NAME +"] 임시 비밀번호 발급 안내";
            case "en" -> "["+ PLATFORM_NAME +"] Temporary Password Issuance Notice";
            default -> "["+ PLATFORM_NAME +"] Temporary Password Issuance Notice";
        };
    }
    private String getText(String tempPwd, String language) {
        return switch (language) {
            case "ko" -> "안녕하세요,<br><br>" +
                    "요청하신 <strong>임시 비밀번호</strong>를 발급해드립니다:<br><br>" +
                    "<strong style='font-size: 18px; color: #2b6cb0;'>" + tempPwd + "</strong><br><br>" +
                    "보안을 위해 <strong>로그인 후 반드시 비밀번호를 변경</strong>해 주시기 바랍니다.<br><br>" +
                    "감사합니다.<br><br>" +
                    "<em>"+ PLATFORM_NAME +" 팀 드림</em>";
            case "en" -> "Hello,<br><br>" +
                    "Here is your <strong>temporary password</strong> as requested:<br><br>" +
                    "<strong style='font-size: 18px; color: #2b6cb0;'>" + tempPwd + "</strong><br><br>" +
                    "For security reasons, please make sure to <strong>change your password</strong> after logging in.<br><br>" +
                    "Thank you.<br><br>" +
                    "<em>Sincerely,<br>The "+ PLATFORM_NAME +" Team</em>";
            default -> "Hello,<br><br>" +
                    "Here is your <strong>temporary password</strong> as requested:<br><br>" +
                    "<strong style='font-size: 18px; color: #2b6cb0;'>" + tempPwd + "</strong><br><br>" +
                    "For security reasons, please make sure to <strong>change your password</strong> after logging in.<br><br>" +
                    "Thank you.<br><br>" +
                    "<em>Sincerely,<br>The "+ PLATFORM_NAME +" Team</em>";
        };
    }
}
