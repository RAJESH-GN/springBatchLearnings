package com.example.JMS.demo;

import com.example.JMS.demo.sample.MyRoute;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

import javax.jms.ConnectionFactory;

@EnableJms
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
        MyRoute routeBuilder = new MyRoute();
        CamelContext ctx = new DefaultCamelContext();

        //configure jms component
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://0.0.0.0:61616");
        ctx.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

        try {
            ctx.addRoutes(routeBuilder);
            ctx.start();
            Thread.sleep(5 * 60 * 1000);
            ctx.stop();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        SpringApplication.run(DemoApplication.class, args);
	}

}
