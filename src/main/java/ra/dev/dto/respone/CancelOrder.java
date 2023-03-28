package ra.dev.dto.respone;

public class CancelOrder {
    private int orderId;
    private String userName;

    private int quantityOfCancellations;

    private int totalAmount;

    public CancelOrder() {
    }

    public CancelOrder(int orderId, String userName, int quantityOfCancellations, int totalAmount) {
        this.orderId = orderId;
        this.userName = userName;
        this.quantityOfCancellations = quantityOfCancellations;
        this.totalAmount = totalAmount;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getQuantityOfCancellations() {
        return quantityOfCancellations;
    }

    public void setQuantityOfCancellations(int quantityOfCancellations) {
        this.quantityOfCancellations = quantityOfCancellations;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
