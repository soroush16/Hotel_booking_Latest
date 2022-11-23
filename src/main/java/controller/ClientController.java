package controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import model.Client;
import repository.ClientRepository;

import javax.swing.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class ClientController {

   private ClientRepository clientRepository = new ClientRepository();


    public Client createClient() {
        Client client = new Client();
        Long userResponse = Long.parseLong(this.getUserInput("Please enter your personal id code"));
        List<Client> clientsFound = clientRepository.findClientByPersonalIdCode(userResponse);
        if(clientsFound.isEmpty()){

            client.setPersonalId(userResponse);
            client.setFirstName(this.getUserInput("Please enter your name: "));
            client.setLastName(this.getUserInput("Please enter your last name: "));
            client.setAge(Integer.parseInt(this.getUserInput("Please enter your age: ")));
            clientRepository.createClientToDB(client);
        }
        else{
            client= clientsFound.get(0);


        }

        return client;
    }

    public void deleteClient() {
        Long chosenId = Long.parseLong(this.getUserInput("Please enter the client personal id code to be removed"));
        clientRepository.deleteClientFromDB(chosenId);

    }

    public void updateClient() {
        Long chosenId = (long) Integer.parseInt(this.getUserInput("Please enter the client personal id from database to be updated"));
        List<Client> foundClient = clientRepository.findClientByPersonalIdCode(chosenId);
        if (foundClient == null){

        }else {
            for(Client c: foundClient){
                int userChoice = Integer.parseInt(JOptionPane.showInputDialog("Please specify what info you want to update:\n"
                        + " for personal ID enter 1\n"
                        + " for firstname enter 2\n"
                        + " for lastname enter 3\n"
                        + " for age enter 4"));
                if (userChoice == 1) {
                    long personalIdCode = Long.parseLong(this.getUserInput("Please enter new personal ID code: "));
                    c.setPersonalId(personalIdCode);
                } else if (userChoice == 2) {
                    c.setFirstName(this.getUserInput("Please enter new firstname: "));
                } else if (userChoice == 3) {
                    c.setLastName(this.getUserInput("Please enter new lastname: "));
                } else if (userChoice == 4) {
                    c.setAge(Integer.parseInt(this.getUserInput("Please enter new age: ")));
                } else {
                    JOptionPane.showMessageDialog(null, "Something went wrong!");
                    System.exit(0);
                }
                clientRepository.updateClientInfo(c);
            }

        }

    }

    public void findClientByPersonalId() {
        Long chosenId = (long) Integer.parseInt(this.getUserInput("To view client, please enter the client personal id"));
        clientRepository.findClientByPersonalIdCode(chosenId);
    }

    public List<Client> viewAllMyClients() {
        String myText;
        List<Client> myClients;
        myClients = clientRepository.showAllMyClientsFromDB();
        StringBuilder builder = new StringBuilder();
        for (Client c : myClients) {
            builder.append(c);
        }
        myText = builder.toString();

        JOptionPane.showMessageDialog(null, myText);
        return myClients;

    }

    private String getUserInput(String message) {
        return JOptionPane.showInputDialog(message);
    }

}
