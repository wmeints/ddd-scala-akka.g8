package $package$.integration.rest

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, ActorSystem, Behavior, PostStop}
import akka.http.scaladsl.Http
import scala.concurrent.Future
import scala.util.{Success, Failure}

/** Implements the server behavior for the microservice
  */
object Server {
  sealed trait Message

  case object Stop extends Message  

  private final case class StartFailed(cause: Throwable) extends Message

  private final case class Started(binding: Http.ServerBinding) extends Message

  /** Initializes the behavior of the server
    * @param settings
    *   The settings for the server
    */
  def apply(settings: ServerSettings): Behavior[Message] =
    Behaviors.setup[Message] { context =>
      implicit val system: ActorSystem[_] = context.system

      val router = new Router()

      val serverBinding: Future[Http.ServerBinding] = Http()
        .newServerAt(settings.interface, settings.port)
        .bind(router.routes)

      context.pipeToSelf(serverBinding) {
        case Success(binding) => Started(binding)
        case Failure(ex)      => StartFailed(ex)
      }

      def running(binding: Http.ServerBinding): Behavior[Message] =
        Behaviors
          .receiveMessagePartial[Message] { case Stop =>
            context.log.info("Stopping server")
            Behaviors.stopped
          }
          .receiveSignal { case (_, PostStop) =>
            binding.unbind()
            Behaviors.same
          }

      def starting(wasStopped: Boolean): Behaviors.Receive[Message] =
        Behaviors.receiveMessage[Message] {
          case StartFailed(cause) =>
            throw new RuntimeException("Server failed to start", cause)
          case Started(binding) =>
            context.log.info(
              "Server started at http://{}:{}",
              binding.localAddress.getHostString,
              binding.localAddress.getPort
            )

            if (wasStopped) context.self ! Stop

            running(binding)
          case Stop =>
            starting(wasStopped = true)
        }

      starting(wasStopped = false)
    }
}
