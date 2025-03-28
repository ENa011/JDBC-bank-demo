package entity;

public class ClientAccount {
    int id;
    double balance;
    int ownerId;
    String ownerName;

    public ClientAccount(){

    }

    public ClientAccount(String ownerName, int ownerId, double balance, int id) {
        this.ownerName = ownerName;
        this.ownerId = ownerId;
        this.balance = balance;
        this.id = id;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public double getBalance() {
        return balance;
    }

    public int getId() {
        return id;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ClientAccount{" +
                "id=" + id +
                ", balance=" + balance +
                ", ownerId=" + ownerId +
                ", ownerName='" + ownerName + '\'' +
                '}';
    }
}
