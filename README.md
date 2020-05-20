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





## Todo
- [x] vedieť zobraziť prílety a odlety konkrétneho letiska
- [x] vedieť zobraziť všetky lietadlá
- [x] vedieť zobraziť informácie o lietadle
- [x] vedieť pridať lietadlo
- [x] simulácia letu lietadla
- [ ] vedieť zobraziť lietadlo a jeho dráhu na mape
- [x] registrácia nových používateľov
- [ ] vedieť vygenerovať textový súbor s informáciami o lietadle/letisku
- [ ] ošetriť výnimnky