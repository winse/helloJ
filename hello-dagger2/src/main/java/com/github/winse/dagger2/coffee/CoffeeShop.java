package com.github.winse.dagger2.coffee;

import com.github.winse.dagger2.coffee.provider.DripCoffeeModule;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {DripCoffeeModule.class})
public interface CoffeeShop {
    CoffeeMaker maker();
}