package flightManagement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public interface searchStrategy
{
	void search(ArrayList<flight> flights);
}
class searchByPrice implements searchStrategy
{
	@Override
    public void search(ArrayList<flight> flights)
	{
		Collections.sort(flights, new Comparator<flight>()
		{
			@Override
			public int compare(flight flight1, flight flight2)
			{
				// Compare prices of flights
				return Double.compare(flight1.getPrice(), flight2.getPrice());
			}
		});
		System.out.println("\nflights by price:");
		for(flight flight : flights)
		{
			System.out.println(flight);
		}
	}
}
class searchByDate implements searchStrategy
{
	@Override
	public void search(ArrayList<flight> flights)
	{
		Collections.sort(flights, new Comparator<flight>()
		{
			@Override
			public int compare(flight flight1, flight flight2)
			{
				// Compare prices of flights
				int month1 = Integer.parseInt(flight1.getDate().split("/")[1]);
				int month2 = Integer.parseInt(flight2.getDate().split("/")[1]);
				if(month1 == month2)
				{
					int day1 = Integer.parseInt(flight1.getDate().split("/")[0]);
					int day2 = Integer.parseInt(flight2.getDate().split("/")[0]);
					return Integer.compare(day1, day2);
				}
				return Integer.compare(month1, month2);
			}
		});
		System.out.println("\nflights by date:");
		for(flight flight : flights)
		{
			System.out.println(flight);
		}
	}
}
class searchByTimeOfDeparture implements searchStrategy
{
	@Override
    public void search(ArrayList<flight> flights)
	{
		Collections.sort(flights, new Comparator<flight>()
		{
			@Override
			public int compare(flight flight1, flight flight2)
			{
				// Compare prices of flights
				int hour1 = Integer.parseInt(flight1.getDepartureTime().split(":")[0]);
				int hour2 = Integer.parseInt(flight2.getDepartureTime().split(":")[0]);
				if(hour1 == hour2)
				{
					int minutes1 = Integer.parseInt(flight1.getDepartureTime().split(":")[1]);
					int minutes2 = Integer.parseInt(flight2.getDepartureTime().split(":")[1]);
					return Integer.compare(minutes1, minutes2);
				}
				return Integer.compare(hour1, hour2);
			}
		});
		System.out.println("\nflights by time of departure:");
		for(flight flight : flights)
		{
			System.out.println(flight);
		}
	}
}
class searchByFlightDuration implements searchStrategy
{
	@Override
    public void search(ArrayList<flight> flights)
	{
		Collections.sort(flights, new Comparator<flight>()
		{
			@Override
			public int compare(flight flight1, flight flight2)
			{
				int hourDeparture1 = Integer.parseInt(flight1.getDepartureTime().split(":")[0]);
				int hourArrival1 = Integer.parseInt(flight1.getArrivalTime().split(":")[0]);
				int durationHours1 = hourArrival1 - hourDeparture1;
				if(durationHours1 < 0)
				{
					durationHours1 = 24 - hourDeparture1 + hourArrival1;
				}
				int hourDeparture2 = Integer.parseInt(flight2.getDepartureTime().split(":")[0]);
				int hourArrival2 = Integer.parseInt(flight2.getArrivalTime().split(":")[0]);
				int durationHours2 = hourArrival2 - hourDeparture2;
				if(durationHours2 < 0)
				{
					durationHours2 = 24 - hourDeparture2 + hourArrival2;
				}
				if(durationHours1 == durationHours2)
				{
					int minutesDeparture1 = Integer.parseInt(flight1.getDepartureTime().split(":")[1]);
					int minutesArrival1 = Integer.parseInt(flight1.getArrivalTime().split(":")[1]);
					int durationMinutes1 = minutesArrival1 - minutesDeparture1;
					if(durationMinutes1 < 0)
					{
						durationMinutes1 = 60 - minutesDeparture1 + minutesArrival1;
					}
					int minutesDeparture2 = Integer.parseInt(flight2.getDepartureTime().split(":")[1]);
					int minutesArrival2 = Integer.parseInt(flight2.getArrivalTime().split(":")[1]);
					int durationMinutes2 = minutesArrival2 - minutesDeparture2;
					if(durationMinutes2 < 0)
					{
						durationMinutes2 = 60 - minutesDeparture2 + minutesArrival2;
					}
					return Integer.compare(durationMinutes1, durationMinutes2);
				}
				// Compare prices of flights
				return Integer.compare(durationHours1, durationHours2);
			}
		});
		System.out.println("\nflights by duration:");
		for(flight flight : flights)
		{
			System.out.println(flight);
		}
	}
}
class SearchFlight
{
	private searchStrategy searchStrategy;
	public void setSearchStrategy(searchStrategy searchStrategy)
	{
		this.searchStrategy = searchStrategy;
	}
	public void checkFlights(airline airline, ArrayList<flight> flights)
	{
		for(airlines item: airline.getSubsidiaries())
		{
			if(item instanceof flight)
			{
				flights.add((flight)item);
			}
			else if(item instanceof airline)
			{
				checkFlights((airline)item, flights);
			}
		}
	}
	public void search(airline root)
	{
		ArrayList<flight> flights = new ArrayList<>();
		checkFlights(root,flights);
		searchStrategy.search(flights);
	}
}
