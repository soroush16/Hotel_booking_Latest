package repository;

import model.Bookings;
import model.Client;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.swing.*;
import java.util.List;

public class ClientRepository {

    private static SessionFactory factory = SessionManager.getFactory();

    public Client createClientToDB(Client client) {
        Session session = factory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.persist(client);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println(e.getClass() + " : " + e.getMessage());
        } finally {
            session.close();
            JOptionPane.showMessageDialog(null, "Client " + client.getFirstName() + " created successfully!");
        }
        return client;
    }

    public List<Client> deleteClientFromDB(Long id) {
        Session session = factory.openSession();
        Transaction transaction = null;
        List<Client> client = null;
        List<Bookings> booking=null;
        BookingRepository bookingRepository = new BookingRepository();
        try {
            transaction = session.beginTransaction();
            booking = bookingRepository.findBookingsFromDBByPersonalId(id);
            if(booking!= null){
                booking.forEach(b->{bookingRepository.deleteBookingsFromDB(b.getId());});
            }
            client = session.createQuery("FROM clients WHERE personalId = " + id, Client.class).getResultList();
            if (client != null){
                client.forEach(c->session.remove(c));
                //session.remove(client);
                JOptionPane.showMessageDialog(null, "Client deleted successfully!");

            }

            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();

        }
        return client;
    }

    public void updateClientInfo(Client client) {
        Session session = factory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.merge(client);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println(e.getClass() + " : " + e.getMessage());
        } finally {
            session.close();
        }
        JOptionPane.showMessageDialog(null, "Client updated successfully!");
    }

    public List<Client> findClientByPersonalIdCode(Long id) {
        Session session = factory.openSession();
        Transaction transaction = null;
        List<Client> client = null;

        try {
            transaction = session.beginTransaction();
            client = session.createQuery("FROM clients WHERE personalId = " + id, Client.class).getResultList();
            if (client != null) {
                JOptionPane.showMessageDialog(null, client.get(0));
            } else {
                JOptionPane.showMessageDialog(null,"You don't have an account with us");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println(e.getClass() + " : " + e.getMessage());
        } finally {
            session.close();
        }
        return client;
    }

    public List<Client> showAllMyClientsFromDB() {
        Session session = factory.openSession();
        Transaction transaction = null;
        List<Client> myClients = null;
        try {
            transaction = session.beginTransaction();
            myClients = session.createQuery("FROM clients", Client.class).getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println(e.getClass() + " : " + e.getMessage());
        } finally {
            session.close();
        }

        return myClients;
    }
}
