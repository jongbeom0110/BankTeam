package BankDTO;

// BCODE class

public class BCode {

    private int AcCode; // 계좌 코드
    private String AcType; // 계좌 종류

    public int getAcCode() {
        return AcCode;
    }

    public void setAcCode(int acCode) {
        this.AcCode = AcCode;
    }

    public String getAcType() {
        return AcType;
    }

    public void setAcType(String acType) {
        this.AcType = AcType;
    }

    @Override
    public String toString() {
        return " Code [" +
                "acCode=" + AcCode +
                ", acType='" + AcType +
                " ]";

    }
}