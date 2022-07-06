package com.hubert.profile;

import javax.validation.constraints.NotBlank;

public class Profile {
    private Long id;
    private String username;

    @NotBlank(message = "Current password is needed!")
    private String currentPassword;
    @NotBlank(message = "New password is needed!")
    private String newPassword;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

}
