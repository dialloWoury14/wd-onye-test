package app;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "app_user", schema = "PUBLIC")
public class AppUser {

    @Id
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "timestamp_created")
    private Timestamp timestampCreated;

    protected AppUser() {

    }

    public AppUser(
            String username,
            String password
    ) {
        this.username = username;
        this.password = password;
        this.timestampCreated = new Timestamp(System.currentTimeMillis());
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }
}
