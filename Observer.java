package flightManagement;

import java.util.ArrayList;

public abstract class Observer
{
    ArrayList<String> notifications;
    public Observer()
    {
        notifications = new ArrayList<>();
    }
    public void update(String message)
    {
        notifications.add(message);
    }
}
abstract class Subject
{
    static ArrayList<Observer> observers;
    public Subject()
    {
        observers = new ArrayList<>();
    }
    public void addObserver(Observer observer)
    {
        if(!observers.contains(observer))
        {
            observers.add(observer);
        }
    }
    public void removeObserver(Observer observer)
    {
        if(observers.contains(observer))
        {
            observers.remove(observer);
        }
    }
    public void notifyObservers(String message)
    {
        for(Observer observer : observers)
        {
            observer.update(message);
        }
    }
}
