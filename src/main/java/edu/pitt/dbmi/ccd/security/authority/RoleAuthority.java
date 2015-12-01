package edu.pitt.dbmi.ccd.security.authority;

import org.springframework.security.core.GrantedAuthority;
import edu.pitt.dbmi.ccd.db.entity.UserRole;

/**
 * Transforms {@link edu.pitt.dbmi.ccd.db.entity.UserRole} into {@link org.springframework.security.core.GrantedAuthority} implementation
 * 
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
public class RoleAuthority implements GrantedAuthority {
    
    private String name;

    public RoleAuthority(UserRole role) {
        this.name = "ROLE_" + role.getName().toUpperCase();
    }

    /**
     * Returns name of {@link UserRole} as proper role (example: "ROLE_X")
     * 
     * @return name of authority
     * @see edu.pitt.dbmi.ccd.db.entity.UserRole#getName()
     */
    @Override
    public String getAuthority() {
        return name;
    }
}