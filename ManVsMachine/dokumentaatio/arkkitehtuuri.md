# Arkkitehtuurikuvaus
## Rakenne
Ohjelmassa ei ole selkeää kerrosrakennetta, sillä kyseessä on peli. Rekenteessa ollaankin pyritty erilaisia tiloja sisältävään rakenteeseen. Käyttäjän syötteiden mukaan ohjelma siirtyy eri tiloihin. Keskeisenä rakenteessa on luokka StateManager, jonka tehtävänä on hallinnoida tiloja, sekä tarjota tiloille palveluja. Statemanagerin tehtävänä on mm. tilan vaihtaminen, sekä tilan päivittäminen. Jokaisen tilan on myös tunnettava StateManager, jotta ne voivat pyytää siltä palveluja.

Vaikka riippuvuuksia rakenteessa on jonkin verran riippuvuuksia, on silti pyritty siihen, että eri tilat olisivat mahdollisimman helposti irrotettavissa, sekä uusien tilojen lisääminen olisi helppoa.
Ohjelman pakkausrakenne on seuraava:
![](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/dokumentaatio/kuvat/pakkausrakenne.png)

mvsm.ui sisältää ohjelman alustavan Main luokan, sekä kaikki tilaluokat. Alustettaessa luodaan uusi Statemanager, tarvittavat tilat, dao-oliot ja tapahtumankuuntelijat. 

mvsm.statemanagement sisältää tilan määrittelevän luokan State.java, joka jokaisen tilan pitää toteuttaa, sekä StateManager luokan, joka vastaa tilojen hallinnasta ja joka tarjoaa tiloille palveluja, kuten musiikin soittaminen. 

mvsm.eventhandling pakkauksessa on tapahtumien kuuntelemisesta, kuten näppäimen painalluksista, vastaavat luokat. 

mvsm.dao paketin luokat vastaavat ohjelman tietojen pysyväistallennuksesta. 

mvsm.game pakkaus sisältää pelitilan tarvitsemat luokat. Luokat vastaavat mm. pelin fysiikoista, kartan piirtämisestä, sekä pisteiden laskusta. 

mvsm.algorithm pakkaus taas vastaa pelin tarvitsemien algoritmien toteutuksesta. mvsm.sprite sisältää pelaajan ja algoritmin hahmojen tarvitseman koodin, ja se on riippuvainen pakkauksesta mvsm.algorithm.

#### Sovellusta pääpiirteittäin kuvaava luokkakaavio

![](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/dokumentaatio/kuvat/luokkakaavio.png)

### Käyttöliittymä
Sovelluksen käyttöliittymä sisältää pääpiirteittäin 6 näkymää, minkä lisäksi joissain näkymissä on "alinäkymiä". Näkymät ovat:
 - Kirjautumisnäkymä (LoginState)
 - Päävalikko (MenuState)
 - Pelinvalintanäkymä (GameSelectionState)
 - Pelitila (PlayingState)
 - Asetusnäkymä (SettingsState)
 - Huipputulosten tarkastelu (HighscoreState)
 
Yksi tila vastaa siis yhdestä näkymästä. Tilat, ja näkymät on toteutettu niin, että sovelluksella on vain yksi Scene olio, jonka root-nodea GameStateManager vaihtaa. Jokainen tila omaa siis root paneelin, joka tarvittaessa vaihdetaan Scene olion root-nodeksi. Vaihtelu jokaisen tilan näkymässä toteutetaan manipuloimalla tilan root paneelin lapsia. 

Käyttöliittymä on pyritty erottamaan sovelluslogiikasta niin hyvin kuin pelin rakenne sallii. Eri tilat kutsuvat sopivilla parametreilla omaaviensa olioiden, kuten dao-olioiden metodeja. Esimerkiksi HighscoreState pyytää DatabaseScoreDao oliota tuomaan sille järjestetyn listan tietyn kartan huipputuloksia, minkä jälkeen tulokset piirretään käyttäjälle taulukkona metodin formScoreList avulla.

## Sovelluslogiikka
Sovelluslogiikan esitteleminen on helpompi jakaa osiin. 

Keskeisin sovelluslogiikan osa on itse pelitilan pyörittäminen. Pelin toiminnan mahdollistamisessa on pääosin mukana luokat PlayingState, Statemanager, MapRenderer, GamePhysics, sekä KeyEventHandler.
![](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/dokumentaatio/kuvat/pelilogiikka.png)
Ylläolevassa kuvassa näytetään näiden keskeisten luokkien suhteet toisiinsa. Luokkien muuttujat ja metodit ovat pelkistettyjä, ja niistä on kuvassa vain oleellisimmat.

