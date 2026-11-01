package hai704i.tp2.hotel.metier.securite;

import hai704i.tp2.hotel.metier.model.EmpreinteSecret;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.HexFormat;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public final class Hachage {
    private static final int ITERATIONS = 120_000;
    private static final int TAILLE_CLE_BITS = 256;
    private static final int TAILLE_SEL = 16;
    private static final SecureRandom ALEA = new SecureRandom();

    private Hachage() {
    }

    public static EmpreinteSecret deriver(String secret) {
        byte[] sel = new byte[TAILLE_SEL];
        ALEA.nextBytes(sel);
        return deriver(secret, sel);
    }

    public static EmpreinteSecret deriver(String secret, byte[] sel) {
        PBEKeySpec spec = new PBEKeySpec(secret.toCharArray(), sel, ITERATIONS, TAILLE_CLE_BITS);
        try {
            byte[] cle = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
                    .generateSecret(spec).getEncoded();
            return new EmpreinteSecret(sel, cle);
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException("dérivation impossible", e);
        } finally {
            spec.clearPassword();
        }
    }

    public static boolean verifier(String secretPresente, EmpreinteSecret stockee) {
        if (secretPresente == null || stockee == null) return false;
        EmpreinteSecret calculee = deriver(secretPresente, stockee.sel());
        return MessageDigest.isEqual(calculee.empreinte(), stockee.empreinte());
    }

    public static String versTexte(EmpreinteSecret e) {
        HexFormat hex = HexFormat.of();
        return hex.formatHex(e.sel()) + ":" + hex.formatHex(e.empreinte());
    }

    public static EmpreinteSecret depuisTexte(String texte) {
        String[] parties = texte.split(":");
        HexFormat hex = HexFormat.of();
        return new EmpreinteSecret(hex.parseHex(parties[0]), hex.parseHex(parties[1]));
    }
}