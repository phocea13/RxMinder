package net.jgrand.rxminder;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button connectButton;
    private Button disconnectButton;
    private TextView connectionStatus, buttonPressCount;
    private Chronometer connectTimer;
    private boolean connected;
    private Button deviceButton;
    private int pressCount = 0;
    private List<LocalDateTime> instantsMedsTaken;
    private TextView medsTakenLogTextView;
    private StringBuilder sbMedsTaken;

    final String dbDatePattern = "yyyy-MM-dd @ HH:mm:ss";
    final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dbDatePattern);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectButton = (Button) findViewById(R.id.connectButton);
        disconnectButton = (Button) findViewById(R.id.disconnectButton);
        deviceButton = (Button) findViewById(R.id.buttonPressButton);
        medsTakenLogTextView = (TextView) findViewById(R.id.medsTakenLog);
        connectButton.setOnClickListener(this);
        disconnectButton.setOnClickListener(this);
        deviceButton.setOnClickListener(this);

        connectionStatus = (TextView) findViewById(R.id.connectionStatus);
        buttonPressCount = (TextView) findViewById(R.id.buttonPressCount);

        connectTimer = (Chronometer) findViewById(R.id.connectionTimer);
        connectTimer.setBase(SystemClock.elapsedRealtime());
        disconnectButton.setEnabled(false);
        deviceButton.setEnabled(false);
        instantsMedsTaken = new ArrayList<>();
        sbMedsTaken = new StringBuilder("Meds Log:\n");

    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.connectButton:
                if(!connected) {
                    connectTimer.setBase(SystemClock.elapsedRealtime());
                    connectTimer.start();
                    connectionStatus.setText("Connected");
                    connected = true;
                    connectButton.setEnabled(!connected);
                    disconnectButton.setEnabled(connected);
                    deviceButton.setEnabled(connected);
                } else {
                    Toast.makeText(this, "Already connected", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.disconnectButton:
                connectionStatus.setText("Disconnected");
                connectTimer.stop();
                connected = false;
                pressCount = 0;
                buttonPressCount.setText("0");
                connectButton.setEnabled(!connected);
                disconnectButton.setEnabled(connected);
                deviceButton.setEnabled(connected);
                break;
            case R.id.buttonPressButton:
                if(connected){
                    pressCount++;
                    buttonPressCount.setText(String.format("%d times", pressCount));
                    LocalDateTime now = LocalDateTime.now();
                    instantsMedsTaken.add(now);
                    sbMedsTaken.append(dateTimeFormatter.format(now));
                    sbMedsTaken.append("\n");
                    medsTakenLogTextView.setText(sbMedsTaken.toString());
                } else {
                    Toast.makeText(this, "You must be connected first.", Toast.LENGTH_LONG).show();
                }
        }
    }

}
