package com.checkinn.checkinn.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
