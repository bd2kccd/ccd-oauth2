package edu.pitt.dbmi.ccd.security.authority;

import org.springframework.security.core.GrantedAuthority;
import edu.pitt.dbmi.ccd.db.entity.UserRole;

public class RoleAuthority implements GrantedAuthority {
    
    private String name;

    public RoleAuthority(UserRole role) {
        this.name = "ROLE_" + role.getName().toUpperCase();
    }

    @Override
    public String getAuthority() {
        return name;
    }
}