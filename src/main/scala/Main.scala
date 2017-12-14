import akka.actor.ActorSystem


object Main extends App {

  val system = ActorSystem("ex1")

   val id1: String = "alice@test.com"
  val id2: String = "bob@test.com"

  val server = system.actorOf(Server.props(),name = "server")
  val client = system.actorOf(Client.props(id1 , server),name = "client")
  val client1 = system.actorOf(Client.props(id2, server), name = "client1")


  client ! ConnectionRequest(id1)
  client1 ! ConnectionRequest(id2)


  Thread.sleep(1000)

  val msg = new Message("alice@test.com" + "&" + "bob@test.com" + "&" + "Test" + "&" + "body Test")
  val email = new Email(msg)


  server ! email


}
