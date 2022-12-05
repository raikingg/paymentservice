package com.stackroute.paymentservice;

import com.stackroute.paymentservice.controller.TestController;
import com.stackroute.paymentservice.model.Payment;
import com.stackroute.paymentservice.serviceimpl.PaymentServiceImpl;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PaymentServiceApplicationTests {

	@Mock
	PaymentServiceImpl paymentService;
	@InjectMocks
	TestController testController;

	 String userId = "shivan";
	String bookingId = "sdfsadfsf";
	String transactionId = "sdfasdfdo";
	boolean isExpired = false;
	String invoiceId = "adsfasdfasdfsdf";
	String status = "PAID";



	Payment payment;
	Payment payment2;
	List<Payment> listOfPayments = new ArrayList<>();

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		payment = new Payment(1,userId,232.23,bookingId,status,new Date(),transactionId,isExpired,invoiceId);
		listOfPayments.add(payment);
    }

	@Test
	void testFindByBookingId(){
		when(paymentService.findByBookingId(anyString())).thenReturn(payment);
		MatcherAssert.assertThat(paymentService.findByBookingId(bookingId), equalTo(payment));
	}
	@Test
	void testFetchAllPayments(){
		when(paymentService.findAll()).thenReturn(listOfPayments);
		MatcherAssert.assertThat(paymentService.findAll().size(), equalTo(listOfPayments.size()));
		MatcherAssert.assertThat(paymentService.findAll().get(0).getUserId(), equalTo(userId));
	}

}
