

 -----------------------------------------------------
 | Logbuch zu dem Projekt 'Raumplaner' von Jim Gerth |
 -----------------------------------------------------


>> Tue Nov 6 22:35:49 2018
   
   Da ich bereits das Moebel-Auswahl Feature implementiert hatte und es zu funktionieren schien, will ich zunaecht versuchen,
   die Moebel in einer Datei zu speichern bzw. dann auch wieder zu laden. Das Grundgeruest dafuer gestalte ich so:
      
     -Ich definiere ein Protokoll, zum speichern von Moebeln, welches aber noch nicht das Speichern an sich implementiert,
      sondern abstrakt die Funktionen beschreibt, welche eine Klasse haben muss, um dem "Speicher-Protokoll" zu entsprechen.
      
     -Da zwei Moeglichkeiten des Speicherns angegeben waren, koennte ich nun analog dazu zwei Klassen definieren, welche
      das Speichern direkt implementieren und somit acuh beide dem "Speicher-Protokoll" entsprechen.
      
     -Ueberall wo nun im Raumplener etwas gespeichert werden muss, kann ich ein Objekt, welches dem "Speicher-Protokoll"
      entspricht, dazu delegieren, etwas in einer Datei zu speichern. Das gibt Freiheit, da an dem Punkt dann egal ist,
      welcher Klasse dieses Objekt angehoert und wie genau es die Moebel Speichert.
      
   Mit den neuen Klassen "SpeicherProtokoll" und "SpeicherDelegate" habe ich also erstmal dieses Grundgeruest geschaffen,
   das tatsaechliche Speichern muss ich aber noch implementieren.


