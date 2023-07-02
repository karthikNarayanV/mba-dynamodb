package com.fse.moviebooking.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;

@DynamoDBDocument
public class Role {
    private String name;

    @DynamoDBAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @DynamoDBIgnore
    public String getAuthority() {
        return getName();
    }

	public Role(String name) {
		super();
		this.name = name;
	}

	public Role() {
		super();
	}
    
    
}