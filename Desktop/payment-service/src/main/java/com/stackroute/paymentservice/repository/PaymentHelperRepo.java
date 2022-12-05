package com.stackroute.paymentservice.repository;

import com.stackroute.paymentservice.model.PaymentHelper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface PaymentHelperRepo extends JpaRepository<PaymentHelper,String> {
    @Transactional
    PaymentHelper findByBookingId(String bookingId);
}
