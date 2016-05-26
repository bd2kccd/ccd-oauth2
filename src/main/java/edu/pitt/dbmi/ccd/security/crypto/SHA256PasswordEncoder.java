package edu.pitt.dbmi.ccd.security.crypto;

import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
public final class SHA256PasswordEncoder implements PasswordEncoder {

    private static DefaultPasswordService passwordService = new DefaultPasswordService();

    public SHA256PasswordEncoder() { };

    public SHA256PasswordEncoder(int iterations) {
        this.setHashIterations(iterations);
    }

    /**
     * Encrypt password
     * @param rawPassword unencrypted password
     * @return encrypted password
     */
    @Override
    public String encode(CharSequence rawPassword) {
        return passwordService.encryptPassword(rawPassword);
    }

    /**
     * Check if password matches encrypted value
     * @param rawPassword     password
     * @param encodedPassword encrypted password
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return passwordService.passwordsMatch(rawPassword, encodedPassword);
    }

    /**
     * Set hash iterations (default 500,000)
     * @param iterations hash iterations
     */
    protected void setHashIterations(int iterations) {
        DefaultHashService defaultHashService = (DefaultHashService) passwordService.getHashService();
        defaultHashService.setHashIterations(iterations);
        passwordService.setHashService(defaultHashService);
    }
}
