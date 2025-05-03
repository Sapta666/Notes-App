package com.sapta.portfolio.apps.notes.app.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Base64.Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sapta.portfolio.apps.notes.app.models.cnct.CnctDto;
import com.sapta.portfolio.apps.notes.app.models.common.AppSettingsDto;
import com.sapta.portfolio.apps.notes.app.models.common.db.UsersDBDto;

public class HelperUtils {

	@Autowired
	private JWTUtils jwtUtils;

	private final String secret_KeyText = "1996052222051996";
	private final String encryption_Transformation = "AES/ECB/PKCS5Padding";
	private final String encryption_Algorithm = "AES";
	private final byte[] encryption_IV = {
            114, 95, 47, -94, -47, 15, 64, -121, 
            -74, 62, 4, -102, -3, -90, 87, 96
        }; 
	private final int keySize = 128;
	private SecretKeySpec secretKeySpec;
	private IvParameterSpec ivParameterSpec;

	public HelperUtils() {
		this.secretKeySpec = new SecretKeySpec(this.secret_KeyText.getBytes(), this.encryption_Algorithm);
		
        this.ivParameterSpec = new IvParameterSpec(this.encryption_IV);
	}

	public AppSettingsDto createAppSettingsFromUserInfo(UsersDBDto usersDB) {
		AppSettingsDto appSettings = new AppSettingsDto();

		// set appSettings field values from the cnctInfo object
		appSettings.setToken(jwtUtils.getJWTWithUsername(usersDB.getUsername()));
		appSettings.setCnct_PKey(usersDB.getUser_Pkey());
		appSettings.setUsername(usersDB.getUsername());
		appSettings.setFirstName(usersDB.getFirst_name());
		appSettings.setLastName(usersDB.getLast_name());

		return appSettings;
	}

	public String getDecryptedData(String value) throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		String data = "";

		Cipher cipher = Cipher.getInstance(this.encryption_Transformation);
		cipher.init(Cipher.DECRYPT_MODE, this.secretKeySpec);

		byte[] decryptedBytes = cipher.doFinal( Base64.getDecoder().decode(value));
		data = new String(decryptedBytes);

		return data;
	}

	public String getEncryptedData(String value) throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		String data = "";

		Cipher cipher = Cipher.getInstance(this.encryption_Transformation);
		cipher.init(Cipher.ENCRYPT_MODE, this.secretKeySpec,this.ivParameterSpec);

		byte[] encryptedBytes = cipher.doFinal(value.getBytes());
		data = Base64.getEncoder().encodeToString(encryptedBytes);

		return data;
	}

	public static String convertStackTraceToString(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);

		return sw.toString();
	}

	public static String getStoredProcedureParamString(int paramNum) {
		String paramString = "";

		for (int a = 1; a <= paramNum; a++)
			paramString += (a != paramNum ? "?, " : "?");

		System.out.println(paramString);

		return paramString;
	}

}
