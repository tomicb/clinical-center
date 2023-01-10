package com.ftn.clinicCentre.security.token;

import com.ftn.clinicCentre.entity.User;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "passwordless_token")
public class PasswordlessToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "expires_at", nullable = false)
    private Date expiresAt;

    @Column(name = "confirmed_at")
    private Date confirmedAt;

    @ManyToOne
    private User user;

    public PasswordlessToken() {}

    public PasswordlessToken(String token, Date createdAt, User user) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = new Date(createdAt.getTime() + 600000);
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Date getConfirmedAt() {
        return confirmedAt;
    }

    public void setConfirmedAt(Date confirmedAt) {
        this.confirmedAt = confirmedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "PasswordlessToken{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", createdAt=" + createdAt +
                ", expiresAt=" + expiresAt +
                ", confirmedAt=" + confirmedAt +
                ", user=" + user +
                '}';
    }
}
