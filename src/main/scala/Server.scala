import akka.actor.{Actor, ActorRef, Props}
import akka.event.Logging

object Server {

}

class Server() extends Actor {
  val log = Logging(context.system , this)

 var userList : List[ActorRef] = null

  override def receive: Receive = {
// Connessione Riuscita
    case ConnectionRequest =>
      log.info("Ricevuta richiesta di connessione da" + sender() )
      userList = sender() :: userList
      sender() ! ConnectionSuccess

      /* Implementare connessione non riuscita...*/

    case Email() =>

      // Verificare che il destinatario sia in userList
    // if true mandare email
    // if false mandare email con oggetto" Destinatario Sconosciuto "

    case Ack =>
      // Mandare ack a destinatario ( mittente dell'email )



  }
}
