package com.checkinn.checkinn.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.checkinn.checkinn.Entities.Hotel;
import com.checkinn.checkinn.Repositories.HotelRepository;

@Service
public class HotelService {

    private HotelRepository hotelRepository;

    @Autowired
    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public Hotel getHotelById(int hotel_id) {
        Optional<Hotel> resp = hotelRepository.findById(hotel_id);
        if (resp.isPresent()) {
            return resp.get();
        }
        return null;
    }

    public Iterable<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    public Iterable<Hotel> getHotelsByLocation(String location) {
        return hotelRepository.findByLocation(location);
    }

    public String editHotel(int hotel_id, Hotel hotel) {
        return null;
    }
}
