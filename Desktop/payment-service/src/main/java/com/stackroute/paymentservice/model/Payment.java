package com.stackroute.paymentservice.model;





import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Date;

@Setter
@Getter
@ToString
@Entity
public class Payment {
    @Id
    private int paymentId;
    private String userId;
    private double amount;
    private String bookingId;
    private String status;
    private Date date;
    private String transactionId;
    private boolean isExpired;
    private String invoiceId;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    PaymentHelper paymentHelper;

    public Payment(int paymentId, String userId, double amount, String bookingId, String status, Date date, String transactionId, boolean isExpired, String invoiceId) {
        this.paymentId = paymentId;
        this.userId = userId;
        this.amount = amount;
        this.bookingId = bookingId;
        this.status = status;
        this.date = date;
        this.transactionId = transactionId;
        this.isExpired = isExpired;
        this.invoiceId = invoiceId;
    }

    public Payment(int paymentId, String userId, double amount, String bookingId, String status, Date date, String transactionId, boolean isExpired, String invoiceId, PaymentHelper paymentHelper) {
        this.paymentId = paymentId;
        this.userId = userId;
        this.amount = amount;
        this.bookingId = bookingId;
        this.status = status;
        this.date = date;
        this.transactionId = transactionId;
        this.isExpired = isExpired;
        this.invoiceId = invoiceId;
        this.paymentHelper = paymentHelper;
    }

    public Payment() {
    }

    public Payment(String userId, double amount, String bookingId, String status, Date date, String transactionId, boolean isExpired, String invoiceId) {
        this.userId = userId;
        this.amount = amount;
        this.bookingId = bookingId;
        this.status = status;
        this.date = date;
        this.transactionId = transactionId;
        this.isExpired = isExpired;
        this.invoiceId = invoiceId;
    }
}
