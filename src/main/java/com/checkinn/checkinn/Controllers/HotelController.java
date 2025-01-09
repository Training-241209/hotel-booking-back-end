package com.checkinn.checkinn.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.checkinn.checkinn.Entities.Hotel;
import com.checkinn.checkinn.Services.HotelService;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    private HotelService hotelService;
    
    @Autowired
    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/{hotel_id}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable int hotel_id) {
        Hotel hotel = hotelService.getHotelById(hotel_id);
        
        if (hotel == null) { return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); }
        
        return ResponseEntity.ok().body(hotel);
    }

    @GetMapping("/")
    public ResponseEntity<Iterable<Hotel>> getAllHotels() {
        return ResponseEntity.ok().body(hotelService.getAllHotels());
    }

}
