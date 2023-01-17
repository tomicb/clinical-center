# eKlinika

#### Projekat implementirao tročlani tim

### Predmet - Upravljanje projektima, Informaciona bezbednost (2021)

#### Korišćene tehnologije: JavaScript, React, Java, Spring Boot, Spring Security, JPA, MySQL

Sistem kliničkog centra omogućava pacijentima zakazivanje pregleda. Pristup sistemu imaju medicinske sestre i lekari koji mogu da unose izveštaje o izvršenim pregledima. Klinički centar se sastoji iz više usko specijalizovanih klinika koje su registrovane u okviru informacionog sistema. Osnovna namena aplikacije je vođenje evidencije o zaposlenima, registrovanim klinikama, pacijentima i njihovim zdravstvenim kartonima, kao i zakazivanje pregleda.

##

### Deo specifikacije projekta za koji sam bio zadužen

#### Postupak unošenja izveštaja o pregledu
Lekar iz svog radnog kalendara bira pregled koji se započinje. Dok pregled traje, lekar može u slobodnoj formi da unese informacije o pregledu i unese dijagnozu. Lekar može dodatno da unosi recepte koje medicinska sestra na kraju pregleda mora da overi. Nakon popunjenog izveštaja, sve promene koje su unete od strane medicinske sestre ili lekara, trajno se beleže u zdravstveni karton pacijenta pod istorijom bolesti.

#### Postupak overe recepta
Nakon što lekar završi pregled, medicinska sestra treba da overi recepte koje je lekar propisao pacijentu. Recept se overava jednim klikom. Sistem ima informaciju koja medicinska sestra je overila recept.

#### Grafički prikaz radnog kalendara
Lekar na svom profilu ima opciju da pregleda svoj radni kalendar na nedeljnom, mesečnom i godišnjem nivou. Potrebno je za svaki dan u mesecu da se prikažu svi pregledi, a za svaki pregled vreme početka, trajanje i proizvoljne dodatne informacije o istom, kao na primer ime i prezime pacijenta.

#### Prijava na sistem bez upotrebe lozinke
Korisnik može da odabere opciju da se prijavi na sistem samo uz pomoć email adrese (tzv. •passwordless login) • . Nakon unosa email adrese, šalje se zahtev pri čemu je na serverskoj strani potrebno generisati jednokratni token sa periodom važenja od maksimalno 10 minuta. Taj token se kao deo “magičnog linka” šalje korisniku na njegovu email adresu. Korisnik ima 10 minuta da otvori email i poseti link. Klikom na link, na serveru se proverava ispravnost tokena. Ukoliko je token ispravan, server će za autentifikaciju korisnika izgenerisati novi par refresh i access tokena. Voditi računa o tome da se jednom generisani link ne može posetiti više puta.

#### Osvežavanje tokena
Access token ima period važenja od 15 minuta. Ukoliko je access token istekao, a ispravno je generisan i potpisan, korisnik može da pošalje novi zahtev za osvežavanje access tokena, pri čemu će se proveriti identitet tog korisnika na osnovu refresh tokena. Ukoliko u sistemu postoji takav korisnik i on nije blokiran, potrebno je izgenerisati novi access token, koji će se poslati kao odgovor na klijentski deo aplikacije (front-end). Novi izgenerisani token treba da zameni stari u zaglavlju budućih HTTP zahteva sa klijentske strane. Access token može da se osvežava do datuma važenja refresh tokena.

#### Kontrola pristupa pomoću RBAC modela
Potrebno je implementirati model kontrole pristupa svakom delu sistema uz pomoć uloga i permisija. Jedan korisnik može da ima više uloga, a jednoj ulozi može biti dodeljeno više permisija. Potrebno je na nivou kompletnog sistema (za svaku metodu kontrolera) definisati prava pristupa. Izuzeci mogu biti metode za prijavu i registraciju na sistem. Takođe, potrebno je postaviti permisije za sve CRUD operacije koje postoje u sistemu.

#### Šifrovanje osetljivih podataka
Osetljive podatke (LBO pacijenta) je potrebno šifrovati pre skladištenja u bazi.
