/**
 * RabbitMQ in Action - Chapter 2 Examples
 *
 * @author Alvaro Videla (original)
 * @author Jason J. W. Williams (original)
 * @author Simon Fraser, Siniatech Ltd (translation)
 */
package rabbitmqinaction.sourcecode.chapter2

import com.rabbitmq.client.AMQP
import com.rabbitmq.client.ConfirmListener
import com.rabbitmq.client.ConnectionFactory

import Chapter2Configuration.Exchange
import Chapter2Configuration.Password
import Chapter2Configuration.RoutingKey
import Chapter2Configuration.Username
import rabbitmqinaction.sourcecode.GenericConfiguration.PlainContentType
import rabbitmqinaction.sourcecode.GenericConfiguration.host

object ProducerWithConfirms {

  def main(args: Array[String]) {
    if (args.length != 1) {
      System.err.println("Message body must be supplied");
      System.exit(1);
    }

    val factory = new ConnectionFactory
    factory.setUsername(Username)
    factory.setPassword(Password)
    factory.setHost(host)

    val connection = factory.newConnection

    val msg = args.head
    val msgPropertiesBuilder = new AMQP.BasicProperties.Builder
    msgPropertiesBuilder.contentType(PlainContentType)
    val msgProperties = msgPropertiesBuilder.build

    val channel = connection.createChannel
    channel.confirmSelect
    channel.addConfirmListener(ConfirmHandler);
    channel.basicPublish(Exchange, RoutingKey, msgProperties, msg.getBytes)
    channel.waitForConfirmsOrDie

    channel.close
    connection.close
  }
}

object ConfirmHandler extends ConfirmListener {
  override def handleAck(deliveryTag: Long, multiple: Boolean) {
    println("Confirm receipt")
  }

  override def handleNack(deliveryTag: Long, multiple: Boolean) {
    println("Message lost");
  }
}




