import java.util.Scanner;

public class Main {
	public static void main(String args[]) throws Exception {
		System.out.println("請輸入檔案名稱：");
		Scanner sc = new Scanner(System.in);
		
		Img_startprocess Img_startprocess =new Img_startprocess(sc.next()); 
		Img_startprocess.show();
		
		if(Img_startprocess.get_Check_ImgFile()==1) {
			Img_startprocess.imgprocess();
		}else {
			throw new Img_NotFound("請檢查路徑或者圖片名稱是否存在");
		}	
	}	
}


