package VendingMachine;

import java.util.LinkedHashMap;

public class VendingMachine {
    private final WaitingState waitingState = new WaitingState();
    private final ProductSelectedState productSelectedState = new ProductSelectedState();
    private final ProductDispensingState productDispensingState = new ProductDispensingState();
    private ProductEnum selectedProduct;
    private MachineState currentState = waitingState;

    private int machineMoney;
    private int userMoney = 0;

    private final LinkedHashMap<ProductEnum, Integer> inventory;

    public VendingMachine(int _machineMoney) {
        machineMoney = _machineMoney;
        inventory = new LinkedHashMap<>();
    }

    /// public API

    public void selectProduct(ProductEnum selectedProductType) {
        currentState.selectProduct(this, selectedProductType);
    }

    public void acceptUserMoney(int currentUserMoney) {
        currentState.acceptMoney(this, currentUserMoney);
    }

    public int getUserMoney() {
        return userMoney;
    }

    public ProductDispenseResult dispenseProduct() {
        return currentState.dispenseProduct(this);
    }

    // cancel order and return money
    public int cancelOrder() {
        return currentState.cancelOrder(this);
    }

    public void refillInventory(ProductEnum productType, int numItem) {
        inventory.compute(productType, (_, previousVal) -> {
            int newVal = previousVal != null ? previousVal : 0;
            return newVal+numItem;
        });
    }

    /// package private -> To be used by state classes

    // selectedProduct fns
    ProductEnum getSelectedProduct() {
        return selectedProduct;
    }

    void setProductSelected(ProductEnum _selectedProduct) {
        selectedProduct = _selectedProduct;
    }
    LinkedHashMap<ProductEnum, Integer> getInventory() {
        return inventory;
    }

    // machineMoney fns

    int getMachineMoney() {
        return machineMoney;
    }

    int incrementMachineMoney(int m) {
        machineMoney += m;
        return machineMoney;
    }

    int decrementMachineMoney(int m) {
        machineMoney -= m;
        return machineMoney;
    }

    void setMachineMoney(int m) {
        machineMoney = m;
    }

    // user money fns
    void incrementUserMoney(int money) {
        userMoney += money;
    }

    void decrementUserMoney(int money) {
        userMoney -= money;
    }

    void setUserMoney(int _userMoney) {
        userMoney = _userMoney;
    }

    int resetUserMoney() {
        int previousUserMoney = userMoney;
        userMoney = 0;
        return previousUserMoney;
    }

    // state setters and getters
    MachineState getCurrentState() {
        return currentState;
    }

    WaitingState getWaitingState() {
        return waitingState;
    }

    ProductDispensingState getDispensingProductState() {
        return productDispensingState;
    }

    ProductSelectedState getProductSelectedState() {
        return productSelectedState;
    }

    void setCurrentState(MachineState currentState) {
        this.currentState = currentState;
    }


}
