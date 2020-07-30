package com.example.JMS.demo.sample;

import com.example.JMS.demo.model.Employee;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;

@Component
public class MyRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		//from("file:E://inputFolder?noop=true").to("file:E://outputfolder");

		// XML Data Format
		JaxbDataFormat xmlDataFormat = new JaxbDataFormat();
		JAXBContext con = JAXBContext.newInstance(Employee.class);
		xmlDataFormat.setContext(con);

		// JSON Data Format
		JacksonDataFormat jsonDataFormat = new JacksonDataFormat(Employee.class);

		from("file:E:/inputFolder").doTry().unmarshal(xmlDataFormat).
				process(new MyProcessor()).marshal(jsonDataFormat).
				to("jms:queue:javainuse").doCatch(Exception.class).process(new Processor() {
			public void process(Exchange exchange) throws Exception {
				Exception cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
				System.out.println(cause);
			}
		});
	}
}