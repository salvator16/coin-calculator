package com.example.coincalculator.config;

import com.example.coincalculator.model.CurrencyWrapper;
import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.springframework.stereotype.Component;


@Component
public class CacheClient {

    public static final String CURRENCY_RATE_MAP = "currency-rate";
    private final HazelcastInstance hazelcastInstance
            = Hazelcast.newHazelcastInstance(createConfig());

    public CurrencyWrapper put(String symbol, CurrencyWrapper currencyWrapper){
        IMap<String, CurrencyWrapper> map = hazelcastInstance.getMap(CURRENCY_RATE_MAP);
        return map.put(symbol, currencyWrapper);
    }

    public CurrencyWrapper get(String key){
        IMap<String, CurrencyWrapper> map = hazelcastInstance.getMap(CURRENCY_RATE_MAP);
        return map.get(key);
    }

    public void removeFromMap(String key) {
        getMap().remove(key);
    }

    public IMap getMap() {
        return hazelcastInstance.getMap(CURRENCY_RATE_MAP);
    }

    public Config createConfig() {
        Config config = new Config();
        config.addMapConfig(mapConfig());
        return config;
    }

    private MapConfig mapConfig() {
        MapConfig mapConfig = new MapConfig(CURRENCY_RATE_MAP);
        mapConfig.setTimeToLiveSeconds(30);
        mapConfig.setMaxIdleSeconds(40);
        return mapConfig;
    }

}
