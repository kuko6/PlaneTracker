# PlaneTracker
**Jakub Povinec**

## Pracovná verzia
V tejto verzií som sa sústredil na vytvorenie grafického rozhrania, hlavne zobrazenia informácií o letiskách a lietadlách.

**V aplikácií sa zatiaľ dajú:**
- zobraziť prílety a odlety zvoleného letiska
- pridať nové lietadlo so štartom vo zvolenom letisku
- dajú sa zobraziť všetky lietadlá
- po dvojkliku na lietadlo v tabuľkách sa o ňom zobrazia informácie

**agregácia** sa nachádza v triede `Airport`, kde jedno letisko môže mať lietadlá, ktoré doň smerujú alebo z neho odchádzajú. Ak by letisko zaniklo, tak nezaniknú lietadlá.

**kompozícia** sa nachádza v triede `Plane`, kde jedno lietadlo má práve jednu dráhu letu `FlightPath`, ktorá zanikne spolu s lietadlom.

**dedenie** sa nachádza v triedach `Airbus`, `Boeing` a `Cessna`, ktoré dedia z abstraktnej triedy `Plane`. Ďalej sa **dedenie** taktiež nachádza v triedach `MapController` a `AddPlaneController`, ktoré dedia z abstraktnej triedy `Serialization`.

jednoduchý **polymorfizmus** sa nachádza v triede `PlaneInfoController`, kde sa zavolá metóda `plane.getManufacturer`, na ktorú rôzne podtriedy triedy `Plane` reaguju iným výpisom. Premennú `manufacturer` má každá trieda "na pevno" danú.


## Finálna verzia
Vo finálnej verzií sa mi podarilo dokončiť simuláciu letu lietadla, v aplikácií je na mape vidno lietadlá a po kliknutí na nich aj ich dráhu. Taktiež sa mi podarilo pridať registráciu nových používateľov a vytvoril som aj pár vlastných výnimiek. Nepodarilo sa mi ale dokončiť generovanie textového súboru. Veľa času mi zabrala práve simulácia letu lietadiel a ich vykresľovanie a keďže som už mal málo času, rozhodol som sa, že túto funkcionalitu z môjho riešenia vypustím. 

Myslím, že tú podstatnú časť zadania, ktorú som si určil nazačiatku v zámere projektu som splnil. Jediná funkcionalita, ktorá chýba je generovanie textových súborov a rozlišovanie medzi normálnym používateľom a administrátorom. 

### Hlavné kritéria
**dedenie** sa nachádza v triedach `Airbus`, `Boeing` a `Cessna`, ktoré dedia z abstraktnej triedy `Plane`. Ďalej sa **dedenie** taktiež nachádza v triedach `LoginScreenController`, `MapController` a `RegisterController`, ktoré dedia z abstraktnej triedy `Serialization`.

