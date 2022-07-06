package com.hubert.profile;

public interface IProfileService {
    boolean isPasswordValid(String currentPassword, String rawPassword);

    boolean updatePassword(String newPassword);
}
