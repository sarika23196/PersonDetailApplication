package com.sarika.com.sarika.wrapper;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.sarika.Constant;
import com.sarika.model.Person;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class S3ClientWrapper {

    private Logger logger = LogManager.getLogger(S3ClientWrapper.class);

    @Autowired
    private AmazonS3 s3Client;

    public String downloadS3File(String fileName) throws IOException {

        logger.info("Started downloading file from s3");

        GetObjectRequest request = new GetObjectRequest(Constant.PERSONS_BUCKET, fileName);

        S3Object s3Object;
        try{
            s3Object = s3Client.getObject(request);
        }catch (RuntimeException e) {
            logger.error("Error occurred while calling S3 API", e);
            throw e;
        }


        InputStream stream = s3Object.getObjectContent();

        String result;
        try{
            result = IOUtils.toString(stream, StandardCharsets.UTF_8.toString());
        } catch (IOException e) {
            logger.error("Error found while converting the S3 content to string: ",e);
            throw e;
        }

        logger.info("Successfully downloaded file from S3");

        return result;

    }

    public void uploadFile(String record, String fileName) {

        try {
            s3Client.putObject(Constant.REJECT_PERSON_BUCKET, fileName, record);
        }catch (RuntimeException e) {
            logger.error("Error occurred while writing rejected records to S3");
        }

    }
}
