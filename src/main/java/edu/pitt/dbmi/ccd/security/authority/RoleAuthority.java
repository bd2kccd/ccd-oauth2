package edu.pitt.dbmi.ccd.security.authority;

import org.springframework.security.core.GrantedAuthority;
import edu.pitt.dbmi.ccd.db.entity.UserRole;

public final class RoleAuthority implements GrantedAuthority {
    
    private UserRole role;

    public RoleAuthority(UserRole role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return "ROLE_" + role.getName();
    }
}