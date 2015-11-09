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
@Table(name="oauth_refresh_token")
public class RefreshToken implements Serializable {
    
    private static final long serialVersionUID = 6671308861475893032L;

    @Id
    private String tokenId;

    @NotNull
    @Column(nullable=false,
            columnDefinition="BLOB")
    private byte[] token;

    @NotNull
    @Column(nullable=false,
            columnDefinition="BLOB")
    private byte[] authentication;
}