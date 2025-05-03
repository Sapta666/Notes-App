package com.sapta.portfolio.apps.notes.app.models.common.db;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity()
@Table(name = "users")
@Data
@NoArgsConstructor
public class UsersDBDto {

	@Id
	@ColumnDefault("") private String user_Pkey;
	@ColumnDefault("") private String username;
	@ColumnDefault("") private String password;
	@ColumnDefault("") private String first_name;
	@ColumnDefault("") private String last_name;
	@ColumnDefault("0") private int create_numdt;
	@ColumnDefault("0") private int create_dectime;
	@ColumnDefault("0") private String is_admin;		   
	
}
