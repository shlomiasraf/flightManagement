package flightManagement;

public class flightSystem
{
    public static void main(String[] args)
    {
        //create the root of the airlines:
        airline root = new airline("Root");
        //create the big airlines and connect to the root:
        airline lufthansa = new airline("Lufthansa");
        root.add(lufthansa);
        airline turkishAirlines = new airline("Turkish Airlines");
        root.add(turkishAirlines);
        //create the subsidiaries of lufthansa:
        airline swiss = new airline("Swiss");
        airline eurowings = new airline("Eurowings");
        lufthansa.add(swiss);
        lufthansa.add(eurowings);
        //create the subsidiaries of Turkish Airlines:
        airline AirAlbania = new airline("Air Albania");
        airline SunExpress = new airline("SunExpress");
        turkishAirlines.add(AirAlbania);
        turkishAirlines.add(SunExpress);
        //add flights to these airlines:
        flight flight1 = new flight("A37","London", "Paris", "17/8", "14:25", "17:30", 1000);
        swiss.add(flight1);
        flight flight2 = new flight("A40","Madrid", "Berlin", "20/8", "18:30", "20:30", 1500);
        lufthansa.add(flight2);
        flight flight3 = new flight("A45","Istanbul", "Roma", "22/8", "16:40", "22:50", 2500);
        AirAlbania.add(flight3);
        flight flight4 = new flight("A52","Lisbon", "Tokyo", "25/7", "18:30", "13:00", 2500);
        SunExpress.add(flight4);

        //Sort the flights:
        SearchFlight flightsBy = new SearchFlight();
        flightsBy.setSearchStrategy(new searchByPrice());
        flightsBy.search(root);
        flightsBy.setSearchStrategy(new searchByFlightDuration());
        flightsBy.search(root);
        flightsBy.setSearchStrategy(new searchByTimeOfDeparture());
        flightsBy.search(root);
        flightsBy.setSearchStrategy(new searchByDate());
        flightsBy.search(root);
        //add notifications to Workers and Customers based on their sources and their favorite destinations and print them:
        Customer user1 = new Customer("John", "Madrid", new String[]{"Berlin", "Roma", "Tokyo"});
        Customer user2 = new Customer("Mary", "London", new String[]{"Lisbon", "Paris"});
        Worker user3 = new Worker("David", AirAlbania, "Lisbon", new String[]{"Tokyo", "Paris"});
        root.registerTravelers(user1);
        root.registerTravelers(user2);
        root.registerTravelers(user3);
        root.notifyObservers("The notification system will not work on 16/8 from 16:00-18:00");
        swiss.flightSetPrice(flight1,500);
        swiss.remove(flight2);
        swiss.flightSetDepartureTime(flight1,"17:25");
        SunExpress.flightSetDate(flight4,"19/8");
        AirAlbania.setFlightPriceForWorkers(flight4, 600);
        user1.printNotifications();
        user2.printNotifications();
        user3.printNotifications();
        root.removeObserver(user3);
    }
}
