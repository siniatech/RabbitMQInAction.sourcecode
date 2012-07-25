/**
 * RabbitMQ in Action - Chapter 2 Examples 
 * 
 * @author Alvaro Videla (original)
 * @author Jason J. W. Williams (original)
 * @author Simon Fraser, Siniatech Ltd (translation)
 */
package rabbitmqinaction.sourcecode.chapter2;

import static rabbitmqinaction.sourcecode.GenericConfiguration.*;
import static rabbitmqinaction.sourcecode.chapter2.Chapter2Configuration.*;

import java.io.IOException;
import java.util.HashMap;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class ConsumerWithConfirms {

    public static void main( String[] args ) throws IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername( USERNAME );
        factory.setPassword( PASSWORD );
        factory.setHost( HOST );

        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();
        channel.exchangeDeclare( EXCHANGE, DIRECT_EXCHANGE_TYPE, ACTIVE, DURABLE, NON_AUTO_DELETE, new HashMap<String, Object>() );
        channel.queueDeclare( QUEUE_NAME, ACTIVE, DURABLE, NON_AUTO_DELETE, null );
        channel.queueBind( QUEUE_NAME, EXCHANGE, ROUTING_KEY, EMPTY_MAP );
        channel.basicConsume( QUEUE_NAME, false, CONSUMER_TAG, new ConsumerCallbackWithConfirms( channel ) );
    }

}

class ConsumerCallbackWithConfirms extends DefaultConsumer {

    public ConsumerCallbackWithConfirms( Channel channel ) {
        super( channel );
    }

    @Override
    public void handleDelivery( String consumerTag, Envelope envelope, BasicProperties properties, byte[] body ) throws IOException {
        getChannel().basicAck( envelope.getDeliveryTag(), false );
        String msg = new String( body );
        if ( "quit".equals( msg ) ) {
            getChannel().basicCancel( consumerTag );
            getChannel().close();
            getChannel().getConnection().close();
            System.exit( 0 );
        } else {
            System.out.println( msg );
        }
    }

}
