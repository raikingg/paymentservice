package com.stackroute.paymentservice.service;

import com.stackroute.paymentservice.model.Payment;

import java.util.List;

public interface PaymentService {

     void save(Payment payment);

     Payment saveOrUpdate(Payment payment);
     Payment findByBookingId(String bookingId);

     Payment findByBookingIdAndUserId(String bookingId, String userId);



    void deleteByPaymentId(int paymentId);

    Payment findById(int paymentId);

    List<Payment> findByPaymentIdOrderByDate(int paymentId);
    List<Payment> findAll();

    List<Payment> findByUserId(String userId);

    List<Payment> listOfPaymentsOlderThanTenMinutes();

    void deleteAll(List<Payment> paymentList);
   }
