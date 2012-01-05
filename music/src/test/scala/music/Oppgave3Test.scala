package music

import org.scalatest.{FunSuite}


class Oppgave3Test extends FunSuite {
  
  test("Lyrics for Alt kan repeteres"){
    val lyrics = Music.findLyrics("http://lyrics.wikia.com/Jokke_%26_Valentinerne:Alt_Kan_Repareres")
    
    def words(s:String) = s.split("\\W+").toSet
    
    assert(words(lyrics) === words(altkanrepteres))
  }

  def altkanrepteres =
"""All den dopen som jeg tok i går
Kommer ikke til å hjelpe meg når jeg våkner
Forhåpentligvis i en seng

Og all den alk'en som jeg drekker hver dag
Den alk'en som går løs på kroppen min
som en sirkelsag gir meg ubehag

Men alt kan repareres
Alt kan repareres
Alt kan repareres

Noen ganger når jeg er på fest
Og er den som, som drekker mest
da kan det hende

at jeg får et slags delirium
Og slåss med alle, unntatt en hund
Når jeg våkner da har jeg det ikke bra

Men alt kan repareres
Alt kan repareres
Alt kan repareres

Når jeg engang i min kiste ligger
På vei til graven med en sprukken lever og roser
Et følge i sort, Oooo

Da kan det hende at jeg blir for tung
At taket glepper og min kiste går i stykker
Jeg snur meg jeg, for

Alt kan repareres
Alt kan repareres
Alt kan repareres

Alt kan repareres
Alt kan repareres
Alt kan repareres
"""  
}