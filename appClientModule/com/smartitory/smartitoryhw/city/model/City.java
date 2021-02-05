package com.smartitory.smartitoryhw.city.model;

public class City {

	private Long id;
	private Long popoulation;
	private String name;
	
	public City(Long id, Long popoulation, String name)
	{
		this.id = id;
		this.popoulation = popoulation;
		this.name = name;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPopoulation() {
		return popoulation;
	}

	public void setPopoulation(Long popoulation) {
		this.popoulation = popoulation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
