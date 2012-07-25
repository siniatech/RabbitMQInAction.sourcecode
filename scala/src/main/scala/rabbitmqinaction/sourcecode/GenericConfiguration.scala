/**
 * RabbitMQ in Action - Generic constants
 *
 * @author Simon Fraser, Siniatech Ltd 
 */
package rabbitmqinaction.sourcecode

object GenericConfiguration {
  val Active = false
  val Passive = false
  val Durable = true
  val NonDurable = false
  val AutoDelete = true
  val NonAutoDelete = false
  val DirectExchangeType = "direct"
  val PlainContentType = "text/plain"
  val EmptyJavaMap = new java.util.HashMap[String, Object]
  def host = "localhost"
}