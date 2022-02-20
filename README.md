# Domain driven design with Akka and Scala project template

Welcome to this repository, here you can find a project template for a DDD (domain-driven design) style project with
Akka and Scala. 

I created this template to help myself build a sample application called recommend-coffee. I was building each service
by hand, and that took an awful lot of time to set up. So I decided to turn my boilerplate into a template to speed
things up a bit.

## Using this template

You can create a new instance of this template by invoking the following command:

```shell
sbt new wmeints/ddd-scala-akka.g8
```

After creating the project you can run it by executing the following command from the root of the generated project:

```shell
sbt run
```

## Contents of this template

This template generates a project structure that looks like this:

```
├── build.sbt
├── project
│   └── build.properties
└── src
    └── main
        └── scala
            └── <package>
                ├── Main.scala                      # The application entrypoint
                ├── Guardian.scala                  # The root guardian for the actor system
                ├── common                          # Contains shared logic for the app
                ├── domain                          # Contains the domain layer
                │   ├── services                    # Contains the domain services for the application
                │   └── aggregates                  # Contains the root aggregates for the application
                ├── application                     # Contains the logic for the application layer
                │   └── projections                 # Contains the projection handlers for the read model
                └── integration                     # Contains the infrastructure/integration layer
                    └── rest                        # Contains the logic for the REST interface
                        ├── Router.scala            # The router for the HTTP endpoints
                        ├── Server.scala            # The HTTP server implementing the REST interface
                        └── ServerSettings.scala    # The settings for the HTTP server
```
