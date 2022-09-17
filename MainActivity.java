package com.mainactivity2.bluetooth;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private  static final int REQUEST_ENABLE_BT = 0;
    private  static  final int REQUEST_DISCOVER_BT = 1;
    TextView mStatusBlueTv,mPairedTv;
    ImageView mBlueTv;
    Button mOnBtn,mOffBtn,mDiscoverBtn,mPairedBtn;
    BluetoothAdapter mBlueAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStatusBlueTv = findViewById(R.id.statusBluetoothTv);
        mPairedTv = findViewById(R.id.pairedTv);
        mBlueTv = findViewById(R.id.bluetoothIv);
        mOnBtn=findViewById(R.id.onBtn);
        mOffBtn = findViewById(R.id.offBtn);
        mDiscoverBtn = findViewById(R.id.discoverableBtn);
        mPairedBtn = findViewById(R.id.pairedbtn);

        mBlueAdapter = BluetoothAdapter.getDefaultAdapter();


        if(mBlueAdapter == null){
            mStatusBlueTv.setText("Bluetooth is not available");

        }
        else{
            mStatusBlueTv.setText("Bluetooth is available");
        }
        if(mBlueAdapter.isEnabled()){
            mBlueTv.setImageResource(R.drawable.ic_action_on);

        }
        else{
            mBlueTv.setImageResource(R.drawable.ic_action_off);
        }
        mOnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mBlueAdapter.isEnabled()){
                    showToast("turning On Bluetooth...");
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent,REQUEST_ENABLE_BT);
                }
                else{
                    showToast("Bluetooth is already on");
                }

            }
        });
        mDiscoverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mBlueAdapter.isDiscovering()){
                    showToast("Making Your Device Discoverable");
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    startActivityForResult(intent,REQUEST_DISCOVER_BT);
                }

            }
        });
        mOffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mBlueAdapter.isEnabled()){
                    mBlueAdapter.disable();
                    showToast("Turning Bluetooth off");
                    mBlueTv.setImageResource(R.drawable.ic_action_off);
                }
                else{
                    showToast("Bluetooth is already Off");
                }

            }
        });
        mPairedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBlueAdapter.isEnabled()) {
                    mPairedTv.setText("paired Devices");
                    Set<BluetoothDevice> devices = mBlueAdapter.getBondedDevices();
                    for (BluetoothDevice device : devices) {
                        mPairedTv.append("\nDevice  " + device.getName() + "," + device);
                    }
                }
                else{
                        showToast("Turn On bluetooth to get paired devices");
                    }
                }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case REQUEST_ENABLE_BT:
                if(resultCode == RESULT_OK){
                    mBlueTv.setImageResource(R.drawable.ic_action_on);
                    showToast("Bluetooth is on");
                }
                else{
                    showToast("could't on bluetooth");
                }
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();

    }
}