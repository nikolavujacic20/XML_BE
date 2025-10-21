<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:aut="http://www.ftn.uns.ac.rs/autorskoDelo" version="2.0">

    <xsl:template match="/">
        <html>
            <head>
                <title>Zahtev za unošenje u evidenciju i deponovanje autorskih prava</title>
                <style type="text/css">

                    table {
                    font-family: serif;
                    border-collapse: collapse;
                    margin: 0;
                    width: 100%;
                    border: 1px solid black;
                    }
                    th, td {
                    text-align: left;
                    padding: 3px;
                    }
                    th {
                    font-family: sans-serif;
                    }
                    .leftBorder{
                    border-left-width:1px;
                    }
                    .topBorder{
                    border-top-width:1px;
                    }

                    body { font-family: sans-serif; }
                    p { text-indent: 30px; }
                    .sup {
                    vertical-align: super;
                    padding-left: 4px;
                    font-size: small;
                    text-transform: lowercase;
                    }

                </style>
            </head>
            <body>
                <table>
                    <tr>
                        <td>
                            <b>ZAVOD ZA INTELEKTUALNU SVOJINU</b>
                            <br/>
                            Beograd, Knjeginje Ljubice 5
                        </td>
                        <td>
                            <b>OBRAZAC A-1</b>
                        </td>
                    </tr>
                    <br/>
                    <br/>
                    <br/>
                    <p style="text-align:center; font-size: 15px; margin-top: 10px; margin-bottom: 10px">
                        <b>ZAHTEV ZA UNOŠENJE U EVIDENCIJU I DEPONOVANJE AUTORSKIH DELA</b>
                    </p>
                    <br/>
                    <br/>
                    <br/>
                </table>
                <br/>
                <table>
                    <tr>
                        <td style="border-left-width:1px;">
                            1) Podnosilac - ime, prezime, adresa i drzavljanstvo autora ili drugog nosioca autorskog
                            prava ako je podnosilac fizicko lice, odnosno poslovno ime i sediste nosioca autorskog prava
                            ako je podnosilac pravno lice:
                            <br/>
                            <br/>
                            <xsl:if test="//aut:Podnosilac_zahteva//aut:Ime">
                                <xsl:value-of select="//aut:Podnosilac_zahteva//aut:Podaci_o_podnosiocu//aut:Ime"/><xsl:text>&#x20;</xsl:text>
                                <xsl:value-of select="//aut:Podnosilac_zahteva//aut:Podaci_o_podnosiocu//aut:Prezime"/>,
                                <xsl:text>&#x20;</xsl:text>
                                <br/>
                                <xsl:value-of
                                        select="//aut:Podnosilac_zahteva//aut:Podaci_o_podnosiocu//aut:Adresa//aut:Ulica"/>
                                <xsl:text>&#x20;</xsl:text>
                                <xsl:value-of
                                        select="//aut:Podnosilac_zahteva//aut:Podaci_o_podnosiocu//aut:Adresa//aut:Broj_u_ulici"/>,
                                <xsl:text>&#x20;</xsl:text>
                                <xsl:value-of
                                        select="//aut:Podnosilac_zahteva//aut:Podaci_o_podnosiocu//aut:Adresa//aut:Postanski_broj"/><xsl:text>&#x20;</xsl:text>
                                <xsl:value-of
                                        select="//aut:Podnosilac_zahteva//aut:Podaci_o_podnosiocu//aut:Adresa//aut:Mesto"/><xsl:text>&#x20;</xsl:text>
                                <xsl:value-of
                                        select="//aut:Podnosilac_zahteva//aut:Podaci_o_podnosiocu//aut:Adresa//aut:Drzava"/>
                                <br/>
                                <xsl:value-of
                                        select="//aut:Podnosilac_zahteva//aut:Podaci_o_podnosiocu//aut:Drzavljanstvo"/>
                            </xsl:if>
                            <xsl:if test="//aut:Podnosilac_zahteva//aut:Poslovno_ime">
                                <i>
                                    <xsl:value-of
                                            select="//aut:Podnosilac_zahteva//aut:Podaci_o_podnosiocu//aut:Poslovno_ime"/>
                                </i>
                                ,
                                <xsl:text>&#x20;</xsl:text>
                                <xsl:value-of select="//aut:Podnosilac_zahteva//aut:Podaci_o_podnosiocu//aut:Sediste"/>
                            </xsl:if>
                        </td>
                    </tr>
                </table>
                <br/>

                <table style="margin: 5px">
                    <tr>
                        <td style="border-left-width:1px;">
                            telefon:
                            <xsl:value-of
                                    select="//aut:Podnosilac_zahteva//aut:Podaci_o_podnosiocu//aut:Broj_telefona"/>
                        </td>
                        <td>
                            e-mail:
                            <xsl:value-of select="//aut:Podnosilac_zahteva//aut:Podaci_o_podnosiocu//aut:Email"/>
                        </td>
                    </tr>
                </table>
                <br/>
                <table>
                    <tr>
                        <td style="border-left-width:1px;">
                            2) Pseudonim ili znak autora, (ako ga ima):
                            <br/>
                            <br/>
                            <xsl:choose>
                                <xsl:when test="//aut:Autor//aut:Podaci_o_autoru//aut:Pseudonim">
                                    <xsl:value-of select="//aut:Autor//aut:Podaci_o_autoru//aut:Pseudonim"/>
                                    <br/>
                                    <br/>
                                </xsl:when>
                                <xsl:otherwise>
                                    Autor nema pseudonim.
                                    <br/>
                                    <br/>
                                </xsl:otherwise>
                            </xsl:choose>

                            3) Ime, prezime i adresa punomocnikam ako se prijava podnosi preko punomocnika:
                            <xsl:choose>
                                <xsl:when test="//aut:Podaci_o_punomocniku//aut:Osnovni_licni_podaci//aut:Ime">
                                    <br/>
                                    <br/>
                                    <xsl:value-of
                                            select="//aut:Podaci_o_punomocniku//aut:Osnovni_licni_podaci//aut:Ime"/><xsl:text>&#x20;</xsl:text>
                                    <xsl:value-of
                                            select="//aut:Podaci_o_punomocniku//aut:Osnovni_licni_podaci//aut:Prezime"/>,
                                    <xsl:text>&#x20;</xsl:text>
                                    <br/>
                                    <xsl:value-of
                                            select="//aut:Podaci_o_punomocniku//aut:Osnovni_licni_podaci//aut:Adresa//aut:Ulica"/>
                                    <xsl:text>&#x20;</xsl:text>
                                    <xsl:value-of
                                            select="//aut:Podaci_o_punomocniku//aut:Osnovni_licni_podaci//aut:Adresa//aut:Broj_u_ulici"/>,
                                    <xsl:text>&#x20;</xsl:text>
                                    <xsl:value-of
                                            select="//aut:Podaci_o_punomocniku//aut:Osnovni_licni_podaci//aut:Adresa//aut:Postanski_broj"/><xsl:text>&#x20;</xsl:text>
                                    <xsl:value-of
                                            select="//aut:Podaci_o_punomocniku//aut:Osnovni_licni_podaci//aut:Adresa//aut:Mesto"/><xsl:text>&#x20;</xsl:text>
                                    <xsl:value-of
                                            select="//aut:Podaci_o_punomocniku//aut:Osnovni_licni_podaci//aut:Adresa//aut:Drzava"/>
                                    <br/>
                                    <br/>
                                </xsl:when>

                                <xsl:otherwise>
                                    Prijava se ne odnosi preko punomocnika.
                                    <br/>
                                    <br/>
                                </xsl:otherwise>
                            </xsl:choose>

                            4) Naslov autorskog dela, odnosno alternativni naslov, ako ga ima, po kome autorsko delo
                            moze da se identifikuje:
                            <br/>
                            <br/>
                            <xsl:value-of select="//aut:Autorsko_delo//aut:Naslov_autorskog_dela"/>
                            <br/>
                            <br/>

                            5) Podaci o naslovu autorskog dela na kome se zasniva delo prerade, kao i podatak o autoru
                            izvornog dela:
                            <br/>
                            <br/>
                            <xsl:choose>
                                <xsl:when test="//aut:Zasnivano_delo//aut:Naslov">
                                    Naslov:
                                    <xsl:value-of select="//aut:Zasnivano_delo//aut:Naslov"/>
                                    <br/>
                                    Autor:
                                    <xsl:value-of select="//aut:Zasnivano_delo//aut:Autor"/>
                                    <br/>
                                    <br/>
                                </xsl:when>
                                <xsl:otherwise>
                                    Autorsko delo nije zasnovano ni na jednom drugom delu.
                                    <br/>
                                    <br/>
                                </xsl:otherwise>
                            </xsl:choose>

                            6) Podaci o vrsti autorskog dela (knjizevno delo, muzicko delo, likovno delo, racunarski
                            program i dr.):
                            <br/>
                            <br/>
                            <xsl:value-of select="//aut:Autorsko_delo//aut:Vrsta_autorskog_dela"/>
                            <br/>
                            <br/>

                            7) Podaci o formi zapisa autorskog dela (stampani tekst, opticki disk i slicno):
                            <br/>
                            <br/>
                            <xsl:value-of select="//aut:Autorsko_delo//aut:Forma_zapisa_autorskog_dela"/>
                            <br/>
                            <br/>

                            8) Podaci o autoru:
                            <br/>
                            <br/>
                            <xsl:choose>
                                <xsl:when test="//aut:Autor//aut:Podaci_o_autoru//aut:Lice//aut:Ime">
                                    Ime i prezime:
                                    <xsl:value-of select="//aut:Autor//aut:Podaci_o_autoru//aut:Lice//aut:Ime"/><xsl:text>&#x20;</xsl:text>
                                    <xsl:value-of select="//aut:Autor//aut:Podaci_o_autoru//aut:Lice//aut:Prezime"/>,
                                    <xsl:text>&#x20;</xsl:text>
                                    <br/>
                                    Adresa:
                                    <xsl:value-of
                                            select="//aut:Autor//aut:Podaci_o_autoru//aut:Lice//aut:Adresa//aut:Ulica"/>
                                    <xsl:text>&#x20;</xsl:text>
                                    <xsl:value-of
                                            select="//aut:Autor//aut:Podaci_o_autoru//aut:Lice//aut:Adresa//aut:Broj_u_ulici"/>,
                                    <xsl:text>&#x20;</xsl:text>
                                    <xsl:value-of
                                            select="//aut:Autor//aut:Podaci_o_autoru//aut:Lice//aut:Adresa//aut:Postanski_broj"/><xsl:text>&#x20;</xsl:text>
                                    <xsl:value-of
                                            select="//aut:Autor//aut:Podaci_o_autoru//aut:Lice//aut:Adresa//aut:Mesto"/><xsl:text>&#x20;</xsl:text>
                                    <xsl:value-of
                                            select="//aut:Autor//aut:Podaci_o_autoru//aut:Lice//aut:Adresa//aut:Drzava"/>
                                    <br/>
                                    Drzavljanstvo:
                                    <xsl:value-of
                                            select="//aut:Autor//aut:Podaci_o_autoru//aut:Lice//aut:Drzavljanstvo"/>
                                    <br/>
                                    <br/>
                                </xsl:when>
                                <xsl:when test="//aut:Autor//aut:Podaci_o_autoru//aut:Lice//aut:Godina_smrti">
                                    Ime autora:
                                    <xsl:value-of select="//aut:Autor//aut:Podaci_o_autoru//aut:Lice//aut:Ime"/>
                                    <br/>
                                    <br/>
                                    Godina smrti:
                                    <xsl:value-of
                                            select="//aut:Autor//aut:Podaci_o_autoru//aut:Lice//aut:Godina_smrti"/>
                                    <br/>
                                    <br/>
                                </xsl:when>
                                <xsl:otherwise>
                                    Autor je anoniman.
                                </xsl:otherwise>
                            </xsl:choose>

                            9) Podatak da li je u pitanju autorsko delo stvoreno u radnom odnosu:
                            <br/>
                            <br/>
                            <xsl:choose>
                                <xsl:when test="aut:Autorsko_delo//aut:Autorsko_delo_stvoreno_u_radnom_odnosu">
                                    Autorsko delo je stvoreno u radnom odnosu.
                                </xsl:when>
                                <xsl:otherwise>
                                    Autorsko delo nije stvoreno u radnom odnosu.
                                </xsl:otherwise>
                            </xsl:choose>
                            <br/>
                            <br/>

                            10) Nacin korišcenja ili nameravani nacin korišcenja autorskog dela:
                            <br/>
                            <br/>
                            <xsl:value-of select="//aut:Autorsko_delo//aut:Nacin_koriscenja_autorskog_dela"/>
                            <br/>
                            <br/>

                        </td>
                    </tr>
                </table>
                <br/>

                <table>
                    <tr>
                        <td>
                            <div style="width: 100%">
                                <p style="text-align: center; font-weight: 500; margin-bottom: 15px">POPUNJAVA ZAVOD
                                </p>
                                <p>Prilozi uz prijavu:</p>
                                <br></br>
                                <br></br>
                                <xsl:element name="a">
                                    <xsl:attribute name="href">
                                        http://localhost:8003/autorskaPrava/download/prilog/<xsl:value-of
                                            select="//aut:Putanja_do_opisa"/>
                                    </xsl:attribute>
                                    Opis autorskog dela
                                </xsl:element>
                                <br></br>
                                <br></br>
                                <xsl:element name="a">
                                    <xsl:attribute name="href">
                                        http://localhost:8003/autorskaPrava/download/prilog/<xsl:value-of
                                            select="//aut:Putanja_do_primera"/>
                                    </xsl:attribute>
                                    Primer autorskog dela
                                </xsl:element>
                            </div>
                            <div style="width: 100%; float: right">
                                <table style="margin-top: 80px">
                                    <tr>
                                        <td style="font-size: 20px; padding: 15px 0 5px 15px;">Broj
                                            prijave
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="font-size: 22px; font-weight: bold; padding: 5px 0 15px 15px">
                                            <xsl:value-of select="//aut:Broj_prijave"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="font-size: 20px; padding: 15px 0 5px 15px;">
                                            Datum
                                            podnošenja:
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="font-size: 22px; padding: 5px 0 15px 15px">
                                            <xsl:value-of select="substring(//aut:Datum_podnosenja, 0, 11)"/>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                        </td>
                    </tr>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
