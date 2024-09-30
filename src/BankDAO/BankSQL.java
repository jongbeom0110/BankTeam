package BankDAO;

import BankDTO.Account;

import BankDTO.BCode;
import BankDTO.Client;
import Util.BankUtil;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class BankSQL {
    Connection con;
    PreparedStatement pstmt;
    ResultSet rs;
    BCode code = new BCode();
    // 회원가입
    public void conClose(){
        try {
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void join(Client client) {
        con = BankUtil.getConnection();
        try {

            // (1) sql문 작성
            String sql = "INSERT INTO ACLIENT VALUES (?, ?, ?, ?)";

            // (2) db준비
            pstmt = con.prepareStatement(sql);

            // (3) '?' 데이터 입력
            pstmt.setString(1, client.getBId());
            pstmt.setString(2, client.getBPw());
            pstmt.setString(3, client.getBName());
            pstmt.setInt(4, client.getBAge());

            // (4) 입력
            int result = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }




// 로그인
    public boolean login(String BId, String BPw) {
        con = BankUtil.getConnection();
        try {
            // (1) sql문 작성
            String sql = "SELECT * FROM ACLIENT WHERE BID = ? AND BPW = ?";

            // (2) db준비
            pstmt = con.prepareStatement(sql);

            // (3) '?' 데이터 입력
            pstmt.setString(1,BId);
            pstmt.setString(2,BPw);


            // (4) 입력
            rs = pstmt.executeQuery();
            Client client = null;

            // (5) 결과

            if(rs.next()){
                client = new Client();
                client.setBId(rs.getString("BId"));
                client.setBPw(rs.getString("BPw"));

                return true;
            }

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return false;
    }



// 계좌 생성
    public void createAccount(Account account){
        con = BankUtil.getConnection();
        try {
            String sql = "INSERT INTO CACCOUNT VALUES (?, ?, ?, ?)";

            pstmt = con.prepareStatement(sql);

            pstmt.setString(1,account.getCId());
            pstmt.setInt(2, account.getCCode());
            pstmt.setString(3, account.getBAccount());
            pstmt.setInt(4, account.getBalance());

            int result = pstmt.executeUpdate();

            if (result > 0) {
                System.out.println("✨ 계좌가 성공적으로 생성되었습니다! "+ "금융의 새로운 지평을 여는 첫걸음을 내딛으셨습니다. 🌟");
            } else {
                System.out.println("계좌 생성 실패");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        }


    public void checkAccount(String cId) {
            String sql = "SELECT A.CID,A.BALANCE, B.ACTYPE FROM CACCOUNT A, BCODE B  WHERE B.ACCODE = A.CCODE AND CID=?";
                    ;
            try {
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, cId);
                rs = pstmt.executeQuery();

                while (rs.next()) {
                    System.out.print("예금주 : " + rs.getString("CID"));
                    System.out.print(" 잔액  : " + rs.getInt("BALANCE") + "원" +"   " ); // 잔액 출력
                    System.out.println("계좌유형 : " + rs.getString("ACTYPE")); // 코드 타입 출력
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
// 계좌 중복 체크
    public int checkBalance(String cId) {
        int balance = 0;
        con = BankUtil.getConnection();
        try {
            String sql = "SELECT BALANCE FROM CACCOUNT WHERE CID = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, cId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                balance = rs.getInt(1);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return balance;
    }

    // 입금
    public void deposit(String cAccount, int amount) {
        con = BankUtil.getConnection();
        try {
            String sql = "UPDATE CACCOUNT SET BALANCE = BALANCE+? WHERE BACCOUNT = ?";
            pstmt=con.prepareStatement(sql);

            pstmt.setInt(1,amount); //데이터 입력
            pstmt.setString(2, cAccount);

            int result = pstmt.executeUpdate();     // 실행

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 출금
    public void withdraw(String cAccount, int amount) {
        con = BankUtil.getConnection();
        try{
            String sql = "UPDATE CACCOUNT set BALANCE = BALANCE - ? WHERE BACCOUNT = ?";
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1,amount);
            pstmt.setString(2,cAccount);

             int result = pstmt.executeUpdate();

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public String countAccount(String cId) {
        String result = null;
        con = BankUtil.getConnection();
        try{
            String sql = "SELECT COUNT(CID) FROM CACCOUNT WHERE CID =? ";
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1,cId);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                int ac = rs.getInt(1);
                if (ac < 3) {
                      result = accountNumber();
                }
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return result;
    }

    // 사용자 계좌 목록 조회
    public List<Account> getacList() {
        con = BankUtil.getConnection();
        List<Account> acList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM CACCOUNT";

            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Account account = new Account();
                account.setCId(rs.getString("cid"));
                account.setCCode(rs.getInt("ccode"));
                account.setBAccount(rs.getString("baccount"));
                account.setBalance(rs.getInt("balance"));
                acList.add(account);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return acList;
    }
    public String accountNumber(){
        int age = 0;
        String account = null;
        if(age <= 20) {
            account = "3333-";
            for (int i = 1; i <= 9; i++) {
                account += (int) (Math.random() * 9);
            }
        } else if(age > 14){
            account = "7777-";
            for (int i = 1; i <= 9; i++) {
                account += (int) (Math.random() * 9);
            }
        }
        return account;
    }
}
