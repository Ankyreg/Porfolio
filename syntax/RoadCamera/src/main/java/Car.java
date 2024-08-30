public class Car {
    private final int height;
    private final double weight;
    private final boolean hasTrailer;
    private final boolean isSpecial;
    private final String number;

    public Car(){
        this.height = (int) (1000 + 3500. * Math.random());
        this.weight = 600 + 10000 * Math.random();
        this.hasTrailer = Math.random() > 0.5;
        this.isSpecial = Math.random() < 0.15;
        this.number = Double.toString(Math.random()).substring(2, 5);
    }


    public int getHeight() {
        return height;
    }


    public double getWeight() {
        return weight;
    }

    public boolean getIsHasTrailer() {
        return hasTrailer;
    }


    public boolean getIsSpecial() {
        return isSpecial;
    }


    public String getNumber() {
        return number;
    }


    public String toString() {
        String special = isSpecial ? "Official transport " : "";
        String trailer = hasTrailer ? "Has Trailer" : "";
        return "\n=========================================\n" +
                special + "Сar with number" + number +
                ":\n\tHeight: " + height + " мм\n\tWeight: " + weight + "kg" + trailer;
    }
}

