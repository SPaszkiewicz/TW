package lab3.CustomerProducer;

import java.util.concurrent.ThreadLocalRandom;

public class Main {
    static int bufforSize = 5;
    static int customerNumber = 50;
    static int producentNumber =  50;

    static int foodPortion = 5;

    public static void main(String[] args) {
        manyCustomerManyProducerManyBuffor();
    }

    public static int randomPortion(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
    public static void manyCustomerManyProducerManyBuffor () {
        Bakery bakery = new Bakery(bufforSize, foodPortion);
        for (int i = 0; i < customerNumber; i++) {
            new Thread(new Customer(bakery, 1)).start();
        }
        for (int i = 0; i < producentNumber; i++) {
            new Thread(new Producent(bakery, 1)).start();
        }
    }
}
