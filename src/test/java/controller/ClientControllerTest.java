package controller;

import model.Client;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.ClientRepository;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;


@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class ClientControllerTest {
    private  ClientController clientController;

    @Mock
    private static ClientRepository clientRepositoryMock;

    @BeforeEach
    void setUp(){
        clientController = new ClientController(clientRepositoryMock);
    }


    @Test
    @Order(1)
    void testCreateClient() {

        Client client = clientController.createClient();
        ArgumentCaptor<Client> argumentCaptor = ArgumentCaptor.forClass(Client.class);
        verify(clientRepositoryMock).createClientToDB(argumentCaptor.capture());
        Client capturedClient =argumentCaptor.getValue();
        Assertions.assertEquals(client,capturedClient);
    }

    @Test
    @Order(2)
    void testDeleteClient() {

        clientController.deleteClient();
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(clientRepositoryMock).deleteClientFromDB(argumentCaptor.capture());
        Long capturedId = argumentCaptor.getValue();
        Assertions.assertEquals(12l,capturedId);

    }

    @Test
    @Order(3)
    void testUpdateClient() {
        Client client = new Client(3l,1235l,"john","didi",38);
        client.setAge(41);
        client.setLastName("dodo");
        clientRepositoryMock.updateClientInfo(client);

        ArgumentCaptor<Client> myClient = ArgumentCaptor.forClass(Client.class);

        verify(clientRepositoryMock).updateClientInfo(myClient.capture());

        Client mockClient = myClient.getValue();
        assertThat(mockClient).isEqualTo(client);
        Assertions.assertEquals(mockClient,client);

    }

    @Test
    @Order(4)
    void testFindClientByPersonalId() {
        clientController.findClientByPersonalId();
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(clientRepositoryMock).findClientByPersonalIdCode(argumentCaptor.capture());
        Long captured = argumentCaptor.getValue();
        Assertions.assertEquals(1234,captured);
    }

    @Test
    @Order(5)
    void viewAllMyClients() {
        List<Client> allClients = clientController.viewAllMyClients();
        assertThat(allClients).isNotNull();
    }
}