PlayingState vastaa sen paneelin hallinnasta, jossa peli näytetään. StateManager vastaa tässä tapauksessa peliloopin pyörittämisestä (eli toistuvasta handle kutsusta). GamePhysics vastaa pelin tilan päivittämisestä, KeyEventListener tallentaa näppäimien painallukset ja MapRenderer piirtää kartan.

Peli "pyörii" seuraavasti: Kun uusi peli aloitetaan, rakentaa PlayingState pelinäkymän restore(a, mapName) metodissaan. Tässä metodissa luodaan Spritet, alustetaan fysiikat, sekä annetaan mapName parametrina MapRenderin metodille formArrayMap(mapName). MapRenderer lukee kartan resurssikansiosta. Kun tarvittavat MapRendererin metodit on kutsuttu, on PlayingStatella valmis GridPane paneeli, jonka se voi näyttää pelaajalle. Nyt pelaaja näkee pelin. restore-metodin lopuksi PlayingState kutsuu StateManagerin startLoop() metodia, joka aloittaa pelin päivittämisen.

Pelin ollessa käynnissä, StateManager kutsuu 60 kertaa sekunnissa metodia handle, joka kutsuu StateManagerin nykyisen tilan update metodia, joka PlayingState:n tapauksessa kutsuu GamePhysics:n metodia updateGameworld(). 

Update gameGameWorld() hoitaa törmäysten tunnistuksen ja käsittelyn, sekä Spritejen liikuttamisen. Pelaajan liikuttamiseen GamePhysics käyttää KeyEventListenerin keyCode listaa. Lopulta GamePhysics palauttaa PlayingStatelle arvon, joka kertoo PlayingStatelle tapahtuiko päivityksen aikana mitään. Jos esimerkiksi kone pääsee maaliin, niin GamePhysics palauttaa arvon -2, ja PlayingState saa viestin koneen voitosta, jolloin se kutsuu update metodissaan metodiaan machineWin(), jossa pysäytetään peli(StateManager.stopLoop()), ja ilmoitetaan pelaajalle häviöstä.

Alla pelkistetty sekvenssikaavio GameLoopin toiminnasta.
![](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/dokumentaatio/kuvat/Starting%20the%20GameLoop%20with%20BFS%20and%20map1.png)

### Tilan vaihtaminen
Tilan vaihto tapahtuu kutsumalla tilassa ensin StateManagerin metodia setCurrentState(int index), joka muuttaa nykyisen tilan id:n. Tilat on tallennettuna StateManagerissa listaan, joten indeksin vaihto riittää. Sitten kutsutaan StateManagerin metodia

`setSceneRoot(StateManager.getCurrentState.getCurrent)`

, joka vaihtaa scenen rootiksi nykyisen tilan root-paneelin. Näiden kutsujen yhteydessä voidaan myös tarvittaesssa kutsua uudeksi nykyiseksi tilaksi vaihdetun tilan restore metodia StateManagerin avulla.

### Nappien painallusten käsittely.
Sillä ActionEventHandler kiinnitetään sovelluksen alustuksessa sceneen, käsittelee se kaikki nappien painallukset mitä sovelluksessa tulee. Sama Handler siis käsittelee kaikkien tilojen painallukset. ActionEventHandler tuntee kuitenkin StateManagerin, ja aina kun sen pitää käsitellä painallus, se ohjaa painalluksen käsittelyn StateManagerin nykyiselle tilalle.

    `StateManager.getCurrentState.handleAction(ActionEvent t)`

Painalluksen ActionEvent kulkee siis käsiteltäväksi aina oikealle tilalle, eli sille joka on aktiivisena.

## Tietojen pysyväistallennus
Pakkauksen mvsm.dao luokat huolehtivat tietojen tallentamisesta tietokantaan, ja tietokannasta tietojen noutamisesta.

Luokat UserDao ja ScoreDao toimivat Data Access Object- suunnittelumallin mukaisina rajapintoina tietokannan käsittelylle. Tilaluokat jotka tarvitsevat kumman tahansa palveluja, pyytävät niitä DatabaseScoreDao ja DatabaseUserDao implementaatioiden avulla. Esimerkiksi HighScoreState omaa DatabaseScoreDao instanssin jolta se voi esimerkiksi pyytää listan käyttäjien huipputuloksia järjestettynä. Peli käyttää vain yhtä tietokantatiedostoa, mutta UserDao ja ScoreDao käsittelevät pääasiassa omia taulujaan. 

UserDao käsittelee Userrname nimistä taulua jonka rakenne on alla.

username(Primary key) | password | red | green | blue
--------------------- | -------- | --- | ----- | ----
username1             | password1 | 255 | 150 | 50
username2             | password12 | 255 | 157 | 60
username3             | password123 | 255 | 157 | 0
username4             | password1234 | 0 | 157 | 60

