# Testausdokumentti
Peliä on testattu automatisoiduin yksikkö ja integraatiotestein JUnitilla, sekä manuaalisesti järjestelmätasolla.

## Yksikkö- ja integraatiotestaus
### sovelluslogiikka
Pelilogiikan ytimen testauksesta vastaa [game](https://github.com/LauriTahvanainen/ot-harjoitustyo/tree/master/ManVsMachine/src/test/java/game)-paketin testiluokat [GamePhysicsTest](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/src/test/java/game/GamePhysicsTest.java), [MachineTest](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/src/test/java/game/MachineTest.java), [MapRendererTest](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/src/test/java/game/MapRendererTest.java) ja [SpriteTest](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/src/test/java/game/SpriteTest.java).

GamePhysiscTest-luokan testit testaavat fysiikoiden toimimista kaikilla tarvittavilla riippuvuuksilla, kuten piirretyllä kartalla ja toimivalla Machine-oliolla. Testauksessa ei kuitenkaan onnistuttu testaamaan törmäysfysiikoiden toimintaa tai hahmojen liikkumista intersects metodin toiminnan virheellisyyden vuoksi, vaikka Fysiikoiden ympäristö pyrittiin luomaan täysin samanlaiseksi kuin itse pelissä. Luokassa testataan pääasiassa fysiikkaympäristön oikeellinen rakentuminen.

MachineTest-luokan testeissä on testattu myös [Scanner](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/src/main/java/mvsm/sprite/Scanner.java)-luokka. Testeissä käytetään [BFS](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/src/main/java/mvsm/algorithm/BFS.java) algoritmia. Testeissä on testattu, että Machine ja Scanner liikkuvat oikeellisesti, oikeaa reittiä.

[MapRenderer](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/src/main/java/mvsm/game/MapRenderer.java)-luokan testeissä on luokkaa testattu käyttämällä oikeaa pelin karttaa, ja vertaamalla tuloksia testiluokassa määriteltyyn karttaan. 

SpriteTest-luokka vastaa pelaajan hahmon liikuttamiseen ja törmäyksentunnistuksen ja käsittelyn testaamisesta testausta varten luodulla paneelilla. 

Luokkaa [StateManager](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/src/main/java/mvsm/statemanagement/StateManager.java) testatessa on pyritty testaamaan, että tilan vaihtaminen toimii, ja päivitykset menevät Managerilta oikealle luokalle. Testatessa on käytetty Login- ja MenuStatea matkivia luokkia [FakeMenuState](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/src/test/java/Helpers/FakeMenuState.java) ja [FakeLoginState](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/src/test/java/Helpers/FakeLoginState.java). 

[Tapahtumankäsittelijöiden](https://github.com/LauriTahvanainen/ot-harjoitustyo/tree/master/ManVsMachine/src/main/java/mvsm/eventhandling) testit on toteutettu antamalla käsittelijöille syötteenä uusia tapahtuma-oloita. ActionEventHandler luokan testeihin on integroitu StateManager.

[Algoritmien](https://github.com/LauriTahvanainen/ot-harjoitustyo/tree/master/ManVsMachine/src/main/java/mvsm/algorithm) toimintaa on testattu yksikkötesteillä tarkastellen algoritmin laskemaa reittiä.

### Dao-luokat
[Dao-luokkien](https://github.com/LauriTahvanainen/ot-harjoitustyo/tree/master/ManVsMachine/src/main/java/mvsm/dao) testaamiseen on käytetty JUnitin [TemporaryFolder](https://junit.org/junit4/javadoc/4.12/org/junit/rules/TemporaryFolder.html)-ruleja, joiden avulla on luotu tilapäinen tiedosto testausta varten.
DatabaseUserDao luokan yhteydessä on testattu myös luokan toimintaa yhdessä DatabaseScoreDao-luokan kanssa. Dao-luokkien testeissä on testattu virheellisten syötteiden antamisen käsittely, sillä tilat käyttävät Dao-luokkia syötteitä käsitellessään.

### Testauskattavuus
Käyttöliittymää lukuunottamatta pelin testauksen rivikattavuus on 94% ja haarautumakattavuus 91%.
![](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/dokumentaatio/kuvat/testikattavuus.png)

## Järjestelmätestaus
Järjestelmätestaus on suoritettu manuaalisesti.
### Asennus ja konfigurointi
Sovellus on haettu ja sitä on testattu [käyttöohjeen](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/dokumentaatio/kayttoohje.md) mukaisesti x, x ja Linux-ympäristöissä, siten, että käynnistyshakemistossa on ollut käyttöohjeen mukainen config.properties-tiedosto, sekä niin, että tiedostoa ei ole ollut.

Sovellusta on testattu myös tilanteessa, jossa config-tiedoston sisältö on virheellistä.

### Toiminnallisuudet
Kaikki [määrittelydokumentin](https://github.com/LauriTahvanainen/ot-harjoitustyo/blob/master/ManVsMachine/dokumentaatio/vaatimusmaarittely.md) ja käyttöohjeen listaamat toiminnallisuudet on käyty järjestelmällisesti läpi. Testatessa syötekentät on myös täytetty virheellisillä syötteillä, kuten salasanan tapauksessa salasanalla joka sisältää muita merkkejä kuin kirjaimia tai numeroita.
