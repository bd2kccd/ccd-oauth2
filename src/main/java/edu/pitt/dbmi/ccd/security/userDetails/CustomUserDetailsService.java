package edu.pitt.dbmi.ccd.security.userDetails;

import java.util.Optional;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import edu.pitt.dbmi.ccd.security.authority.RoleAuthority;
import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import edu.pitt.dbmi.ccd.db.entity.UserRole;
import edu.pitt.dbmi.ccd.db.repository.UserAccountRepository;

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
     * Finds UserDetails by {@link UserAccount#username}
     * @param  username  username of 
     * @return UserDetails of corresponding UserAccount if username is found,
     *         otherwise throws {@link org.springframework.security.core.userdetails.UsernameNotFoundException}s
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<UserAccount> account = accountRepository.findByUsername(username);
        if (!account.isPresent()) {
            throw new UsernameNotFoundException(String.format("User %s does not exist", username));
        }
        return new CustomUserDetails(account.get());
    }

    /**
     * Extends UserAccount entity into a class suitable for Spring Security's UserDetails
     */
    private static final class CustomUserDetails extends UserAccount implements UserDetails, Serializable {
        private static final long serialVersionUID = 7123123887734014705L;

        private CustomUserDetails(UserAccount account) {
            super(account);
        }

        /**
         * Returns UserAccount's set of UserRoles as GrantedAuthority implementation
         * 
         * @return Collection<RoleAuthority>
         */
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return new HashSet<RoleAuthority>(super.getRoles().stream()
                                                              .map(RoleAuthority::new)
                                                              .collect(Collectors.toList()));
        }

        /**
         * Get username
         * 
         * @return username
         * @see  UserAcount#getUsername()
         */
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
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
