package $package$.application.projections

import akka.actor.typed.{ActorSystem, Behavior}
import akka.actor.typed.scaladsl._
import akka.persistence.jdbc.query.scaladsl.JdbcReadJournal
import akka.persistence.query.Offset
import akka.projection.{ProjectionBehavior, ProjectionId}
import akka.projection.eventsourced.EventEnvelope
import akka.projection.eventsourced.scaladsl.EventSourcedProvider
import akka.projection.scaladsl.SourceProvider
import akka.projection.slick.SlickProjection
import slick.basic.DatabaseConfig
import slick.jdbc.PostgresProfile
import scala.concurrent.ExecutionContext

import $package$.common.DomainEvent

object EventProjections {
  def apply(databaseConfig: DatabaseConfig[PostgresProfile]): Behavior[String] =
    Behaviors.setup[String] { context =>
      implicit val executionContext: ExecutionContext =
        context.system.executionContext
      implicit val system: ActorSystem[_] = context.system

      // Use this event source provider for publishing domain events to the read model.
      val domainEventSourceProvider
          : SourceProvider[Offset, EventEnvelope[DomainEvent]] =
        EventSourcedProvider.eventsByTag[DomainEvent](
          system = context.system,
          readJournalPluginId = JdbcReadJournal.Identifier,
          tag = "domain-event"
        )

      // Use this event source provider for publishing integration events to Kafka or a similar broker.
      val integrationEventSourceProvider
          : SourceProvider[Offset, EventEnvelope[DomainEvent]] =
        EventSourcedProvider.eventsByTag[DomainEvent](
          system = context.system,
          readJournalPluginId = JdbcReadJournal.Identifier,
          tag = "integration-event"
        )

        //TODO: Add event projections here.
        // val myProjection = SlickProjection.exactlyOnce(
        //     projectionId = ProjectionId("my-projection", "domain-event"),
        //     sourceProvider = domainEventSourceProvider,
        //     handler = () => new MyProjectionHandler(databaseConfig),
        //     databaseConfig = databaseConfig
        // )

        //TODO: Spawn actors for the projections
        // context.spawn(ProjectionBehavior(myProjection), myProjection.projectionId.id)

        Behaviors.empty
    }
}
