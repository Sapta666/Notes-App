package com.sapta.portfolio.apps.notes.app.models.notes;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddEditNotesDto {
	
	@JsonProperty("Title") private String Title = "";
	@JsonProperty("Body") private String Body = "";
	@JsonProperty("Notes_PKey") private String Notes_PKey = "";

}
