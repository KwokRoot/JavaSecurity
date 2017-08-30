package org.kwok.testlib.HMAC;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;

public class Test_HMAC {
	
    public static String src = "hmac test";
    
    public static void main(String[] args) throws Exception {
    	
        jdkHmacMD5();
        
        bcHmacMD5();
    
    }

    private static void jdkHmacMD5() throws Exception {
    	
    	//初始化KeyGenerator
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacMD5");
        //产生密钥
        SecretKey secretKry = keyGenerator.generateKey();
        //获得密钥
        //byte[] key  = secretKry.getEncoded();
      
        byte[] key = Hex.decodeHex(new char[] {'a','b','c','1','2','3'});
        //还原密钥
        SecretKey restoreSecretKey = new SecretKeySpec(key,"HmacMD5");
        //实例化mac
        Mac mac = Mac.getInstance(restoreSecretKey.getAlgorithm());
        //初始化mac
        mac.init(restoreSecretKey);
        //执行摘要
        byte[] hmacMD5Bytes = mac.doFinal(src.getBytes());
        
        System.out.println("JDK HmacMD5:"+ Hex.encodeHexString(hmacMD5Bytes));

    }
    
    
    public static void bcHmacMD5(){
    	
        HMac hmac  = new HMac(new MD5Digest());
        hmac.init(new KeyParameter(org.bouncycastle.util.encoders.Hex.decode("abc123")));
        hmac.update(src.getBytes(),0,src.getBytes().length);
        byte[] hmacMD5Bytes = new byte[hmac.getMacSize()];
        hmac.doFinal(hmacMD5Bytes,0);
        System.out.println("BC HmacMD5:"+org.bouncycastle.util.encoders.Hex.toHexString(hmacMD5Bytes));
        
    }

}
