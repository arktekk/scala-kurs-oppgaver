MUSIC
=====

Oppgave1Test.scala - Enkel xml parsing
------------------
Første del av oppgaven er å parse xml fra `src/test/resources/music/altkanrepareres.xml` inn i korrekte datastrukturer.
`altkanrepareres.xml` er en dump av resultatet fra `http://lyrics.wikia.com/api.php?artist=Jokke&song=Alt%20kan%20repareres&fmt=xml`

Andre del av oppgaven er å hente disse datene direkte fra lyrics.wikia med Dispatch og returnere korrekte datastrukturer.

Når alle testene er grønne er oppgaven løst.

Oppgave2Test.scala - Avansert xml parsing
------------------
Her skal mer data parses inn i korrekte datastrukturerer.

Som i forrige oppgave avsluttes også denne med å hente live versjoner av data fra lyrics.wikia med Dispatch

Når alle testene er grønne er oppgaven løst

Oppgave3Test.scala - Screenscraping og skikkelig vanskelig screenscraping
------------------
i elementet `url` fra `findSong` metoden fra oppgave1 ligger den en url til full html versjon av lyrics for sangen
Benytt TagSoup støtten i Dispath til å hente ut lyrics fra denne html siden

Når testen kjører (eller du er fornøyd med resultatet - og har endret testen deretter) er oppgaven ferdig 


