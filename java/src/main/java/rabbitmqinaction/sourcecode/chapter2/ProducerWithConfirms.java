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

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.AMQP.BasicProperties.Builder;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ProducerWithConfirms {

    public static void main( String[] args ) throws IOException, InterruptedException {
        if ( args.length != 1 ) {
            System.err.println( "Message body must be supplied" );
            System.exit( 1 );
        }
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername( USERNAME );
        factory.setPassword( PASSWORD );
        factory.setHost( HOST );

        Connection connection = factory.newConnection();

        String msg = args[0];
        Builder msgPropertiesBuilder = new AMQP.BasicProperties.Builder();
        msgPropertiesBuilder.contentType( PLAIN_CONTENT_TYPE );
        BasicProperties msgProperties = msgPropertiesBuilder.build();

        Channel channel = connection.createChannel();
        channel.confirmSelect();
        channel.addConfirmListener( new ConfirmHandler() );
        channel.basicPublish( EXCHANGE, ROUTING_KEY, msgProperties, msg.getBytes() );
        channel.waitForConfirmsOrDie();

        channel.close();
        connection.close();
    }

}

class ConfirmHandler implements ConfirmListener {

    public void handleAck( long deliveryTag, boolean multiple ) throws IOException {
        System.out.println( "Confirm receipt" );
    }

    public void handleNack( long deliveryTag, boolean multiple ) throws IOException {
        System.out.println( "Message lost" );
    }

}
