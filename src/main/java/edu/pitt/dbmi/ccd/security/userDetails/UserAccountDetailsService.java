package edu.pitt.dbmi.ccd.security.userDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import edu.pitt.dbmi.ccd.db.repository.UserAccountRepository;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Service
public class UserAccountDetailsService implements UserDetailsService {

    private final UserAccountRepository accountRepository;

    @Autowired
    public UserAccountDetailsService(UserAccountRepository accountRepository) {
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
        return new UserAccountDetails(account);
    }
}
