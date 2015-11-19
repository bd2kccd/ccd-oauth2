package edu.pitt.dbmi.ccd.security.oauth2;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Mark Silvis (marksilvis@pitt.edu)
 */
@Entity
@Table(name="oauth_approvals")
public class Approvals implements Serializable {
    
    private static final long serialVersionUID = 2315864436929156972L;

    @Id
    private Integer id;

    @Column(nullable=true)
    private Long userId;

    @Column(nullable=true)
    private Long clientId;

    @Column(nullable=true)
    private String scope;

    @Size(max=10)
    @Column(length=10, nullable=true)
    private String status;

    @NotNull
    @Column(nullable=false,
            columnDefinition="TIMESTAMP")
    private Date expires;

    @NotNull
    @Column(nullable=false,
            columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date lastModified;
}