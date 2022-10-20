package controller;


import model.Bookings;
import model.Client;
import model.Hotel;
import org.hibernate.internal.util.type.PrimitiveWrapperHelper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.BookingRepository;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
public class BookingControllerTest {

    @Mock
    private BookingRepository bookingRepositoryMock;
    private BookingController bookingController;

    @BeforeEach
    public void setUp() {
        bookingController = new BookingController(bookingRepositoryMock);
    }

    @Test
    @Order(1)
    void testCreateNewBooking() {

        Bookings booking = bookingController.createNewBooking();
        ArgumentCaptor<Bookings> capturedBooking = ArgumentCaptor.forClass(Bookings.class);

        verify(bookingRepositoryMock).createBookingToDB(capturedBooking.capture());

        Bookings madeBooking = capturedBooking.getValue();
        Assertions.assertEquals(madeBooking,booking);
    }

    @Test
    @Order(2)
    void testDeleteBooking() {

        bookingController.deleteBooking();
        ArgumentCaptor<Long> captured = ArgumentCaptor.forClass(Long.class);
        verify(bookingRepositoryMock).deleteBookingsFromDB(captured.capture());
        Long capturedValue = captured.getValue();
        Assertions.assertEquals(capturedValue,1l);

    }

    @Test
    @Order(3)
    void testUpdateBooking() {

       Bookings booking= bookingController.updateBooking();
       ArgumentCaptor<Bookings> argumentCaptor = ArgumentCaptor.forClass(Bookings.class);

       verify(bookingRepositoryMock).updateBookingFromDB(argumentCaptor.capture());
        Bookings newBooking = new Bookings();


        Bookings capturedBooking = argumentCaptor.getValue();
        Assertions.assertEquals(capturedBooking, newBooking);



    }

    @Test
    @Order(4)
    void testViewAllMyBookings() {
        List<Bookings> allBookings = bookingController.viewAllMyBookings();
        Assertions.assertNotNull(allBookings);

    }
    @Test
    @Order(5)
    void testFindBookingById() {

        bookingController.findBookingById();
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(bookingRepositoryMock).findBookingFromDBById(argumentCaptor.capture());
        Long captured =argumentCaptor.getValue();
        Assertions.assertEquals(captured,1l);

    }
}
