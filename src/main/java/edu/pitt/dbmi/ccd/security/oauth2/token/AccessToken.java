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
@Table(name="oauth_access_token")
public class AccessToken implements Serializable {
    
    private static final long serialVersionUID = 6243926451161494315L;

    @Id
    private String authenticationId;

    @NotNull
    @Column(nullable=false)
    private String tokenId;

    @NotNull
    @Lob
    @Column(nullable=false)
    private byte[] token;

    @NotNull
    @Column(nullable=false)
    private String userName;

    @NotNull
    @Column(nullable=false)
    private String clientId;

    @NotNull
    @Lob
    @Column(nullable=false)
    private byte[] authentication;

    @NotNull
    @Column(nullable=false)
    private String refreshToken;
}