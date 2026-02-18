package VendingMachine;

public enum ProductEnum {
    COKE(40),
    LAYS(10);
    private final int price;
    ProductEnum(int p) {
        price = p;
    }
    public int getPrice() {
        return this.price;
    }
}
