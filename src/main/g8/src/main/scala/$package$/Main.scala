package $package$

import akka.actor.typed.ActorSystem

/** The main entrypoint for the application. This will start the guardian that
  * controls the lifetime of all actors in the application.
  */
object Main extends App {
  ActorSystem(Guardian(), "$name$")
}
