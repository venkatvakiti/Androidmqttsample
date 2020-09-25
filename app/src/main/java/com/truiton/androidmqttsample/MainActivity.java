package com.truiton.androidmqttsample;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import androidx.appcompat.app.AppCompatActivity;

import static com.truiton.androidmqttsample.MqttHelper.sdabluetoothEventsTopic;
import static com.truiton.androidmqttsample.MqttHelper.sensoreventsTopic;

public class MainActivity extends AppCompatActivity implements IMqttMessageListener {
    public MqttAndroidClient mqttAndroidClient;

     String serverUri = "tcp://52.10.72.183:1883";

  //  final String clientId = "JavaSample";
     String subscriptionTopic = "FirstTest";
    Button btnid,btnid1;
    MqttHelper mqttHelper;
    TextView displaytext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnid=findViewById(R.id.btnid);
        btnid1=findViewById(R.id.btnid1);
        displaytext=findViewById(R.id.displaytext);
        startMqtt();
       /* String clientId = MqttClient.generateClientId();
        mqttAndroidClient = new MqttAndroidClient(getApplicationContext(), serverUri, clientId);
        mqttAndroidClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {
                Log.e("mqtt", s);
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
*/
        btnid1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                publishMessagebluetooth();
            }
        });
        btnid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                publishMessage();
            }
        });
        //connect();
    }

    private void publishMessagebluetooth()
    {
        try {
/*
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", "sensors_info");

            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("id", 1);
            jsonObject1.put("name", 21);
            jsonObject1.put("temperature", 39);

            JSONArray jsonArray = new JSONArray();
            jsonArray.put(jsonObject1);

            JSONObject jsonObject2 = new JSONObject();
            jsonObject2.put("sensors_info", jsonArray);

            jsonObject.put("payload",jsonObject2);



            jsonObject.put("type", "sensors_info");*/

            String Sensordata="[{\"rssi\":-54.0,\"tx\":127,\"blMCAddress\":\"79:B9:5B:F9:66:3E\",\"latAndLong\":\"17.486279,78.4610788\",\"imei\":\"357956086580364\",\"userId\":225},{\"rssi\":-54.0,\"tx\":127,\"blMCAddress\":\"79:B9:5B:F9:66:3E\",\"latAndLong\":\"17.486279,78.4610788\",\"imei\":\"357956086580364\",\"userId\":225},{\"rssi\":-53.800003,\"tx\":127,\"blMCAddress\":\"79:B9:5B:F9:66:3E\",\"latAndLong\":\"17.486279,78.4610788\",\"imei\":\"357956086580364\",\"userId\":225}]";

            MqttMessage message = new MqttMessage();
            message.setPayload(Sensordata.getBytes());
            message.setQos(0);
            mqttHelper.publish(sdabluetoothEventsTopic, message, displaytext);
            Log.e("ypp","Message Published");

        } catch (Exception e) {
            System.err.println("Error Publishing: " + e.getMessage());
            e.printStackTrace();
            displaytext.setText(e.getMessage());
        }
    }

    private void startMqtt(){
        mqttHelper = new MqttHelper(getApplicationContext(),displaytext);
        mqttHelper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {

            }

            @Override
            public void connectionLost(Throwable throwable) {
                displaytext.setText(throwable.getMessage());
            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                Log.e("ypp: ","data ..." + topic + " : " + mqttMessage);
               // dataReceived.setText(mqttMessage.toString());
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
    }
    public void publishMessage(){
//String tempdemo="I am from android";
        try {
/*
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", "sensors_info");

            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("id", 1);
            jsonObject1.put("name", 21);
            jsonObject1.put("temperature", 39);

            JSONArray jsonArray = new JSONArray();
            jsonArray.put(jsonObject1);

            JSONObject jsonObject2 = new JSONObject();
            jsonObject2.put("sensors_info", jsonArray);

            jsonObject.put("payload",jsonObject2);



            jsonObject.put("type", "sensors_info");*/

String Sensordata="{\"orgCode\":\"606\",\"imei\":\"1377a3aa8d50ce1a\",\"userId\":223,\"assetId\":\"None\",\"webCustomerId\":42,\"latitude\":17.4862747,\"longitude\":78.4610771,\"sensorInfo\":[{\"sensorName\":\"GPS\",\"sensorValue\":\"17.4862747, 78.4610771, 477.3000183105469\"},{\"sensorValue\":\"0.09239795804023743, 0.061822716146707535, 9.45228099822998, 9.452934755957079\",\"sensorName\":\"Accelerometer\"},{\"sensorName\":\"GyroScope\",\"sensorValue\":\"0.0022704079747200012, 7.522255182266235E-4, 1.0693725198507309E-4\"},{\"sensorValue\":100,\"sensorName\":\"Battery\"},{\"sensorName\":\"Ambient Light\",\"sensorValue\":3.5286717}]}";

            MqttMessage message = new MqttMessage();
            message.setPayload(Sensordata.getBytes());
            message.setQos(0);
            mqttHelper.publish(sensoreventsTopic, message,displaytext);
            Log.e("ypp","Message Published");

        } catch (Exception e) {
            System.err.println("Error Publishing: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void connect(){
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(false);
        //mqttConnectOptions.setUserName(username);
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
                    Log.e("ypp","ready to subscribe");
                   subscribeToTopic();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.e("Mqtt", "Failed to connect to: " + serverUri + exception.toString());
                }
            });


        } catch (MqttException ex){
            ex.printStackTrace();
        }
    }
    public void subscribeToTopic(){
        try {
            mqttAndroidClient.subscribe(subscriptionTopic, 0, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.e("ypp","subscribed");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.e("ypp","subscribed failed");
                }
            });
/*

            // THIS DOES NOT WORK!
            mqttAndroidClient.subscribe(subscriptionTopic, 0, new IMqttMessageListener() {
                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    // message Arrived!
                 Log.e("ypp: ","" + topic + " : " + message);
                }
            });
*/

        } catch (MqttException ex){
            Log.e("ypp","Exception whilst subscribing");
            ex.printStackTrace();
        }
    }
    @Override
    protected void onPause() {

        super.onPause();

        try {

            mqttHelper.disconnect();
           /* IMqttToken disconToken = mqttAndroidClient.disconnect();
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
            });*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        Log.e("ypp: ","data ..." + topic + " : " + message);
    }
}