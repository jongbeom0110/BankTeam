package BankMain;

import BankDAO.BankSQL;
import BankDTO.BCode;
import BankDTO.Client;
import Util.BankUtil;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // 키보드로 입력받기 위한 Scanner 객체 생성
        Scanner sc = new Scanner(System.in);

        // 프로그램이 실행 중인지 여부를 확인하는 변수
        boolean run = true;

        // 로그인 후 메뉴가 실행 중인지 여부를 확인하는 변수
        boolean run2 = true;

        // 데이터베이스 관련 작업을 처리하는 객체
        BankSQL sql = new BankSQL();

        // 은행 코드를 저장하는 객체
        BCode bcode = new BCode();
        // 고객 정보를 저장하는 객체
        Client client = new Client();

        BankUtil util = new BankUtil();

        String BId;
        String BPw;

        // 메인 메뉴와 서브 메뉴의 선택을 저장하는 변수
        int menu = 0;
        int menu1 = 0;

        while(run){
            System.out.println("\u001B[31m" + "==============================" + "\u001B[0m");
            System.out.println("\u001B[32m" + "[1]회원가입\t[2]로그인\t\t[3]종료" + "\u001B[0m");
            System.out.println("\u001B[31m" + "==============================" + "\u001B[0m");
            System.out.println("선택 >>");


            menu = sc.nextInt();

            switch (menu){
                case 1: // 회원가입
                    System.out.println("아이디 >>" );
                    client.setBId(sc.next());

                    System.out.println("비밀번호 >>" );
                    client.setBPw(sc.next());

                    System.out.println("이름 >>" );
                    client.setBName(sc.next());

                    System.out.println("나이 >>");
                    client.setBAge(sc.nextInt());

                    sql.join(client);
                    System.out.println("환영합니다! 🌟 " + client.getBName() + "님, 회원가입이 성공적으로 완료되었습니다. 새로운 시작을 함께할 수 있어 기쁩니다!");
                    break;

                case 2: // 로그인
                    System.out.println("\u001B[34m" + "[로그인]" + "\u001B[0m");

                    System.out.print("아이디 입력 : ");
                    BId = sc.next(); // ID 입력받음
                    System.out.print("비밀번호 입력 : ");
                    BPw = sc.next(); // 비밀번호 입력받음

                    // ID와 비밀번호가 맞는지 확인
                    boolean login = sql.login(BId, BPw);
                    String cId = null;
                    if(login){  // 로그인 성공 시
                        while(run2) {
                            System.out.println("\u001B[33m" + "=============================" + "\u001B[0m");
                            System.out.println("\u001B[35m" + "[1]생성\t\t[2]입금\t\t[3]출금" + "\u001B[0m");
                            System.out.println("\u001B[35m" + "[4]송금\t\t[5]조회\t\t[6]로그아웃" + "\u001B[0m");
                            System.out.println("\u001B[33m" + "=============================" + "\u001B[0m");
                            System.out.println("선택 >>");

                            // 서브 메뉴 선택 입력 받음
                            menu1 = sc.nextInt();
                            switch (menu1) {
                                case 1: // 계좌 생성
                                    util.GetAc(client.getBId());
                                    break;
                                case 2: // 입금
                                    util.deposit();
                                    break;
                                case 3: // 출금
                                    util.withdraw();
                                    break;
                                case 4:  // 송금
                                    util.remit();
                                    break;
                                case 5: // 잔액 조회
                                    sql.checkAccount(BId);
                                    break;
                                case 6: // 종료
                                    System.out.println("이용해주셔서감사합니다");
                                    sql.conClose();
                                    run2 = false;
                                    break;
                                default:
                                    System.out.println("잘못 눌렀습니다");
                                    break;
                            }
                        }
                    }
                    break;

                case 3: // 프로그램 종료
                    System.out.println("프로그램을 종료합니다");
                    run = false;
                    break;

                default:
                    System.out.println("잘못 눌렀습니다");
                    break;
            }
        }
    }
}