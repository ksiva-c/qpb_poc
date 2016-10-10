package com.demo.limits.common.uuid;

import javax.inject.Named;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.Security;
import java.text.SimpleDateFormat;

/**
 * Created by lnv.
 */
@Named("md5UUIDGenerator")
public class MD5UUIDGenerator implements IdGenerator {

    public String id(String seed) {
        return generateUUID(seed);
    }

    public static String generateUUID(final String seed) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/d-HH:mm:ss.SS");
            String md5Seed = seed + sdf.format(new java.util.Date());
            return MD5Hash(md5Seed);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    public static final String MD5Hash(String toHash) throws java.security.GeneralSecurityException {
        if(toHash==null ||  toHash.length()==0){
            return "";
        }
        // first create an instance, given the provider
        init();
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.reset();
        // call the update method one or more times
        // (useful when you don't know the size of your data, eg. stream)
        md.update(toHash.getBytes());
        byte[] digest = md.digest();
        //return (getLong(digest, 0) ^ getLong(digest, 8)); /* unreliable */
        return (data2hex(digest));
    }


    private static final void init() {
        // NOTE: Programmatically add SunJCE provider
        if (Security.getProviders() == null || Security.getProvider("SunJCE") == null) {
            Security.addProvider(new com.sun.crypto.provider.SunJCE());
        }
    }

    public static final String data2hex(byte[] data) {
        if (data == null)
            return null;

        int len = data.length;
        StringBuffer buf = new StringBuffer(len * 2);
        for (int pos = 0; pos < len; pos++)
            buf.append(toHexChar((data[pos] >>> 4) & 0x0F)).append(toHexChar(data[pos] & 0x0F));
        return buf.toString().trim();
    }
    private static final char toHexChar(int i) {
        if ((0 <= i) && (i <= 9))
            return (char) ('0' + i);
        else
            return (char) ('a' + (i - 10));
    }

    // not used
    private static final long getLong(final byte[] array, final int offset) {
        long value = 0;
        for (int i = 0; i < 8; i++) {
            value = ((value << 8) | (array[offset+i] & 0xFF));
        }
        return value;
    }

}
