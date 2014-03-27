package com.ssi.kernel.utils;
import java.util.Map;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Mensageria {
	
	public void enviarMensagem(String strmensagem,String assunto, Map<String,String> listaemails, String smtpserver, String modulo, String ssi){
		
		if(ssi == null || ssi.isEmpty() || ssi.equals("")){
			ssi = "SEM N�MERO";
		}
		
		if(listaemails.size()>=1){
			for(Map.Entry<String, String>mapa:listaemails.entrySet()){
				
				InitialContext ic;
				try {
					ic = new InitialContext();
				
					ConnectionFactory cf = (ConnectionFactory)ic.lookup("/ConnectionFactory");
					Queue q = (Queue)ic.lookup("/queue/QueueSSI");
					
					javax.jms.Connection jmsconnection = cf.createConnection();
					Session session = jmsconnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
					MessageProducer producer = session.createProducer(q);
					jmsconnection.start();
					
					TextMessage message = session.createTextMessage(strmensagem);
					message.setStringProperty("emaildest",mapa.getValue());
					message.setStringProperty("nomedest",mapa.getKey());
					message.setStringProperty("emailremet","ssi@jms.mailer.com");
					message.setStringProperty("nomeremet","SSI");
					message.setStringProperty("assunto",assunto);
					message.setStringProperty("smtpserver", smtpserver);
					message.setStringProperty("modulo", modulo);
					message.setStringProperty("ssi", ssi);
					
					producer.send(message);
					
					jmsconnection.close();
					
					ic = null;
					cf = null;
					q = null;
					jmsconnection = null;
					session = null;
					producer = null;
					message = null;
					mapa = null;
					
				} catch (NamingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			listaemails = null;
			assunto = null;
			strmensagem = null;
		}
	}
}
