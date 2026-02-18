import VendingMachine.VendingMachine;
import VendingMachine.ProductEnum;
import VendingMachine.ProductDispenseResult;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        VendingMachine machine = new VendingMachine(20);
        machine.refillInventory(ProductEnum.LAYS, 20);
        machine.refillInventory(ProductEnum.COKE, 20);

        machine.selectProduct(ProductEnum.COKE);
        machine.acceptUserMoney(100);
        ProductDispenseResult res = machine.dispenseProduct();
        System.out.println(res);

        machine.selectProduct(ProductEnum.COKE);
        machine.acceptUserMoney(100);
        int cancelRes = machine.cancelOrder();
        System.out.println("cancelRes "+cancelRes);
    }
}

//Core Requirements
//Before jumping into code, let’s define the scope for a FAANG-level SDE2 response:
//Selection: User selects a product.
//Payment: User inserts coins/notes (handling different denominations).
//Dispense: Machine releases product and returns change.
//Concurrency: Ensure two people can’t buy the last "Coke" at the exact same millisecond.
//Maintenance: Ability for an admin to refill inventory or collect cash.

//1. Functional Requirements
//Inventory Management: Support multiple products (Coke, Pepsi, Soda, etc.) with unique IDs, names, and prices.
//Payment System: Accept multiple denominations (e.g., $1, $2, $5 coins/notes).
//Selection: User selects a product via a unique code or ID.
//
//Transaction Flow:
//Initialize a transaction when a product is selected.
//Track the "Current Amount" inserted.
//Dispense the product if payment ≥ price.
//Return change if payment > price.
//Cancellation: User can cancel the transaction at any time before dispensing to get a full refund of inserted money.
//
//2. Technical / Non-Functional Requirements
//Concurrency: Ensure thread safety. If two users try to buy the last "Coke" simultaneously, only one should succeed.
//State Management: Use the State Design Pattern to handle transitions (Idle, Waiting for Money, Dispensing, etc.) to avoid "if-else hell."
//Extensibility: It should be easy to add new payment methods (e.g., Card/UPI) or new types of products later.
//
// 3. Edge Cases (The "Senior" Details)
//Out of Stock: What happens if a user selects a product that is empty?
//Insufficient Change: What if the machine owes $1.50 in change but only has $1.00 in its internal coin storage?
//Transaction Timeout: If a user selects an item but doesn't insert money for 5 minutes, does the machine reset? (For the LLD, you can just define the reset() hook).

// State flow: waiting state ->{user selects product} VendingMachine.ProductSelectedState -> {userEnters money} VendingMachine.ProductDispensingState -> {dispenses produ ct and returns to waiting state}