package com.github.winse.dagger2.coffee.provider;

import com.github.winse.dagger2.coffee.Pump;
import dagger.Binds;
import dagger.Module;

import javax.inject.Singleton;

@Module
public abstract class PumpModule {
    @Binds
    abstract Pump providePump(Thermosiphon pump);
}