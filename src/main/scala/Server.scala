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

    case Email(msg : Message) =>

      /* find = true se presente */
      var find = false;
      val src = msg.getSrcAddr()
      val dst = msg.getDstAddr()
      log.info("Ricevuta Email da " + src)

      users.foreach((x : (String ,ActorRef)) =>



        if(  dst == x._1   ) {
        log.info("Mando Email a " + dst)
          x._2 ! Email(msg)
          find = true
        }
        else if(!find)  {
          find = false;

        }
      )
        if(!find) {
          log.info("Destinatario " + dst + " non presente ")
          val msg = new Message(src + "&" + dst + "&" + "Destinatario Sconosciuto" + "&")

          val email = Email(msg)
          sender() ! email
        }


    case Ack(srcAddr,dstAddr) =>
      log.info("Ricevuto ACK da " + srcAddr)

      var find = false
      users.foreach((x : (String ,ActorRef)) =>


        if(  dstAddr == x._1) {
          find = true
          log.info("Mando Ack a " + dstAddr)
          x._2 ! Ack(srcAddr,dstAddr)
        }
        else if(!find) {
           find = false
        }
      )
      if(!find)
        log.info("Destinatario  dell' ACK " + dstAddr + " non presente ")


    // Mandare ack a destinatario ( mittente dell'email )



  }
}
