package $package$.integration.rest

import akka.actor.typed.{ActorRef, ActorSystem}
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.util.Timeout

import scala.concurrent.duration._
import scala.util.{Failure, Success}

/** Implements the routing logic for the REST interface
  *
  * @param system
  *   The actor system to work with
  */
class Router()(implicit val system: ActorSystem[_]) {
  // These imports ensure that JSON serialization/deserialization works
  import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
  import io.circe.generic.auto._

  // These imports add support for the ask pattern
  import akka.actor.typed.scaladsl.AskPattern.schedulerFromActorSystem
  import akka.actor.typed.scaladsl.AskPattern.Askable

  implicit val timeout: Timeout = 30.seconds

  /** Defines the routes for the application. You can use various Akka HTTP
    * directives here. Please review the docs to learn more:
    *
    * https://doc.akka.io/docs/akka-http/current/routing-dsl/directives/index.html
    */
  lazy val routes: Route = get {
    complete("Hello world")
  }
}
