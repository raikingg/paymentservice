package com.stackroute.paymentservice.serviceimpl;

import com.stackroute.paymentservice.model.Payment;
import com.stackroute.paymentservice.repository.IPaymentRepo;
import com.stackroute.paymentservice.service.PaymentService;
import com.stackroute.paymentservice.util.PaymentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private IPaymentRepo paymentRepo;
    @Override
    public void save(Payment payment) {
        paymentRepo.saveAndFlush(payment);
    }
    @Override
    public Payment saveOrUpdate(Payment payment) {
        if(findById(payment.getPaymentId()) != null){
            paymentRepo.deleteByPaymentId(payment.getPaymentId());
            return paymentRepo.save(payment);
        }
        else {
            return paymentRepo.save(payment);
        }
    }
    @Override
    public Payment findByBookingId(String bookingId) {
        return paymentRepo.findByBookingId(bookingId);
    }

    @Override
    public Payment findByBookingIdAndUserId(String bookingId, String userId) {
        return paymentRepo.findByBookingIdAndUserId(bookingId,userId);
    }



    @Override
    public void deleteByPaymentId(int paymentId) {
        paymentRepo.deleteByPaymentId(paymentId);
    }


    @Override
    public Payment findById(int paymentId) {
        return paymentRepo.findById(paymentId).get();
    }

    @Override
    public List<Payment> findByPaymentIdOrderByDate(int paymentId) {
        return  paymentRepo.findByPaymentIdOrderByDateDesc(paymentId);
    }
    @Override
    public List<Payment> findAll() {
        return paymentRepo.findAllOrderByDate();
    }

    @Override
    public List<Payment> findByUserId(String userId) {
        return paymentRepo.findByUserIdOrderByDate(userId);
    }

    @Override
    public List<Payment> listOfPaymentsOlderThanTenMinutes() {
        List<Payment> paymentsList = paymentRepo.findAll();
        List<Payment> resultList = new ArrayList<>();
        //Fetch the failed payments older than 10 minutes
        for(Payment payment:paymentsList){
            if(new Date().getTime() - payment.getDate().getTime() > 600000 && Objects.equals(payment.getStatus(), PaymentStatus.FAILED.name())){
                resultList.add(payment);
            }
        }
        return resultList;
    }

    @Override
    public void deleteAll(List<Payment> paymentList) {
        paymentRepo.deleteAllInBatch(paymentList);
    }


}
