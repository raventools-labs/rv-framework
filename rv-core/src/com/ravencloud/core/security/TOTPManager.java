package com.ravencloud.core.security;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import de.taimos.totp.TOTP;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum TOTPManager {
	
	INSTANCE;
	
	public String generateTOTKey() {
		
		SecureRandom random = new SecureRandom();
		byte[] bytes = new byte[20];
		random.nextBytes(bytes);
		Base32 base32 = new Base32();
		return base32.encodeToString(bytes);
	}
	
	public String getTOTPCode(String secretKey) {
		
		Base32 base32 = new Base32();
		byte[] bytes = base32.decode(secretKey);
		String hexKey = Hex.encodeHexString(bytes);
		return TOTP.getOTP(hexKey);
	}
	
	public byte[] createQRCode(String user, String totpKey, String domain, int width, int height) {
		
		try {
		
			String barCodeUrl = getAuthenticatorBarCode(totpKey, user, domain);
			
			log.info(barCodeUrl);

			BitMatrix matrix = new MultiFormatWriter().encode(barCodeUrl, BarcodeFormat.QR_CODE, width, height);
			
			try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
				
				MatrixToImageWriter.writeToStream(matrix, "png", out);
				
				return out.toByteArray();
			}
		
		} catch (Exception ex) {
			
			throw new IllegalStateException(ex);
		}
	}
	
	private static final int DEFAULT_WIDTH = 200;
	
	public byte[] createQRCode(String user, String totpKey, String domain) {
		
		return createQRCode(user, totpKey, domain, DEFAULT_WIDTH, DEFAULT_WIDTH);
	}
	
	private static final String OTPAUTH_URL = "otpauth://totp/%s?secret=%s&issuer=%s";
	
	private static final String URL_REPLACE_ORIGIN = "+";
	
	private static final String URL_REPLACE_DESTINY = "%20";
	
	private String getAuthenticatorBarCode(String secretKey, String account, String issuer)
		throws UnsupportedEncodingException {
		
		String charset = StandardCharsets.UTF_8.toString();
		
		return String.format(OTPAUTH_URL,
			URLEncoder.encode(issuer + ":" + account, charset).replace(URL_REPLACE_ORIGIN, URL_REPLACE_DESTINY),
			URLEncoder.encode(secretKey, charset).replace(URL_REPLACE_ORIGIN, URL_REPLACE_DESTINY),
			URLEncoder.encode(issuer, charset).replace(URL_REPLACE_ORIGIN, URL_REPLACE_DESTINY));
	}
	
//	public static void main(String[] args) {
//		
//		String secretKey = "HWBPWC55PRDBUJ67RRI2UG6CXU5E7KGC";
//		String lastCode = null;
//		
//		int i = 1;
//		
//		while (i == 1) {
//			
//			String code = TOTPManager.INSTANCE.getTOTPCode(secretKey);
//			
//			if(!code.equals(lastCode)) { System.out.println(code); }
//			
//			lastCode = code;
//			
//			try {
//				
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				
//			}
//			
//		}
//		
//	}
}
