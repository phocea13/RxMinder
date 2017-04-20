package net.jgrand.rxminder;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button connectButton;
    private Button disconnectButton;
    private TextView connectionStatus, buttonPressCount;
    private Chronometer connectTimer;
    private boolean connected;
    private Button deviceButton;
    private int pressCount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectButton = (Button) findViewById(R.id.connectButton);
        disconnectButton = (Button) findViewById(R.id.disconnectButton);
        deviceButton = (Button) findViewById(R.id.buttonPressButton);
        connectButton.setOnClickListener(this);
        disconnectButton.setOnClickListener(this);
        deviceButton.setOnClickListener(this);

        connectionStatus = (TextView) findViewById(R.id.connectionStatus);
        buttonPressCount = (TextView) findViewById(R.id.buttonPressCount);

        connectTimer = (Chronometer) findViewById(R.id.connectionTimer);
        connectTimer.setBase(SystemClock.elapsedRealtime());
        disconnectButton.setEnabled(false);
        buttonPressCount.setEnabled(false);

    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.connectButton:
                //handle
                if(!connected) {
                    connectTimer.setBase(SystemClock.elapsedRealtime());
                    connectTimer.start();
                    connectionStatus.setText("Connected");
                    connected = true;
                    connectButton.setEnabled(false);
                    disconnectButton.setEnabled(true);
                    buttonPressCount.setEnabled(true);
                } else {
                    Toast.makeText(this, "Already connected", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.disconnectButton:
                //handle
                connectionStatus.setText("Disconnected");
                connectTimer.stop();
                connected = false;
                pressCount = 0;
                buttonPressCount.setText("0");
                connectButton.setEnabled(true);
                disconnectButton.setEnabled(false);
                buttonPressCount.setEnabled(false);
                break;
            case R.id.buttonPressButton:
                if(connected){
                    pressCount++;
                    buttonPressCount.setText(String.format("%d times", pressCount));
                } else {
                    Toast.makeText(this, "You must be connected first.", Toast.LENGTH_LONG).show();
                }
        }
    }

}
