public class Main {
    public static void main(String[] args) {

        Client client = new Client();
        Server server = new Server();
        Channel connection = new Channel(server, client);

        connection.registration();
        connection.authentication();

    }

}
