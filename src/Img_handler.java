
import java.awt.image.BufferedImage;
import java.io.File;


import javax.imageio.ImageIO;

public class Img_handler extends Img_Attribute {
	BufferedImage bufferedImage;
	String name;
	private int check=1;
	public Img_handler(String img_name){
		System.out.println("Img_handler_檢查路徑");
		
		img_path = new File("").getAbsolutePath();	
		this.name=img_name;
		
		try{
			
			bufferedImage = ImageIO.read(new File(img_path +"/"+name+".jpg"));
			set_img_Attribute(img_path,bufferedImage.getHeight(),bufferedImage.getWidth());
		}catch(Exception e) {
			check=0;
//			set_Check_ImgFile(check);
		}
	}
	
//	public void set_Check_ImgFile(int check) {
//		this.check=check;
//	}
	
	public int get_Check_ImgFile() {
		return check;
	}
}
