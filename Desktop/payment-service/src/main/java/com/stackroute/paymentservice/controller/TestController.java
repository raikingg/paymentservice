package com.stackroute.paymentservice.controller;

import com.stackroute.paymentservice.model.Payment;
import com.stackroute.paymentservice.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
public class TestController {

    Logger logger
            = LoggerFactory.getLogger(TestController.class);

    @Autowired
    PaymentService paymentService;

    @GetMapping("/echo")
    public String test() {
        return "Hello There";
    }

    @GetMapping("/payment/{paymentId}")
    public ResponseEntity<List<Payment>> fetchPaymentByPaymentId(@PathVariable int paymentId) {
        List<Payment> listOfPayments = paymentService.findByPaymentIdOrderByDate(paymentId);
        if (listOfPayments.isEmpty()) {
            logger.warn(String.format("Empty payment list returned for payment id: %d", paymentId));
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listOfPayments, HttpStatus.OK);
    }

    @GetMapping("/payment")
    public ResponseEntity<List<Payment>> fetchAllPayments() {
        List<Payment> listOfPayments = paymentService.findAll();
        if (listOfPayments.isEmpty()) {
            logger.warn("No payments found in the database");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listOfPayments, HttpStatus.OK);
    }

    @GetMapping("/transactions/{userId}")
    public ResponseEntity<List<Payment>> fetchAllPaymentsByUserId(@PathVariable String userId) {
        try {
            List<Payment> listOfPayments = paymentService.findByUserId(userId);
            if (listOfPayments.isEmpty()) {
                logger.warn(String.format("No transactions found for user id %s", userId));
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(listOfPayments, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Threw exception "+e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
