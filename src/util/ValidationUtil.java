package util;

import java.util.regex.Pattern;

public class ValidationUtil {

    // Patterns de validation
    private static final Pattern PATTERN_EMAIL = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern PATTERN_NOM = Pattern.compile("^[a-zA-ZÀ-ÿ\\s'-]{2,100}$");
    private static final Pattern PATTERN_ID = Pattern.compile("^CNE-\\d{5}$");
    private static final Pattern PATTERN_NUM = Pattern.compile("^^[a-zA-ZÀ-ÿ0-9\\s,'-]{6,100}$");


    private static final double MONTANT_MIN = 0.01;
    private static final double MONTANT_MAX = 1000000.00;
    private static final double DECOUVERT_MIN = 0.00;
    private static final double DECOUVERT_MAX = 5000.00;
    private static final double TAUX_INTERET_MIN = 0.01;
    private static final double TAUX_INTERET_MAX = 10.00;
}