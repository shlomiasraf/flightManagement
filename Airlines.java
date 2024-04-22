package flightManagement;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public interface Airlines
{
	String getName();
}

class Flight implements Airlines
{
    private String name;
    private String from;
    private String destination;
    private String date;
    private String departureTime;
    private String arrivalTime;
    private double price;
    private double priceForWorkers;

    public Flight(String name,String from, String destination, String date, String departureTime, String arrivalTime, double price)
    {
        this.name = name;
        this.from = from;
        this.destination = destination;
        this.date = date;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.price = price;
        this.priceForWorkers = price;
    }
    @Override
    public String getName()
    {
        return this.name;
    }
    @Override
    public String toString()
    {
        return "flight name: " + this.name + " from: " + this.from + " to: " + this.destination + " date: " + this.date + " departureTime: " + this.departureTime
            + " arrivalTime: " + this.arrivalTime + " price: " + this.price + "$";
    }
    public String getFrom()
    {
        return this.from;
    }
    public String getDestination()
    {
        return this.destination;
    }
    public String getDate()
    {
        return this.date;
    }
    public String getDepartureTime()
    {
        return this.departureTime;
    }
    public String getArrivalTime()
    {
        return this.arrivalTime;
    }
    public double getPrice()
    {
        return this.price;
    }
    public double getPriceForWorkers()
    {
        return this.priceForWorkers;
    }
    public void setDepartureTime(String newDepartureTime)
    {
        this.departureTime = newDepartureTime;
    }
    public void setDate(String newDate)
    {
        this.date = newDate;
    }
    public void setPrice(double newPrice)
    {
        this.price = newPrice;
        this.priceForWorkers = newPrice;
    }
    public void setPriceForWorkers(double newPriceForWorkers)
    {
        this.priceForWorkers = newPriceForWorkers;
    }
}
class FlightFactory
{
    private static final Map<String, Flight> flights = new HashMap<>();
    public static Flight getFlight(String name, String from, String destination, String date, String departureTime, String arrivalTime, double price)
    {
        String key = name + ": " + date;
        if (!flights.containsKey(key))
        {
            flights.put(key, new Flight(name, from, destination, date, departureTime, arrivalTime, price));
        }
        else if(!flights.get(key).getFrom().equals(from) || !flights.get(key).getDestination().equals(destination) || !flights.get(key).getDepartureTime().equals(departureTime) || !flights.get(key).getArrivalTime().equals(arrivalTime) || flights.get(key).getPrice() != price)
        {
            System.out.println("\nThis flight name: " + name + " already exists in this date with other details, choose other name to your flight");
            return null;
        }
        return flights.get(key);
    }
}
class Airline implements Airlines
{

    private String airlineName;
    private List<Airlines> subsidiaries;

    public Airline(String airlineName) {
        this.airlineName = airlineName;
        this.subsidiaries = new ArrayList<>();
    }
    public List<Airlines> getSubsidiaries()
    {
        return this.subsidiaries;
    }

    public void add(Airlines subsidiary)
    {
        if(!subsidiaries.contains(subsidiary))
        {
            subsidiaries.add(subsidiary);
        }
    }
    public void remove(Airlines subsidiary)
    {
        if(!subsidiaries.contains(subsidiary))
        {
            subsidiaries.remove(subsidiary);
        }
    }

    @Override
    public String getName()
    {
        return airlineName;
    }
}
class AirlineFactory
{
    private static final Map<String, Airline> airlines = new HashMap<>();
    public static Airline getAirline(String name)
    {
        if (!airlines.containsKey(name))
        {
            airlines.put(name, new Airline(name));
        }
        return airlines.get(name);
    }
}