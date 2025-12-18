package com.example.project.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {
    
    public enum UserRole {
        USER("User"),
        ADMIN("Administrator"),
        PHARMACIST("Pharmacist"),
        DOCTOR("Doctor"),
        RESEARCHER("Researcher");
        
        private final String displayName;
        
        UserRole(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    public enum AccountStatus {
        PENDING("Pending"),
        ACTIVE("Active"),
        SUSPENDED("Suspended"),
        DEACTIVATED("Deactivated");
        
        private final String displayName;
        
        AccountStatus(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;
    
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;
    
    @Column(nullable = false, unique = true, length = 100)
    private String email;
    
    @Column(nullable = false, length = 255)
    private String password;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserRole role = UserRole.USER;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "account_status", nullable = false, length = 20)
    private AccountStatus accountStatus = AccountStatus.PENDING;
    
    @Column(name = "email_verified", nullable = false)
    private boolean emailVerified = false;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_login_at")
    private Date lastLoginAt;
    
    @Column(name = "phone_number", length = 20)
    private String phoneNumber;
    
    @Column(name = "profile_picture_url", length = 500)
    private String profilePictureUrl;
    
    @Column(name = "reset_password_token", length = 100)
    private String resetPasswordToken;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "reset_password_expiry")
    private Date resetPasswordExpiry;
    
    @PrePersist
    protected void onCreate() {
        Date now = new Date();
        this.createdAt = now;
        this.updatedAt = now;
    }
    
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }
    
    // Constructors
    public User() {
        // Default constructor for JPA
    }
    
    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = UserRole.USER;
        this.accountStatus = AccountStatus.PENDING;
        this.emailVerified = false;
    }
    
    public User(String firstName, String lastName, String email, String password, UserRole role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role != null ? role : UserRole.USER;
        this.accountStatus = AccountStatus.PENDING;
        this.emailVerified = false;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getFullName() {
        return (firstName != null ? firstName : "") + " " + (lastName != null ? lastName : "").trim();
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public UserRole getRole() {
        return role;
    }
    
    public void setRole(UserRole role) {
        this.role = role;
    }
    
    public AccountStatus getAccountStatus() {
        return accountStatus;
    }
    
    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }
    
    public boolean isEmailVerified() {
        return emailVerified;
    }
    
    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }
    
    public Date getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    
    public Date getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public Date getLastLoginAt() {
        return lastLoginAt;
    }
    
    public void setLastLoginAt(Date lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }
    
    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }
    
    public String getResetPasswordToken() {
        return resetPasswordToken;
    }
    
    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }
    
    public Date getResetPasswordExpiry() {
        return resetPasswordExpiry;
    }
    
    public void setResetPasswordExpiry(Date resetPasswordExpiry) {
        this.resetPasswordExpiry = resetPasswordExpiry;
    }
    
    // Business logic methods
    public void activateAccount() {
        this.accountStatus = AccountStatus.ACTIVE;
        this.emailVerified = true;
    }
    
    public void suspendAccount() {
        this.accountStatus = AccountStatus.SUSPENDED;
    }
    
    public void deactivateAccount() {
        this.accountStatus = AccountStatus.DEACTIVATED;
    }
    
    public boolean isActive() {
        return this.accountStatus == AccountStatus.ACTIVE;
    }
    
    public boolean isAdmin() {
        return this.role == UserRole.ADMIN;
    }
    
    public boolean isStaff() {
        return this.role == UserRole.ADMIN || 
               this.role == UserRole.PHARMACIST || 
               this.role == UserRole.DOCTOR;
    }
    
    public boolean hasPermission(String requiredRole) {
        if (requiredRole == null) return false;
        
        try {
            UserRole required = UserRole.valueOf(requiredRole.toUpperCase());
            return hasPermission(required);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
    
    public boolean hasPermission(UserRole requiredRole) {
        if (requiredRole == null) return false;
        
        // Admin has all permissions
        if (this.role == UserRole.ADMIN) return true;
        
        // Regular hierarchy check
        return this.role == requiredRole;
    }
    
    public void updateLastLogin() {
        this.lastLoginAt = new Date();
    }
    
    public boolean isResetTokenValid() {
        return this.resetPasswordToken != null && 
               this.resetPasswordExpiry != null && 
               this.resetPasswordExpiry.after(new Date());
    }
    
    public void clearResetToken() {
        this.resetPasswordToken = null;
        this.resetPasswordExpiry = null;
    }
    
    // Validation methods
    public boolean isValid() {
        return this.firstName != null && !this.firstName.trim().isEmpty() &&
               this.lastName != null && !this.lastName.trim().isEmpty() &&
               this.email != null && !this.email.trim().isEmpty() &&
               this.password != null && this.password.length() >= 6;
    }
    
    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        
        // For persisted entities
        if (id != null && user.id != null) {
            return Objects.equals(id, user.id);
        }
        
        // For transient entities
        return Objects.equals(email, user.email);
    }
    
    @Override
    public int hashCode() {
        if (id != null) {
            return Objects.hash(id);
        }
        return Objects.hash(email);
    }
    
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullName='" + getFullName() + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", accountStatus=" + accountStatus +
                ", emailVerified=" + emailVerified +
                '}';
    }
}