package com.sarika.com.sarika.wrapper;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.sarika.Constant;
import com.sarika.model.ValidAndRejectedPersons;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SNSWrapper {

    private Logger logger = LogManager.getLogger(SNSWrapper.class);

    @Autowired
    private AmazonSNS snsClient;

    public void snsHandler(String fileName, ValidAndRejectedPersons validAndRejectedPersons) {

        String subject = "Report" + fileName;
        String msg = "Number of successful records: " + validAndRejectedPersons.getValidPersonList().size();
        msg += "\nNumber of Rejected records: " + validAndRejectedPersons.getRejectedPersonList().size();
        msg += "\nYou can download rejected persons data from: " + Constant.REJECT_PERSON_BUCKET + "/" + fileName;


        PublishRequest request = new PublishRequest("arn:aws:sns:us-west-2:309901561626:Offers",msg, subject);

        try{
            PublishResult result = snsClient.publish(request);
            logger.info("Successfully sent msg with msgID:" + result.getMessageId());
        }catch (RuntimeException e) {
            throw e;
        }


    }

}
