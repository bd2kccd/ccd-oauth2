package edu.pitt.dbmi.ccd.security.oauth2.token;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Entity
@Table(name="oauth_access_token")
public class AccessToken implements Serializable {
    
    private static final long serialVersionUID = 6243926451161494315L;

    @Id
    private String authenticationId;

    @NotNull
    @Column(nullable=false)
    private String tokenId;

    @NotNull
    @Column(nullable=false,
            columnDefinition="BLOB")
    private byte[] token;

    @NotNull
    @Column(nullable=false)
    private String username;

    @NotNull
    @Column(nullable=false)
    private String clientId;

    @NotNull
    @Column(nullable=false,
            columnDefinition="BLOB")
    private byte[] authentication;

    @NotNull
    @Column(nullable=false)
    private String refreshToken;
}