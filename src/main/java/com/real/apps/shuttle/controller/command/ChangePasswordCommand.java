package com.real.apps.shuttle.controller.command;

/**
 * Created by zorodzayi on 15/06/13.
 */
public class ChangePasswordCommand {
    private final String username;
    private final String currentPassword;
    private final String newPassword;

    public ChangePasswordCommand(String username, String currentPassword, String newPassword) {
        this.username = username;
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    public String getUsername() {
        return username;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    @Override
    public String toString() {
        return String.format("{username:%s,CurrentPassword:%s,NewPassword:%s}", username, currentPassword, newPassword);
    }
}
