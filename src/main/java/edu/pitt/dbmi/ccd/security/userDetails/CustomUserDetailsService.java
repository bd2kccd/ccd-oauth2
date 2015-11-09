package edu.pitt.dbmi.ccd.security.userDetails;

import java.util.Collection;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import edu.pitt.dbmi.ccd.db.repository.UserAccountRepository;

/**
 * Extends 
 * 
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    private final UserAccountRepository accountRepository;

    @Autowired
    public CustomUserDetailsService(UserAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserAccount account = accountRepository.findByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException(String.format("User %s does not exist", username));
        }
        return new CustomUserDetails(account);
    }

    /**
     * Extends UserAccount entity into a class usable for Spring Security's UserDetails
     */
    private static class CustomUserDetails extends UserAccount implements Serializable, UserDetails {
        private static final long serialVersionUID = 7123123887734014705L;

        private CustomUserDetails(UserAccount account) {
            super(account);
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return getGrantedAuthorities();
        }

        @Override
        public String getUsername() {
            return super.getUsername();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}