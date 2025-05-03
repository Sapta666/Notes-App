package com.sapta.portfolio.apps.notes.app.models.common.db;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity()
@Table(name = "notes")
@Data
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class NotesDBDto {
	
	@Id
	@ColumnDefault("") private String notes_PKey;
	@ColumnDefault("") private String user_PKey;
	@ColumnDefault("") private String title;
	@ColumnDefault("") private String body;	
	@ColumnDefault("0") private int create_numdate;
	@ColumnDefault("0") private int create_numtime;
	@ColumnDefault("0") private int lastmod_numdate;
	@ColumnDefault("0") private int lastmod_dectime;

}
