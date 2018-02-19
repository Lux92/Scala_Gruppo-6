<h1><b>Progetto di Advanced Programming Language</b> </h1> 
<h2>
Anno 2017-2018 <br>
Linguaggio Scala
</h2>
<b> Gruppo 6 </b> <br>
Limina Luciano <br>
Longhitano Andrea 

<br>
<br>
Realizzazione di un sistema di gestione email tramite l'utilizzo di Akka actor in Scala.

<h3>Contenuto </h3> 
Il progetto si compone di 3 file .scala: <br>
Client.scala <br>
Message.scala <br>
Server.scala <br>

<h3>Istruzioni </h3>
<h4> Dipedendenze </h4>
Per la realizzazione di questo progetto sono stati utilizzate le seguenti librerie:

akka-actor"  versione "2.4.20" <br>
"akka-remote" versione "2.4.20" 

Per tanto scaricarle tramite il file di configurazione build.sbt


Avviare il Server mandando in run il file Server.scala . <br>
Avviare uno o più Client mandando in run il file Client.scala .


All'avvio del client:

1) Assegnare un email identificativa ad un client, una volta assegnata il client tenterà la connessione al server.

2) A connessione riuscita scrivere un email con i seguenti campi separati da '&' :
	1) Indirizzo Sorgente ( formato example@example.ex )
	2) Indirizzo Destinatario
	3) Oggetto ( max 64 caratteri )
	4) Corpo   (max 255 caratteri) 

3) Se l'email è corretta viene inviata al server che controlla il destinatario ed effettua la consegna*.

4) Se il destinatario è connesso al server verrà ricevuto un ACK

*In caso in cui il destinatario non è connesso viene inviato un messaggio "Destinatario sconosciuto"

Nei log delle rispettive classi è possibile osservare il traffico durante l'esecuzione.
