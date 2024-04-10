package flightManagement;
import java.util.ArrayList;
import java.util.List;

abstract class Traveler extends Observer
{
    private String name;
    private String country;
    private String[] favoriteDestinations;
    public Traveler(String name, String country, String[] favoriteDestinations)
    {
        this.name = name;
        this.country = country;
        this.favoriteDestinations = favoriteDestinations;
    }
    public String getName()
    {
        return this.name;
    }
    public String getCountry()
    {
        return this.country;
    }
    public String[] getFavoriteDestinations()
    {
        return this.favoriteDestinations;
    }
    public void notify(String msg)
    {
        this.update(msg);
    }
    public void printNotifications()
    {
        System.out.println("\n" + this.name + " notifications:");
        for(String notification : notifications)
        {
            System.out.println(notification);
        }
    }
}

class Customer extends Traveler
{
    public Customer(String name, String country, String[] favoriteDestinations)
    {
        super(name, country, favoriteDestinations);
    }
}
class Worker extends Traveler
{
    airline airline;
    public Worker(String name,airline airline, String country, String[] favoriteDestinations)
    {
        super(name, country, favoriteDestinations);
        this.airline = airline;
    }
}