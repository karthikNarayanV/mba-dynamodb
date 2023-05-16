package com.fse.moviebooking.model;



public class Role {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
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