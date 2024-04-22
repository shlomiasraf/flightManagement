package flightManagement;

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
        if(!airline.getSubsidiaries().contains(subsidiary))
        {
            airline.add(subsidiary);
        }
    }
    public void AddFlightToAirline(Airline airline, Flight flight)
    {
        if(!airline.getSubsidiaries().contains(flight))
        {
            airline.add(flight);
        }
    }
    public void signIn(Traveler traveler)
    {
        if(!observers.contains(traveler) && observers.size() < 100)
        {
            addObserver(traveler);
        }
    }
    public void signOut(Traveler traveler)
    {
        if(!observers.isEmpty() && observers.contains(traveler))
        {
            removeObserver(traveler);
        }
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
        System.out.println("\n" + traveler.getName() + " notifications:");
        for(String notification : traveler.notifications)
        {
            System.out.println(notification);
        }
    }
    public void removeFlight(Airline airline,Flight flight)
    {
        if(airline.getSubsidiaries().contains(flight))
        {
            airline.remove(flight);
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
    public void removeSubsidiary(Airline airline,Airline subsidiary)
    {
        if(airline.getSubsidiaries().contains(subsidiary))
        {
            airline.remove(subsidiary);
            for (Observer observer : observers)
            {
                if(observer instanceof Traveler)
                {
                    Traveler traveler = (Traveler) observer;
                    String notification = "Airline: " + subsidiary.getName() + " stop working with: " + airline.getName() + " for now";
                    traveler.notify(notification);
                }
            }
        }
    }
    public void flightSetPrice(Flight flight, double newPrice)
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
    public void flightSetDepartureTime(Flight flight, String newDepartureTime)
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
    public void flightSetDate(Flight flight, String newDate)
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
    public void setFlightPriceForWorkers(Airline airline, Flight flight, double newPrice)
    {
        if(newPrice < flight.getPriceForWorkers())
        {
            for (Observer observer : observers)
            {
                if (observer instanceof Worker)
                {
                    Worker worker = (Worker) observer;
                    if (worker.getAirline().equals(airline) && airline.getSubsidiaries().contains(flight))
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
