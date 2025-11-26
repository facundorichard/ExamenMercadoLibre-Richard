package org.example.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class ValidDnaSequenceValidator implements ConstraintValidator<ValidDnaSequence, String[]> {

    private static final Pattern VALID_PATTERN = Pattern.compile("^[ATCG]+$");

    @Override
    public boolean isValid(String[] dna, ConstraintValidatorContext context) {
        if (dna == null || dna.length == 0) {
            return false; // @NotEmpty handles the message, but we return false here for safety
        }

        int n = dna.length;

        for (String row : dna) {
            if (row == null) {
                return false;
            }
            if (row.length() != n) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("La matriz de ADN debe ser cuadrada (NxN)")
                        .addConstraintViolation();
                return false;
            }
            if (!VALID_PATTERN.matcher(row).matches()) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                        "El ADN contiene caracteres inválidos (solo A, T, C, G permitidos)")
                        .addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}
