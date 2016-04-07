package edu.pitt.dbmi.ccd.security.crypto;

import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
public final class SHA256PasswordEncoder implements PasswordEncoder {

    private static final PasswordService passwordService = new DefaultPasswordService();

    @Override
    public String encode(CharSequence rawPassword) {
        return passwordService.encryptPassword(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return passwordService.passwordsMatch(rawPassword, encodedPassword);
    }
}
