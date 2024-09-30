package Util;


import BankDAO.BankSQL;
import BankDTO.Account;
import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class BankUtil {

    BankSQL sql = new BankSQL();

    List<Account> acList = sql.getacList();

    Scanner sc = new Scanner(System.in);

    Account account = new Account();
    int balance = 0;

    static {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException cne) {
            System.out.println("드라이버 로딩 실패");
            cne.printStackTrace();
        }
    }

    public static Connection getConnection() { //메소드(함수)
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.30:1521:xe",
                    "rok", "1111");
            //수동 커밋으로 변경
            //con.setAutoCommit(false); //기본값:자동커밋(true)
        } catch (SQLException se) {
            System.out.println("DB 접속 실패");
            se.printStackTrace();
        }
        return con;
    }//connect end


    public void GetAc(String cId){
        // 계좌 개수를 확인하여 3개 이상인지 확인
        String acCode = sql.countAccount(cId);

        if (acCode == null) { // 3개 초과 시 계좌 생성 불가 메시지
            System.out.println("계정당 3개의 계좌를 초과할수 없습니다.");
        } else {
            // 계좌 유형 선택
            System.out.println("원하시는 계좌 개설 유형을 선택해주세요.");
            System.out.println("============================================");
            System.out.println("[1]청약\t\t[2] 주식\t\t[3]일반\t\t[4]적금\t\t");
            System.out.println("============================================");
            System.out.println("선택 >>");
            int acNum = sc.nextInt();
            account.setCId(cId);
            account.setCCode(acNum);
            account.setBAccount(acCode);
            account.setBalance(0);
            sql.createAccount(account);

        }
    }

    public void remit(){
        List<Account> acList = sql.getacList();
        // 계좌 목록을 출력하고 사용자가 선택하도록 함
        System.out.println("계좌목록");
        for (int i = 0; i < acList.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + acList.get(i));
        }
        System.out.print("출금할 계좌 번호를 선택하세요: ");
        int sAccount = sc.nextInt() - 1; //1부터 시작하는 번호를 0부터 시작하는 인덱스로 변환
        // 선택한 계좌가 유효한지 확인
        if (sAccount >= 0 && sAccount < acList.size()) {
            // 선택한 계좌가 유효한 경우
            Account sac = acList.get(sAccount);
            System.out.println();

            System.out.println("계좌목록");
            acList = sql.getacList();
            for (int i = 0; i < acList.size(); i++) {
                System.out.println("[" + (i + 1) + "] " + acList.get(i));
            }
            System.out.print("입금할 계좌 번호를 선택하세요: ");

            int rIndex = sc.nextInt() - 1; //1부터 시작하는 번호를 0부터 시작하는 인덱스로 변환
            // 선택한 계좌가 유효한지 확인
            if (rIndex >= 0 && rIndex < acList.size()) {
                // 선택한 계좌가 유효한 경우
                Account rac = acList.get(rIndex);
                System.out.print("송금할 금액을 입력하세요: ");
                int amount = sc.nextInt();
                balance = sql.checkBalance(sac.getBAccount());
                if (amount >= balance) {
                    sql.withdraw(sac.getBAccount(), amount);
                    sql.deposit(rac.getBAccount(), amount);
                    System.out.println("송금 성공!");
                } else {
                    System.out.println("잔액이 부족합니다.");
                }
            } else {
                System.out.println("잘못된 계좌 번호입니다.");
            }
        } else {
            // 선택한 계좌가 유효하지 않은 경우
            System.out.println("잘못된 계좌 번호입니다.");
        }

    }

    public void deposit() {
        // 로그인한 사용자의 모든 계좌 목록을 가져옴
        List<Account> acList = sql.getacList();
        // 계좌 목록을 출력하고 사용자가 선택하도록 함
        System.out.println("계좌목록");
        for (int i = 0; i < acList.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + acList.get(i));
        }
        System.out.print("입금할 계좌 번호를 선택하세여 >> ");
        int sAccount = sc.nextInt() - 1;

        // 선택한 계좌에 대해 입금 처리
        if (sAccount >= 0 && sAccount <= acList.size()) {
            Account selectedAccount = acList.get(sAccount);
            System.out.print("입금액 >> ");
            int amount = sc.nextInt(); // 입금액 입력받음
            sql.deposit(selectedAccount.getBAccount(), amount);
            System.out.println(account.getBalance() + "원이 입금되었습니다");// 선택한 계좌에 입금 처리
        } else {
            System.out.println("잘못된 계좌번호입니다.");
        }

    }

    public void withdraw() {
        acList = sql.getacList();

        System.out.println("계좌목록");
        for (int i = 0; i < acList.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + acList.get(i).getCId());
        }
        System.out.print("출금할 계좌 번호를 선택하세여 >> ");
        int rAccount = sc.nextInt() - 1;

        // 선택한 계좌에 대해 입금 처리
        if (rAccount >= 0 && rAccount <= acList.size()) {
            Account selectedAccount = acList.get(rAccount);
            System.out.print("출금액 >> ");
            int amount = sc.nextInt(); // 입금액 입력받음
            sql.withdraw(selectedAccount.getBAccount(), amount);
            System.out.println(balance + "원이 출금되었습니다");// 선택한 계좌에 입금 처리
        } else {
            System.out.println("잘못된 계좌번호입니다.");
        }
    }

}