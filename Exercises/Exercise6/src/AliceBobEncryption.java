import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.security.*;
import java.util.Base64;

public class AliceBobEncryption {
    private static final int AES_KEY_SIZE = 256;
    private static final int GCM_TAG_LENGTH = 16;
    private static final int RSA_KEY_SIZE = 2048;

    public static void main(String[] args) throws Exception {
        //Create Alice and Bob's key pairs for RSA (Asymmetric)
        KeyPair aliceKeyPair = generateRSAKeyPair();
        KeyPair bobKeyPair = generateRSAKeyPair();

        //Symmetric Encryption/Decryption using AES-256
        SecretKey aesKey = generateAESKey();
        byte[] iv = generateIV();
        String plainText = "Hello, Bob! This is Alice.";

        System.out.println("Symmetric Encryption (AES-256):");
        byte[] encryptedMessage = encryptAES(plainText, aesKey, iv);
        System.out.println("Encrypted: " + Base64.getEncoder().encodeToString(encryptedMessage));

        String decryptedMessage = decryptAES(encryptedMessage, aesKey, iv);
        System.out.println("Decrypted: " + decryptedMessage);

        //Asymmetric Encryption/Decryption using RSA-2048
        System.out.println("\nAsymmetric Encryption (RSA-2048):");
        byte[] encryptedRSA = encryptRSA(plainText, bobKeyPair.getPublic());
        System.out.println("Encrypted: " + Base64.getEncoder().encodeToString(encryptedRSA));

        String decryptedRSA = decryptRSA(encryptedRSA, bobKeyPair.getPrivate());
        System.out.println("Decrypted: " + decryptedRSA);

        //Signing and Verifying Messages using RSA
        System.out.println("\nSigning and Verifying (RSA-2048):");
        byte[] signature = signMessage(plainText, aliceKeyPair.getPrivate());
        System.out.println("Signature: " + Base64.getEncoder().encodeToString(signature));

        boolean isVerified = verifySignature(plainText, signature, aliceKeyPair.getPublic());
        System.out.println("Signature Verified: " + isVerified);
    }

    // Generate AES key
    private static SecretKey generateAESKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(AES_KEY_SIZE);
        return keyGen.generateKey();
    }

    // Generate IV for AES-GCM
    private static byte[] generateIV() {
        byte[] iv = new byte[12]; // 12 bytes for GCM
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        return iv;
    }

    // AES Encryption
    private static byte[] encryptAES(String plainText, SecretKey key, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, parameterSpec);
        return cipher.doFinal(plainText.getBytes());
    }

    // AES Decryption
    private static String decryptAES(byte[] cipherText, SecretKey key, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv);
        cipher.init(Cipher.DECRYPT_MODE, key, parameterSpec);
        byte[] decryptedBytes = cipher.doFinal(cipherText);
        return new String(decryptedBytes);
    }

    // Generate RSA key pair
    private static KeyPair generateRSAKeyPair() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(RSA_KEY_SIZE);
        return keyGen.generateKeyPair();
    }

    // RSA Encryption
    private static byte[] encryptRSA(String plainText, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(plainText.getBytes());
    }

    // RSA Decryption
    private static String decryptRSA(byte[] cipherText, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(cipherText);
        return new String(decryptedBytes);
    }

    // RSA Sign Message
    private static byte[] signMessage(String message, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(message.getBytes());
        return signature.sign();
    }

    // RSA Verify Signature
    private static boolean verifySignature(String message, byte[] signatureBytes, PublicKey publicKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(message.getBytes());
        return signature.verify(signatureBytes);
    }
}
