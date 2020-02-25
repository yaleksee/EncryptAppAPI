package com.encryptapp.utils.cryptography.asymmetric;

import java.io.File;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

public class AsymmetricCryptography {
    private Cipher cipher;
    PrivateKey privateKey;
    PublicKey publicKey;

    public AsymmetricCryptography() throws Exception {
        this.cipher = Cipher.getInstance("RSA");
        this.privateKey = getPrivate("KeyPair/privateKey");
        this.publicKey = getPublic("KeyPair/publicKey");
    }

    private PrivateKey getPrivate(String filename) throws Exception {
        byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    private PublicKey getPublic(String filename) throws Exception {
        byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    public String encryptText(String msg) throws Exception {
        this.cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return Base64.encodeBase64String(cipher.doFinal(msg.getBytes("UTF-8")));
    }

    public String decryptText(String msg) throws Exception {
        this.cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return new String(cipher.doFinal(Base64.decodeBase64(msg)), "UTF-8");
    }
}
