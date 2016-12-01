package edu.pitt.dbmi.ccd.security.authority;

import edu.pitt.dbmi.ccd.db.entity.UserRole;
import org.springframework.security.core.GrantedAuthority;

/**
 * Transforms {@link edu.pitt.dbmi.ccd.db.entity.UserRole} into
 * {@link org.springframework.security.core.GrantedAuthority} implementation
 *
 * @see edu.pitt.dbmi.ccd.db.entity.UserRole
 *
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
public final class RoleAuthority implements GrantedAuthority {

    private static final long serialVersionUID = -120833575778896596L;

    // prefix to role
    private static final String rolePrefix = "ROLE_";

    // role name
    private final String name;

    public RoleAuthority(UserRole role) {
        this.name = rolePrefix + role.getName().toUpperCase();
    }

    /**
     * Returns name of {@link UserRole} as proper role (example: "ROLE_X")
     *
     * @return name of authority
     */
    @Override
    public String getAuthority() {
        return name;
    }

}
