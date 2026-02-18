package VendingMachine;

public class WaitingState extends MachineState {

    @Override
    public void acceptMoney(VendingMachine machineContext, int currentUserMoney) {
        throw new IllegalArgumentException("Cannot accept money. In waiting state.");
    }

    @Override
    public ProductDispenseResult dispenseProduct(VendingMachine machineContext) {
        throw new IllegalArgumentException("Cannot dispense product. In waiting state.");
    }

    @Override
    public int cancelOrder(VendingMachine machineContext) {
        throw new IllegalArgumentException("Cannot cancel order. In waiting state.");
    }

    @Override
    public void selectProduct(VendingMachine machineContext, ProductEnum selectedProductType) {
        machineContext.setProductSelected(selectedProductType);
        machineContext.setCurrentState(machineContext.getProductSelectedState());
    }
}
