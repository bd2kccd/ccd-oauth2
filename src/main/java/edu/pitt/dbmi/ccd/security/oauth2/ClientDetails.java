package edu.pitt.dbmi.ccd.security.oauth2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import java.io.Serializable;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Entity
@Table(name="oauth_client_details")
public class ClientDetails implements Serializable {
    
    private static final long serialVersionUID = 3932864394592817342L;

    @Id
    private String clientId;

    @Column(nullable=true)
    private String resourceIds;

    @Column(nullable=true)
    private String clientSecret;

    @Column(nullable=true)
    private String scope;

    @Column(nullable=true)
    private String authorizedGrantTypes;

    @Column(nullable=true)
    private String webServerRedirectUri;

    @Column(nullable=true)
    private String authorities;

    @Column(nullable=true, columnDefinition="INT(11)")
    private Integer accessTokenValidity;

    @Column(nullable=true, columnDefinition="INT(11)")
    private Integer refreshTokenValidity;

    @Size(max=4096)
    @Column(length=4096, nullable=true)
    private String additionalInformation;

    @Column(name="autoapprove", nullable=true)
    private String autoApproveScopes;
}