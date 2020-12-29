public class Channel {

    private int q, n;
    private Server server;
    private Client client;
    private MyRandom rand = new MyRandom();

    Channel(Server server, Client client){
        this.server = server;
        this.client = client;

        distributeN();
    }

    void distributeN(){
        int q;
        do{
            q = rand.generateRandomPrimeNb(1000);
            n = 2 * q + 1;
        }while (!RabinMillerTest.probablyPrime(n));

        server.setN(n);
        client.setN(n);
    }

    void registration(){
        client.registerOnServer();
        server.setI(client.getI());
        server.setSalt(client.getSalt());
        server.setV(client.getV());
        System.out.println("Регистрация прошла успешно!");
        System.out.println();
    }

    void authentication(){
        if (authenticationPhase1()){
            if (authenticationPhase2()){
                System.out.println();
                System.out.println("Аутентификация прошла успешно!");
            }
            else System.out.println("Аутентификация 2 прошла плачевно(((");
        }
        else System.out.println("Аутентификация 1 прошла плачевно(((");
    }

    boolean authenticationPhase1(){
        if (!server.checkI(client.getI())) System.out.println("No such user");
        server.setA(client.getA());
        if (!server.checkA()) System.out.println("A = 0;");

        server.findB();
        client.setB(server.getB());
        client.setSalt(server.getSalt());
        if (!client.checkB()) System.out.println("B = 0;");

        server.calculateU();
        client.calculateU();
        if (!(server.checkU() && client.checkU())){
            System.out.println("u = 0;");
            return false;
        }

        System.out.println("Начало аутентификации фазы 1");
        client.generateKey();
        server.generateKey();
        System.out.println();

        return true;
    }

    boolean authenticationPhase2(){
        System.out.println("Начало аутентификации фазы 2");
        client.calculateM();
        if(!server.compareM(client.getM())) {
            System.out.println("M-s are different");
            return false;
        }
        System.out.println();

        server.calculateR();
        if(!client.compareR(server.getR())) {
            System.out.println("Ошибка");
            return false;
        }

        return true;
    }

}
