package com.fse.moviebooking.model;

import java.util.Set;

import org.springframework.data.annotation.Id;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBFlattened;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperFieldModel;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperFieldModel.DynamoDBAttributeType;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedJson;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTyped;

@DynamoDBTable(tableName = "UserCredential")
public class UserCredential {
	@Id
	private String email;
	private String password;
	private String resetPasswordToken;
	
	private Set<Role> userRoles;

	@DynamoDBHashKey(attributeName = "id")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@DynamoDBAttribute
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@DynamoDBAttribute
	public String getResetPasswordToken() {
		return resetPasswordToken;
	}

	public void setResetPasswordToken(String resetPasswordToken) {
		this.resetPasswordToken = resetPasswordToken;
	}


	
	@DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.L)
	@DynamoDBAttribute
	public Set<Role> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<Role> userRoles) {
		this.userRoles = userRoles;
	}

	public UserCredential() {

	}

	public UserCredential(String email, String password, String resetPasswordToken, Set<Role> userRoles) {
		super();
		this.email = email;
		this.password = password;
		this.resetPasswordToken = resetPasswordToken;
		this.userRoles = userRoles;
	}
	
	

	
	

	

}
