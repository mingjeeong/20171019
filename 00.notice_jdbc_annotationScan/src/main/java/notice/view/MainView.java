package notice.view;

import java.util.ArrayList;
import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import notice.controller.Controller;
import notice.model.dto.NoticeDto;

public class MainView {
	
	private Scanner s = new Scanner(System.in);
	private String id, pw;
	private String no;
	String title,content;
	int num;
	private ApplicationContext context;
	private Controller controller;
	
	/**
	 * 로그인하기
	 */
	public void login() {
		//DI객체 생성
		context = new ClassPathXmlApplicationContext("annotationScan.xml");
		controller = context.getBean(Controller.class);
		
		System.out.println("-----로그인 입력하시오-----");
		System.out.println("아이디 :");
		id=s.nextLine();
		System.out.println("비밀번호 :");
		pw=s.nextLine();
		
		if(controller.loginAction(id, pw)!=null) {
			notice();
		}else {
			System.out.println("아이디 또는 비밀번호를 잘못 입력하셨습니다.");
			login();
		}
	}
	/**
	 * 로그인 후 
	 */
	public void notice() {
			System.out.println(id+"님 환영합니다.");
			menu();
	}
	
	/**
	 * 게시판과 메뉴
	 */
	public void menu() {
		showList();//공지사항 리스트 출력
		
		System.out.println("--------메뉴--------");
		System.out.println("1.공지보기");
		System.out.println("2.공지등록");
		System.out.println("3.공지수정");
		System.out.println("4.공지삭제");
		System.out.println("5.로그아웃");
		System.out.println("6.종료");
		System.out.println("---이용할 서비스를 선택하시오.---");
		no=s.nextLine();
		
		switch(no) {
		case "1":
			showNotice();
			break;
		case "2":
			insertNotice();
			break;
		case "3":
			updateNotice();
			break;
		case "4":
			deleteNotice();
			break;
		case "5":
			logout();
			break;
		case "6":
			System.out.println("프로그램을 종료합니다.이용해주셔서 감사합니다.");
			System.exit(0);
			break;
		}
	}
	
	/**
	 * 공지 리스트 보기
	 */
	private void showList() {
		
		controller = context.getBean(Controller.class);
		System.out.println("--------게시판-------");
		System.out.println("번호   작성자       작성일             제목");
		ArrayList<NoticeDto> list = controller.listAction();
		for(int i=0 ; i<list.size() ; i++) {
			System.out.println(list.get(i));
		}
	}
	
	/**
	 * 공지 내용 보기
	 */
	private void showNotice() {
		controller = context.getBean(Controller.class);
		System.out.println("---글번호 입력---");
		num = Integer.parseInt(s.nextLine());
		NoticeDto dto = controller.viewAction(num);
		System.out.println("번호    작성자      날짜               제목        내용");
		System.out.println(dto+","+dto.getContent());
		menu();
	}
	
	/**
	 * 공지등록
	 */
	private void insertNotice() {
		System.out.println("-----공지글 등록-----");
		System.out.println("제목 :");
		title = s.nextLine();
		System.out.println("내용 :");
		content = s.nextLine();
		controller.saveAction(id, title, content);
		System.out.println("등록되었습니다.");
		menu();
	}
	
	/**
	 * 공지글 삭제하기
	 */
	public void deleteNotice() {
		System.out.println("---삭제할 글번호 입력---");
		num = Integer.parseInt(s.nextLine());
		controller.deleteAction(num);
		System.out.println("삭제되었습니다.");
		menu();
	}
	
	/**
	 * 로그아웃
	 */
	public void logout() {
		System.out.println("성공적으로 로그아웃 되었습니다.");
		login();
	}
	
	/**
	 * 게시글수정	
	 */
	public void updateNotice() {
		System.out.println("---수정할 글번호 입력---");
		num = Integer.parseInt(s.nextLine());
		NoticeDto noticeDto = context.getBean("noticeDto", NoticeDto.class);
		noticeDto = controller.viewAction(num);
		System.out.println("번호    작성자      날짜               제목        내용");
		System.out.println(noticeDto+","+noticeDto.getContent());
	
		System.out.println("제목 :");
		title = s.nextLine();
		System.out.println("내용 :");
		content = s.nextLine();
		
		noticeDto.setTitle(title);
		noticeDto.setContent(content);
		controller.updateAction(noticeDto);
		menu();
	}

	public static void main(String[] args) {

		MainView main = new MainView();
		main.login();
	}

}
