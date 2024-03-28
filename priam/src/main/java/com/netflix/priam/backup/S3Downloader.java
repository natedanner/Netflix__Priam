package com.netflix.priam.backup;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.netflix.priam.aws.auth.IS3Credential;
import com.netflix.priam.compress.CompressionType;
import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

public class S3Downloader {
    private static final long MAX_BUFFER_SIZE = 5L * 1024L * 1024L;


    private final IS3Credential credential;
    private final Splitter SLASH_SPLITTER = Splitter.on("/");
    private final Joiner DASH_JOINER = Joiner.on("-");
    private String getRegion(String metaFileRemotePath) {
        String region =
            SLASH_SPLITTER.split(metaFileRemotePath).iterator().next().substring(0,7);
        return DASH_JOINER.join(region.substring(0, 2), region.substring(2, 6), region.substring(6, 7));
    }

    private void downloadMetaFile(String metaFile) {
        AmazonS3 s3Client = AmazonS3Client.builder()
            .withCredentials(credential.getAwsCredentialProvider())
            .withRegion(getRegion(metaFile))
            .build();
        long size = getFileSize(metaFile);
        final int bufferSize = Math.toIntExact(Math.min(MAX_BUFFER_SIZE, size));
        BufferedInputStream is =
            new BufferedInputStream(
                new RangeReadInputStream(s3Client, getShard(), size, remotePath),
                bufferSize);
        BufferedOutputStream os =
            new BufferedOutputStream(new FileOutputStream(localFile))) {
            if (path.getCompression() == CompressionType.NONE) {
                IOUtils.copyLarge(is, os);
            } else {
                compress.decompressAndClose(is, os);
            }
        }
    }
}
