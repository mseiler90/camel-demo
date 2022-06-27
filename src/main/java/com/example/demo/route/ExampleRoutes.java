package com.example.demo.route;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import static org.apache.camel.LoggingLevel.INFO;

@Component
@Slf4j
@RequiredArgsConstructor
public class ExampleRoutes extends RouteBuilder {

    public static final String EXAMPLE_FROM_ROUTE = "azure-servicebus:" +
            "{{service-bus.queue-name}}";

    static final String EXAMPLE_SEND_ROUTE = "azure-servicebus:" +
            "{{service-bus.queue-name}}";

    static final String EXAMPLE_FROM_ROUTE_ID = "FromRoute";

    @Override
    public void configure() {
        fromRoute();
        sendRoute();
    }

    // Send a message to the queue every 10 seconds
    private void sendRoute() {
        from("timer:java?period=10s")
                .setBody().simple("test message")
                .log(INFO, log, "Sending message with body: ${body}")
                .to(EXAMPLE_SEND_ROUTE);
    }

    // Receive the message from the same queue
    private void fromRoute() {
        from(EXAMPLE_FROM_ROUTE).id(EXAMPLE_FROM_ROUTE_ID)
                .log(INFO, log, "Received message with body: ${body}");
    }

}
