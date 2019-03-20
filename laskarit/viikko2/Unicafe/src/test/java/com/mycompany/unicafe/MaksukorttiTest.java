package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void saldoAlussaOikein() {
        assertEquals("saldo: 0.10", kortti.toString());
    }
    
    @Test
    public void lataaminenToimii() {
        kortti.lataaRahaa(1000);
        kortti.lataaRahaa(5);
        assertEquals("saldo: 10.15", kortti.toString());
    }
    
//    @Test
//    public void negatiivinenLataaminenEiOnnistu() {
//        kortti.lataaRahaa(-10);
//        kortti.lataaRahaa(-5);
//        assertEquals("saldo: 0.10", kortti.toString());
//    }
    
    @Test
    public void otaRahaaToimiiJosRahaaOn() {
        kortti.lataaRahaa(100);
        kortti.otaRahaa(50);
        assertEquals("saldo: 0.60", kortti.toString());
    }
    
    @Test
    public void otaRahaaSaldoEiMuutuJosRahaaEiTarpeeksi() {
        kortti.otaRahaa(50);
        assertEquals("saldo: 0.10", kortti.toString());
    }
    
    @Test
    public void otaRahaaPalauttaaTrueJosRahaaTarpeeksi() {
        kortti.lataaRahaa(150);
        boolean tulos = kortti.otaRahaa(70);
        assertTrue(tulos);
    }
    
    @Test
    public void otaRahaaPalauttaaFalseJosRahaaEiTarpeeksi() {
        kortti.lataaRahaa(150);
        boolean tulos = kortti.otaRahaa(200);
        assertFalse(tulos);
    }
}
