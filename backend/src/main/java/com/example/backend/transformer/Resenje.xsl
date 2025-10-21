<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:aut="http://www.ftn.uns.ac.rs/autorskoDelo" version="2.0">

    <xsl:template match="/">
        <html>
            <head>
                <title>Rešenje zahteva za unošenje u evidenciju i deponovanje autorskih prava</title>
                <style type="text/css">
                    table {
                    font-family: serif;
                    border-collapse: collapse;
                    margin: 0;
                    width: 95%;
                    border: 1;
                    }
                    th, td {
                    text-align: left;
                    padding: 3px;
                    border: 1;
                    }
                    tr:nth-child(even){ background-color: #f2f2f2 }
                    th {
                    font-family: sans-serif;
                    }
                    tr { border: 1; }

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
                <center>
                    <h3>Rešenje zahteva za unošenje u evidenciju i deponovanje autorskih prava</h3>
                </center>

                <table>
                    <tr>
                        <td>Broj prijave</td>
                        <td>
                            <xsl:value-of
                                    select="//aut:broj_prijave"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Datum obrade</td>
                        <td>
                            <xsl:value-of
                                    select="//aut:resenje_zahteva//aut:datum_obrade"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <xsl:element name="a">
                                <xsl:attribute name="href">
                                    http://localhost:8003/autorskaPrava/download/zahtev/<xsl:value-of select="//aut:resenje_zahteva//aut:broj_prijave"/>
                                </xsl:attribute>
                                Referenca na zahtev
                            </xsl:element>
                        </td>
                    </tr>
                </table>
                <br/>
                <table>
                    <tr>
                        <th colspan="2">Službenik koji je obradio zahtev</th>
                    </tr>
                    <tr>
                        <td>Ime i prezime</td>
                        <td>
                            <xsl:value-of
                                    select="//aut:resenje_zahteva//aut:ime_sluzbenika"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Email adresa</td>
                        <td>
                            <xsl:value-of
                                    select="//aut:resenje_zahteva//aut:email_sluzbenika"/>
                        </td>
                    </tr>
                </table>
                <br/>
                <table>
                    <xsl:choose>
                        <xsl:when test="//aut:resenje_zahteva//aut:odbijen='false'">
                            <tr>
                                <td>Status zahteva</td>
                                <td>
                                    <xsl:attribute name="style">
                                        <xsl:value-of select="'color:green;'"/>
                                    </xsl:attribute>
                                    PRIHVACEN
                                </td>
                            </tr>
                            <tr>
                                <td>Sifra</td>
                                <td>
                                    <xsl:value-of
                                            select="//aut:resenje_zahteva//aut:sifra"/>
                                    <xsl:attribute name="style">
                                        <xsl:value-of select="'color:green;'"/>
                                    </xsl:attribute>
                                </td>
                            </tr>
                        </xsl:when>
                        <xsl:otherwise>
                            <tr>
                                <td>Status zahteva</td>
                                <td>
                                    <xsl:attribute name="style">
                                        <xsl:value-of select="'color:red;'"/>
                                    </xsl:attribute>
                                    ODBIJEN
                                </td>
                            </tr>
                            <tr>
                                <td>Razlog odbijanja</td>
                                <td>
                                    <xsl:value-of
                                            select="//aut:resenje_zahteva//aut:razlog_odbijanja"/>
                                </td>
                            </tr>
                        </xsl:otherwise>
                    </xsl:choose>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
