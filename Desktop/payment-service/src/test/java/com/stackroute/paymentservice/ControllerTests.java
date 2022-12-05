package com.stackroute.paymentservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.paymentservice.controller.TestController;
import com.stackroute.paymentservice.model.Payment;
import com.stackroute.paymentservice.repository.IPaymentRepo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Date;

@WebMvcTest(TestController.class)
class ControllerTests {
//    @MockBean
//    private IPaymentRepo paymentRepo;
//
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    void shouldCreateTutorial() throws Exception {
//       Payment payment = new Payment(2, "test",232.11,"bookingId","PAID",new Date(),"transactionId",false,"invoiceId");
//
//        mockMvc.perform(get("/payment").contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(payment)))
//                .andExpect(status().isOk())
//                .andDo(print());
//    }
}