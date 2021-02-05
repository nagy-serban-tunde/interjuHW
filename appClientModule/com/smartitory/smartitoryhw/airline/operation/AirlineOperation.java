package com.smartitory.smartitoryhw.airline.operation;

import java.io.File;
import java.util.ArrayList;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.smartitory.smartitoryhw.airline.model.Airline;
import com.smartitory.smartitoryhw.flight.model.Flight;

public class AirlineOperation {

	private ArrayList<Airline> airLines = new ArrayList<Airline>();
	
	public AirlineOperation() throws Exception
	{
		this.readXmlFiles();
	}
	
	private void readXmlFiles() throws Exception
	{
		try {
			File file = new File(".\\resource\\airline.xml");  
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
					Long id = Long.valueOf(tmpId);
					Airline airline = new Airline(id, eElement.getElementsByTagName("name").item(0).getTextContent());
					this.airLines.add(airline);
				}  
			}  
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public ArrayList<Airline> getAirLines() {
		return airLines;
	}
	
	public ArrayList<Flight> returnAllFlightByAirline(String airlineName, ArrayList<Flight> flights)
	{
		ArrayList<Flight> flightByAirline = new ArrayList<Flight>();
		for (Flight flight : flights) {
			if (flight.getAirline().getName().contentEquals(airlineName))
			{
				flightByAirline.add(flight);
			}
		}
		return flightByAirline;
	}
	
}
