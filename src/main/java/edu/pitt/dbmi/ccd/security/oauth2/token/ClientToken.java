package edu.pitt.dbmi.ccd.security.oauth2.token;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Entity
@Table(name="oauth_client_token")
public class ClientToken implements Serializable {
    
    private static final long serialVersionUID = 7534882149225664328L;

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
}