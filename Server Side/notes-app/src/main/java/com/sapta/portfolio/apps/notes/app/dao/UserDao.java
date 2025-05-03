package com.sapta.portfolio.apps.notes.app.dao;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.CallableStatement;
import java.util.HashMap;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sapta.portfolio.apps.notes.app.models.auth.UserSignUpDto;
import com.sapta.portfolio.apps.notes.app.models.cnct.CnctDto;
import com.sapta.portfolio.apps.notes.app.models.common.OperationResultDto;
import com.sapta.portfolio.apps.notes.app.models.common.UserDetailsDto;
import com.sapta.portfolio.apps.notes.app.models.common.db.UsersDBDto;
import com.sapta.portfolio.apps.notes.app.utils.DateUtils;
import com.sapta.portfolio.apps.notes.app.utils.HelperUtils;
import com.sapta.portfolio.apps.notes.app.utils.HibernateUtils;
import com.sapta.portfolio.apps.notes.app.utils.JWTUtils;

import jakarta.persistence.ParameterMode;

@Service
public class UserDao {

	@Autowired 
	private HelperUtils helperUtils;
	
	private HibernateUtils hibernateUtils;

//	private HashMap<String, CnctDto> userDataList = new HashMap<String,CnctDto>();

	private SessionFactory _sessionFactory = null;

	@Autowired
	UserDao(HibernateUtils hibernateUtils) {
		this.hibernateUtils = hibernateUtils;
		this._sessionFactory = this.hibernateUtils.getSessionFactory();
	}

	public OperationResultDto<String> addUser(UserSignUpDto userSign) {
		OperationResultDto<String> cnctResult = new OperationResultDto<String>();

		try {
			userSign.setFirstName(this.helperUtils.getDecryptedData(userSign.getFirstName()));
			userSign.setLastName(this.helperUtils.getDecryptedData(userSign.getLastName()));
			userSign.setPassword(userSign.getPassword());
			userSign.setUsername(this.helperUtils.getDecryptedData(userSign.getUsername()));

			Transaction transaction = null;

			try {
				Session session = this._sessionFactory.openSession();
				transaction = session.beginTransaction();
				ProcedureCall procedureCall = session.createStoredProcedureCall("SP_INSERT_INTO_USERS");

				procedureCall.registerStoredProcedureParameter("p_USER_PKEY", String.class, ParameterMode.OUT);
				procedureCall.registerStoredProcedureParameter("p_USERNAME", String.class, ParameterMode.IN)
						.setParameter("p_USERNAME", userSign.getUsername());
				procedureCall.registerStoredProcedureParameter("p_PASSWORD", String.class, ParameterMode.IN)
						.setParameter("p_PASSWORD", userSign.getPassword());
				procedureCall.registerStoredProcedureParameter("p_FIRST_NAME", String.class, ParameterMode.IN)
						.setParameter("p_FIRST_NAME", userSign.getFirstName());
				procedureCall.registerStoredProcedureParameter("p_LAST_NAME", String.class, ParameterMode.IN)
						.setParameter("p_LAST_NAME", userSign.getLastName());
				procedureCall.registerStoredProcedureParameter("p_CREATE_NUMDT", Integer.class, ParameterMode.IN)
						.setParameter("p_CREATE_NUMDT", DateUtils.getCurrentNumDate());
				procedureCall.registerStoredProcedureParameter("p_CREATE_DECTIME", Integer.class, ParameterMode.IN)
						.setParameter("p_CREATE_DECTIME", DateUtils.getCurrentDecTime());
				procedureCall.registerStoredProcedureParameter("p_IS_ADMIN", Character.class, ParameterMode.IN)
				.setParameter("p_IS_ADMIN", 'N');				

				procedureCall.execute();

				// Execute the stored procedure
				cnctResult.setSuccess(true);
				cnctResult.setData(procedureCall.getOutputParameterValue("p_USER_PKEY").toString());

				transaction.commit();

			} catch (Exception e) {
				cnctResult.setSuccess(false);
				cnctResult.setMessage(HelperUtils.convertStackTraceToString(e));
				e.printStackTrace();
			}

		} catch (Exception e) {
			cnctResult.setSuccess(false);
			cnctResult.setMessage(HelperUtils.convertStackTraceToString(e));
			e.printStackTrace();
		}


		return cnctResult;
	}

	public OperationResultDto<UsersDBDto> getUserDataFromEncryptedUsername(String usernameEncrypted, boolean isEncrypted) {
		OperationResultDto<UsersDBDto> userInfo = new OperationResultDto<UsersDBDto>();
		
		Transaction transaction = null;

		try {

			String usernameDecrypted = "";

			if (isEncrypted)
				usernameDecrypted = this.helperUtils.getDecryptedData(usernameEncrypted);
			else
				usernameDecrypted = usernameEncrypted;

			Session session = this._sessionFactory.openSession();
			transaction = session.beginTransaction();
			
			String hql = "FROM UsersDBDto WHERE username = :p_username";
            Query<UsersDBDto> query = session.createQuery(hql, UsersDBDto.class);
            query.setParameter("p_username", usernameDecrypted);
			
            UsersDBDto userDB = query.uniqueResult();			

			// Execute the stored procedure
			userInfo.setSuccess(true);
			userInfo.setData(userDB);

			transaction.commit();

		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
				| InvalidAlgorithmParameterException  | BadPaddingException e) {
			userInfo.setSuccess(false);
			userInfo.setData(null);
			userInfo.setMessage("No such user exists!");

//			e.printStackTrace();
//			return userInfo;
		}

		return userInfo;
	}

	public OperationResultDto<UsersDBDto> getUserDataFromUserPKey(String user_PKey) {
		OperationResultDto<UsersDBDto> userInfo = new OperationResultDto<UsersDBDto>();

		Transaction transaction = null;

		try {
			Session session = this._sessionFactory.openSession();
			transaction = session.beginTransaction();

			UsersDBDto userDB = (UsersDBDto) session.get(UsersDBDto.class, user_PKey);

			// Execute the stored procedure
			userInfo.setSuccess(userDB != null);
			userInfo.setData(userDB);

			transaction.commit();

		} catch (Exception e) {
			userInfo.setSuccess(false);
			userInfo.setMessage(HelperUtils.convertStackTraceToString(e));
			e.printStackTrace();
		}

		return userInfo;
	}

}
