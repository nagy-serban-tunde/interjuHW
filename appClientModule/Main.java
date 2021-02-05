import java.util.ArrayList;

import com.smartitory.smartitoryhw.airline.model.Airline;
import com.smartitory.smartitoryhw.airline.operation.AirlineOperation;
import com.smartitory.smartitoryhw.city.model.City;
import com.smartitory.smartitoryhw.city.operation.CityOperation;
import com.smartitory.smartitoryhw.flight.model.Flight;
import com.smartitory.smartitoryhw.flight.operation.FlightOperation;


public class Main {
	
	private static AirlineOperation airlineOperation;
	private static CityOperation cityOperation;
	private static FlightOperation flightOperation;
	
	public static void main(String[] args) throws Exception {
		airlineOperation = new AirlineOperation();
		cityOperation = new CityOperation();
		flightOperation = new FlightOperation(airlineOperation.getAirLines(), cityOperation.getCities());
		
		City smallestCity = cityOperation.SmallestCity();
		System.out.println("Legkissebb város: " + smallestCity.getName() + ", " + smallestCity.getPopoulation() + " lakos");
		City biggestCity = cityOperation.BiggestCity();
		System.out.println("Legnagyobb város: " + biggestCity.getName() + ", " + biggestCity.getPopoulation() + " lakos");
		System.out.println();
		writeShortestFlightbyAirline(smallestCity, biggestCity);
		System.out.println();
		writeShortestFlight(smallestCity, biggestCity);
	}
	
	public static void writeShortestFlightbyAirline(City departuresCity, City arrivalCity)
	{
		System.out.println("Legrövidebb út adott légitársasággal:");
		ArrayList<Airline> airlines = airlineOperation.getAirLines();
		for (Airline airline : airlines) {
			System.out.println("\t" + airline.getName() + ":");
			ArrayList<Flight>flightByAirline =  airlineOperation.returnAllFlightByAirline(airline.getName(),flightOperation.getFlights());
			if (!flightByAirline.isEmpty())
			{
				ArrayList<City> listCities = CityOperation.returnCitiesByAirline(flightByAirline);
				ArrayList<ArrayList<City>> permutationCityList = new ArrayList<ArrayList<City>>();
				permutationCityList = cityOperation.possibleFligth(listCities);
				ArrayList<ArrayList<Flight>> goodRoad = new ArrayList<ArrayList<Flight>>(); 
				for (ArrayList<City> cityList : permutationCityList) {
					ArrayList<Flight> goodPath = transferFligth(cityList, flightByAirline, departuresCity, arrivalCity);
					if(goodPath != null)
					{
						if(!checkExistGoodFlight(goodRoad,goodPath))
						{
							goodRoad.add(goodPath);
						}
					}
				}
				if(goodRoad.isEmpty())
				{
					System.out.println("\t\tNincs útvonal!");
				}
				if(!goodRoad.isEmpty())
				{
					print(countShortestRoad(goodRoad),'a');
				}
			}
			else {
				System.out.println("\t\tNincs útvonal!");
			}
		}
	}
	
	public static void writeShortestFlight(City departuresCity, City arrivalCity)
	{
		System.out.println("Legrövidebb út bármely légitársasággal:");
		ArrayList<Flight>flight = flightOperation.getFlights();
		if (!flight.isEmpty())
		{
			ArrayList<City> listCities = CityOperation.returnCitiesByAirline(flight);
			ArrayList<ArrayList<City>> permutationCityList = new ArrayList<ArrayList<City>>();
			permutationCityList = cityOperation.possibleFligth(listCities);
			ArrayList<ArrayList<Flight>> goodRoad = new ArrayList<ArrayList<Flight>>(); 
			for (ArrayList<City> cityList : permutationCityList) {
				ArrayList<Flight> goodPath = transferFligth(cityList, flight, departuresCity, arrivalCity);
				if(goodPath != null)
				{
					if(!checkExistGoodFlight(goodRoad,goodPath))
					{
						goodRoad.add(goodPath);
					}
				}
			}
			if(goodRoad.isEmpty())
			{
				System.out.println("\t\tNincs útvonal!");
			}
			if(!goodRoad.isEmpty())
			{
				print(countShortestRoad(goodRoad),' ');
			}
		}
		else {
			System.out.println("\t\tNincs útvonal!");
		}
	}

