import java.util.Scanner;


public class RoadCamera {
    public static final double PASSENGER_CAR_MAX_WEIGHT = 3500.0; // kg
    public static final int PASSENGER_CAR_MAX_HEIGHT = 2000; // mm
    public static final int CONTROLLER_MAX_HEIGHT = 3500; // mm

    public static final int PASSENGER_CAR_PRICE = 100; // RUB
    public static final int CARGO_CAR_PRICE = 250; // RUB
    public static final int TRAILER_ADDITIONAL_PRICE = 200; // RUB

    public static void main(String[] arg) {
        System.out.println("How much random cars need to be created? Please enter the number:");
        Scanner scanner = new Scanner(System.in);
        int randomCarsCount = scanner.nextInt();
        for (int i = 0; i < randomCarsCount; i++) {
            Car car = new Car();
            System.out.println(car);
            if (car.getIsSpecial()) {
                openWay();
                continue;
            }
            int price = priceCalculate(car);
            if (price == -1) {
                blockWay("over the maximum height");
                continue;
            }
            System.out.println("payable amount " + price + " rub");

        }

    }

    private static int priceCalculate(Car car) {
        int price = (car.getIsHasTrailer() ? TRAILER_ADDITIONAL_PRICE : 0);
        int carHigh = car.getHeight();
        double carWeigh = car.getWeight();
        if (carHigh > CONTROLLER_MAX_HEIGHT) {
            return -1;
        } else if (carHigh > PASSENGER_CAR_MAX_HEIGHT || carWeigh > PASSENGER_CAR_MAX_WEIGHT) {
            price = price + CARGO_CAR_PRICE;
        } else {
            price = price + PASSENGER_CAR_PRICE;
        }
        return price;
    }

    private static void blockWay(String reason) {
        System.out.println("Vehicle passage is impossible: " + reason);
    }

    private static void openWay() {
        System.out.println("Have a safe trip!");
    }
}
