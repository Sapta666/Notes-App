package com.sapta.portfolio.apps.notes.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sapta.portfolio.apps.notes.app.enums.SuccessFailedEnum;
import com.sapta.portfolio.apps.notes.app.models.common.AppSettingsDto;
import com.sapta.portfolio.apps.notes.app.models.common.BasicResponseDto;
import com.sapta.portfolio.apps.notes.app.models.common.OperationResultDto;
import com.sapta.portfolio.apps.notes.app.models.common.ResponseDataDto;
import com.sapta.portfolio.apps.notes.app.models.common.db.NotesDBDto;
import com.sapta.portfolio.apps.notes.app.models.notes.AddEditNotesDto;
import com.sapta.portfolio.apps.notes.app.services.NotesService;

@RestController
@RequestMapping("Notes")
//@CrossOrigin(origins = "*")
public class NotesController {

	@Autowired
	private NotesService notesService;

	@GetMapping("/list/{user_pkey}")
	public ResponseDataDto<List<NotesDBDto>> getNotes(@PathVariable("user_pkey") String user_pkey) {

		OperationResultDto<List<NotesDBDto>> result;
		ResponseDataDto<List<NotesDBDto>> response = new ResponseDataDto<List<NotesDBDto>>();

		// *********************************
		// Calling service for adding new note
		result = this.notesService.getUserNotes(user_pkey);

		// *********************************
		// calculating response based on result
		if (result.isSuccess()) {
			response.setStatus(SuccessFailedEnum.SUCCESS.name());
			response.setData(result.getData());
		} else {
			response.setStatus(SuccessFailedEnum.FAILED.name());
			response.setMessage(result.getMessage());
		}

		return response;
	}

	@PostMapping("/addNote/{user_pkey}")
	public BasicResponseDto addNotes(@PathVariable("user_pkey") String user_pkey,
			@RequestBody AddEditNotesDto addNotes) {

		OperationResultDto<Object> result;
		BasicResponseDto basicResponse = new BasicResponseDto();

		// *********************************
		// Calling service for adding new note
		result = this.notesService.addNote(user_pkey, addNotes);

		// *********************************
		// calculating response based on result
		if (result.isSuccess())
			basicResponse.setStatus(SuccessFailedEnum.SUCCESS.name());
		else {
			basicResponse.setStatus(SuccessFailedEnum.FAILED.name());
			basicResponse.setMessage(result.getMessage());
		}

		return basicResponse;
	}

	@PutMapping("/updateNote/{notes_PKey}")
	public BasicResponseDto updateNotes(@PathVariable("notes_PKey") String notes_PKey,
			@RequestBody AddEditNotesDto addEditNotes) {

		OperationResultDto<Object> result;
		BasicResponseDto basicResponse = new BasicResponseDto();

		// *********************************
		// Calling service for adding new note
		result = this.notesService.updateNote(notes_PKey, addEditNotes);

		// *********************************
		// calculating response based on result
		if (result.isSuccess())
			basicResponse.setStatus(SuccessFailedEnum.SUCCESS.name());
		else {
			basicResponse.setStatus(SuccessFailedEnum.FAILED.name());
			basicResponse.setMessage(result.getMessage());
		}

		return basicResponse;
	}

	@GetMapping("/{notes_pkey}")
	public ResponseDataDto<NotesDBDto> getNoteInfo(@PathVariable("notes_pkey") String notes_pkey) {

		OperationResultDto<NotesDBDto> result;
		ResponseDataDto<NotesDBDto> response = new ResponseDataDto<NotesDBDto>();

		// *********************************
		// Calling service for adding new note
		result = this.notesService.getNoteInfo(notes_pkey);

		// *********************************
		// calculating response based on result
		if (result.isSuccess()) {
			response.setStatus(SuccessFailedEnum.SUCCESS.name());
			response.setData(result.getData());
		} else {
			response.setStatus(SuccessFailedEnum.FAILED.name());
			response.setMessage(result.getMessage());
		}

		return response;
	}

	@DeleteMapping("/{notes_pkey}")
	public BasicResponseDto deleteNote(@PathVariable("notes_pkey") String notes_pkey) {

		OperationResultDto<Object> result;
		BasicResponseDto response = new BasicResponseDto();

		// *********************************
		// Calling service for adding new note
		result = this.notesService.deleteNote(notes_pkey);

		// *********************************
		// calculating response based on result
		if (result.isSuccess())
			response.setStatus(SuccessFailedEnum.SUCCESS.name());
		else {
			response.setStatus(SuccessFailedEnum.FAILED.name());
			response.setMessage(result.getMessage());
		}

		return response;
	}

}
