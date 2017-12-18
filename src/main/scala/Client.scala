import java.io.File

import akka.actor.{Actor, ActorRef, ActorSelection, ActorSystem, Props}
import akka.event.Logging
import com.typesafe.config.ConfigFactory

/* Client Actor che ha:
   - Un id corrispondente all' Email
   - Un ActorRef (ovvero il Server )
   - Una lista delle mail ricevute
* */
case class ViewInbox()
object Client {
  def props( id: String , server : ActorSelection  ): Props = Props(new Client(id , server))



}

class Client(id : String , server : ActorSelection) extends Actor {

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
      log.info("Connessione Fallita cambia indirizzo")

      /* Email */

      /* Email ricevuta */
    case Email(msg)=>
      val src = msg.getSrcAddr()
      val dst = msg.getDstAddr()

      log.info("Ricevuta Email  da " + src  )
       /* Aggiunge a Inbox*/
      inbox =  Email( msg) :: inbox
      log.info("Aggiunta ad Inbox , invio ACK")
      viewInbox
        /* Invia ACK */
      server ! Ack(id,src)

    case Ack(srcAddr, dstAddr) =>
      log.info("Ricevuto ACK da " + srcAddr  )
    case ViewInbox =>
      viewInbox()

  }

  /* Funzione che printa le mail */
  def viewInbox() : Unit = {
    println("EMAIL RICEVUTE: ")
    inbox.foreach((email: Email) => println(email.toString))
  }



}
object ClientMain {
  def main(args: Array[String]) {
    val configFile = getClass.getClassLoader.getResource("client_configuration.conf").getFile
    val config = ConfigFactory.parseFile(new File(configFile))
    val system = ActorSystem("ClientSystem", config)
    val serverActor = system.actorSelection("akka.tcp://RemoteSystem@127.0.0.1:2552/user/server")

    println("Inserisci email")
    val id = scala.io.StdIn.readLine()
    if(Message.verifyEmail(id))  {
      val clientActor = system.actorOf(Client.props(id, serverActor), name = "client")
      clientActor ! ConnectionRequest(id)
    }

    println("Inserisci Email : (sorgente, destinazione, oggetto , testo) <separatore &> ")
    val txt = scala.io.StdIn.readLine()
    val msg = new Message(txt)
    if(msg.verifyMessage())
    {
      val email = new Email(msg)
      serverActor ! email
    }


  }
}


