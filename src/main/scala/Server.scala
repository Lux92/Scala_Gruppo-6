import akka.actor.{Actor, ActorRef, Props}
import akka.event.Logging


class Server() extends Actor {
  val log = Logging(context.system , this)

 var users = Map[String,ActorRef]()

  override def receive: Receive = {
// Connessione Riuscita
    case ConnectionRequest(id) =>
      log.info("Ricevuta richiesta di connessione da" + sender() )
      users = users +  (id -> sender())
      sender() ! ConnectionSuccess

      /* Implementare connessione non riuscita...*/



    /*Verificare che il destinatario sia in users
    *   In caso positivo recupera ActorRef e manda l'email
    *    In caso negativo manda un email con oggetto
    *     "Destinatario sconosciuto*/

    case Email() =>
      users.foreach((x : (String ,ActorRef)) =>
        if(  Email().destAddr.equals(x._1))
        x._2 ! Email()else{
          sender() ! Email().subject = "Destinatario Sconosciuto"
        } )


    case Ack =>
      // Mandare ack a destinatario ( mittente dell'email )



  }
}
