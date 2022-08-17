package com.wgpdb.transportmanager.scheduler;

import com.wgpdb.transportmanager.domain.Trip;
import com.wgpdb.transportmanager.domain.WeatherData;
import com.wgpdb.transportmanager.domain.enumerantion.TripStatus;
import com.wgpdb.transportmanager.exception.TripNotFoundException;
import com.wgpdb.transportmanager.service.dbservice.TripDbService;
import com.wgpdb.transportmanager.service.weather.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class WeatherDataScheduler {

    private final WeatherService weatherService;
    private final TripDbService tripDbService;

    @Scheduled(cron = "0 0 6 * * *")
    public void retrieveWeatherData() throws TripNotFoundException {
        log.info("running retrieve weather data cron job");
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        List<Trip> upcomingTrips = tripDbService.getByTripStatusAndTripDate(TripStatus.UPCOMING, tomorrow);

        for (Trip trip : upcomingTrips) {
            List<WeatherData> weatherData = weatherService.getWeatherForecastByDayList(trip.getTripDate(),
                    trip.getDestination());
            trip.setWeatherData(weatherData);

            tripDbService.updateTrip(trip);
        }
    }
}