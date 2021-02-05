package com.smartitory.smartitoryhw.city.operation;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.smartitory.smartitoryhw.city.model.City;
import com.smartitory.smartitoryhw.flight.model.Flight;


public class CityOperation {
	
	private ArrayList<City> cities = new ArrayList<City>();
	
	public CityOperation() throws Exception {
		readXmlFiles();
	}
	
	private void readXmlFiles() throws Exception
	{
		try {
			File file = new File(".\\resource\\city.xml");  
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
					String tmpPopulation = eElement.getElementsByTagName("population").item(0).getTextContent();
					Long id = Long.valueOf(tmpId);
					Long population = Long.valueOf(tmpPopulation);
					City city = new City(id,population, eElement.getElementsByTagName("name").item(0).getTextContent());
					this.cities.add(city);
				}  
			}  
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public ArrayList<City> getCities() {
		return cities;
	}

	public City SmallestCity()
	{
		City tmp = null;
		Integer pop = Integer.MAX_VALUE;
		for (City city : this.cities) {
			if( pop.longValue() > city.getPopoulation())
			{
				tmp = city;
				pop = city.getPopoulation().intValue();
			}	
		}
		return tmp;
	}
	
	public City BiggestCity()
	{
		City tmp = null;
		Integer pop = Integer.MIN_VALUE;
		for (City city : this.cities) {
			if( pop.longValue() < city.getPopoulation())
			{
				tmp = city;
				pop = city.getPopoulation().intValue();
			}	
		}
		return tmp;
	}
	
	public static ArrayList<City> returnCitiesByAirline (ArrayList<Flight> flightByAirline) {
		ArrayList<City> citiesByAirline = new ArrayList<City>();
		for(Flight fligth : flightByAirline)
		{
			if(!visitedCityCheck(citiesByAirline, fligth.getDepartureCity()))
			{
				citiesByAirline.add(fligth.getDepartureCity());
			}
			if(!visitedCityCheck(citiesByAirline, fligth.getArrivalCity()))
			{
				citiesByAirline.add(fligth.getArrivalCity());
			}
		}
		return citiesByAirline;
	}
	
	private static boolean visitedCityCheck(ArrayList<City> listCity, City city )
	{
		for (City ci : listCity) {
			if(ci == city)
			{
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<ArrayList<City>> possibleFligth(ArrayList<City> list)
	{
		ArrayList<ArrayList<City>> resultList = new ArrayList<ArrayList<City>>();
		ArrayList<ArrayList<City>> listResult = new ArrayList<ArrayList<City>>();
		resultList = listCombination(list);
		for (ArrayList<City> arrayList : resultList) {
			ArrayList<ArrayList<City>> result = new ArrayList<ArrayList<City>>();
			result = listPermutations(arrayList);
			for (ArrayList<City> array : result) {
				if(array.size() != 1)
				{
					listResult.add(array);
				}
			}
		}
		return listResult;
	}
	
	public static ArrayList<ArrayList<City>> listCombination(ArrayList<City> list) {
		int maxbit = 1 << list.size();
		ArrayList<ArrayList<City>> result = new ArrayList<ArrayList<City>>();
        for (int p = 0; p < maxbit; p++) {
        	ArrayList<City> res = new ArrayList<City>();
            for (int i = 0; i < list.size(); i++) {
                if ((1 << i & p) > 0) {
                    res.add(list.get(i));
                }
            }
            result.add(res);
        }
        return result;
	}
	
	public static ArrayList<ArrayList<City>> listPermutations(ArrayList<City> list) {

	    if (list.size() == 0) {
	    	ArrayList<ArrayList<City>> result = new ArrayList<ArrayList<City>>();
	        result.add(new ArrayList<City>());
	        return result;
	    }
	    ArrayList<ArrayList<City>> returnMe = new ArrayList<ArrayList<City>>();

	    City firstElement = list.remove(0);

	    ArrayList<ArrayList<City>> recursiveReturn = listPermutations(list);
	    for (ArrayList<City> li : recursiveReturn) {

	        for (int index = 0; index <= li.size(); index++) {
	        	ArrayList<City> temp = new ArrayList<City>(li);
	            temp.add(index, firstElement);
	            returnMe.add(temp);
	        }

	    }
	    return returnMe;
	}
	
}
