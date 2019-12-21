import java.util.Scanner;

public class Main {
	private static Scanner sc = new Scanner(System.in);

	public static void main(String args[]) throws Exception {
		System.out.println("請輸入檔案名稱：");
		
		Img_startprocess Img_startprocess =new Img_startprocess(sc.next()); 
		Img_startprocess.show();
		
		//if(Img_startprocess.get_Check_ImgFile()==1) {
			Img_startprocess.imgprocess();
		//}
			
		
	}	
}


