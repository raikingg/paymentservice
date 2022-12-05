package com.stackroute.paymentservice.serviceimpl;

import com.stackroute.paymentservice.model.PaymentHelper;
import com.stackroute.paymentservice.repository.PaymentHelperRepo;
import com.stackroute.paymentservice.service.PaymentHelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PaymentHelperServiceImpl implements PaymentHelperService {

    @Autowired
    PaymentHelperRepo paymentHelperRepo;
    @Override
    public void save(PaymentHelper paymentHelper) {
        paymentHelperRepo.saveAndFlush(paymentHelper);
    }

    @Override
    public PaymentHelper findByBookingId(String bookingId) {
        PaymentHelper paymentHelper = paymentHelperRepo.findByBookingId(bookingId);
        return paymentHelper;
    }

    @Override
    public List<PaymentHelper> listOfPaymentHelper() {
        return paymentHelperRepo.findAll();
    }

}
