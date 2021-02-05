package com.smartitory.smartitoryhw.flight.model;

import com.smartitory.smartitoryhw.airline.model.Airline;
import com.smartitory.smartitoryhw.city.model.City;

public class Flight {

	private Long id;
	private Long distance;
	private City arrivalCity;
	private City departureCity;
	private Airline airline;
	private Integer flightTime;

	public Flight(Long id, Long distance, City arrivalCity, City departureCity, Airline airline, Integer flightTime)
	{
		this.id = id;
		this.distance = distance;
		this.arrivalCity = arrivalCity;
		this.departureCity = departureCity;
		this.airline = airline;
		this.flightTime = flightTime;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDistance() {
		return distance;
	}

	public void setDistance(Long distance) {
		this.distance = distance;
	}

	public City getArrivalCity() {
		return arrivalCity;
	}

	public void setArrivalCity(City arrivalCity) {
		this.arrivalCity = arrivalCity;
	}

	public City getDepartureCity() {
		return departureCity;
	}

	public void setDepartureCity(City departureCity) {
		this.departureCity = departureCity;
	}

	public Airline getAirline() {
		return airline;
	}

	public void setAirline(Airline airline) {
		this.airline = airline;
	}

	public Integer getFlightTime() {
		return flightTime;
	}

	public void setFlightTime(Integer flightTime) {
		this.flightTime = flightTime;
	}

}
