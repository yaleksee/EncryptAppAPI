package com.encryptapp.utils.cryptography;

import com.encryptapp.Application;
import com.encryptapp.utils.cryptography.asymmetric.AsymmetricCryptography;
import com.encryptapp.utils.cryptography.keypair.GenerateKeys;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class AsymmetricCryptographyTest {
    static {
        System.setProperty("keylenght", "1024");
        System.setProperty("writeToFilePublicKey", "keyPair/publicKey");
        System.setProperty("writeToFilePrivateKey", "keyPair/privateKey");
    }

    @Autowired
    private AsymmetricCryptography asymmetricCryptography;

    @Before
    public void contextLoads() throws Exception {
        String writeToFilePublicKey = System.getProperty("writeToFilePublicKey");
        String writeToFilePrivateKey = System.getProperty("writeToFilePrivateKey");
        GenerateKeys generateKeys = new GenerateKeys(Integer.valueOf(System.getProperty("keylenght")));
        generateKeys.createKeys();
        generateKeys.writeToFile(writeToFilePublicKey, generateKeys.getPublicKey().getEncoded());
        generateKeys.writeToFile(writeToFilePrivateKey, generateKeys.getPrivateKey().getEncoded());
    }

    @Test
    public void testEncryptText() throws Exception {
        String mes = "test";
        String encryptTest = asymmetricCryptography.encryptText(mes);
        assertNotNull(encryptTest);
    }

    @Test
    public void testDecryptText() throws Exception {
        String mes = "test";
        String encryptTest = asymmetricCryptography.encryptText(mes);
        String decryptTest = asymmetricCryptography.decryptText(encryptTest);
        assertNotNull(decryptTest);
        assertEquals(mes, decryptTest);
    }

}
