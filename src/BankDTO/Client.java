package BankDTO;

public class Client {

    private String BId;         // 고객아이디
    private String BPw;         // 고객비밀번호
    private String BName;       // 고객이름
    private int BAge;


    public String getBId() {
        return BId;
    }

    public void setBId(String BId) {
        this.BId = BId;
    }

    public String getBPw() {
        return BPw;
    }

    public void setBPw(String BPw) {
        this.BPw = BPw;
    }

    public String getBName() {
        return BName;
    }

    public void setBName(String BName) {
        this.BName = BName;
    }


    public int getBAge() {
        return BAge;
    }

    public void setBAge(int BAge) {
        this.BAge = BAge;
    }
    @Override
    public String toString() {
        return "client[" +
                ", 아이디 : '" + BId + '\'' +
                ", 비밀번호 : " + BPw + '\'' +
                ", 이름 : " + BName + '\'' +
                ", 나이 : " + BAge + '\'' +
                ']';
    }
}
