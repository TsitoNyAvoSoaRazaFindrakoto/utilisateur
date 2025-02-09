package perso.utilisateur.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.Random;
import java.util.UUID;
import java.security.MessageDigest;

public final class SecurityUtil {

    private static final Random random = new Random();

    public static String hashPassword(String password){
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        }
        catch(NoSuchAlgorithmException e){
            throw new RuntimeException("Easter egg mahafinaritra");
        }
    }

    public static String generatePin(){
        String valiny="";
        for (int i=0;i<4;i++){
            valiny+=random.nextInt(10);
        }
        return valiny;
    }

    public static String generateToken(){
        return UUID.randomUUID().toString();
    }

    public static boolean matchPassword(String rawPassword,String hashedPassword){
        return hashPassword(rawPassword).equals(hashedPassword);
    }
}
