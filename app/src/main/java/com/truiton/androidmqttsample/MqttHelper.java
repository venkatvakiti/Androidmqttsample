package com.truiton.androidmqttsample;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttHelper {
    public MqttAndroidClient mqttAndroidClient;
    final String serverUri = "tcp://52.10.72.183:1883";

    //  final String clientId = "JavaSample";
    //final String subscriptionTopic = "FirstTest";
    public static String sensoreventsTopic = "sensorevents";
    public static String sdabluetoothEventsTopic = "sdabluetoothevents";
   // final String clientId = "ExampleAndroidClient";
    //final String subscriptionTopic = "sensor/+";

    final String username = "xxxxxxx";
    final String password = "yyyyyyyyyy";
TextView txtdisply;
    public MqttHelper(Context context, TextView displaytext) {
        this.txtdisply=displaytext;
        String clientId = MqttClient.generateClientId();
        mqttAndroidClient = new MqttAndroidClient(context, serverUri, clientId);
        mqttAndroidClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {
                Log.w("mqtt", s);
            }

            @Override
            public void connectionLost(Throwable throwable) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                Log.e("Mqtt", mqttMessage.toString());
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
        connect();
    }

    public void setCallback(MqttCallbackExtended callback) {
        mqttAndroidClient.setCallback(callback);
    }

    private void connect() {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(false);
      //  mqttConnectOptions.setUserName(username);
       // mqttConnectOptions.setPassword(password.toCharArray());

        try {

            mqttAndroidClient.connect(mqttConnectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {

                    DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                    disconnectedBufferOptions.setBufferEnabled(true);
                    disconnectedBufferOptions.setBufferSize(100);
                    disconnectedBufferOptions.setPersistBuffer(false);
                    disconnectedBufferOptions.setDeleteOldestMessages(false);
                    mqttAndroidClient.setBufferOpts(disconnectedBufferOptions);
                    subscribeToTopic();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.e("Mqtt", "Failed to connect to: " + serverUri + exception.toString());
                    txtdisply.setText(exception.toString());
                }
            });


        } catch (MqttException ex) {
            ex.printStackTrace();
            txtdisply.setText(ex.getMessage());
        }
    }


    private void subscribeToTopic() {
        try {

            mqttAndroidClient.subscribe(sensoreventsTopic, 0, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.w("Mqtt", "sensoreventsTopic Subscribed!");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.w("Mqtt", "sensoreventsTopic Subscribed fail!");
                    txtdisply.setText(exception.getMessage());
                }
            });

            mqttAndroidClient.subscribe(sdabluetoothEventsTopic, 0, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.w("Mqtt", " sdabluetoothEventsTopic Subscribed!");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.w("Mqtt", "sdabluetoothEventsTopic Subscribed fail!");
                    txtdisply.setText(exception.getMessage());
                }
            });

        } catch (MqttException ex) {
            System.err.println("Exceptionst subscribing");
            ex.printStackTrace();
            txtdisply.setText(ex.getMessage());
        }
    }

    public void publish(String subscriptionTopic, MqttMessage message, TextView displaytext) {

        try {
            mqttAndroidClient.publish(subscriptionTopic, message);
            if(!mqttAndroidClient.isConnected()){
                Log.e("ypp",mqttAndroidClient.getBufferedMessageCount() + " messages in buffer.");
            }
        } catch (MqttException ex) {
            System.err.println("Exceptionst subscribing");
            ex.printStackTrace();
            displaytext.setText(ex.getMessage());
        }

    }

    public void disconnect()
    {
        try {
            IMqttToken disconToken = mqttAndroidClient.disconnect();
            disconToken.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // we are now successfully disconnected

                    Log.e("ypp: ","we are now successfully disconnected" );
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken,
                                      Throwable exception) {
                    // something went wrong, but probably we are disconnected anyway
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
