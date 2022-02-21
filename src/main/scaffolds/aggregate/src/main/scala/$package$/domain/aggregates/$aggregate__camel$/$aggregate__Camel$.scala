package $package$.domain.aggregates.$aggregate;format="camel"$

import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.scaladsl.Behaviors
import akka.persistence.typed.PersistenceId
import akka.persistence.typed.scaladsl.{Effect, EventSourcedBehavior, ReplyEffect}

import $package$.common.{CborSerializable, DomainEvent}

object $aggregate;format="Camel"$ {
    trait Command
    
    trait Reply
    
    trait Event extends DomainEvent

    case object OK extends Command

    trait State {
        def executeCommand(cmd: Command): ReplyEffect[CatalogEvents.Event, Option[State]]

        def applyEvent(evt: CatalogEvents.Event): State
    }

    def apply(id: String): Behavior[Command] = Behaviors.setup { context =>
        val executeInitialCommand: Command => ReplyEffect[Event, Option[State]] = {
            //case ... => - Translate the command to an event and return a reply!
        }

        val commandHandler: (Option[State], Command) => ReplyEffect[Event, Option[State]] = { (state, command) =>
            state match {
                case Some(s) => s.executeCommand(command)
                case None => executeInitialCommand(command)
            }
        }

        // This event handler produces the initial state.
        val applyInitialEvent: Event => State = { 
            // case ... => - This should map to your event and return a state!
        }

        val eventHandler: (Option[State], Event) => Option[State] = { (state, event) =>
            state match {
                case Some(s) => Some(s.applyEvent(event))
                case None => Some(applyInitialEvent(event))
            }
        }

        EventSourcedBehavior.withEnforcedReplies[Command, Event, Option[State]](
            persistenceId = PeristenceId.ofUniqueId(id),
            emptyState = None,
            eventHandler = eventHandler,
            commandHandler = commandHandler
        )
    }
}