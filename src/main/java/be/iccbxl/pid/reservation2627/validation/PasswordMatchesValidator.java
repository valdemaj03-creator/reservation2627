package be.iccbxl.pid.reservation2627.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, PasswordConfirmable> {

    @Override
    public boolean isValid(PasswordConfirmable dto, ConstraintValidatorContext context) {
        String pwd = dto.getPassword();
        String confirm = dto.getConfirmPassword();

        // Si les deux champs sont vides, le mot de passe n'est pas modifié => OK
        if ((pwd == null || pwd.isBlank()) && (confirm == null || confirm.isBlank())) {
            return true;
        }

        // Sinon, il faut qu'ils soient identiques
        if (pwd == null) return false;
        return pwd.equals(confirm);
    }
}