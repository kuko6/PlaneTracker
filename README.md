# PlaneTracker
Simple plane tracker application which allows you to track simulated planes on the map and see their flight paths.

## WIP version
In this version, I focused on creating a graphical interface for displaying information about airports and airplanes.

**At this point, the app could:**
- display arrivals and departures of the selected airport
- add a new plane departure from the selected airport
- displayed all planes current planes
- after double-clicking on the plane in the table, information about it will be displayed

**aggregation** is found in the `Airport` class, where a single airport can have planes going to or departing from it. If the airport were to disappear, the airplanes would not disappear.

**composition** is located in the `Plane` class, where one plane has exactly one flight path `FlightPath`, which disappears with the plane.

**inheritance** is found in the classes `Airbus`, `Boeing` and `Cessna`, which inherit from the abstract class `Plane`. Furthermore, **inheritance** is also found in the `MapController` and `AddPlaneController` classes, which inherit from the `Serialization` abstract class.

simple **polymorphism** is found in the `PlaneInfoController` class, where the `plane.getManufacturer` method is called, to which different subclasses of the `Plane` class respond with a different statement. Each class has a ``manufacturer'' variable.


## Final version
In the final versions, I managed to complete the simulation of the planes (flying), in the application you can see the planes on the map and, after clicking on them, also their path. I also managed to add new user registration and created a few custom exceptions.

I think that I have fulfilled the essential part of the task that I set for myself at the beginning in the purpose of the project. The only functionality that is missing is the generation of text files and the distinction between a normal user and an administrator.

### Main criteria
**inheritance** is found in the classes `Airbus`, `Boeing` and `Cessna`, which inherit from the abstract class `Plane`. Furthermore, **inheritance** is also found in the `LoginScreenController`, `MapController` and `RegisterController` classes, which inherit from the `Serialization` abstract class.