**agregácia** sa napríklad nachádza v triede `Airport`, kde jedno letisko môže mať lietadlá, ktoré doň smerujú alebo z neho odchádzajú. Ak by letisko zaniklo, tak nezaniknú lietadlá.
[agregácia](https://github.com/OOP-FIIT/oop-2020-str-18-a-valach-kuko6/blob/bcaad9892eeb80d41e1ee5e6328623cf95b32816/src/model/Airport.java#L22-L23)

**kompozícia** sa nachádza v triede `Plane`, kde jedno lietadlo má práve jednu dráhu letu `FlightPath`, ktorá zanikne spolu s lietadlom.
[kompozícia](https://github.com/OOP-FIIT/oop-2020-str-18-a-valach-kuko6/blob/bcaad9892eeb80d41e1ee5e6328623cf95b32816/src/model/planes/Plane.java#L43)

**polymorfizmus** sa nachádza v triede `PlaneInfoController`, kde sa zavolá metóda `plane.getManufacturer`, na ktorú rôzne podtriedy triedy `Plane` reaguju iným výpisom. Premennú `manufacturer` má každá trieda "na pevno" danú.
[polymorfizmus uplatnenie](https://github.com/OOP-FIIT/oop-2020-str-18-a-valach-kuko6/blob/bcaad9892eeb80d41e1ee5e6328623cf95b32816/src/controller/PlaneInfoController.java#L133) , [podtrieda Plane](https://github.com/OOP-FIIT/oop-2020-str-18-a-valach-kuko6/blob/bcaad9892eeb80d41e1ee5e6328623cf95b32816/src/model/planes/Airbus.java#L14)


Ďalej sa **polymorfizmus** ešte nachádza v triede `Airport` v metóde `updatePlane`, kde sa volajú metódy `plane.ascend`, `plane.descend`, na ktoré reagujú podriedy triedy `Plane` inak.
[polymorfizmus uplatnenie2](https://github.com/OOP-FIIT/oop-2020-str-18-a-valach-kuko6/blob/bcaad9892eeb80d41e1ee5e6328623cf95b32816/src/model/Airport.java#L85),
[podtrieda Plane](https://github.com/OOP-FIIT/oop-2020-str-18-a-valach-kuko6/blob/bcaad9892eeb80d41e1ee5e6328623cf95b32816/src/model/planes/Airbus.java#L59-L80)


### Vedľajšie kritéria
použil som **návrhový vzor** mediator, ktorý som uplatnil pri aktualizovaní stavu lietadla. Každé lietadlo komunikuje so svojim letiskom `start`. Toto letisko im následne môže v metóde `updatePlane` zmeniť stav, či majú stúpať, klesať, pristávať alebo len tak letieť.
[airport](https://github.com/OOP-FIIT/oop-2020-str-18-a-valach-kuko6/blob/bcaad9892eeb80d41e1ee5e6328623cf95b32816/src/model/Airport.java#L85),
[plane](https://github.com/OOP-FIIT/oop-2020-str-18-a-valach-kuko6/blob/bcaad9892eeb80d41e1ee5e6328623cf95b32816/src/model/planes/Plane.java#L149-L154)

**vlastné výnimky** som uplatnil pri serializácií v triede `Serialization`, kde sa keď je súbor `user.txt` prázdny vyhodí výnimka [NoRegisteredUsersException](https://github.com/OOP-FIIT/oop-2020-str-18-a-valach-kuko6/blob/bcaad9892eeb80d41e1ee5e6328623cf95b32816/src/controller/abstracts/Serialization.java#L120),
[chytanie](https://github.com/OOP-FIIT/oop-2020-str-18-a-valach-kuko6/blob/bcaad9892eeb80d41e1ee5e6328623cf95b32816/src/controller/LoginScreenController.java#L118-L124).

Druhú výnimku som uplatnil v rozhraní `PlaneInfo`, kde sa keď používateľ zvolí prázdnu bunku v `TableView` vyhodí výnimka [BlankTableException](https://github.com/OOP-FIIT/oop-2020-str-18-a-valach-kuko6/blob/bcaad9892eeb80d41e1ee5e6328623cf95b32816/src/controller/abstracts/PlaneInfo.java#L41-L45).

**multithreading** som využil v triedach [AirTraffic](https://github.com/OOP-FIIT/oop-2020-str-18-a-valach-kuko6/blob/bcaad9892eeb80d41e1ee5e6328623cf95b32816/src/controller/AirTraffic.java#L22) a [PlaneInfoController](https://github.com/OOP-FIIT/oop-2020-str-18-a-valach-kuko6/blob/bcaad9892eeb80d41e1ee5e6328623cf95b32816/src/controller/PlaneInfoController.java#L91).

**RTTI** som využil v [MapController](https://github.com/OOP-FIIT/oop-2020-str-18-a-valach-kuko6/blob/bcaad9892eeb80d41e1ee5e6328623cf95b32816/src/controller/MapController.java#L64),
ďalej v triede [AirTraffic](https://github.com/OOP-FIIT/oop-2020-str-18-a-valach-kuko6/blob/bcaad9892eeb80d41e1ee5e6328623cf95b32816/src/controller/AirTraffic.java#L90) a
v triede [PlaneRenderer](https://github.com/OOP-FIIT/oop-2020-str-18-a-valach-kuko6/blob/bcaad9892eeb80d41e1ee5e6328623cf95b32816/src/view/PlaneRenderer.java#L64).

použitie **vzhniezdených rozhraní** v triede [AirTraffic](https://github.com/OOP-FIIT/oop-2020-str-18-a-valach-kuko6/blob/bcaad9892eeb80d41e1ee5e6328623cf95b32816/src/controller/AirTraffic.java#L22),
[AirportInfoController](https://github.com/OOP-FIIT/oop-2020-str-18-a-valach-kuko6/blob/bcaad9892eeb80d41e1ee5e6328623cf95b32816/src/controller/AirportInfoController.java#L29),
[PlaneListController](https://github.com/OOP-FIIT/oop-2020-str-18-a-valach-kuko6/blob/bcaad9892eeb80d41e1ee5e6328623cf95b32816/src/controller/PlaneListController.java#L25).  

použitie **lambda výrazov a referencií na metódy**, lambda výrazy som použil skoro všade, ale napríklad v triede `AirTraffic` som použil aj [lambda](https://github.com/OOP-FIIT/oop-2020-str-18-a-valach-kuko6/blob/bcaad9892eeb80d41e1ee5e6328623cf95b32816/src/controller/AirTraffic.java#L88) výrazy aj [referencie](https://github.com/OOP-FIIT/oop-2020-str-18-a-valach-kuko6/blob/bcaad9892eeb80d41e1ee5e6328623cf95b32816/src/controller/AirTraffic.java#L78) na metódy.

**implicitné implementácie metód v rozhraniach** som použil v rozhraniach [Controller](https://github.com/OOP-FIIT/oop-2020-str-18-a-valach-kuko6/blob/bcaad9892eeb80d41e1ee5e6328623cf95b32816/src/controller/abstracts/Controller.java#L30) a [PlaneInfo](https://github.com/OOP-FIIT/oop-2020-str-18-a-valach-kuko6/blob/bcaad9892eeb80d41e1ee5e6328623cf95b32816/src/controller/abstracts/PlaneInfo.java#L36).

**serializáciu** som použil v triedach [Serialization](https://github.com/OOP-FIIT/oop-2020-str-18-a-valach-kuko6/blob/bcaad9892eeb80d41e1ee5e6328623cf95b32816/src/controller/abstracts/Serialization.java#L18) a vo všetkých triedach, ktoré ju dedia
`LoginScreenController`, `RegisterController`, `MapController`.

## Todo
- [x] vedieť zobraziť prílety a odlety konkrétneho letiska
- [x] vedieť zobraziť všetky lietadlá
- [x] vedieť zobraziť informácie o lietadle
- [x] vedieť pridať lietadlo
- [x] simulácia letu lietadla
- [x] vedieť zobraziť lietadlo a jeho dráhu na mape
- [x] registrácia nových používateľov
- [ ] vedieť vygenerovať textový súbor s informáciami o lietadle/letisku
- [x] ošetriť výnimnky