
import java.awt.image.BufferedImage;
import java.io.File;


import javax.imageio.ImageIO;

public class Img_handler extends Img_Attribute {
	BufferedImage bufferedImage;
	String name;
	private int check=1;
	public Img_handler(String img_name) throws Img_NotFound{
		System.out.println("Img_handler_檢查路徑");
		
		img_path = new File("").getAbsolutePath();	
		this.name=img_name;
		
		try{
			
			bufferedImage = ImageIO.read(new File(img_path +"/"+name+".jpg"));
			set_img_Attribute(img_path,bufferedImage.getHeight(),bufferedImage.getWidth());
			
		}catch(Exception e) {
			
			check=0;

		}finally {
			
			if(check!=0)
				System.out.println("finally 檢查確認無誤...執行影像處理");
			else
				throw new Img_NotFound("請檢查路徑或者圖片名稱是否存在");
			
		}
	}
	
	public String end() {
		System.out.println("Finish");
		return "Finish";
	}
	
	public int get_Check_ImgFile() {
		return check;
	}
}
