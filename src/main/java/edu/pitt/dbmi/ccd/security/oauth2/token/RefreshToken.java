package edu.pitt.dbmi.ccd.security.oauth2.token;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Entity
@Table(name="oauth_refresh_token")
public class RefreshToken implements Serializable {
    
    private static final long serialVersionUID = 6671308861475893032L;

    @Id
    private String tokenId;

    @NotNull
    @Lob
    @Column(nullable=false)
    private byte[] token;

    @NotNull
    @Lob
    @Column(nullable=false)
    private byte[] authentication;
}