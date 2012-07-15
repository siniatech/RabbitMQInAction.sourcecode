/**
 * RabbitMQ in Action - Chapter 2 Examples
 *
 * @author Alvaro Videla (original)
 * @author Jason J. W. Williams (original)
 * @author Simon Fraser, Siniatech Ltd (translation)
 */
package rabbitmqinaction.sourcecode.chapter2

import com.rabbitmq.client.AMQP.BasicProperties
import com.rabbitmq.client.Channel
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DefaultConsumer
import com.rabbitmq.client.Envelope

import Chapter2Configuration.ConsumerTag
import Chapter2Configuration.Exchange
import Chapter2Configuration.Password
import Chapter2Configuration.QueueName
import Chapter2Configuration.RoutingKey
import Chapter2Configuration.Username
import rabbitmqinaction.sourcecode.GenericConfiguration.Active
import rabbitmqinaction.sourcecode.GenericConfiguration.DirectExchangeType
import rabbitmqinaction.sourcecode.GenericConfiguration.Durable
import rabbitmqinaction.sourcecode.GenericConfiguration.EmptyJavaMap
import rabbitmqinaction.sourcecode.GenericConfiguration.NonAutoDelete
import rabbitmqinaction.sourcecode.GenericConfiguration.host

object Consumer {
  def main(args: Array[String]) {
    val factory = new ConnectionFactory
    factory.setUsername(Username)
    factory.setPassword(Password)
    factory.setHost(host)

    val connection = factory.newConnection();

    val channel = connection.createChannel();
    channel.exchangeDeclare(Exchange, DirectExchangeType, Active, Durable, NonAutoDelete, EmptyJavaMap);
    channel.queueDeclare(QueueName, Active, Durable, NonAutoDelete, null);
    channel.queueBind(QueueName, Exchange, RoutingKey, EmptyJavaMap);
    channel.basicConsume(QueueName, false, ConsumerTag, new ConsumerCallback(channel));
  }
}

class ConsumerCallback(channel: Channel) extends DefaultConsumer(channel) {
  override def handleDelivery(consumerTag: String, envelope: Envelope, props: BasicProperties, body: Array[Byte]) {
    getChannel.basicAck(envelope.getDeliveryTag, false)
    val msg = new String(body)
    msg match {
      case "quit" => {
        getChannel.basicCancel(consumerTag)
        getChannel.close
        getChannel.getConnection.close
        System.exit(1)
      }
      case _ => println(msg);
    }
  }
}