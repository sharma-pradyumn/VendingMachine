package VendingMachine;

public class ProductSelectedState extends MachineState {

    @Override
    public void acceptMoney(VendingMachine machineContext, int currentUserMoney) {
        machineContext.incrementUserMoney(currentUserMoney);
        machineContext.setCurrentState(machineContext.getDispensingProductState());
    }

    @Override
    public ProductDispenseResult dispenseProduct(VendingMachine machineContext) {
        throw new IllegalArgumentException("Cannot dispense product. Currently accepting money");
    }

    @Override
    public int cancelOrder(VendingMachine machineContext) {
        machineContext.setProductSelected(null);
        machineContext.setCurrentState(machineContext.getWaitingState());
        return machineContext.resetUserMoney();
    }

    @Override
    public void selectProduct(VendingMachine machineContext, ProductEnum selectedProductType) {
        int productCount = machineContext.getInventory().getOrDefault(selectedProductType, 0);
        if (productCount == 0) {
            System.out.println("Cannot select "+selectedProductType.toString()+" as empty for that product type");
            return;
        }
        machineContext.setProductSelected(selectedProductType);
    }
}
