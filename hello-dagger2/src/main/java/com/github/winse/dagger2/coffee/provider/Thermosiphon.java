package com.github.winse.dagger2.coffee.provider;

import com.github.winse.dagger2.coffee.Heater;
import com.github.winse.dagger2.coffee.Pump;

import javax.inject.Inject;

class Thermosiphon implements Pump {
    private final Heater heater;

    @Inject
    Thermosiphon(Heater heater) {
        this.heater = heater;
    }

    @Override
    public void pump() {
        if (heater.isHot()) {
            System.out.println("=> => pumping => =>");
        }
    }
}