package com.stackroute.paymentservice.controller;

import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;

import com.stackroute.paymentservice.model.Payment;
import com.stackroute.paymentservice.model.PaymentHelper;
import com.stackroute.paymentservice.model.PaytmDetails;
import com.stackroute.paymentservice.service.PaymentHelperService;
import com.stackroute.paymentservice.service.PaymentService;
import com.stackroute.paymentservice.util.ChecksumGeneration;
import com.stackroute.paymentservice.util.PaymentStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class PaymentController {

	Logger logger
			= LoggerFactory.getLogger(PaymentController.class);

	@Autowired
	private PaytmDetails paytmDetails;
	@Autowired
	private Environment env;

	@GetMapping("/")
	public String home() {
		return "home";
	}

	@Value("${paytm.payment.sandbox.merchantKey}")
	String merchantKey;

	@Autowired
	private PaymentService paymentService;


	@Autowired
	private PaymentHelperService paymentHelperService;
	@PostMapping(value = "/paymentredirect")
	public ModelAndView getRedirect(@RequestParam(name = "CUST_ID") String customerId,
									@RequestParam(name = "TXN_AMOUNT") String transactionAmount,
									@RequestParam(name = "ORDER_ID") String orderId) throws Exception {
		logger.info("Payment for amount "+transactionAmount + " initiated for customer id: "+ customerId);
		ModelAndView modelAndView = new ModelAndView("redirect:" + paytmDetails.getPaytmUrl());
		TreeMap<String, String> parameters = new TreeMap<>();
		paytmDetails.getDetails().forEach((k, v) -> parameters.put(k, v));
		parameters.put("MOBILE_NO", env.getProperty("paytm.mobile"));
		parameters.put("EMAIL", env.getProperty("paytm.email"));
		orderId = UUID.randomUUID().toString();
		parameters.put("ORDER_ID", orderId);
		parameters.put("TXN_AMOUNT", transactionAmount);
		parameters.put("CUST_ID", customerId);
		String checkSum = ChecksumGeneration.getPaytmChecksum(parameters,merchantKey);
		parameters.put("CHECKSUMHASH", checkSum);
		modelAndView.addAllObjects(parameters);
		Payment payment = new Payment();
		payment.setPaymentId(new Random().nextInt());
		payment.setDate(new Date());
		payment.setAmount(Double.parseDouble(transactionAmount));
		payment.setUserId(customerId);
		payment.setBookingId(orderId);
		payment.setExpired(false);
		payment.setStatus(PaymentStatus.PENDING.name());
		paymentService.save(payment);
		PaymentHelper paymentHelper = new PaymentHelper(payment.getPaymentId(),customerId,orderId,transactionAmount,payment.getDate());
		paymentHelperService.save(paymentHelper);
		return modelAndView;
	}




	@PostMapping(value = "/paymentresponse")
	public String getResponseRedirect(HttpServletRequest request, Model model) {

		Map<String, String[]> mapData = request.getParameterMap();
		TreeMap<String, String> parameters = new TreeMap<String, String>();
		mapData.forEach((key, val) -> parameters.put(key, val[0]));
		String result;
		String receipt = null ;
		boolean isValidChecksum = false;
		try {
			TreeMap<String, String> newMap = new TreeMap<>();
			newMap.put("MID",parameters.get("MID"));
			newMap.put("ORDERID",parameters.get("ORDERID"));
			isValidChecksum = ChecksumGeneration.verifyPaytmChecksum(newMap, merchantKey, ChecksumGeneration.getPaytmChecksum(newMap,merchantKey));

			if (isValidChecksum && parameters.containsKey("RESPCODE")) {
				String orderId = parameters.get("ORDERID");
				if (parameters.get("RESPCODE").equals("01")) {
					String transactionId = parameters.get("TXNID");
					Payment payment = paymentService.findByBookingId(orderId);
					//long diff =   new Date().getTime() - payment.getDate().getTime();//as given
					//long seconds = TimeUnit.MILLISECONDS.toMillis(diff);
						payment.setTransactionId(transactionId);
						payment.setStatus(PaymentStatus.PAID.name());
						payment.setExpired(false);
						receipt = String.valueOf(new Random().nextLong());
						payment.setInvoiceId(receipt);
						paymentService.save(payment);
						result = "Payment Successful";

				} else {
					Payment payment = paymentService.findByBookingId(orderId);
					payment.setStatus(PaymentStatus.FAILED.name());
					payment.setInvoiceId(receipt);
					paymentService.save(payment);
					logger.warn("Response code is not 01");
					result = "Payment Failed";
				}
			} else {
				logger.warn("Payment failed because of invalid checksum");
				result = "Checksum mismatched";
			}
		} catch (Exception e) {
			result = e.toString();
		}
		model.addAttribute("result",result);
		model.addAttribute("receipt",receipt);
		parameters.remove("MID");
		parameters.remove("CHECKSUMHASH");
		model.addAttribute("parameters",parameters);
		return "report";
	}





}
