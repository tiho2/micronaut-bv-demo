package page.app;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import io.micronaut.rabbitmq.connect.ChannelInitializer;

import javax.inject.Singleton;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class ChannelPoolListener extends ChannelInitializer {

    @Override
    public void initialize(Channel channel) throws IOException {
        System.out.println("Initializing ChannelPoolListener");
        channel.exchangeDeclare("micronaut", BuiltinExchangeType.DIRECT, true); // <1>

        channel.queueDeclare("book-processing", true, false, false, null); // <2>
        channel.queueBind("book-processing", "micronaut", "book-processing"); // <3>

        channel.queueDeclare("status", true, false, false, null); // <2>
        channel.queueBind("status", "micronaut", "status"); // <3>
    }
}
