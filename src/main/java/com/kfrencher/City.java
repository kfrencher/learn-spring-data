package com.kfrencher;

import javax.persistence.Transient;

import lombok.Data;

@Data
public class City {
	private String name;
	@Transient
	private State state;
}
