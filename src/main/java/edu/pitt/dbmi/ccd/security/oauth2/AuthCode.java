package edu.pitt.dbmi.ccd.security.oauth2;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Lob;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Entity
@Table(name="oauth_code")
public class AuthCode implements Serializable {
    
    private static final long serialVersionUID = 3953792325553972428L;

    @Id
    private Integer id;

    @Column(nullable=true)
    private String code;

    @Lob
    @Column(nullable=true)
    private byte[] authentication;
}