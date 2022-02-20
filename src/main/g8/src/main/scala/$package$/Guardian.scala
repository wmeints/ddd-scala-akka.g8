package $package$

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

import $package$.integration.rest.{Server, ServerSettings}

/**
  * The root guardian controls all other actors in the application.
  * We use the guardian to control the error boundaries for various pieces of the application.
  */
object Guardian {
    /**
      * Initializes the behavior of the guardian
      * @return The guardian behavior
      */
    def apply(): Behavior[String] = Behaviors.setup[String] { context => 

        context.spawn(Server(ServerSettings("0.0.0.0", 8080)), "http-server")

        Behaviors.empty        
    }
}