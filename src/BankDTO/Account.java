package BankDTO;

public class Account {

    private String CId;   //
    private int CCode;
    private String BAccount;
    private int Balance;


    public String getCId() {
        return CId;
    }

    public void setCId(String CId) {
        this.CId = CId;
    }

    public int getCCode() {
        return CCode;
    }

    public void setCCode(int CCode) {
        this.CCode = CCode;
    }

    public String getBAccount() {
        return BAccount;
    }

    public void setBAccount(String BAccount) {
        this.BAccount = BAccount;
    }

    public int getBalance() {
        return Balance;
    }

    public void setBalance(int balance) {
        Balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "계좌번호='" + BAccount + '\'' +
                " 사용자" + CId + '\'' +
                ", 코드번호=" + CCode +
                ", 잔액=" + Balance +
                '}';
    }
}
