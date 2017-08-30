package org.kwok.testlib.PBE;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.commons.codec.binary.Hex;

/**
 * 基于口令的加密，口令容易被穷举破解
 * 通过加“盐”解决安全性问题
 */
public class Test_PBE {
    public static final String src = "pbe test";

    public static void main(String[] args) throws Exception {
    	
        jdkPBE();
        
    }

    public static void jdkPBE() throws Exception {
    	
        //初始化盐
        SecureRandom random = new SecureRandom();
        byte[] salt = random.generateSeed(8);//8位
        
        //口令与密钥
        String password = "abc123";
        PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray());
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBEWITHMD5andDES");
        SecretKey secretKey = factory.generateSecret(pbeKeySpec);
        
        //加密
        PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(salt, 100);//迭代100次
        Cipher cipher = Cipher.getInstance("PBEWITHMD5andDES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, pbeParameterSpec);
        byte[] result = cipher.doFinal(src.getBytes());
        System.out.println("JDK PBE ENCRYPT:" + Hex.encodeHexString(result));
        
        //解密
        cipher.init(Cipher.DECRYPT_MODE, secretKey, pbeParameterSpec);
        result = cipher.doFinal(result);
        System.out.println("JDK PBE DECRYPT:" + new String(result));
        
    }
}
