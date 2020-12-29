public class MyRandom {

    private final long seed = System.currentTimeMillis();
    private final int a = 57, c = 461, m = 10007;
    private int x = (int) (seed % m);


    public int generateRandomNb(){
        int newX = ((a * x) + c) % m;
        x = newX;
        return newX;
    }

    public int generateRandomNb(int min, int max){
        int newX;
        do {
             newX = generateRandomNb();
        }while (newX<min || newX>max);

        return newX;
    }

    public int generateRandomPrimeNb(){
        int prime = generateRandomNb();
        while (!RabinMillerTest.probablyPrime(prime)){
            prime = generateRandomNb();
        }
        return prime;
    }

    public int generateRandomPrimeNb(int ceiling){
        int prime = generateRandomNb() % ceiling;
        while (!RabinMillerTest.probablyPrime(prime)){
            prime = generateRandomNb() % ceiling;
        }
        return prime;
    }

}