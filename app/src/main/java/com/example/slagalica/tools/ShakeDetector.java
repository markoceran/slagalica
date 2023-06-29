package com.example.slagalica.tools;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class ShakeDetector implements SensorEventListener {
    // Minimum acceleration threshold for considering it as a shake gesture
    private static final float SHAKE_THRESHOLD = 25f;
    // Minimum time interval between two shake gestures (in milliseconds)
    private static final int SHAKE_INTERVAL = 2000;

    private SensorManager sensorManager;
    private OnShakeListener listener;
    private long lastShakeTime;

    public ShakeDetector(SensorManager sensorManager, OnShakeListener listener) {
        this.sensorManager = sensorManager;
        this.listener = listener;
    }

    public interface OnShakeListener {
        void onShake();
    }

    public void start() {
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stop() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float accelerationX = event.values[0];
            float accelerationY = event.values[1];
            float accelerationZ = event.values[2];

            float accelerationMagnitude = (float) Math.sqrt(
                    accelerationX * accelerationX +
                            accelerationY * accelerationY +
                            accelerationZ * accelerationZ
            );

            long currentTime = System.currentTimeMillis();
            if (accelerationMagnitude > SHAKE_THRESHOLD && currentTime - lastShakeTime > SHAKE_INTERVAL) {
                lastShakeTime = currentTime;
                listener.onShake();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not used in this example
    }
}

