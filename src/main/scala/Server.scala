import java.io.File

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.event.Logging
import com.typesafe.config.ConfigFactory

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
      if(!users.contains(id)) {
        users = users + (id -> sender())
        log.info("Connessione riuscita " +id)
        sender() ! ConnectionSuccess
      }
      /* Se l'indirizzo email è già presente manda un connection Fail...*/
      else {
        log.info("indirizzo email già presente"+ id)
        sender()! ConnectionFail
      }



    /*Verificare che il destinatario sia in users
    *   In caso positivo recupera ActorRef e manda l'email
    *    In caso negativo manda un email con oggetto
    *     "Destinatario sconosciuto*/

    case Email(msg : Message) =>



      val src = msg.getSrcAddr()
      val dst = msg.getDstAddr()
      log.info("Ricevuta Email da " + src)
      // verifica la presenza della destinazione
      if( users.contains(dst)) {
        val destination= users.get(dst).get
        log.info("Mando Email a " + dst)
        destination ! Email(msg)

      }
      else {
        log.info("Destinatario " + dst + " non presente ")
        val msg = new Message(src + "&" + dst + "&" + "Destinatario Sconosciuto" + "&")
        val email = Email(msg)
        sender() ! email
      }


    case Ack(srcAddr,dstAddr) =>
      log.info("Ricevuto ACK da " + srcAddr)
      // verifica la presenza della destinazione
      if( users.contains(dstAddr)) {
        val destination= users.get(dstAddr).get
        log.info("Mando Email a " + dstAddr)
        destination ! Ack(srcAddr,dstAddr)
      }
      else {
        log.info("Destination "+dstAddr +" not Found" )
      }




  }
}
  object ServerMain {
    def main(args: Array[String]) {

      val configFile = getClass.getClassLoader.getResource("remote_configuration.conf").getFile
      //parse the config
      val config = ConfigFactory.parseFile(new File(configFile))
      //create an actor system with that config
      val system = ActorSystem("RemoteSystem", config)
      //create a remote actor from actorSystem
      val remote = system.actorOf(Server.props(), name = "server")
    }
  }

