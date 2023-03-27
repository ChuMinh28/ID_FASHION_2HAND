package ra.dev.dto.respone;

public class CancelOrder {
    private int orderId;
    private String userName;

    public CancelOrder() {
    }

    public CancelOrder(int orderId, String userName) {
        this.orderId = orderId;
        this.userName = userName;
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