ScoreDao käsittelee tauluja joiden nimet ovat algoritmeja ja sarakkeet seuraavanlaisia:

username(Primary key) | map1 | map2 | map3 | map4
--------------------- | -------- | --- | ----- | ----
username1             | 5132 | 6120 | 1504 | 5000
username2             | 10000 | 8461 | 4552 | 6000
username3             | 1000 | 1000 | 4222 | 1021
username4             | 1000 | 1236 | 4522 | 6014

Tietokantajärjestelmänä toimii sqlite.

## Päätoiminnallisuudet
### Sisäänkirjautuminen
![](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/dokumentaatio/kuvat/Onnistunut%20sis%C3%A4%C3%A4nkirjautuminen.png)
ActionEventHandler käsittelee kaikki nappitapahtumat, ja kun käyttäjä on kirjoittanut käyttäjänimensä tekstikenttään ja painaa kirjautumisnappia, kutsuu ActionEventHandler handle metodiaan, jossa se kysyy omaavaltaan StateManagerilta, että mikä tila on tällä hetkellä aktiivisena. StateManager palauttaa LoginStaten, joten handle metodissa kutsutaan nimenomaan vain LoginState:n handleAction metodia. handleAction metodissa tarkistetaan, mikä paneeli loginStatessa on tällä hetkellä näkyvillä, ja koska aktiivisena on kirjautumispaneeli, kutsutaan LoginStaten metodia handleSignInView, mikä saa parametrinaan tiedot painalluksesta, errortekstikentän sekä tekstikentän johon käyttäjä on syöttänyt nimensä. Tässä metodissa kutsutaan UserDaon metodia read, joka hakee ja palauttaa tietokannasta syötettyä käyttäjänimeä vastaavan käyttäjäolion. Sitten nykyisen paneelin tila nollataan, ja StateManagerin avulla vaihdetaan nykyiseksi tilaksi MenuState ja Scenen root-paneeliksi MenuStaten alkupaneeli. StateManagerin avulla LoginStatesta myös lopuksi kutsutaan MenuStaten update metodia, joka päivittää oikean tervehdystekstin menu näkymään. Loppujen lopuksi käyttäjä on siis siirtynyt menu-tilaan.

### Pelaaminen
Pelaamisen arkkitehtuuria käytiin läpi kohdassa sovelluslogiikka.

### Huipputulosten tarkastelu
HighScoreState käyttöliittymään on toteutettu napit jokaiselle pelin algoritmille. Kun nappia painetaan, näyttää käyttöliittymä ne kartat joilla algoritmi toimii. Kun kartta valitaan, kutsuu tila sen omaavan DatabaseScoreDao instanssin metodia listAllSorted(algoritmi, "mapName"), joka hakee tietokannasta kaikki huipputulokset järjestyksessä suuremmasta pienempään käyttäen apunaan HighScoreUser oliorakennetta. Metodi palauttaa HighScoreStatelle HighScoreUserit listana. Tästä listasta HighScoreState sitten rakentaa tarkastelunäkymän käyttäjälle.

Kaikki daojen toiminnallisuuksia pyytävät ominaisuudet toimivat jotakuinkin samaan tapaan (Nimen, salasanan, värin vaihtaminen, tulosten tallentaminen). Joko haetaan jotain ja esitetään se graafisesti, tai tallennetaan jotain tietoja, kuten huipputulos, tietokantaan.

### Muita toiminnallisuuksia
Musiikin soittamista pyydetään StateManagerilta. Musiikin soittaminen tapahtuukin varsin yksinkertaisesti. Aina tilanvaihdon yhteydessä tila pyytää StateManageria soittamaan musiikkia. Esimerkiksi LoginStaten vaihtuessa MenuStateen kutsutaan LoginStatessa tilaa vaihdettaessa StateManagerin metodia playMenuMusic().

## Yleisesti
Pelin vallitseva rakenne koostuu tiloista, joilla on tietyt ominaisuudet, ja jotka voivat kaikki pyytää StateManagerin palveluja. Tämä on varsin hyvä rakenne siinä mielessä, että uusien ominaisuuksien lisääminen on varsin helppoa. Pelkistettynä lisätään vain uusi tila johon peli voi siirtyä, tai päivitetään vanhan tilan toimintaa.

## Rakenteen heikkoudet
Käyttöliittymän toteuttamiseen ei käytetty FXML:ää. Käyttöliittymäkoodi onkin varsin sekavaa, ja sillä tilat ovat nimenomaan käyttöliittymää, vaikeuttaa tämä suuresti uusien toiminnallisuuksien luomista.


