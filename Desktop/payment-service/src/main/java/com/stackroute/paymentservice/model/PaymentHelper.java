package com.stackroute.paymentservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class PaymentHelper {
    @Id
    private int paymentId;
    private String customerId;
    private String bookingId;
    private String amount;
    private Date date;

}
