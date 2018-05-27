package com.github.winse.dagger2.coffee.provider;

import com.github.winse.dagger2.coffee.Heater;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module(includes = PumpModule.class)
public class DripCoffeeModule {
    @Provides
    @Singleton
    Heater provideHeater() {
        return new ElectricHeater();
    }
}