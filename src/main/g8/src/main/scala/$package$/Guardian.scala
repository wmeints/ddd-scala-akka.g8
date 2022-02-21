package $package$

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import slick.jdbc.PostgresProfile
import slick.basic.DatabaseConfig

import $package$.integration.rest.{Server, ServerSettings}
import $package$.application.projections.EventProjections

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
        val databaseConfig: DatabaseConfig[PostgresProfile] = DatabaseConfig.forConfig(path = "slick")

        context.spawn(EventProjections(databaseConfig), "event-projections")
        context.spawn(Server(ServerSettings("0.0.0.0", 8080)), "http-server")

        Behaviors.empty        
    }
}