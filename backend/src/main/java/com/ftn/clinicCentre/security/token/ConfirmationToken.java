package com.ftn.clinicCentre.security.token;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ftn.clinicCentre.entity.*;;

@Entity
@Table(name = "confirmation_token")
public class ConfirmationToken {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	@Column(nullable = false)
    private String token;

    @Column(nullable = true)
    private LocalDateTime confirmedAt;
    
    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "user_id"
    )
    private User user;
    
    public ConfirmationToken() {}

	public ConfirmationToken(Long id, String token,
			LocalDateTime confirmedAt, User user) {
		super();
		this.id = id;
		this.token = token;
		this.confirmedAt = confirmedAt;
		this.user = user;
	}
    
	public ConfirmationToken( String token,
			LocalDateTime confirmedAt, User user) {
		super();
		this.token = token;
		this.confirmedAt = confirmedAt;
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

	public LocalDateTime getConfirmedAt() {
		return confirmedAt;
	}

	public void setConfirmedAt(LocalDateTime confirmedAt) {
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
		return "ConfirmationToken [id=" + id + ", token=" + token + ", confirmedAt=" + confirmedAt + ", user=" + user
				+ "]";
	}

}
