package edu.pitt.dbmi.ccd.security.userDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import edu.pitt.dbmi.ccd.db.repository.UserAccountRepository;
import edu.pitt.dbmi.ccd.security.authority.RoleAuthority;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserAccountRepository accountRepository;

    @Autowired
    public CustomUserDetailsService(UserAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Finds UserDetails by username of UserAccount
     * @param  username  username of UserAccount
     * @return UserDetails of corresponding UserAccount if username is found,
     *         otherwise throws {@link org.springframework.security.core.userdetails.UsernameNotFoundException}
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount account = accountRepository.findByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException(String.format("User %s does not exist", username));
        }
        return new CustomUserDetails(account);
    }

    /**
     * Extends UserAccount entity into a class suitable for Spring Security's UserDetails
     */
    private static final class CustomUserDetails extends UserAccount implements UserDetails, Serializable {
        private static final long serialVersionUID = 7123123887734014705L;

        private CustomUserDetails(UserAccount account) {
            super();
        }

        /**
         * Returns UserAccount's set of UserRoles as GrantedAuthority implementation
         *
         * @return Collection<RoleAuthority>
         */
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return new HashSet<RoleAuthority>(super.getUserRoles().stream()
                    .map(RoleAuthority::new)
                    .collect(Collectors.toList()));
        }

        /**
         * Get username
         *
         * @return username
         */
        @Override
        public String getUsername() {
            return super.getUsername();
        }

        /**
         * Get account expiration status
         *
         * @return account expiration status
         */
        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        /**
         * Get account locked status
         *
         * @return account locked status
         */
        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        /**
         * Get credentials expiration status
         *
         * @return credentials expiration status
         */
        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        /**
         * Get account enabled status
         *
         * @return account enabled status
         */
        @Override
        public boolean isEnabled() {
            return super.getActive();
        }
    }
}
