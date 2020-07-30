package com.example.JMS.demo.sample;

import com.example.JMS.demo.model.Employee;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class MyProcessor implements Processor {

	public void process(Exchange exchange) throws Exception {
		Employee employee = exchange.getIn().getBody(Employee.class);
		employee.setEmpName("JavaInUse Rocks");
		exchange.getIn().setBody(employee);
	}

}