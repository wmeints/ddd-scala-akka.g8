package $package$.integration.rest

/** The settings for the HTTP server
  *
  * @param interface
  *   The interface to bind to
  * @param port
  *   The port to bind to
  */
case class ServerSettings(interface: String, port: Int)