>> Wed Nov 7 14:57:42 2018

   Ich habe mich dazu entschieden, das Speichern zunaechst mit der ersten Variante zu implementieren, also die Daten als
   Textdatei zu speichern. Dabei verwende ich JSON zum speichern der Moebel, da ich dafuer eine externe Library von
   https://github.com/stleary/JSON-java verwenden kann, was den Speicherprozess etwas vereinfacht.
   Bisher konvertiere ich das alleMoebel Array "haendisch" zu JSON, also erstelle einen langen String aus den Moebeln und
   deren properties, wie Position oder Farbe, mit vielen zusaetzlichen {'s und }'s...
   Allerdings kann ich das eventuell auch einfacher mit dem JSONStringer aus der bereits angesprochenen Bibliothek machen.
   Die Dokumentation zu der Bibliothek ist auch auf bei der angegebenen URL zu finden, welche ich natuerlich auch zur
   Hilfe ziehe.
   
   
>> Thu Nov 8 21:19:45 2018

   Die JSON Serialization mache ich weiterhin haendisch. Ich versuche das aber so uebersichtlich zu machen, wie moeglich.
   Bisher findet alles in der JSONSpeicherDelegate Klasse statt, wo ich durch das alleMoebel Array iteriere und je nach
   Art des Moebels (ueberprueft mit instanceof) eine weitere Funktion zum serializen dieses einzelnen Moebels aufrufe.
   Ich versuche ausserdem diese weitere Funktion generic zu gestalten, sodass es automatisch jede Art von Moebel zu JSON
   konvertieren kann, und so nicht nur das Type-checking, sondern auch viele, an bestimmte Moebel angepasste, Funktionen
   einsparen wuerde. Allerdings weiss ich noch nicht, wie ich ueber alle Atribute eines Moebels iterieren kann, im Notfall
   muss ich ein weiteres Array "attribute" in der Moebel Klasse anlegen...
   
   
>> Mon Nov 12 22:41:59 2018

   Parallel zum Speichern der Moebel versuche ich auch den ganzen Quellcode fuer den Raumplaner etwas uebersichtlicher zu
   gestalten. Dazu gehoert zum Beispiel die Reduzierung von Klassen durch generische Klassen. Momentan habe ich fuer jede
   Moebelart eine einzelne weitere Klasse, die ein Fenster zum erstellen eines entsprechenden Moebels darstellt. Durch ein
   "optionen"-Array in der jeweiligen Moebelklasse weiss die zugehoerige GUI-Klasse, welche Werte (wie z.B. Hoehe oder Breite)
   wichtig zum Erstellen sind und welche nicht (z.B. die Position, welche am Anfang einfach immer (0|0) iat). Dadurch, dass
   ich schon in der "Moebel"-Klasse bestimme, dass jede Subklasse eben so ein Optionen-Array haben muss (und ausserdem eine
   "art"-Variable), kann ich eine allgemeine GUI-Klasse erstellen, die fuer alle Klassen 'T' gueltig ist, die eine Subklasse
   der "Moebel"-Klasse sind:
   
     -class AlgemeineGUI<T extends Moebel> extends JFrame {...}
     
   Momentan kann ich allerdings noch keinen dieser allgemeinen GUIs erstellen, da das Programm dabei abstuerzt... Ich weiss
   noch nicht genau warum das passiert, aber das werde ich mir nochmal genau angucken muessen.
   
   
>> Tue Nov 13 15:22:04 2018

   Bisher konnte ich herausfinden, dass der Absturz an einer Null-Pointer-Exception liegt. Aus einem mir unbekannten Grund
   sind die Werte fuer das "optionen"-Array und die "art" fuer die generische Klasse nicht zugaenglich. [-> noch mehr schreiben]
   
   Ausserdem habe ich vorerst die Moebel Klavier und Badewanne geloescht, da diese nur halb fertig waren und somit bloss fuer
   Verwirrung sorgten. Da ich Git benutze, kann ich notfalls wieder auf die beiden Moebel zurueckgreifen, alledings koennen
   natuerlich auch jeder Zeit andere neue Moebel hinzugefuegt werden, was ich allerdings eher gegen Ende der Entwicklung machen
   werde, da viele Klassen, wie gesagt, momentan eher nur stoeren.


>> Tue Nov 13 16:29:44 2018

   Das Speichern habe ich jetzt so geloest, dass jedes Moebel eine Methode toJSON() haben muss, welche ein String aller, fuer
   das Erstellen dieses Moebels wichtige, Attribute im JSON Format wiedergiebt. Eine Schrankwand sieht z.B. so aus:
   
     -{
         "Art": "Schrankwand",
         "X-Position": 30,
         "Y-Position": 15,
         "Farbe": "schwarz",
         "Orientierung": 3,
         "Anzahl der Einheiten": 5,
         "Breite": 50,
         "Tiefe": 45
      }
     	
   Zudem wird dieses String von Moebeln dann direkt in eine .txt Datei geschrieben, wobei ich auf
   http://www.javaschubla.de/2007/javaerst0260.html erfahren habe, wie genau das funktioniert.
   
   
>> Tue Nov 13 21:55:12 2018

   Zusaetslich zum Speichern, implementiert JSONSpeicherDelegate jetzt auch die lade() Methode aus dem Speicherprotokoll.
   Hierbei lade ich zunaechst die JSON Daten aus einer Datei in ein String, wobei ich auf
   https://www.geeksforgeeks.org/different-ways-reading-text-file-java/ erfahren habe, wie genau dies funktioniert. Dann nutze
   ich die bereits erwaehnte Bibliothek (welche in meinem Projekt JAVASON heisst, was von mir kommt, um mir den Namen besser zu
   merken...), um den einfachen String von JSON Daten, welcher ja zunaechst nichts fuer das Programm bedeutet, zu parsen, also
   irgendwie zu ordnen um Sinn aus der Zeichenkette zu machen. Dies mache ich mit der JSONArray Klasse aus dieser Bibliothek,
   wobei ich noetige Informationen der Dokumentation entnommen habe. Momentan printe ich einfach alle Moebel in JSON Form, in
   Zukunft muss ich natuerlich noch alle bestehenden Moebel loeschen und die neuen Moebel in die Leinwand laden, um die
   lade() Methode zu vervollstaendigen.


>> Wed Nov 14 21:20:41 2018

   Um die lade() Methode zu verfollstaendigen, habe ich nun die ladeMoebel() Methode zur Leinwand Klasse hinzugefuegt. Darin
   werden zunaechst alle bisherigen Figuren aus der Zeichenflaeche geloescht, indem ich der "figuren"-Variable einfach ein neues
   leeres Array zuweise. Dannach iteriere ich einfach ueber das, der Funktion uebergebene, Array von Moebeln und fuege jedes
   Moebel bzw. seine Shape zu der "figuren"- und der "figurZuShape"-Variable hinzu und rufe dannach die erneutZeichnen() Methode
   auf.
   
   Ausserdem fange ich gerade mit der Arbeit an einer Menueleiste an, wobei ich auf https://www.java-tutorial.org/jmenu.html
   und https://docs.oracle.com/javase/tutorial/uiswing/components/menu.html viele Wichtige Infos darueber finden konnte.
   Vorerst muss ich mir aber noch ueberlegen, welche Funktionen ich eigentlich genau in das Menue einbauen will, bevor ich
   daran weiter arbeite...
   
   
>> Wed Nov 14 22:36:59 2018

   Aehnlich, wie wir auch bereits im Unterricht besprochen haben, habe ich meine Auswahl Funktion auch noch etwas verbessert.
   Ich habe zuvor noch mit letzteFarbe gearbeitet, aber habe nun auch zu einem boolean istAusgewaehlt getauscht, da dieses
   Vorgehen doch etwas uebersichtlicher und einfacher zu verstehen ist.
   
   
>> Fri Nov 16 18:33:40 2018

   Die Speicherfunktion ist nun groestenteils fertig. Alle Schritte beim Speichern und Laden benutzen das "optionen"-Array in
   der entsprechenden Moebelklasse, um auf Werte zuzugreifen, diese als JSON zu speichen und dann aber auch wieder zu parsen.
   Somit ist der gesamte Speicherprozess sehr flexibel, da einzig die Optionen im Moebel angepasst werden muessen, wenn man 
   weitere hinzufuegen oder entfernen will. Allerdings gibt es dabei immernoch ein Problem, welches ich bisher nicht loesen
   konnte. Wenn ich z.B. zum speichern auf die Optionen zugreifen will, welche static in der Moebelklasse sind, sind diese
   aus einem mir noch unerfindlichem Grund noch nicht initialisiert und somit null. Das gleiche Problem besteht auch bei der
   "art"-Variable, weshalb der gesamte Prozess nur mit umstaendlichen hilfsfunktionen und unnoetigem extra Code funktioniert.
   Ich werde mich schlau machen muessen, warum die Variablen noch nicht inizialisiert worden sind und wie man dies beheben koennte.
   
   Ausserdem habe ich die JSON Serialisation von der toJSON() Methode in der Moebel Klasse zur serialize<T extends Moebel>()
   Methode in der JSONSpeicherDelegate verlegt, da diese verantwortlich fuer das Speichern und somit auch fuer die Serialisation
   der Moebels sein sollte, was es deutlich einfacher macht, neue Moebel hinzuzufuegen, da sich der Autor einer Moebelklasse
   nicht auch noch um ein Teil des Speicherprozesses dieses Moebels kuemmern muessen sollte. Ermoeglicht wird dies auch durch
   die gibWert(String attributName) Methode, welche einen Wert einesAttributs, welches auch als Option vorliegt, als String
   wiedergibt.


>> Sat Nov 17 19:34:37 2018

   Die Moebel koennen nun auch aus einer Datei geladen werden. Dies passiert in der lade() Funktion in des JSONSpeicherDelegates.
   Zuerst wird der Inhalt einer Datei als String gespeichert und dann von der JSONArray Klasse der JAVASON Bibliothek geparsed.
   Die JSON Objekte werden in JSONArrayToMoebelArray() zu Moebel Objekten konvertiert und dann einzeln zu alleMoebel in GUI
   hinzugefuegt und mit zeige() sichtbar gemacht. Davor werden auch noch alle vorher bestehenden Moebel in der Leinwand mit
   loescheMoebel() geloescht. Anfaenglich habe ich versucht die Moebel direkt zu dem figuren-Array in der Leinwand hinzuzufuegen
   und dadurch sichtbar zu machen. Das Problem damit war, dass es dadurch zwei unterschiedliche Instanzen jedes Moebels einmal
   in der Leinwand Klasse und einmal in der GUI Klasse gab, wodurch ich nach dem Laden nicht mehr mit den Moebeln interagieren
   konnte (z.B. bewegen oder Farbe aendern), da diese Dinge von der GUI Klasse kontrolliert wurden und somit nur auf die Instanzen
   in dieser Klasse zugegriffen haben. Das Problem habe ich allerdings geloesst. Nun will ich eventuell noch ein Dateiauswahlfenster
   einbauen, da bisher immer die gleiche Datei geladen und geschrieben wird.
   
   
>> Sat Nov 17 20:34:15 2018

   Im Unterricht haben wir bereits ein Fehler in meinem Programm gefunden, welches auf Windows die Kontrollknoepfe nicht richtig anzeig
   und man deswegen die Beschriftungen nicht lesen kann, was natuerlich sehr schlecht ist, wenn man den Raumplaner bedienen moechte.
   Deshalb habe ich das statische Hinzufuegen der Knoepfe dynamischer gestaltet. Die Koordinaten der Knoepfe sind jetzt nicht mehr
   einprogrammiert, sondern dynamisch mithilfe von einer Breite und einem Abstand, in einem zweidimensionalen For Loop berechnet.
   
   
>> Thu Nov 22 22:42:05 2018

   Die MenuBar ist eigentlich sehr einfach zu implementieren. Es gibt eine Bar in der mehrere Menus leben, die selber jeweils
   verschiedene MenuItems beinhalten koennen. Beim pressen dieser MenuItems kann man eine Funktion aufrufen. Da bisher alle
   Funktionen, die den Raumplaner kontrollieren, in meiner GUI Klasse waren, muss ich diese nun zur Leinwand bewegen, wenn die
   Funktionen in der GUI Klasse privat bleiben sollen. Da die GUI aber sowieso durch Maus und Tastatursteuerung ersetzt werden
   soll, passt das ganz gut. Die MenuBar ist im Grunde genommen fertig und kann jederzeit einfach um neue Optionen und Funktionen
   erweitert werden, das Geruest steht.
   
   Als naechstes werde ich mich an der Maussteuerung versuchen, aber zunaechst habe ich noch einige KeyListener hinzugefuegt, um
   zum Beispiel die Moebel auszuwaehlen oder zu bewegen.
   
   
>> Thu Dec 6 14:58:56 2018

   Sowohl die Keyboard- als auch die Maussteuerung sind online relativ gut beschrieben. auf docs.oracle.com findet man eigentlich alle
   wichtigen infos. Das empfangen von Key- und Mouse-Events erfolgt dabei einfach durch das implementieren der Key- bzw. MouseListener-
   Klasse oder durch ein andocken an einen entsprechenden Adapter. Das andocken funktioniert hier ueber Vererbung, weshalb man sich soweit
   ich weiss trivial nur an ein Adapter anbinden kann, da Java bloss eine Vererbung pro Klasse zulaesst. Ich koennte weitere Klassen
   erstellen, um das Problem zu loesen, aber ich denke es ist einfacher mehrere Interfaces zu implementieren.
   
   Bisher habe ich einige Keyboard short cuts eingebaut, wie ctr+s um den Raum zu sichern. Dabei versuche ich soweit es geht, die implementierung
   dieser Funktionalitaet in externe Funktionen zu verschieben, um nicht 100 unuebersichtliche Zeilen Code in der KeyPressed() Funktion
   zu haben, welche jedes mal aufgerufen wird, wenn eine Taste gedrueckt wird.
   
   Bei dem MausEvent kann ich mir einfach die Position der Maus anzeigen lassen und habe mal versucht mit einer Schleife durch alle Moebel
   zu laufen und mit contains() zu testen, ob der click in einem Moebel im Raum ist. Dabei habe ich bemerkt, dass es Probleme mit 
   manchen Moebeln gibt. Zum Beispiel reagiert der Stuhl nur dann, wenn man auf seine Lehne clickt, nicht aber auf die Sitzflaeche...
   In Betracht dessen werde ich noch ein paar Tests durchfuehren muessen und untersuchen muessen, wie ein Shape funktioniert.


>> ???

   Ziel: Auswaehlen von Moebeln durch Maus-Click
   Plan: Maus-Position bei Click einlesen und mit contains() fuer alle moebel untersuchen, ob der Click auf ein Moebel war
   Problem: Komisches Verhalten: manche Moebel funktionieren, andere lassen sich nur in bestimmten Teilen anklicken, nochmal andere garnicht
   Lösungsweg: bei jeder Mausbewegung die Koordinaten und die Treffer mit Moebeln anzeigen lassen, um besser nach Hitboxen suchen zu koennen
   Lösung: Dabei habeich entdeckt, dass die Koordinaten verschoben waren, weil ich die MouseListener nicht der Zeichenflaeche, sondern
   dem Fester hinzugefuegt habe. Nach aendern dieses Codes funktioniert alles wie geplant.
   Weiteres entdecktes Problem: wenn Moebel uebereinander liegen, werden alle moebel gedragged und somit kann man sie nicht mehr auseinander ziehen
   Lösung: sobald ein Moebel entdeckt wird, beende ich die schleife, die noch alle weiteren moebel überprüft hätte....
   Code: 
      1  public void mousePressed(MouseEvent me) {
      2      System.out.println("Mouse pressed");
      3      for (Moebel moebel : alleMoebel) {
      4          if (moebel.gibAktuelleFigur().contains(me.getX(), me.getY())) {
      5              System.out.println("Pressed on " + moebel + " at " + me.getX() + ", " + me.getY());
      6              moebel.istSchwebend = true;
neu > 7              break;
      8          }
      9      }
      10 }
        
        
>> Fri Dec 7 15:25:52 2018

   Ziel: beim hinzufuegen von neuen moebeln sollen diese "Schwebem", sodass sie der maus folgen, bis man auf den bildschirm clickt und sie "abgesetzt" werden
   Plan: neue Variable in Moebel: istSchwebend, die von anfang an true ist. Dann in mouseMoved die ganze zeit (x, y) = (mouseX, mouseY) und in mousePressed istSchwebend = false
   Problem 1: Die moebel folgen nicht der maus, sondern erscheinen erst, sobald man clickt
   Loesung 2: bei jeder mausbewegung muss die leinwand neu gezeichnet werden, um die veraenderung in der position anzuzeigen, also noch repaint() hinzufuegen
   Problem 2: siehe oben
   Problem 3: Beim oeffnen von datein werden alle moebel normal hinzugefuegt und folgen somit alle der maus...
   Loesung 3: beim laden in ladeMoebel() einfach im loop moebel.istSchwebend = flase;


>> auch freitag ^^ paar commits nachher

   Ziel: Drag and Drop natuerlicher gestalten -> offset
   zugrundeliegendes Problem: drag and drop unnatuerlich und unintuitiv
   loesung: neue variablen fuer offset, beim anfang vom drag, also wenn die maus gepresst wird den offset fuer diesen drag festlegen und dann waehrend
            des drags in mouseDragged offset bei der bewegung mit einberechnen
   code:
      1  private int dragXOffset, dragYOffset = 0;
      
      1  public void mousePressed(MouseEvent me) {
      2      for (Moebel moebel : alleMoebel) {
      3          if (moebel.gibAktuelleFigur().contains(me.getX(), me.getY())) {
neu > 4              dragXOffset = me.getX() - moebel.xPosition;
neu > 5              dragYOffset = me.getY() - moebel.yPosition;
      6              moebel.istSchwebend = true;
      7              break;
      8           }
      9       }
      10 }

      1  public void mouseDragged(MouseEvent me) {
      2      for (Moebel moebel : alleMoebel) {
      3          if (moebel.istSchwebend) {
      4              moebel.loesche();
neu > 5              moebel.xPosition = me.getX() - dragXOffset;
neu > 6              moebel.yPosition = me.getY() - dragYOffset;
      7              moebel.zeichne();
      8          }
      9      }
      10 }


TODO:

  -beim laden dürfen neue möbel nicht standart mäßig schwebend sein!!! eventuell durch init overloading??
  -irgendwie die mausposition im verhältnis zum ursprung des Moebels mit in die Drag and Drop Funktion einberechnen. 
      momentan wird einfach der Ursprung des Moebels zur maus position gesetzt......
  -Drag and Drop nur so weit erlauben, dass sich moebel nicht ueberschneiden koennen: https://stackoverflow.com/questions/15690846/java-collision-detection-between-two-shape-objects
  -show help in Raumplaner menu -> bedinung durch tasten maus und GUI
  -Kommentieren
  -Maus- und Menu-Steuerung und allgemeine UX Verbesserungen...
  -shortcuts? (z.B. shift A to add a moebel...)
  -eventuell noch weitere moebel...
	-Aenderug der Groesse der Zeichenflaeche? (auch in der Datei speichern..?)
