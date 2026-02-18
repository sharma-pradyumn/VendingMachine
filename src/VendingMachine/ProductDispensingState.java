package VendingMachine;

public class ProductDispensingState extends MachineState {

    @Override
    public void acceptMoney(VendingMachine machineContext, int currentUserMoney) {
        throw new IllegalArgumentException("Currently dispensing product. Cannot accept money");
    }

    // if product is not available, or not enough money, machine will flash message and revert to productSelection state
    @Override
    public ProductDispenseResult dispenseProduct(VendingMachine machineContext) {
        ProductEnum selectedProductType = machineContext.getSelectedProduct();
        int cost = selectedProductType.getPrice();
        if (machineContext.getUserMoney() < cost) {
            System.out.println("Cannot dispense "+selectedProductType.toString()+" as current user money "+machineContext.getUserMoney()+" and product cost is "+cost);
            machineContext.setCurrentState(machineContext.getProductSelectedState());
            return new ProductDispenseResult(null,0);
        }

        int previousItemCount = machineContext.getInventory().getOrDefault(selectedProductType,0);
        if (previousItemCount == 0) {
            System.out.println("Cannot select "+selectedProductType.toString()+" as empty for that product type");
            machineContext.setCurrentState(machineContext.getProductSelectedState());
            return new ProductDispenseResult(null,0);
        }

        machineContext.getInventory().put(selectedProductType, previousItemCount-1);
        machineContext.decrementUserMoney(cost);
        machineContext.incrementMachineMoney(cost);
        int remainingMoney = machineContext.getUserMoney();
        machineContext.resetUserMoney();
        machineContext.setCurrentState(machineContext.getWaitingState());
        return new ProductDispenseResult(selectedProductType, remainingMoney);
    }

    @Override
    public int cancelOrder(VendingMachine machineContext) {
        machineContext.setProductSelected(null);
        machineContext.setCurrentState(machineContext.getWaitingState());
        return machineContext.resetUserMoney();
    }

    @Override
    public void selectProduct(VendingMachine machineContext, ProductEnum selectedProductType) {
        throw new IllegalArgumentException("Cannot select Product. Currently dispensing product");
    }
}

// Will later handle multi threading
//        AtomicBoolean isProductAvailable = new AtomicBoolean(false);
//        machineContext.getInventory().compute(selectedProductType, (k, oldValue)->{
//            if (oldValue == null || oldValue == 0) {
//                return 0;
//            }
//            isProductAvailable.set(true);
//            return oldValue-1;
//        });