	public static void print (ArrayList<Flight> goodFligth, char type)
	{
		Integer allFlightTime = 0;
		for (int i = 0; i < goodFligth.size(); i++) {
			Flight flight = goodFligth.get(i);
			Integer flightTime = flight.getFlightTime();
			String time = timeCount(flightTime);
			if(type == 'a')
			{
				System.out.println("\t\t" + flight.getDepartureCity().getName() + " -> " 
						+ flight.getArrivalCity().getName() + " " + time);
			}
			else
			{
				System.out.println("\t\t"+ flight.getAirline().getName() + ": " + flight.getDepartureCity().getName() + " -> " 
						+ flight.getArrivalCity().getName() + ": " + time);
			}
			if(i != goodFligth.size()-1)
			{
				flightTime = plusTime(flightTime);
			}
			allFlightTime+=flightTime;
		}
		System.out.println("\t\t------");
		String str = timeCount(allFlightTime);
		System.out.println("\t\tÖsszesen: "+ str);
	}
	
	public static ArrayList<Flight> transferFligth(ArrayList<City> listCities, ArrayList<Flight> flightByAirline,City departuresCity, City arrivalCity)
	{
		ArrayList<Flight> listFlight = new ArrayList<Flight>();
		for (int j = 1; j < listCities.size(); j++) {
			Flight tmp = flightOperation.checkFleigth(flightByAirline, listCities.get(j-1).getName(), listCities.get(j).getName());
			if ( tmp != null)
			{
				listFlight.add(tmp);
			}
		}
		if(!listFlight.isEmpty())
		{
			ArrayList<Flight> tmp = goodFligths(listFlight,departuresCity,arrivalCity);
			if (tmp != null)
			{
				return tmp;
			}
		}
		return null;
	}
	
	public static ArrayList<Flight> countShortestRoad(ArrayList<ArrayList<Flight>> goodFligths)
	{
		ArrayList<Flight> goodFligth = new ArrayList<Flight>();
		long distance=Integer.MAX_VALUE;
		for (ArrayList<Flight> arrayList : goodFligths) {
			if(arrayList!=null)
			{
				long tmp = 0;
				for (Flight flight : arrayList) {
					tmp += flight.getDistance();
				}
				if(tmp < distance)
				{
					goodFligth = arrayList;
					distance = tmp;
				}
			}
		}
		return goodFligth;
	}
	
	public static boolean checkExistGoodFlight(ArrayList<ArrayList<Flight>> goodFligths, ArrayList<Flight> list)
	{
		return goodFligths.contains(list);
	}
	
	public static ArrayList<Flight> goodFligths(ArrayList<Flight> listFlight, City departuresCity, City arrivalCity)
	{
//		for (Flight flight : listFlight) {
//			System.out.println(flight.getDepartureCity().getName()+"->"+flight.getArrivalCity().getName());
//		}
//		System.out.println();
		if(!checkName(listFlight.get(0).getDepartureCity().getName(),departuresCity.getName()))
		{
			return null;
		}
		if(!checkName(listFlight.get(listFlight.size()-1).getArrivalCity().getName(),arrivalCity.getName()))
		{
			return null;
		}
		for (int i = 1; i < listFlight.size(); i++) {
			if(!checkName(listFlight.get(i-1).getArrivalCity().getName(),listFlight.get(i).getDepartureCity().getName()))
			{
				return null;
			}
		}
		return listFlight;
	}
	
	public static boolean checkName(String cityName1, String cityName2)
	{
		return cityName1.contentEquals(cityName2);
	}
	
	public static String timeCount(Integer flightTime)
	{
		int hours = flightTime / 60; 
		int minutes = flightTime % 60;
		String str;
		if (flightTime < 60)
		{
			str = minutes + " perc";
			return str;
		}
		if(minutes == 0)
		{
			str = hours + " óra";
			return str;
		}
		else
		{
			str = hours + " óra "+ minutes + " perc";
			return str;
		}
	}
	
	public static Integer plusTime(Integer flightTime)
	{
		int minutes = flightTime % 60;
		if(minutes != 0)
		{
			while (minutes != 0)
			{
				flightTime++;
				minutes = flightTime % 60;
			}
		}
		return flightTime;
	}
	
	public Main() {
		super();
	}

}