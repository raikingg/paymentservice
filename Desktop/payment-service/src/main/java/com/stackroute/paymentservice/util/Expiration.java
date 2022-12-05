package com.stackroute.paymentservice.util;


import com.stackroute.paymentservice.model.Payment;
import com.stackroute.paymentservice.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Expiration {

    Logger logger
            = LoggerFactory.getLogger(Expiration.class);

    @Autowired
    PaymentService paymentService;
    @Scheduled(cron = "0 0 2 * * SUN") //runs every sunday at 02:00 AM
    public void deleteChatMessagesAutomatically() {
        //deletes failed payments older than 10 minutes
        try {
            logger.info("Deleting failed payments");
            List<Payment> listOfPayments = paymentService.listOfPaymentsOlderThanTenMinutes();
            if (listOfPayments.isEmpty()) {
                logger.info("No failed payment older than 10 minutes found to be deleted by scheduler");
            } else {
                logger.info(String.format("Deleting %d failed payments", listOfPayments.size()));
                paymentService.deleteAll(listOfPayments);
            }
        }
        catch (Exception e){
            logger.error(String.valueOf(e));
        }
    }
}
