package com.kfrencher;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity
public class State {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String name;
	private String country;
	
	private static String filterName;
	
	@Transient
	private List<City> cities;

    public static String getFilterName() {
        return filterName;
    }

    public static void setFilterName(String filterName) {
        State.filterName = filterName;
    }
}
