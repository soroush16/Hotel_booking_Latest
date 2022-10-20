package controller;

import model.Hotel;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.HotelRepository;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;


@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
public class HotelControllerTest {
    @Mock
    private HotelRepository hotelRepository;
    private HotelController hotelController;

    @BeforeEach
    public void setUp() {
        hotelController = new HotelController(hotelRepository);
    }

    @Test
    @Order(1)
    void testMakeNewHotel() {
        Hotel hotel = hotelController.makeNewHotel();
        ArgumentCaptor<Hotel> argumentCapture = ArgumentCaptor.forClass(Hotel.class);

        verify(hotelRepository).createHotelToDB(argumentCapture.capture());

        Hotel capturedHotel = argumentCapture.getValue();
        Assertions.assertEquals(capturedHotel, hotel);
    }

    @Test
    @Order(2)
    void testUpdateHotel() {
        Hotel hotel = new Hotel(2L, "Hilton", "Paide", 50, 79.0);
        hotel.setNumberOfRooms(45);
        hotel.setPrice(69.0);
        hotelRepository.updateHotelFromDB(hotel);

        ArgumentCaptor<Hotel> myHotel = ArgumentCaptor.forClass(Hotel.class);

        verify(hotelRepository).updateHotelFromDB(myHotel.capture());

        Hotel capturedHotel = myHotel.getValue();
        Assertions.assertEquals(capturedHotel,hotel);


    }

    @Test
    @Order(3)
    void testDeleteHotel() {

        hotelController.deleteHotel();
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(hotelRepository).deleteHotelFromDB(argumentCaptor.capture());
        Long captured = argumentCaptor.getValue();
        Assertions.assertEquals(2l,captured);

    }

    @Test
    @Order(4)
    void testGetAllHotels() {
        List<Hotel> hotels = hotelController.getAllHotels();
        assertThat(hotels).isNotNull();

    }

    @Test
    @Order(5)
    void testFindHotelById() {

        hotelController.findHotelById();
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(hotelRepository).findHotelFromDBById(argumentCaptor.capture());
        Long captured = argumentCaptor.getValue();
        assertThat(captured).isEqualTo(3l);

    }

}

