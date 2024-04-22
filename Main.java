package flightManagement;

import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        //create the Management System.
        FlightManagementSystemFacade SkyScanner = FlightManagementSystemFacade.getInstance();
        //create the root of the Airlines
        Airline root = SkyScanner.getAirline("Root");
        //create the big Airlines and connect to the root:
        Airline lufthansa = SkyScanner.getAirline("Lufthansa");
        SkyScanner.AddSubsidiaryToAirline(root,lufthansa);
        Airline turkishAirlines = SkyScanner.getAirline("Turkish Airlines");
        SkyScanner.AddSubsidiaryToAirline(root,turkishAirlines);
        //create the subsidiaries of lufthansa:
        Airline swiss = SkyScanner.getAirline("Swiss");
        Airline eurowings = SkyScanner.getAirline("Eurowings");
        SkyScanner.AddSubsidiaryToAirline(lufthansa,swiss);
        SkyScanner.AddSubsidiaryToAirline(lufthansa,eurowings);
        //create the subsidiaries of Turkish Airlines:
        Airline AirAlbania = SkyScanner.getAirline("Air Albania");
        Airline SunExpress = SkyScanner.getAirline("SunExpress");
        SkyScanner.AddSubsidiaryToAirline(turkishAirlines,AirAlbania);
        SkyScanner.AddSubsidiaryToAirline(turkishAirlines,SunExpress);
        //add flights to these Airlines:
        Flight A37 = SkyScanner.getFlight("A37","London", "Paris", "17/8", "14:25", "17:30", 1000);
        SkyScanner.AddFlightToAirline(swiss, A37);
        Flight A40 = SkyScanner.getFlight("A40","Madrid", "Berlin", "20/8", "18:30", "20:30", 1500);
        SkyScanner.AddFlightToAirline(lufthansa, A40);
        Flight A45 = SkyScanner.getFlight("A45","Istanbul", "Roma", "22/8", "16:40", "22:50", 2500);
        SkyScanner.AddFlightToAirline(AirAlbania, A45);
        Flight A52 = SkyScanner.getFlight("A52","Lisbon", "Tokyo", "25/7", "18:30", "13:00", 2500);
        SkyScanner.AddFlightToAirline(SunExpress, A52);
        //Sort the flights that connected to the root:

        SkyScanner.sortByPrice(root);
        SkyScanner.sortByFlightDuration(root);
        SkyScanner.sortByTimeOfDeparture(root);

        //Sort the flights that connected to the Lufthansa by date:
        SkyScanner.sortByDate(lufthansa);

        //create the users and register them to the notification system.
        Customer John = SkyScanner.getCustomer("John", 233678901 ,"Madrid", new String[]{"Berlin", "Roma", "Tokyo"});
        Customer Mary = SkyScanner.getCustomer("Mary", 466777891,"London", new String[]{"Lisbon", "Paris"});
        Worker David = SkyScanner.getWorker("David",566789132, AirAlbania, "Istanbul", new String[]{"Tokyo", "Roma"});
        SkyScanner.signIn(John);
        SkyScanner.signIn(Mary);
        SkyScanner.signIn(David);

        //add notifications to Workers and Customers based on their country and their favorite destinations:
        SkyScanner.notifyObservers("The notification system will not work on 16/8 from 16:00-18:00");
        SkyScanner.flightSetPrice(A37,500);
        SkyScanner.removeSubsidiary(lufthansa,eurowings);
        SkyScanner.removeFlight(lufthansa,A40);
        SkyScanner.flightSetDepartureTime(A45,"17:25");
        SkyScanner.flightSetDate(A37,"19/8");
        SkyScanner.setFlightPriceForWorkers(AirAlbania,A45,600);

        //print the notifications of these users:
        SkyScanner.printNotifications(John);
        SkyScanner.printNotifications(Mary);
        SkyScanner.printNotifications(David);
        SkyScanner.signOut(David);

        //try to create Worker with id that already exists with other details.
        Worker Aviv = SkyScanner.getWorker("Aviv",566789132, AirAlbania, "Istanbul", new String[]{"Berlin", "Roma"});
        //try to create Flight with name that already exists in the same date.
        Flight A55 = SkyScanner.getFlight("A52","Lisbon", "Berlin", "25/7", "18:30", "13:00", 2500);
    }
}
