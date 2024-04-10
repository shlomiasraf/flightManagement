package flightManagement;
import java.util.ArrayList;
import java.util.List;


public interface airlines
{
	String getName();
}

class flight implements airlines
{
    private String name;
    private String from;
    private String destination;
    private String date;
    private String departureTime;
    private String arrivalTime;
    private double price;
    private double priceForWorkers;

    public flight(String name,String from, String destination, String date, String departureTime, String arrivalTime, double price)
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
class airline extends Subject implements airlines
{

    private String airlineName;
    private List<airlines> subsidiaries;

    public airline(String airlineName) {
        this.airlineName = airlineName;
        this.subsidiaries = new ArrayList<>();
    }

    public void registerTravelers(Traveler traveler)
    {
        this.addObserver(traveler);
    }

    public List<airlines> getSubsidiaries()
    {
        return this.subsidiaries;
    }

    public void add(airlines subsidiary)
    {
        subsidiaries.add(subsidiary);
    }
    public void setFlightPriceForWorkers(flight flight, double newPrice)
    {
         if(newPrice < flight.getPriceForWorkers())
         {
             for (Observer observer : observers)
             {
                 if (observer instanceof Worker)
                 {
                     Worker worker = (Worker) observer;
                     if (worker.airline.equals(this))
                     {
                         if (worker.getCountry().equals(flight.getFrom()) && flightInDestinations(flight.getDestination(), worker.getFavoriteDestinations()))
                         {
                             String notification = "Flight: " + flight.getName() + " from: " + flight.getFrom() + " to: " + flight.getDestination() + " is on sale for workers! the price for you is: " + newPrice + "$";
                             worker.notify(notification);
                         }
                     }
                 }
             }
         }
        flight.setPriceForWorkers(newPrice);
    }
    public void remove(airlines subsidiary)
    {
        subsidiaries.remove(subsidiary);
        if (subsidiary instanceof flight)
        {
            flight flight = (flight) subsidiary;
            for (Observer observer : observers)
            {
                if(observer instanceof Traveler)
                {
                    Traveler traveler = (Traveler) observer;
                    if (traveler.getCountry().equals(flight.getFrom()) && flightInDestinations(flight.getDestination(), traveler.getFavoriteDestinations()))
                    {
                        String notification = "Flight: " + flight.getName() + " from: " + flight.getFrom() + " to: " + flight.getDestination() + " is cancelled";
                        traveler.notify(notification);
                    }
                }
            }
        }
    }
    public void flightSetPrice(flight flight, double newPrice)
    {
        if(newPrice < flight.getPrice())
        {
            for (Observer observer : observers)
            {
                if (observer instanceof Traveler)
                {
                    Traveler traveler = (Traveler) observer;
                    if (traveler.getCountry().equals(flight.getFrom()) && flightInDestinations(flight.getDestination(), traveler.getFavoriteDestinations()))
                    {
                        String notification = "Flight: " + flight.getName() + " from: " + flight.getFrom() + " to: " + flight.getDestination() + " is on sale! the new price is: " + newPrice + "$";
                        traveler.notify(notification);
                    }
                }
            }
        }
        flight.setPrice(newPrice);
    }
    public void flightSetDepartureTime(flight flight, String newDepartureTime)
    {
        flight.setDepartureTime(newDepartureTime);
        for (Observer observer : observers) {
            if (observer instanceof Traveler)
            {
                Traveler traveler = (Traveler) observer;
                if (traveler.getCountry().equals(flight.getFrom()) && flightInDestinations(flight.getDestination(), traveler.getFavoriteDestinations())) {
                    String notification = "The new departure of: " + flight.getName() + " from: " + flight.getFrom() + " to: " + flight.getDestination() + " at: " + newDepartureTime;
                    traveler.notify(notification);
                }
            }
        }
    }
    public void flightSetDate(flight flight, String newDate)
    {
        flight.setDate(newDate);
        for (Observer observer : observers) {
            if (observer instanceof Traveler)
            {
                Traveler traveler = (Traveler) observer;
                if (traveler.getCountry().equals(flight.getFrom()) && flightInDestinations(flight.getDestination(), traveler.getFavoriteDestinations())) {
                    String notification = "The new date of: " + flight.getName() + " from: " + flight.getFrom() + " to: " + flight.getDestination() + " at: " + newDate;
                    traveler.notify(notification);
                }
            }
        }
    }

    @Override
    public String getName()
    {
        return airlineName;
    }
    public boolean flightInDestinations(String destination, String[] destinations)
    {
        for(String dest: destinations)
        {
            if(dest.equals(destination))
            {
                return true;
            }
        }
        return false;
    }
}
