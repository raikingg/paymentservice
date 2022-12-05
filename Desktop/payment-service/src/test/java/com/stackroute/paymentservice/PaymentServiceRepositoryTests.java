package com.stackroute.paymentservice;

import com.stackroute.paymentservice.model.Payment;
import com.stackroute.paymentservice.repository.IPaymentRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PaymentServiceRepositoryTests {
    @Autowired
    private IPaymentRepo paymentRepo;

    Payment payment;

    int paymentId = 268077131;
    String userId = "shivan";
    String bookingId = "a4be5d5c-36ad-482b-9ac3-a828635be102";
    String transactionId = "sdfasdfdo";
    boolean isExpired = false;
    String invoiceId = "adsfasdfasdfsdf";
    String status = "PAID";

    Payment payment2;
    List<Payment> listOfPayments = new ArrayList<>();

    @BeforeEach
    public void setup() {
        payment = new Payment(paymentId,userId,232.23,bookingId,status,new Date(),transactionId,isExpired,invoiceId);
        payment2 = new Payment(2, "test",232.11,"bookingId","PAID",new Date(),"transactionId",false,"invoiceId");
        listOfPayments.add(payment);
    }

    @Test
    void saveTest(){
        paymentRepo.save(payment);
        Assertions.assertThat(payment.getPaymentId()).isNotZero();
    }

    @Test
    void testGetAll(){
        List<Payment> paymentList= new ArrayList<>();
        Assertions.assertThat(paymentList).isEmpty();
        paymentList = paymentRepo.findAllOrderByDate();
        Assertions.assertThat(paymentList).hasSizeGreaterThan(1);
        if(paymentList.size() > 2) {
            for (int i = 0; i < paymentList.size()-1; i++) {
                Assertions.assertThat(paymentList.get(i).getDate()).isAfter(paymentList.get(i + 1).getDate());
            }
        }
    }

    @Test
    void testFindByPaymentIdOrderByDate(){
        List<Payment> paymentList = new ArrayList<>();
        Assertions.assertThat(paymentList).isEmpty();
        paymentList = paymentRepo.findByPaymentIdOrderByDateDesc(paymentId);
        Assertions.assertThat(paymentList).hasSizeGreaterThan(0);
    }

    @Test
    void testFindByBookingIdAndUserId(){
        Assertions.assertThat(payment.getPaymentId()).isEqualTo(paymentRepo.findByBookingIdAndUserId(bookingId,userId).getPaymentId());
    }

    @Test
    void testDeleteByPaymentId(){
        payment.setPaymentId(12354);
        paymentRepo.save(payment);
        Assertions.assertThat(paymentRepo.findById(payment.getPaymentId())).isNotEmpty();
        paymentRepo.deleteByPaymentId(payment.getPaymentId());
        Assertions.assertThat(paymentRepo.findById(payment.getPaymentId())).isEmpty();
    }
}
