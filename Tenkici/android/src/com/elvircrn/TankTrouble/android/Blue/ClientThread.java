package com.elvircrn.TankTrouble.android.Blue;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;

/**
 * Created by User on 6.3.2016.
 */
public class ClientThread extends Thread {
    private BluetoothSocket bluetoothSocket;
    private BluetoothDevice bluetoothDevice;

    public ClientThread () {

    }

    public ClientThread (BluetoothDevice bluetoothDevice) {
        BluetoothSocket tmp = null;
        this.bluetoothDevice = bluetoothDevice;

        try {
            tmp = this.bluetoothDevice.createRfcommSocketToServiceRecord(BTManager.myUUID);
        }
        catch (IOException e) {
            Log.d("CONNECTTHREAD constr", e.getMessage());
        }
        this.bluetoothSocket = tmp;
    }

    public void init (BluetoothDevice bluetoothDevice) {
        BluetoothSocket tmp = null;
        this.bluetoothDevice = bluetoothDevice;

        try {
            tmp = this.bluetoothDevice.createRfcommSocketToServiceRecord(BTManager.myUUID);
        }
        catch (IOException e) {
            Log.d("CONNECTTHREAD constr", e.getMessage());
        }
        this.bluetoothSocket = tmp;
    }

    @Override
    public void run() {
        BTManager.bluetoothAdapter.cancelDiscovery();

        try {
            bluetoothSocket.connect();
        }
        catch(IOException connectException) {
            Log.d("CONNECTTHREAD run", connectException.toString());
            try {
                bluetoothSocket.close();
            }
            catch(IOException closeException) {
                Log.d("CONNECTTHREAD run", closeException.toString());
                return;
            }
            return;
        }

        BTManager.handshake = new ManageConnectThread(bluetoothSocket);
        BTManager.handshake.start();

        return;
    }

    public boolean cancel() {
        try {
            bluetoothSocket.close();
        } catch(IOException e) {
            Log.d("CONNECTTHREAD cancel", e.toString());
            return false;
        }
        return true;
    }
}