package com.matsak.ellicity.lighting.payload;

import com.matsak.ellicity.lighting.entity.actions.Action;

public class DeviceActionRequest {
    private Action action;

    public DeviceActionRequest(){}

    public DeviceActionRequest(Long deviceId, Action action) {
        this.action = action;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
