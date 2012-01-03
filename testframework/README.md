Avansert Scala
==============

Målet for denne workshopen er å lage et lite testrammeverk i Scala.

Sammen med denne README-filen finner dere et skjelett vi skal ta utgangspunkt i. Skjelettet består av et Scala-prosjekt med standard struktur, som bruker simple build tool (sbt) som byggeverktøy.

Under `src/test/scala/` ligger et sett av testklasser. Disse klassene inneholder tester som viser hvordan testrammeverket kan brukes. Forløpig er alle testene utkommentert, men det skal vi gjøre noe med. Ta for dere hver av stegende beskrevet under. For å gjøre det enklest mulig for dere selv, anbefaler vi å kommentere inn litt og litt. Målet er å få alle testene til å kjøre, uten å endre testene.

Ikke bli sittende fast for lenge - spør om hjelp. Husk, vi skal ha det gøy!

Oppgave1.scala - Enkle assertions
=====

Kommenter inn koden og få testene til å kjøre. I `Oppgave1.scala` er det et object som heter `RunOppgave1`, som vi skal bruke til å kjøre testene.

Her anbefaler vi å printe ut resultatet slik at man får verifisert resultatet. Resultatet av en testkjøring fra sbt kan feks se slik ut:

	[info] Running RunOppgave1 
	2 + 2 == 4 SUCCESS!
	2 + 2 != 5 SUCCESS!
	3 + 2 == 4 FAILURE! 5 does not equal 4

Oppgave2.scala - Håndtere exceptions
=====

Testrammeverket må støtte tester som kaster exceptions under kjøring. Selv om en test kaster exception ønsker vi fortsatt en rapport i consolet.

Kommenter inn koden og få testen til å kjøre med forventet resultat. 

sbt vil ikke vise deg eventuelle exceptions som blir kastet automatisk - den vil kun rapportere noe slikt som `[error] {file:/...}.../test:run-main: Nonzero exit code: 1`. For å få frem stacktracet kan man skrive sbt-kommandoen `last test:run-main`.

Oppgave3.scala - Late utviklere
=====

Noen ganger er vi late... Vi ønsker derfor å støtte "pending" tester. Tester vi skal implementere imorgen! 

Implementer støtte for "pending" tester. En pending test er hverken riktig eller feil - men pending.

Oppgave4.scala - Pimp my syntax
=====

Implementer ved hjelp av implicit conversions en mer fancy assertion syntax.

Oppgave5.scala - Before & After
=====

Implementer støtte for Before og After. Dette er setup og teardown metoder som skal kjøres før og etter hver eneste test.

Oppgave6.scala - Rapportering
=====

Skriv støtte for god rapportering.  

Alle gode test-rammeverk burde produsere en vakker og informativ test-rapport. Hvor mange tester feilet, hvor mange var ok, pending, osv. Lag også en oppsummeringsrapport.

Tips: Linux/Mac brukere kan printe ut fargekodene i Console for fargerik output; f.eks println(Console.GREEN + "Hello World")

ExtraOppgave.scala - JUnit støtte
=====

Skriv JUnit støtte for MiniTest rammeverket så testene kan eksekveres direkte fra IntelliJ
