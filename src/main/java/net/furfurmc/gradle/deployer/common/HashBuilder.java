package net.furfurmc.gradle.deployer.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashBuilder
{
    private static final Integer BUFFER_SIZE = 2024;

    private MessageDigest messageDigest;

    public HashBuilder() throws NoSuchAlgorithmException
    {
        this.messageDigest = MessageDigest.getInstance("SHA-256");
    }

    private String convertDigest(byte[] digest)
    {
        var stringBuilder = new StringBuilder();
        for (Byte dByte : digest) stringBuilder.append(String.format("%02x", dByte));
        return stringBuilder.toString();
    }

    public String makeStringHash(String content)
    {
        this.messageDigest.update(content.getBytes());
        return this.convertDigest(this.messageDigest.digest());
    }

    public String makeInputStreamHash(InputStream inputStream) throws IOException
    {
        byte[] buffer = new byte[HashBuilder.BUFFER_SIZE];
        for (int i = inputStream.read(buffer); i != -1; i = inputStream.read(buffer)) messageDigest.update(buffer, 0, i);
        return this.convertDigest(this.messageDigest.digest());
    }

    public String makeFileHash(File file) throws FileNotFoundException, IOException
    {
        try (InputStream inputStream = new FileInputStream(file))
        {
            return this.makeInputStreamHash(inputStream);
        }
    }

    public String makeUrlHash(URI url)
    {
        return this.makeStringHash(url.toString());
    }
}
