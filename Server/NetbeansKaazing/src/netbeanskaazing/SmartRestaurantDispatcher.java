package netbeanskaazing;


import com.kaazing.gateway.jms.client.JmsConnectionFactory;
import com.kaazing.gateway.jms.client.JmsInitialContext;
import com.kaazing.net.auth.BasicChallengeHandler;
import com.kaazing.net.auth.ChallengeHandler;
import com.kaazing.net.auth.LoginHandler;
import com.kaazing.net.http.HttpRedirectPolicy;
import com.kaazing.net.ws.WebSocketFactory;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class SmartRestaurantDispatcher {
    private String url;
    private String destination;
    public ConnectionFactory connectionFactory;
    public Connection connection;
    public Session session;
    public String username = "admin";
    public String password = "admin";
    public MessageProducer producer;
    public MessageConsumer consumer;
    public InitialContext ctx;
    private Destination dest;
    private final Logger logger;

    public SmartRestaurantDispatcher(String wsurl, String destination) {
        this.url = wsurl;
        this.destination = destination;
        this.logger = Logger.getLogger("[SmartRestaurantDispatcher]");
            
        try {
            Properties props = new Properties();
            props.setProperty("java.naming.factory.initial", "com.kaazing.gateway.jms.client.JmsInitialContextFactory");
            ctx = new InitialContext(props);
            props.put(JmsInitialContext.CONNECTION_TIMEOUT, "15000");
            connessione();
        } catch (NamingException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }
    
    private void connessione() {
        try {
            connectionFactory = (ConnectionFactory) ctx.lookup("ConnectionFactory");
            JmsConnectionFactory jmsConnectionFactory = (JmsConnectionFactory) connectionFactory;
            jmsConnectionFactory.setGatewayLocation(URI.create(url));
            
            WebSocketFactory webSocketFactory = jmsConnectionFactory.getWebSocketFactory();
            webSocketFactory.setDefaultRedirectPolicy(HttpRedirectPolicy.ALWAYS);
            webSocketFactory.setDefaultChallengeHandler(createChallengeHandler());
            
            connection = connectionFactory.createConnection(username, password);
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            connection.start();
            System.out.println("Connected!");
            
            dest = (Destination) ctx.lookup(destination);
            
            producer = session.createProducer(dest);
            
        } catch (NamingException | JMSException ex) {
            try {
                disconnessione();
            } catch (JMSException ex1) {
               logger.log(Level.SEVERE, null, ex1);
            }
            logger.log(Level.SEVERE, null, ex);
        }
    }
    
    public void subscribe(MessageListener listener) throws JMSException, NamingException {            
        consumer = session.createConsumer(dest);
        consumer.setMessageListener(listener);
    }
    
    public void subscribe(MessageListener listener, String selector) throws JMSException, NamingException {            
        consumer = session.createConsumer(dest, selector);
        consumer.setMessageListener(listener);
    }
    
    public void unsubscribe() throws JMSException {
        consumer.close();
    }
    
    private ChallengeHandler createChallengeHandler() {
        final LoginHandler loginHandler = new LoginHandler() {
            @Override
            public PasswordAuthentication getCredentials() {
                return new PasswordAuthentication(username, password.toCharArray());
            }
        };
        
        BasicChallengeHandler challengeHandler = BasicChallengeHandler.create();
        challengeHandler.setLoginHandler(loginHandler);
        return challengeHandler;
    }
    
    public void sendMessage(String message, HashMap<String, String> properties) {
        TextMessage textMessage;
        try {
            textMessage = session.createTextMessage(message);
            for (String key : properties.keySet()) {
                textMessage.setStringProperty(key, properties.get(key));
            }
            producer.send(textMessage);
        } catch (JMSException e) {
            logger.log(Level.SEVERE, "Error sending message [" + message + "]! " + e.getMessage());
        }
        logger.log(Level.INFO, "-> MESSAGE PUBLISHED: " + message);
    }
    
    public void sendMessage(String message) {
        TextMessage textMessage;
        try {
            textMessage = session.createTextMessage(message);
            producer.send(textMessage);
        } catch (JMSException e) {
            logger.log(Level.SEVERE, "Error sending message [" + message + "]! " + e.getMessage());
        }
        logger.log(Level.INFO, "-> MESSAGE PUBLISHED: " + message);
    }
    
    public void disconnessione() throws JMSException {
        producer.close();
        consumer.close();
        session.close();
        connection.stop();
        connection.close();
        logger.log(Level.SEVERE, "Kaazing JMS has stopped");
    }
}
