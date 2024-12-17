package perso.utilisateur.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Random;

public final class PasswordUtil {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private static final Random random = new Random();

    public static String hashPassword(String password){
        return encoder.encode(password);
    }

    public static String generatePin(){
        String valiny="";
        for (int i=0;i<4;i++){
            valiny+=random.nextInt();
        }
        return valiny;
    }

    public static boolean matchPassword(String rawPassword,String hashedPassword){
        return encoder.matches(rawPassword,hashedPassword);
    }
}
