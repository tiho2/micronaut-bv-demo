package bookhandler;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import io.micronaut.rabbitmq.connect.ChannelInitializer;

import javax.inject.Singleton;
import java.io.IOException;

@Singleton
public class ChannelPoolListener extends ChannelInitializer {

    @Override
    public void initialize(Channel channel) throws IOException {
        channel.exchangeDeclare("micronaut", BuiltinExchangeType.DIRECT, true); // <1>
        channel.queueDeclare("book-input", true, false, false, null); // <2>

        channel.queueBind("book-input", "micronaut", "book-input"); // <3>

        //channel.exchangeDeclare("micronaut2", BuiltinExchangeType.DIRECT, true); // <1>
        channel.queueDeclare("book-processing", true, false, false, null); // <2>
        channel.queueBind("book-processing", "micronaut", "book-processing"); // <3>

        channel.queueDeclare("status", true, false, false, null); // <2>
        channel.queueBind("status", "micronaut", "status"); // <3>
    }
}
