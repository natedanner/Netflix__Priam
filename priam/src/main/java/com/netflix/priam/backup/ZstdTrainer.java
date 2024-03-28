package com.netflix.priam.backup;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.netflix.priam.aws.auth.IS3Credential;
import com.netflix.priam.backupv2.IMetaProxy;
import com.netflix.priam.backupv2.MetaV2Proxy;
import com.netflix.priam.compress.CompressionType;
import com.netflix.priam.utils.DateUtil;
import org.apache.commons.io.IOUtils;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

public class ZstdTrainer {
    private static final int
    private final BackupStatusMgr backupStatusMgr;
    private final IMetaProxy metaProxy;
    private final Clock clock;

    @Inject
    public ZstdTrainer(
        @Named("awss3roleassumption") IS3Credential credential) {
        this.credential = credential;
        this.metaProxy = metaProxy;
        this.backupStatusMgr = backupStatusMgr;
        this.clock = clock;
    }

    public void train(String metaFile) {
       downloadMetaFile(metaFile);
    }

}
