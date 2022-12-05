package com.stackroute.paymentservice.service;

import com.stackroute.paymentservice.model.PaymentHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PaymentHelperService {

    void save(PaymentHelper paymentHelper);
    PaymentHelper findByBookingId(String bookingId);

    List<PaymentHelper> listOfPaymentHelper();

    

}
