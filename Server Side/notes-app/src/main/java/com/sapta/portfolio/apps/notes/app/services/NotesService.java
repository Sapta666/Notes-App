package com.sapta.portfolio.apps.notes.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sapta.portfolio.apps.notes.app.dao.NotesDao;
import com.sapta.portfolio.apps.notes.app.models.common.OperationResultDto;
import com.sapta.portfolio.apps.notes.app.models.common.db.NotesDBDto;
import com.sapta.portfolio.apps.notes.app.models.notes.AddEditNotesDto;

@Service
public class NotesService {		
	
	@Autowired private NotesDao notesDao;
	
	public OperationResultDto<List<NotesDBDto>> getUserNotes(String user_pkey) {
		OperationResultDto<List<NotesDBDto>> result = new OperationResultDto<List<NotesDBDto>>();	
		
		// get user data from database		
		result = this.notesDao.getNoteListByOnUserPKey(user_pkey);
		
		// checking whether the data fetching operation is successful or not		
		if(result.isSuccess())					
			result.setSuccess(true);
		else {
			result.setMessage(result.getMessage());
			result.setSuccess(false);
		}
		
		return result;		
	}
	
	public OperationResultDto<Object> addNote(String user_pkey,AddEditNotesDto addNotes) {
		OperationResultDto<Object> result = new OperationResultDto<Object>();	
		
		// get user data from database		
		result = this.notesDao.addNote(user_pkey,addNotes);
		
		// checking whether the data fetching operation is successful or not		
		if(result.isSuccess())					
			result.setSuccess(true);
		else {
			result.setMessage(result.getMessage());
			result.setSuccess(false);
		}
		
		return result;		
	}
	
	public OperationResultDto<Object> updateNote(String notes_PKey,AddEditNotesDto addNotes) {
		OperationResultDto<Object> result = new OperationResultDto<Object>();	
		
		// get user data from database		
		result = this.notesDao.updateNote(notes_PKey,addNotes);
		
		// checking whether the data fetching operation is successful or not		
		if(result.isSuccess())					
			result.setSuccess(true);
		else {
			result.setMessage(result.getMessage());
			result.setSuccess(false);
		}
		
		return result;		
	}
	
	public OperationResultDto<NotesDBDto> getNoteInfo(String notes_pkey) {
		OperationResultDto<NotesDBDto> result = new OperationResultDto<NotesDBDto>();	
		
		// get user data from database		
		result = this.notesDao.getNoteFromNotesPKey(notes_pkey);
		
		// checking whether the data fetching operation is successful or not		
		if(result.isSuccess())					
			result.setSuccess(true);
		else {
			result.setMessage(result.getMessage());
			result.setSuccess(false);
		}
		
		return result;		
	}
		
	public OperationResultDto<Object> deleteNote(String notes_pkey) {
		OperationResultDto<Object> result = new OperationResultDto<Object>();	
		
		// get user data from database		
		result = this.notesDao.deleteNote(notes_pkey);
		
		// checking whether the data fetching operation is successful or not		
		if(result.isSuccess())					
			result.setSuccess(true);
		else {
			result.setMessage(result.getMessage());
			result.setSuccess(false);
		}
		
		return result;		
	}
	
}
