package VendingMachine;

public abstract class MachineState {
    public abstract void acceptMoney(VendingMachine machineContext, int currentUserMoney);
    public abstract ProductDispenseResult dispenseProduct(VendingMachine machineContext);
    public abstract int cancelOrder(VendingMachine machineContext);
    public abstract void selectProduct(VendingMachine machineContext, ProductEnum selectedProductType);
}
