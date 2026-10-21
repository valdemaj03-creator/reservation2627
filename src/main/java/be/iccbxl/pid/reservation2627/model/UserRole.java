package be.iccbxl.pid.reservation2627.model;

public enum UserRole {
    ADMIN("Administrateur"),
    MEMBER("Membre"),
    AFFILIATE("Affilié"),
    PRESS("Critique de presse"),
    PRODUCER("Producteur");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getValue() {
        return role;
    }
}