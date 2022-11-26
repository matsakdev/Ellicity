package com.matsak.ellicity.lighting;

public interface SmartUsage {
    void enablePhotodetector();
    void disablePhotodetector();
    void enableMotionSensor();
    void disableMotionSensor();
    void enableLightWithKey(String key);
    void disableLightWithKey(String key);
}
