package flightManagement;

import java.util.ArrayList;

public class FlightManagementSystemFacade extends Subject
{
    private static FlightManagementSystemFacade instance;
    private SearchFlight flightsBy = new SearchFlight();

    // Static method to get the singleton instance
    public static FlightManagementSystemFacade getInstance()
    {
        if (instance == null)
        {
            instance = new FlightManagementSystemFacade();
        }
        return instance;
    }
    public void AddSubsidiaryToAirline(Airline airline, Airline subsidiary)
    {
        airline.add(subsidiary);
    }
    public void AddFlightToAirline(Airline airline, Flight flight)
    {
        airline.add(flight);
    }
    public void signIn(Traveler traveler)
    {
        addObserver(traveler);
    }
    public void signOut(Traveler traveler)
    {
        removeObserver(traveler);
    }
    public Flight getFlight(String name, String from, String destination, String date, String departureTime, String arrivalTime, double price)
    {
        return FlightFactory.getFlight(name, from, destination, date, departureTime, arrivalTime, price);
    }
    public Airline getAirline(String name)
    {
        return AirlineFactory.getAirline(name);
    }
    public Customer getCustomer(String name, int id, String country, String[] favoriteDestinations)
    {
        return CustomerFactory.getCustomer(name, id, country, favoriteDestinations);
    }
    public Worker getWorker(String name, int id, Airline airline, String country, String[] favoriteDestinations)
    {
        return WorkerFactory.getWorker(name, id, airline, country, favoriteDestinations);
    }
    public void sortByPrice(Airline root)
    {
        flightsBy.setSearchStrategy(new searchByPrice());
        flightsBy.search(root);
    }
    public void sortByFlightDuration(Airline root)
    {
        flightsBy.setSearchStrategy(new searchByFlightDuration());
        flightsBy.search(root);
    }
    public void sortByTimeOfDeparture(Airline root)
    {
        flightsBy.setSearchStrategy(new searchByTimeOfDeparture());
        flightsBy.search(root);
    }
    public void sortByDate(Airline root)
    {
        flightsBy.setSearchStrategy(new searchByDate());
        flightsBy.search(root);
    }
    public void printNotifications(Traveler traveler)
    {
        traveler.printNotifications();
    }
    public void removeFlight(Airline airline,Flight flight)
    {
        if(airline.remove(flight))
        {
            ArrayList<Observer> RelevantObservers = flight.flightRelevantObservers(observers);
            String notification = "Flight: " + flight.getName() + " from: " + flight.getFrom() + " to: " + flight.getDestination() + " is cancelled";
            notifyRelevantObservers(RelevantObservers,notification);
        }
    }
    public void removeSubsidiary(Airline airline,Airline subsidiary)
    {
        if(airline.remove(subsidiary))
        {
            String notification = "Airline: " + subsidiary.getName() + " stop working with: " + airline.getName() + " for now";
            notifyObservers(notification);
        }
    }
    public void flightSetPrice(Flight flight, double newPrice)
    {
        if(newPrice < flight.getPrice())
        {
            ArrayList<Observer> RelevantObservers = flight.flightRelevantObservers(observers);
            String notification = "Flight: " + flight.getName() + " from: " + flight.getFrom() + " to: " + flight.getDestination() + " is on sale! the new price is: " + newPrice + "$";
            notifyRelevantObservers(RelevantObservers,notification);
        }
        flight.setPrice(newPrice);
    }
    public void flightSetDepartureTime(Flight flight, String newDepartureTime)
    {
        flight.setDepartureTime(newDepartureTime);
        ArrayList<Observer> RelevantObservers = flight.flightRelevantObservers(observers);
        String notification = "The new departure of: " + flight.getName() + " from: " + flight.getFrom() + " to: " + flight.getDestination() + " at: " + newDepartureTime;
        notifyRelevantObservers(RelevantObservers,notification);
    }
    public void flightSetDate(Flight flight, String newDate)
    {
        flight.setDate(newDate);
        ArrayList<Observer> RelevantObservers = flight.flightRelevantObservers(observers);
        String notification = "The new date of: " + flight.getName() + " from: " + flight.getFrom() + " to: " + flight.getDestination() + " at: " + newDate;
        notifyRelevantObservers(RelevantObservers,notification);
    }
    public void setFlightPriceForWorkers(Airline airline, Flight flight, double newPrice)
    {
        if(newPrice < flight.getPriceForWorkers())
        {
            ArrayList<Observer> RelevantObservers = flight.WorkersflightRelevantObservers(airline, observers);
            String notification = "Flight: " + flight.getName() + " from: " + flight.getFrom() + " to: " + flight.getDestination() + " is on sale for workers! the price for you is: " + newPrice + "$";
            notifyRelevantObservers(RelevantObservers,notification);
        }
        flight.setPriceForWorkers(newPrice);
    }
}
