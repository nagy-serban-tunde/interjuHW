package com.smartitory.smartitoryhw.flight.operation;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.smartitory.smartitoryhw.airline.model.Airline;
import com.smartitory.smartitoryhw.city.model.City;
import com.smartitory.smartitoryhw.flight.model.Flight;


public class FlightOperation {

	private ArrayList<Flight> flights = new ArrayList<Flight>();
	
	public FlightOperation(ArrayList<Airline> airLines, ArrayList<City> cities) throws Exception
	{
		this.readXmlFiles(airLines, cities);
	}
	
	private void readXmlFiles(ArrayList<Airline> airLines, ArrayList<City> cities) throws Exception
	{
		try {
			File file = new File(".\\resource\\flight.xml");  
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); 
			DocumentBuilder db = dbf.newDocumentBuilder();  
			Document doc = db.parse(file);  
			doc.getDocumentElement().normalize();  
			
			NodeList nodeList = doc.getElementsByTagName("row");  
			
			for (int itr = 0; itr < nodeList.getLength(); itr++)   
			{  
				Node node = nodeList.item(itr);  
				if (node.getNodeType() == Node.ELEMENT_NODE)   
				{  
					Element eElement = (Element) node;  
					String tmpId = eElement.getElementsByTagName("id").item(0).getTextContent();
					String tmpDistance = eElement.getElementsByTagName("distance").item(0).getTextContent();
					String tmpArrivalCityId = eElement.getElementsByTagName("arrival_city_id").item(0).getTextContent();
					String tmpDepartureCityId = eElement.getElementsByTagName("departures_city_id").item(0).getTextContent();
					String tmpAirlineId = eElement.getElementsByTagName("airline_id").item(0).getTextContent();
					String tmpFlightTime = eElement.getElementsByTagName("flight_time").item(0).getTextContent();
					Long id = Long.valueOf(tmpId);
					Long distance = Long.valueOf(tmpDistance);
					City arrivalCity = cities.stream().filter(city -> Long.valueOf(tmpArrivalCityId).equals(city.getId()))
													  .findAny()
													  .orElse(null);
					
					City departureCity = cities.stream().filter(city -> Long.valueOf(tmpDepartureCityId).equals(city.getId()))
														.findAny()
														.orElse(null);
					Airline airline = airLines.stream().filter(airlin -> Long.valueOf(tmpAirlineId).equals(airlin.getId()))
														.findAny()
														.orElse(null);
					Integer flightTime = Integer.valueOf(tmpFlightTime);
					Flight flight = new Flight(id, distance, arrivalCity, departureCity, airline, flightTime);
					this.flights.add(flight);
				}  
			}  
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public ArrayList<Flight> getFlights() {
		return flights;
	}
	
	public ArrayList<Flight> returnFlightByDepartureCityToArrivalCity(String departureCity, String arrivalCity)
	{
		ArrayList<Flight> fligthsList = new ArrayList<Flight>();
		for (Flight flight : this.flights) {
			if(flight.getDepartureCity().getName().contentEquals(departureCity) && flight.getArrivalCity().getName().contentEquals(arrivalCity))
			{
				fligthsList.add(flight);
			}
		}
		return fligthsList;
	}
	
	public Flight checkFleigth(ArrayList<Flight> pFligth, String departureCity, String arrivalCity)
	{
		for (Flight flight : pFligth) {
			if(flight.getDepartureCity().getName().contentEquals(departureCity) && flight.getArrivalCity().getName().contentEquals(arrivalCity))
			{
				return flight;
			}
		}
		return null;
	}
	
}
