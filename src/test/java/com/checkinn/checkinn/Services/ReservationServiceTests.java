package com.checkinn.checkinn.Services;

import com.checkinn.checkinn.Repositories.HotelRepository;
import com.checkinn.checkinn.Repositories.ReservationRepository;
import com.checkinn.checkinn.Repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTests {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    void edit_reservation_successfully() {

    }

    @Test
    void edit_reservation_bad_times_not_vibes_but_actual_time() {

    }

    @Test
    void edit_reservation_incorrect_user() {

    }

    @Test
    void delete_reservation_successfully() {

    }

    @Test
    void delete_reservation_incorrect_user() {

    }

    @Test
    void create_reservation_successfully() {

    }

    @Test
    void create_reservation_incorrect_user() {

    }

    @Test
    void create_reservation_bad_times_not_vibes_but_actual_time() {

    }

    @Test
    void create_reservation_not_enough_rooms() {

    }
}
