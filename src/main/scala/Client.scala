import akka.actor.{Actor, ActorRef, Props}
import akka.event.Logging

/* Client Actor che ha:
   - Un id corrispondente all' Email
   - Un ActorRef (ovvero il Server )
   - Una lista delle mail ricevute
* */

object Client {
  def props(id: String , server : ActorRef  ): Props = Props(new Client(id , server))
}

class Client(id : String , server : ActorRef) extends Actor {

  /* Lista mail ricevute */

 var inbox = List[Email]()

 val log = Logging(context.system,this)

  override def receive: Receive = {


    /* Gestione della connessione */
    case  ConnectionRequest(id) =>
      log.info("Inizializzazione della Connessione")
      server ! ConnectionRequest(id)
    case ConnectionSuccess=>
      log.info("Connessione Riuscita")
      // WriteMAIL
      //Verify Mail
      // server ! Email

    case ConnectionFail =>
      log.info("Connessione Fallita")

      /* Email */

    case Email(destAddr, srcAddr)=>
      log.info("Ricevuta Email  da " + srcAddr )
      inbox =  Email(destAddr,srcAddr) :: inbox
      log.info("Aggiunta ad Inbox")
      viewInbox

    case Ack =>
      log.info("Ricevuto ACK")

  }

  /* Funzione che printa le mail */
  def viewInbox : Unit = {
    println("EMAIL RICEVUTE")
    inbox.foreach((email : Email)=>println(email.toString))

  }

}