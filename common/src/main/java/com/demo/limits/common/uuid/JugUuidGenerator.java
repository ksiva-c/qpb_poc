package com.demo.limits.common.uuid;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.NameBasedGenerator;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

import javax.inject.Named;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * Created by lnv .
 */
@Named("jugUuidGenerator")
public class JugUuidGenerator implements IdGenerator {

    private static final String HASH_ALGORITHM = "MD5";

    public String id(String seed) {
        UUID uuid = generateUUID(seed);
        return uuid.toString().replaceAll("-", "");
    }

    public UUID generateUUID(final String seed) {
        MessageDigest messageDigest = getHashAlgorithm();
        TimeBasedGenerator timeBasedUuidGenerator = Generators.timeBasedGenerator();
        UUID timeBasedUuid = timeBasedUuidGenerator.generate();
        NameBasedGenerator uuidGenerator = Generators.nameBasedGenerator(timeBasedUuid, messageDigest);
        return uuidGenerator.generate(seed);
    }

    public MessageDigest getHashAlgorithm() {
        MessageDigest messageDigest = null;
        if (messageDigest == null) {
            try {
                messageDigest = MessageDigest.getInstance(HASH_ALGORITHM);
            } catch (NoSuchAlgorithmException ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        }
        return messageDigest;
    }

}