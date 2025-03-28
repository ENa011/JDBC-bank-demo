package entity;

public class Transaction {
    private int tractionId;
    private String senderName;
    private String receiverName;
    private double balance;
    private int senderId;
    private int receiverId;

    public Transaction() {

    }

    public Transaction(double balance, String receiverName, String senderName,
                       int tractionId, int senderId, int receiverId) {
        this.balance = balance;
        this.receiverName = receiverName;
        this.senderName = senderName;
        this.tractionId = tractionId;
        this.receiverId = receiverId;
        this.senderId = senderId;
    }

    public double getBalance() {
        return balance;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getSenderName() {
        return senderName;
    }

    public int getTractionId() {
        return tractionId;
    }

    public int getSenderId() {
        return senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public void setTractionId(int tractionId) {
        this.tractionId = tractionId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "tractionId=" + tractionId +
                ", senderName='" + senderName + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", balance=" + balance +
                ", senderId=" + senderId +
                ", receiverId=" + receiverId +
                '}';
    }
}
