import akka.actor.{Actor, ActorRef, Props}
import akka.event.Logging

object Server {
  def props( ) :
  Props  = Props (new  Server())

}

class Server() extends Actor {
  val log = Logging(context.system , this)

 var users = Map[String,ActorRef]()

  override def receive: Receive = {
// Connessione Riuscita
    case ConnectionRequest(id) =>
      log.info("Ricevuta richiesta di connessione da " + id )
      users = users +  (id -> sender())
      sender() ! ConnectionSuccess

      /* Implementare connessione non riuscita...*/



    /*Verificare che il destinatario sia in users
    *   In caso positivo recupera ActorRef e manda l'email
    *    In caso negativo manda un email con oggetto
    *     "Destinatario sconosciuto*/

    case Email(destAddr,srcAddr) =>

      log.info("Ricevuta Email da " + srcAddr)
      users.foreach((x : (String ,ActorRef)) =>
        if(  destAddr.equals(x._1)) {
        log.info("Mando Email a" + destAddr)
          x._2 ! Email(destAddr, srcAddr)
        }
        else{
          log.info("Destinatario " + destAddr + " non presente ")
          val email = new Email(destAddr,srcAddr)
          email.subject ="Destinatario Sconosciuto".toCharArray
          sender() ! email
        } )


    case Ack =>
      log.info("Ricevuto ACK")
      // Mandare ack a destinatario ( mittente dell'email )



  }
}
