package be.iccbxl.pid.reservation2627.validation;

/**
 * Contrat commun aux DTO qui ont un mot de passe et sa confirmation.
 * Permet d'utiliser @PasswordMatches sur plusieurs classes.
 */
public interface PasswordConfirmable {
    String getPassword();
    String getConfirmPassword();
}