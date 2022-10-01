package controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import model.Hotel;
import repository.HotelRepository;

import javax.swing.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class HotelController {
     HotelRepository hotelRepository = new HotelRepository();

    public void makeNewHotel (){
        Hotel hotel = new Hotel();
        hotel.setHotelName(this.getUserInput("Please enter Hotel name: "));
        hotel.setAddress(this.getUserInput("Please enter Hotel address: "));
        hotel.setNumberOfRooms(Integer.parseInt(this.getUserInput("Please enter number how many rooms the hotel contains: ")));
        hotel.setPrice(Double.valueOf(this.getUserInput("Enter the price for one night")));
        hotelRepository.createHotelToDB(hotel);
    }

    public void updateHotel (){
        Long userChoice = Long.valueOf(this.getUserInput("Please enter hotel id to update"));
        Hotel foundHotel = hotelRepository.findHotelFromDBById(userChoice);
        if (foundHotel != null){
            int newUserChoice = Integer.parseInt(JOptionPane.showInputDialog("Please specify what info you want to update:\n"
                    + " for address enter 1\n"
                    + " for Hotel name enter 2\n"
                    + " for number of rooms enter 3\n"
                    + " to change room price enter 4\n"));

            if (userChoice == 1) {
                foundHotel.setAddress(this.getUserInput("Please enter new address: "));
            } else if (userChoice == 2) {
                foundHotel.setHotelName(this.getUserInput("Please enter new Hotel name: "));
            } else if (userChoice == 3) {
                int numberOfRooms = Integer.parseInt(this.getUserInput("Please enter new number of rooms: "));
                foundHotel.setNumberOfRooms(numberOfRooms);
            } else if (userChoice == 4) {
                Double pricePerDay = Double.parseDouble(this.getUserInput("Please enter new price: "));
                foundHotel.setPrice(pricePerDay);
            } else {
                JOptionPane.showMessageDialog(null, "Something went wrong!");
                System.exit(0);
            }
            hotelRepository.updateHotelFromDB(foundHotel);
        }
    }

    public void deleteHotel() {
        Long chosenId = Long.parseLong(this.getUserInput("Please enter the Hotel id to be removed"));
        hotelRepository.deleteHotelFromDB(chosenId);
    }

    public void getAllHotels() {
        String myText;
        List<Hotel> myHotels;
        myHotels = hotelRepository.getAllHotelsFromDB();
        StringBuilder builder = new StringBuilder();
        for (Hotel h : myHotels) {
            builder.append(h);
        }
        myText = builder.toString();

        JOptionPane.showMessageDialog(null, myText);
//        List<Hotel> hotels = hotelRepository.showAllMyHotelsFromDB();
//        System.out.println(hotels);
// --------------------------------------
//        hotels.forEach(System.out::println);
    }
    public void findHotelById() {
        Long chosenId = (long) Integer.parseInt(this.getUserInput("To view Hotel, please enter the Hotel id"));
        hotelRepository.findHotelFromDBById(chosenId);
    }

    private String getUserInput(String message) {
        return JOptionPane.showInputDialog(message);
    }

}