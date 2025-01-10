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

    // possible fix might be needed here with logic ping me (john) if issue is here and not fixed yet
    public String editHotel(int hotel_id, Hotel hotel) {
        Optional<Hotel> resp = hotelRepository.findById(hotel_id);
        if (resp.isPresent()) {
            Hotel hotelToUpdate = resp.get();
            hotelToUpdate.setHotelName(hotel.getHotelName());
            hotelToUpdate.setDescription(hotel.getDescription());
            hotelToUpdate.setRooms(hotel.getRooms());
            hotelToUpdate.setLocation(hotel.getLocation());
            hotelToUpdate.setPrice(hotel.getPrice());
            hotelToUpdate.setImage(hotel.getImage());
            hotelRepository.save(hotelToUpdate);
            return "HOTEL UPDATED";
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "HOTEL NOT FOUND");
    }

    public String createHotel(Hotel hotel) {
        if (hotel.getDescription().length()>0 && hotel.getHotelName().length()>0 && hotel.getLocation().length()>0 && hotel.getPrice()>0 && hotel.getRooms()>0) {
            hotelRepository.save(hotel);
            return "HOTEL CREATED";
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "INVALID HOTEL INFORMATION");
        
    }

    public String deleteHotel(int hotel_id) {
        if (hotelRepository.existsById(hotel_id)) {
            hotelRepository.deleteById(hotel_id);
            return "HOTEL DELETED";
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "HOTEL NOT FOUND");
    }
}
