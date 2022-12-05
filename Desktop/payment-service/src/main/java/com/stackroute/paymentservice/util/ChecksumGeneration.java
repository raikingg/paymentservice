package com.stackroute.paymentservice.util;

import com.paytm.pg.merchant.PaytmChecksum;

import java.util.TreeMap;

public class ChecksumGeneration {
    public static String getPaytmChecksum(TreeMap<String, String> parameter, String merchantKey) throws Exception {
        return PaytmChecksum.generateSignature(parameter, merchantKey);
    }
    public static boolean verifyPaytmChecksum(TreeMap<String, String> parameter, String merchantKey, String paytmChecksum) throws Exception {
        return PaytmChecksum.verifySignature(parameter, merchantKey, paytmChecksum);
    }
}
