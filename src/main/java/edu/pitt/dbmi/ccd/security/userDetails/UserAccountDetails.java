/*
 * Copyright (C) 2016 University of Pittsburgh.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */

package edu.pitt.dbmi.ccd.security.userDetails;

/**
 */

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import edu.pitt.dbmi.ccd.db.entity.UserAccount;
import edu.pitt.dbmi.ccd.security.authority.RoleAuthority;

/**
 * Composes UserAccount entity into a class suitable for Spring Security's UserDetails
 *
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
public final class UserAccountDetails implements UserDetails, Serializable {
    private static final long serialVersionUID = 7123123887734014705L;

    private final UserAccount userAccount;

    public UserAccountDetails(UserAccount userAccount) {
        super();
        this.userAccount = userAccount;
    }

    /**
     * Returns the inner UserAccount
     *
     * @return UserAccount
     */
    public UserAccount getUserAccount() {
        return userAccount;
    }

    /**
     * Returns UserAccount's set of UserRoles as GrantedAuthority implementation
     *
     * @return Collection<RoleAuthority>
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new HashSet<RoleAuthority>(userAccount.getUserRoles().stream()
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
        return userAccount.getUsername();
    }

    /**
     * Get password used to authenticate the user
     *
     * @return password
     */
    @Override
    public String getPassword() {
        return userAccount.getPassword();
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
        return userAccount.getActive();
    }
}
