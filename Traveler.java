package flightManagement;
import java.util.*;

abstract class Traveler extends Observer
{
    private String name;
    private int id;
    private String country;
    private String[] favoriteDestinations;
    public Traveler(String name, int id , String country, String[] favoriteDestinations)
    {
        this.name = name;
        this.id = id;
        this.country = country;
        this.favoriteDestinations = favoriteDestinations;
    }
    public String getName()
    {
        return this.name;
    }
    public int getID()
    {
        return this.id;
    }
    public String getCountry()
    {
        return this.country;
    }
    public String[] getFavoriteDestinations()
    {
        return this.favoriteDestinations;
    }
    public void printNotifications()
    {
        System.out.println("\n" + this.name + " notifications:");
        for(String notification : this.notifications)
        {
            System.out.println(notification);
        }
    }
}

class Customer extends Traveler
{
    public Customer(String name, int id, String country, String[] favoriteDestinations)
    {
        super(name, id, country, favoriteDestinations);
    }
}
class CustomerFactory
{
    private static final Map<Integer, Customer> customers = new HashMap<>();
    public static Customer getCustomer(String name, int id, String country, String[] favoriteDestinations)
    {
        if (!customers.containsKey(id))
        {
            customers.put(id, new Customer(name, id, country, favoriteDestinations));
        }
        else if(!customers.get(id).getName().equals(name) || !customers.get(id).getCountry().equals(country) || !Arrays.equals(customers.get(id).getFavoriteDestinations(), favoriteDestinations))
        {
            System.out.println("\nThis id: " + id + " already exists with other details.");
            return null;
        }
        return customers.get(id);
    }
}

class Worker extends Traveler
{
    Airline airline;
    public Worker(String name,int id, Airline airline, String country, String[] favoriteDestinations)
    {
        super(name, id, country, favoriteDestinations);
        this.airline = airline;
    }
    public Airline getAirline()
    {
        return airline;
    }
}
class WorkerFactory
{
    private static final Map<Integer, Worker> workers = new HashMap<>();
    public static Worker getWorker(String name, int id, Airline airline, String country, String[] favoriteDestinations)
    {
        if (!workers.containsKey(id))
        {
            workers.put(id, new Worker(name, id, airline ,country, favoriteDestinations));
        }
        else if(!workers.get(id).getName().equals(name) || !workers.get(id).getCountry().equals(country) || !workers.get(id).getAirline().equals(airline) ||!Arrays.equals(workers.get(id).getFavoriteDestinations(), favoriteDestinations))
        {
            System.out.println("\nThis id: " + id + " already exists with other details.");
            return null;
        }
        return workers.get(id);
    }
}