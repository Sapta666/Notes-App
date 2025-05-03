package com.sapta.portfolio.apps.notes.app.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sapta.portfolio.apps.notes.app.models.common.AppSettingsDto;
import com.sapta.portfolio.apps.notes.app.models.common.OperationResultDto;
import com.sapta.portfolio.apps.notes.app.models.common.db.NotesDBDto;
import com.sapta.portfolio.apps.notes.app.models.common.db.UsersDBDto;
import com.sapta.portfolio.apps.notes.app.models.notes.AddEditNotesDto;
import com.sapta.portfolio.apps.notes.app.utils.DateUtils;
import com.sapta.portfolio.apps.notes.app.utils.HelperUtils;
import com.sapta.portfolio.apps.notes.app.utils.HibernateUtils;

import jakarta.persistence.ParameterMode;

@Service
public class NotesDao {

	private HibernateUtils hibernateUtils;

//	private HashMap<String, CnctDto> userDataList = new HashMap<String,CnctDto>();

	private SessionFactory _sessionFactory = null;

	@Autowired
	NotesDao(HibernateUtils hibernateUtils) {
		this.hibernateUtils = hibernateUtils;
		this._sessionFactory = this.hibernateUtils.getSessionFactory();
	}
	
	public OperationResultDto<List<NotesDBDto>> getNoteListByOnUserPKey(String user_pkey) {
		OperationResultDto<List<NotesDBDto>> userInfo = new OperationResultDto<List<NotesDBDto>>();

		Transaction transaction = null;

		try {
			Session session = this._sessionFactory.openSession();
			transaction = session.beginTransaction();
					
			String hql = "FROM NotesDBDto WHERE user_PKey= :p_user_pkey";
			Query<NotesDBDto> query = session.createQuery(hql,NotesDBDto.class);
			query.setParameter("p_user_pkey", user_pkey);
			
			List<NotesDBDto> notesList = query.list();

			// Execute the stored procedure
			userInfo.setData(notesList);
			userInfo.setSuccess(true);

			transaction.commit();

		} catch (Exception e) {
			userInfo.setSuccess(false);
			userInfo.setMessage(HelperUtils.convertStackTraceToString(e));
			e.printStackTrace();
		}

		return userInfo;
	}
	
	public OperationResultDto<Object> updateNote(String notes_PKey, AddEditNotesDto addEditNotes) {
		OperationResultDto<Object> result = new OperationResultDto<Object>();

		try {

			Transaction transaction = null;

			try {
				Session session = this._sessionFactory.openSession();
				transaction = session.beginTransaction();	
				
				NotesDBDto notesDB = (NotesDBDto) session.get(NotesDBDto.class, notes_PKey);

				if(notesDB == null)
					throw new Exception("Failed to get note info. ");

				
				notesDB.setBody(addEditNotes.getBody());
				notesDB.setTitle(addEditNotes.getTitle());
				notesDB.setLastmod_dectime(DateUtils.getCurrentDecTime());
				notesDB.setLastmod_numdate(DateUtils.getCurrentNumDate());
				
				session.merge(notesDB);

				// Execute the stored procedure
				result.setSuccess(true);				

				transaction.commit();

			} catch (Exception e) {
				result.setSuccess(false);
				result.setMessage(HelperUtils.convertStackTraceToString(e));
				e.printStackTrace();
			}

		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(HelperUtils.convertStackTraceToString(e));
			e.printStackTrace();
		}

		return result;
	}
	
	public OperationResultDto<Object> addNote(String user_pkey, AddEditNotesDto addNotes) {
		OperationResultDto<Object> result = new OperationResultDto<Object>();

		try {

			Transaction transaction = null;

			try {
				Session session = this._sessionFactory.openSession();
				transaction = session.beginTransaction();
				ProcedureCall procedureCall = session.createStoredProcedureCall("SP_INSERT_INTO_NOTES");

				procedureCall.registerStoredProcedureParameter("p_NOTES_PKEY", String.class, ParameterMode.OUT);
				procedureCall.registerStoredProcedureParameter("p_USER_PKEY", String.class, ParameterMode.IN)
						.setParameter("p_USER_PKEY", user_pkey);
				procedureCall.registerStoredProcedureParameter("p_TITLE", String.class, ParameterMode.IN)
						.setParameter("p_TITLE", addNotes.getTitle());
				procedureCall.registerStoredProcedureParameter("p_BODY", String.class, ParameterMode.IN)
						.setParameter("p_BODY", addNotes.getBody());
				procedureCall.registerStoredProcedureParameter("p_CREATE_NUMDT", Integer.class, ParameterMode.IN)
						.setParameter("p_CREATE_NUMDT", DateUtils.getCurrentNumDate());
				procedureCall.registerStoredProcedureParameter("p_CREATE_NUMTIME", Integer.class, ParameterMode.IN)
						.setParameter("p_CREATE_NUMTIME", DateUtils.getCurrentDecTime());
				procedureCall.registerStoredProcedureParameter("p_LASTMOD_NUMDATE", Integer.class, ParameterMode.IN)
						.setParameter("p_LASTMOD_NUMDATE", DateUtils.getCurrentNumDate());
				procedureCall.registerStoredProcedureParameter("p_LASTMOD_DECTIME", Integer.class, ParameterMode.IN)
						.setParameter("p_LASTMOD_DECTIME", DateUtils.getCurrentDecTime());

				procedureCall.execute();

				// Execute the stored procedure
				result.setSuccess(true);				

				transaction.commit();

			} catch (Exception e) {
				result.setSuccess(false);
				result.setMessage(HelperUtils.convertStackTraceToString(e));
				e.printStackTrace();
			}

		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(HelperUtils.convertStackTraceToString(e));
			e.printStackTrace();
		}

		return result;
	}
	
	public OperationResultDto<NotesDBDto> getNoteFromNotesPKey(String notes_pkey) {
		OperationResultDto<NotesDBDto> notesInfo = new OperationResultDto<NotesDBDto>();

		Transaction transaction = null;

		try {
			Session session = this._sessionFactory.openSession();
			transaction = session.beginTransaction();

			NotesDBDto notesDB= (NotesDBDto) session.get(NotesDBDto.class, notes_pkey);

			// Execute the stored procedure
			notesInfo.setSuccess(notesDB != null);
			notesInfo.setData(notesDB);

			transaction.commit();

		} catch (Exception e) {
			notesInfo.setSuccess(false);
			notesInfo.setMessage(HelperUtils.convertStackTraceToString(e));
			e.printStackTrace();
		}

		return notesInfo;
	}
	
	public OperationResultDto<Object> deleteNote(String notes_pkey) {
		OperationResultDto<Object> notesInfo = new OperationResultDto<Object>();

		Transaction transaction = null;

		try {
			Session session = this._sessionFactory.openSession();
			transaction = session.beginTransaction();

			NotesDBDto notesDB= (NotesDBDto) session.get(NotesDBDto.class, notes_pkey);
			
			session.remove(notesDB);

			// Execute the stored procedure
			notesInfo.setSuccess(notesDB != null);
			notesInfo.setData(notesDB);

			transaction.commit();

		} catch (Exception e) {
			notesInfo.setSuccess(false);
			notesInfo.setMessage(HelperUtils.convertStackTraceToString(e));
			e.printStackTrace();
		}

		return notesInfo;
	}

}
