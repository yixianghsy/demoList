package com.hsy;

import com.hsy.Observer.CurrentConditionsDisplay;
import com.hsy.Observer.WeatherData;
import org.junit.jupiter.api.Test;

public class ObserverTest {
    /**
     * 观察者模式测试
     * Created by mrf on 2016/3/1.
     */
    @Test
    void  contextLoads() {

        WeatherData weatherData = new WeatherData();
        CurrentConditionsDisplay currentDisplay =
                new CurrentConditionsDisplay(weatherData);
        //其他布告板
//        StatisticsDisplay statisticsDisplay = new StatisticsDisplay(weatherData);
//        ForecastDisplay forecastDisplay = new ForecastDisplay(weatherData);
        weatherData.setMeasurements(80, 65, 30.4f);
        weatherData.setMeasurements(82, 70, 29.2f);
        weatherData.setMeasurements(78, 90, 29.2f);
    }
}
