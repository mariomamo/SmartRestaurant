package it.unisa.smartrestaurantapp.server;

import android.util.Log;
import com.kaazing.gateway.jms.client.JmsConnectionFactory;
import com.kaazing.net.auth.BasicChallengeHandler;
import com.kaazing.net.auth.ChallengeHandler;
import com.kaazing.net.auth.LoginHandler;
import com.kaazing.net.ws.WebSocketFactory;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.util.ArrayDeque;
import java.util.HashMap;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;

public class SmartRestaurantDispatcher {
    private String url;
    private String destination;
    private JmsConnectionFactory connectionFactory;
    private Connection connection;
    private Session sessione;
    private DispatchQueue dispatchQueue;
    private WebSocketFactory webSocketFactory;
    private HashMap<String, ArrayDeque<MessageConsumer>> consumers = new HashMap<>();

    public SmartRestaurantDispatcher(String wsurl, String destination) {
        this.url = wsurl;
        this.destination = destination;
        try {
            connectionFactory = JmsConnectionFactory.createConnectionFactory();
            webSocketFactory = connectionFactory.getWebSocketFactory();
            webSocketFactory.setDefaultChallengeHandler(createChallengehandler());
            dispatchQueue = new DispatchQueue("DispatchQueue");
            dispatchQueue.start();
            dispatchQueue.waitUntilReady();
            connessione();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void connessione() {
        Log.d("MY-DEBUG", "Connecting");
        dispatchQueue.dispatchAsync(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d("MY-DEBUG", "POST-FALSE");
                    connectionFactory.setGatewayLocation(URI.create(url));
                    connection = connectionFactory.createConnection();
                    connection.start();
                    sessione = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                    Log.d("MY-DEBUG", "CONNECTED");
                    connection.setExceptionListener(new ExceptionListener() {
                        @Override
                        public void onException(JMSException exception) {
                            Log.d("MY-DEBUG", "ECCEZIONE!");
                            exception.printStackTrace();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void disconnessione() {
        dispatchQueue.removePendingJobs();
        dispatchQueue.quit();
        if (connection != null) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        connection.close();
                        Log.d("MY-DEBUG", "DISCONNECTED");
                    } catch (JMSException e) {
                        e.printStackTrace();
                        Log.d("MY-DEBUG", "EXCEPTION: " + e.getMessage());
                    } finally {
                        connection = null;
                    }
                }
            }).start();
        }
    }

    public void subscribe(final MessageListener listener) {
        dispatchQueue.dispatchAsync(new Runnable() {
            @Override
            public void run() {
                try {
                    Destination destination = getDestination(SmartRestaurantDispatcher.this.destination);
                    if (destination == null) return;

                    MessageConsumer consumer = sessione.createConsumer(destination);
                    ArrayDeque<MessageConsumer> consumerstoDestination = consumers.get(SmartRestaurantDispatcher.this.destination);
                    if (consumerstoDestination == null) {
                        consumerstoDestination = new ArrayDeque<>();
                        consumers.put(SmartRestaurantDispatcher.this.destination, consumerstoDestination);
                    }
                    consumerstoDestination.add(consumer);
                    consumer.setMessageListener(listener);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                Log.d("MY-DEBUG", "SUBSCRIBED");
            }
        });
    }

    public void subscribe(final MessageListener listener, final String selector) {
        dispatchQueue.dispatchAsync(new Runnable() {
            @Override
            public void run() {
                try {
                    Destination destination = getDestination(SmartRestaurantDispatcher.this.destination);
                    if (destination == null) return;

                    MessageConsumer consumer = sessione.createConsumer(destination, selector);
                    ArrayDeque<MessageConsumer> consumerstoDestination = consumers.get(SmartRestaurantDispatcher.this.destination);
                    if (consumerstoDestination == null) {
                        consumerstoDestination = new ArrayDeque<>();
                        consumers.put(SmartRestaurantDispatcher.this.destination, consumerstoDestination);
                    }
                    consumerstoDestination.add(consumer);
                    consumer.setMessageListener(listener);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                Log.d("MY-DEBUG", "SUBSCRIBED");
            }
        });
    }

    public void unsubscribe() {
        ArrayDeque<MessageConsumer> consumersToDestination = consumers.get(destination);
        if (consumersToDestination == null) {
            return;
        }
        final MessageConsumer consumer = consumersToDestination.poll();
        if (consumer == null) {
            return;
        }
        dispatchQueue.dispatchAsync(new Runnable() {
            public void run() {
                try {
                    consumer.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                    Log.d("MY-DEBUG", e.getMessage());
                }
            }
        });
    }

    public void sendMessage(final String msg, final HashMap<String, String> properties) {
        dispatchQueue.dispatchAsync(new Runnable() {
            @Override
            public void run() {
                try {
                    MessageProducer producer = sessione.createProducer(getDestination(destination));
                    Message message = sessione.createTextMessage(msg);
                    for (String key : properties.keySet()) {
                        message.setStringProperty(key, properties.get(key));
                        //Log.d("MY-DEBUG", key + ", " + properties.get(key));
                    }
                    producer.send(message);
                    producer.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public void sendMessage(final String msg) {
        dispatchQueue.dispatchAsync(new Runnable() {
            @Override
            public void run() {
                try {
                    MessageProducer producer = sessione.createProducer(getDestination(destination));
                    Message message = sessione.createTextMessage(msg);
                    producer.send(message);
                    producer.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private Destination getDestination(String destinationName) throws JMSException {
        Destination destination;
        if (destinationName.startsWith("/topic/")) {
            destination = sessione.createTopic(destinationName);
        }
        else if (destinationName.startsWith("/queue/")) {
            destination = sessione.createQueue(destinationName);
        }
        else {
            Log.d("MY-DEBUG", "Invalid destination name: " + destinationName+". Destination should start from '/topic/' or '/queue/'");
            return null;
        }
        return destination;

    }

    public ChallengeHandler createChallengehandler() {
        final LoginHandler loginHandler = new LoginHandler() {
            private String username;
            private String password;
            @Override
            public PasswordAuthentication getCredentials() {
                try {
                    username = "admin";
                    password = "admin";
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return new PasswordAuthentication(username, password.toCharArray());
            }
        };

        BasicChallengeHandler challengeHandler = BasicChallengeHandler.create();
        challengeHandler.setLoginHandler(loginHandler);
        return challengeHandler;
    }

//    private ChallengeHandler createChallengehandler() {
//        final LoginHandler loginHandler = new LoginHandler() {
//            private String username;
//            private char[] password;
//            @Override
//            public PasswordAuthentication getCredentials() {
//                try {
//                    final Semaphore semaphore = new Semaphore(1);
//
//                    // Acquire semaphore so that subsequent acquire will block until released.
//                    // This is used to wait until the login dialog is dismissed
//                    semaphore.acquire();
//                    final LoginDialogFragment loginDialog = new LoginDialogFragment();
//                    loginDialog.setListener(new LoginDialogListener() {
//                        public void onDismissed() {
//                            semaphore.release();
//                        }
//                    });
//                    runOnUiThread(new Runnable() {
//                        public void run() {
//                            loginDialog.show(getSupportFragmentManager(), "Login Dialog Fragment");
//                            loginDialog.getFragmentManager().executePendingTransactions();
//                            loginDialog.getDialog().setCanceledOnTouchOutside(false);
//                        }
//                    });
//
//                    // wait until the dialog is dismissed
//                    semaphore.acquire();
//
//                    if (loginDialog.isCancelled()) {
//                        return null;
//                    }
//
//                    username = loginDialog.getUsername();
//                    password = loginDialog.getPassword().toCharArray();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                return new PasswordAuthentication(username, password);
//            }
//        };
//        BasicChallengeHandler challengeHandler = BasicChallengeHandler.create();
//        challengeHandler.setLoginHandler(loginHandler);
//        return challengeHandler;
//    }

}
