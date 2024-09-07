package org.example.codenames.api.web.Request;

public class RoleJoinRequest {
    private String player;
    private String role;

    public RoleJoinRequest() {
    }

    public RoleJoinRequest(String player, String role) {
        this.player = player;
        this.role = role;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