For example, **aggregation** is found in the `Airport` class, where a single airport can have planes going to or departing from it. If the airport were to disappear, the airplanes would not disappear.
[aggregation](https://github.com/OOP-FIIT/oop-2020-str-18-a-valach-kuko6/blob/bcaad9892eeb80d41e1ee5e6328623cf95b32816/src/model/Airport.java#L22-L23)

The **composition** is located in the `Plane` class, where one plane has exactly one flight path `FlightPath`, which disappears with the plane.
[composite](https://github.com/OOP-FIIT/oop-2020-str-18-a-valach-kuko6/blob/bcaad9892eeb80d41e1ee5e6328623cf95b32816/src/model/planes/Plane.java#L43)

The **polymorphism** is found in the `PlaneInfoController` class, where the `plane.getManufacturer` method is called, to which different subclasses of the `Plane` class respond with different statements. Each class has a `manufacturer` variable.
[polymorphism implementation](https://github.com/OOP-FIIT/oop-2020-str-18-a-valach-kuko6/blob/bcaad9892eeb80d41e1ee5e6328623cf95b32816/src/controller/PlaneInfoController.java#L133) , [subclass Plane] (https://github.com/OOP-FIIT/oop-2020-str-18-a-valach-kuko6/blob/bcaad9892eeb80d41e1ee5e6328623cf95b32816/src/model/planes/Airbus.java#L14)


Furthermore, **polymorphism** is still found in the `Airport` class in the `updatePlane` method, where the `plane.ascend`, `plane.descend` methods are called, to which subclasses of the `Plane` class react differently.
[polymorphism implementation2](https://github.com/OOP-FIIT/oop-2020-str-18-a-valach-kuko6/blob/bcaad9892eeb80d41e1ee5e6328623cf95b32816/src/model/Airport.java#L85),
[subclass Plane](https://github.com/OOP-FIIT/oop-2020-str-18-a-valach-kuko6/blob/bcaad9892eeb80d41e1ee5e6328623cf95b32816/src/model/planes/Airbus.java#L59-L80)


### Secondary criteria
I used a **design pattern** mediator that I applied when updating the aircraft status. Each plane communicates with its `start` airport. This airport can subsequently change their status in the `updatePlane` method, whether they should climb, descend, land or just fly.
[airport](https://github.com/OOP-FIIT/oop-2020-str-18-a-valach-kuko6/blob/bcaad9892eeb80d41e1ee5e6328623cf95b32816/src/model/Airport.java#L85),
[plane](https://github.com/OOP-FIIT/oop-2020-str-18-a-valach-kuko6/blob/bcaad9892eeb80d41e1ee5e6328623cf95b32816/src/model/planes/Plane.java#L149-L154)

I used **custom exceptions** for serializations in the `Serialization` class, where when the `user.txt` file is empty, the exception [NoRegisteredUsersException](https://github.com/OOP-FIIT/oop-2020-str-18-a-valach-kuko6/blob/bcaad9892eeb80d41e1ee5e6328623cf95b32816/src/controller/abstracts/Serialization.java#L120),
[catch](https://github.com/OOP-FIIT/oop-2020-str-18-a-valach-kuko6/blob/bcaad9892eeb80d41e1ee5e6328623cf95b32816/src/controller/LoginScreenController.java#L118-L124).

I used the second exception in the interface `PlaneInfo`, where when the user selects an empty cell in the `TableView`, an exception [BlankTableException](https://github.com/OOP-FIIT/oop-2020-str-18-a-valach-kuko6/blob/bcaad9892eeb80d41e1ee5e6328623cf95b32816/src/controller/abstracts/PlaneInfo.java#L41-L45).

I used **multithreading** in the classes [AirTraffic](https://github.com/OOP-FIIT/oop-2020-str-18-a-valach-kuko6/blob/bcaad9892eeb80d41e1ee5e6328623cf95b32816/src/controller/AirTraffic.java#L22) and [PlaneInfoController](https://github.com/OOP-FIIT/oop-2020-str-18-a-valach-kuko6/blob/bcaad9892eeb80d41e1ee5e6328623cf95b32816/src/controller/PlaneInfoController.java#L91).

I used **RTTI** in [MapController](https://github.com/OOP-FIIT/oop-2020-str-18-a-valach-kuko6/blob/bcaad9892eeb80d41e1ee5e6328623cf95b32816/src/controller/MapController.java#L64),
further in the class [AirTraffic](https://github.com/OOP-FIIT/oop-2020-str-18-a-valach-kuko6/blob/bcaad9892eeb80d41e1ee5e6328623cf95b32816/src/controller/AirTraffic.java#L90) and
in class [PlaneRenderer](https://github.com/OOP-FIIT/oop-2020-str-18-a-valach-kuko6/blob/bcaad9892eeb80d41e1ee5e6328623cf95b32816/src/view/PlaneRenderer.java#L64).

use of **nested interfaces** in the [AirTraffic](https://github.com/OOP-FIIT/oop-2020-str-18-a-valach-kuko6/blob/bcaad9892eeb80d41e1ee5e6328623cf95b32816/src/controller/AirTraffic.java#L22),
[AirportInfoController](https://github.com/OOP-FIIT/oop-2020-str-18-a-valach-kuko6/blob/bcaad9892eeb80d41e1ee5e6328623cf95b32816/src/controller/AirportInfoController.java#L29),
[PlaneListController](https://github.com/OOP-FIIT/oop-2020-str-18-a-valach-kuko6/blob/bcaad9892eeb80d41e1ee5e6328623cf95b32816/src/controller/PlaneListController.java#L25).

use of **lambda expressions and method references**, I used lambda expressions almost everywhere, for example in `AirTraffic` class I also used [lambda](https://github.com/OOP-FIIT/oop-2020-str-18-a-valach-kuko6/blob/bcaad9892eeb80d41e1ee5e6328623cf95b32816/src/controller/AirTraffic.java#L88) expressions and [references](https://github.com/OOP-FIIT/oop-2020-str-18-a-valach-kuko6/blob/bcaad9892eeb80d41e1ee5e6328623cf95b32816/src/controller/AirTraffic.java#L78) to methods.

I used **implicit method implementations in interfaces** in [Controller](https://github.com/OOP-FIIT/oop-2020-str-18-a-valach-kuko6/blob/bcaad9892eeb80d41e1ee5e6328623cf95b32816/src/controller/abstracts/Controller.java#L30) and [PlaneInfo](https://github.com/OOP-FIIT/oop-2020-str-18-a-valach-kuko6/blob/bcaad9892eeb80d41e1ee5e6328623cf95b32816/src/controller/abstracts/PlaneInfo.java#L36).

I used **serialization** in [Serialization](https://github.com/OOP-FIIT/oop-2020-str-18-a-valach-kuko6/blob/bcaad9892eeb80d41e1ee5e6328623cf95b32816/src/controller/abstracts/Serialization.java#L18) and in all classes that inherit it
`LoginScreenController`, `RegisterController`, `MapController`.

## Todo
- [x] be able to display the arrivals and departures of a specific airport
- [x] be able to display all aircraft
- [x] be able to display information about the aircraft
- [x] be able to add an aircraft
- [x] aircraft flight simulation
- [x] be able to display the plane and its path on the map
- [x] registration of new users
- [ ] be able to generate a text file with information about the aircraft/airport
- [x] handle exceptions
