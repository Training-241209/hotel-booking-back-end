package com.checkinn.checkinn.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.checkinn.checkinn.Entities.Hotel;
import com.checkinn.checkinn.Services.AuthService;
import com.checkinn.checkinn.Services.HotelService;
import com.checkinn.checkinn.Constants.GeneralConstants;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    private HotelService hotelService;

    private AuthService authService;
    
    @Autowired
    public HotelController(HotelService hotelService, AuthService authService) {
        this.authService = authService;
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

    @GetMapping("/location/{location}")
    public ResponseEntity<Iterable<Hotel>> getHotelsByLocation(@PathVariable String location) {
        return ResponseEntity.ok().body(hotelService.getHotelsByLocation(location));
    }

    @PatchMapping("/edit/{hotel_id}")
    public ResponseEntity<String> editHotel(@RequestHeader (GeneralConstants.AUTH_HEADER_NAME) String token, @PathVariable int hotel_id, @RequestBody Hotel hotel) {
        authService.isAdminThrowOtherwise(token);
        return ResponseEntity.ok().body(hotelService.editHotel(hotel_id, hotel));
    }

    @PostMapping("/create")
    public ResponseEntity<String> createHotel(@RequestHeader (GeneralConstants.AUTH_HEADER_NAME) String token, @RequestBody Hotel hotel) {
        authService.isAdminThrowOtherwise(token);
        return ResponseEntity.ok().body(hotelService.createHotel(hotel));
    }

    @PostMapping("/del/{hotel_id}")
    public ResponseEntity<String> deleteHotel(@RequestHeader (GeneralConstants.AUTH_HEADER_NAME) String token, @PathVariable int hotel_id) {
        authService.isAdminThrowOtherwise(token);
        return ResponseEntity.ok().body(hotelService.deleteHotel(hotel_id));
    }

}
