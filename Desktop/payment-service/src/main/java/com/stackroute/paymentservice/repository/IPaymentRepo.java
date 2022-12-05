package com.stackroute.paymentservice.repository;

import com.stackroute.paymentservice.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IPaymentRepo extends JpaRepository<Payment, Integer> {

    @Query("select foo from Payment foo order by foo.date desc")
    List<Payment> findAllOrderByDate();
     Payment findByBookingId(String bookingId);
     Payment findByBookingIdAndUserId(String bookingId, String userId);

     void deleteByPaymentId(int paymentId);

    List<Payment> findByPaymentIdOrderByDateDesc(int paymentId);
    List<Payment> findByUserIdOrderByDate(String userId);


}
