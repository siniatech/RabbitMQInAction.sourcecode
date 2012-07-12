/**
 * RabbitMQ in Action - Chapter 2 Examples
 *
 * @author Alvaro Videla (original)
 * @author Jason J. W. Williams (original)
 * @author Simon Fraser, Siniatech Ltd (translation)
 */
package rabbitmqinaction.sourcecode.chapter2

object Chapter2Configuration {
  val RoutingKey = "hola"
  val Username = "guest"
  val Password = "guest"
  val Exchange = "hello-exchange"
  val QueueName = "hello-queue"
  val ConsumerTag = "hello-consumer"
}