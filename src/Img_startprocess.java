import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import javax.imageio.ImageIO;

public class Img_startprocess extends Img_handler {

	public Img_startprocess(String img_name) {
		super(img_name);
		System.out.println("Img_startprocess_執行處理");
	}
	
	public void imgprocess() throws IOException {
	    img2Gray();
		img2neg();
		for(int i=1;i<=3;i++)
			imgContrast(i);
		imagpepper();
		imgmedianfilter();
		imagebinary();		
		imagelaplacian();
		imgmaxfilter();
	}
	
	private void img2Gray() throws IOException {

		for(int x=0 ; x<image_h ; x++) {
			
			for(int y=0 ; y<image_w ; y++) {
				
				int pixel = bufferedImage.getRGB(y, x);
				 
				int alpha 	= (pixel >> 24) & 0xFF;
                int red 	= (pixel >> 16) & 0xFF;
                int green	= (pixel >> 8) 	& 0xFF;
                int blue 	=  pixel 		& 0xFF;
                 
                int avg = (int)(red * 0.299 + green * 0.587 + blue * 0.114);
                 
                pixel = (alpha << 24) | (avg<<16) | (avg<<8) | avg;
                 
                bufferedImage.setRGB(y , x , pixel);
			}
			
		}
		System.out.println("img2Gray完成");
		ImageIO.write(bufferedImage, "jpg", new File(img_path+"/"+name+"_gray.jpg"));
	}
	
	private void img2neg() throws IOException {
		for(int x=0 ; x<image_h ; x++) {
			
			for(int y=0 ; y<image_w ; y++) {
				
				int pixel = bufferedImage.getRGB(y, x);
				 
				int alpha 	= (pixel >> 24) & 0xFF;
                int red 	= 255-(pixel >> 16) & 0xFF;
                int green	= 255-(pixel >> 8) & 0xFF;
                int blue 	= 255-pixel & 0xFF;
				pixel = (alpha << 24) | (red<<16) | (green<<8) | blue;

                
                bufferedImage.setRGB(y, x,pixel);
			}
			
		}
		System.out.println("img2Negative完成");
		ImageIO.write(bufferedImage, "jpg", new File(img_path+"/"+name+"_negative.jpg"));
		
	}
	
	private void imgContrast(int gam) throws IOException {
		
		double gamma=gam;
		
		if(gam==1)
			gamma=0.5;
		else if(gam==2)
			gamma=2;
		else
			gamma=1;
		
		int max=0,min=255;
		//找最大以及最小值
		for(int x=0 ; x<image_h ; x++) {
			
			for(int y=0 ; y<image_w ; y++) {
				
				int pixel = bufferedImage.getRGB(y, x);		
				int red   = (pixel >> 16) & 0xFF;
                
				if(red > max)
					max=red;
				if(min > red)
					min=red;
			}
			
		}
		for(int x=0 ; x<image_h ; x++) { 
			
			for(int y=0 ; y<image_w ; y++) {
				
				int pixel = bufferedImage.getRGB(y, x);		
			
				int alpha 	= (pixel >> 24)     & 0xFF;
                int red 	= (pixel >> 16) 	& 0xFF;
                int green	= (pixel >> 8)  	& 0xFF;
                int blue 	=  pixel 			& 0xFF;
                
                red   =	(int)((Math.pow((double)(red-min)  /(double)(max-min), gamma))*255);
                green =	(int)((Math.pow((double)(green-min)/(double)(max-min), gamma))*255);
                blue  =	(int)((Math.pow(((double)blue-min) /(double)(max-min), gamma))*255);
                
				pixel = (alpha << 24) | (red<<16) | (green<<8) | blue;

                bufferedImage.setRGB(y, x,pixel);
			}
			
		}
		System.out.println("img2Contrast"+gamma+"完成");
		ImageIO.write(bufferedImage, "jpg", new File(img_path+"/"+name+"_contrast"+gamma+".jpg"));
	}
	
	private void imagpepper() throws IOException {
		
		bufferedImage = ImageIO.read(new File(img_path+"/"+name+"_contrast0.5.jpg"));
		
		Random random= new Random();
		for(int x=0 ; x<image_h ; x++) {
			
			for(int y=0 ; y<image_w ; y++) {
				
				int pixel = bufferedImage.getRGB(y, x);
				 
				int alpha 	= (pixel >> 24) & 0xFF;
                int red 	= (pixel >> 16) & 0xFF;
                int green	= (pixel >> 8) 	& 0xFF;
                int blue 	=  pixel 		& 0xFF;
                if(random.nextInt(10)==2) {
                	red=0;
                	green=0;
                	blue=0;
                }else if(random.nextInt(10)==8) {
                	red=255;
                	green=255;
                	blue=255;
                }
             
                pixel = (alpha << 24) | (red<<16) | (green<<8) | blue;
                 
                bufferedImage.setRGB(y , x , pixel);
			}
			
		}
		System.out.println("imgpepper完成");
		ImageIO.write(bufferedImage, "jpg", new File(img_path+"/"+name+"_pepper.jpg"));
	}
	
	private void imgmedianfilter() throws IOException {
		
		bufferedImage = ImageIO.read(new File(img_path +"/"+name+"_pepper.jpg"));
		
		int filter[]=new int[9];
		//這樣可以直接處理陣列爆掉的問題
		for(int x=1 ; x<image_h-1 ; x++) {
			
			for(int y=1 ; y<image_w-1 ; y++) {
				
				
					filter[0]=bufferedImage.getRGB(y-1, x-1);
					filter[1]=bufferedImage.getRGB(y-1, x);
					filter[2]=bufferedImage.getRGB(y-1, x+1);
					filter[3]=bufferedImage.getRGB(y, x-1);
					filter[4]=bufferedImage.getRGB(y, x);
					filter[5]=bufferedImage.getRGB(y, x+1);
					filter[6]=bufferedImage.getRGB(y+1, x);
					filter[7]=bufferedImage.getRGB(y, x+1);
					filter[8]=bufferedImage.getRGB(y+1, x+1);
					Arrays.sort(filter);
					
					int pixel = filter[4];
					int alpha 	= (pixel >> 24) & 0xFF;
	                int red 	= (pixel >> 16) & 0xFF;
	                int green	= (pixel >> 8) 	& 0xFF;
	                int blue 	=  pixel 		& 0xFF;
	                pixel = (alpha << 24) | (red<<16) | (green<<8) | blue;
	                 
	                bufferedImage.setRGB(y , x , pixel);
				
			}
			
		}
		System.out.println("imgmedianfilter完成");
		ImageIO.write(bufferedImage, "jpg", new File(img_path+"/"+name+"_medianfilter.jpg"));
	}
	
	private void imagebinary() throws IOException {
		
		bufferedImage = ImageIO.read(new File(img_path +"/"+name+"_contrast2.0.jpg"));
		
		
		int avgpixel=0;
		for(int x=0 ; x<image_h ; x++) {
			
			for(int y=0 ; y<image_w ; y++) {
				
				int pixel = bufferedImage.getRGB(y, x);
                int red 	= (pixel >> 16) & 0xFF;
                avgpixel=avgpixel+red;
			}
			
		}
		avgpixel=avgpixel/(image_h*image_h);
		
		for(int x=0 ; x<image_h ; x++) {
			
			for(int y=0 ; y<image_h ; y++) {
				
				int pixel = bufferedImage.getRGB(y, x);
				 
				int alpha 	= (pixel >> 24) & 0xFF;
                int red 	= (pixel >> 16) & 0xFF;
                int green	= (pixel >> 8) 	& 0xFF;
                int blue 	=  pixel 		& 0xFF;
                
                if(red>=avgpixel) {
                	red=255;
                	green=255;
                	blue=255;
                }else {
                	red=0;
                	green=0;
                	blue=0;
                }
                pixel = (alpha << 24) | (red<<16) | (green<<8) | blue;
                 
                bufferedImage.setRGB(y , x , pixel);
			}
			
		}
		System.out.println("imagebinary完成");
		ImageIO.write(bufferedImage, "jpg", new File(img_path+"/"+name+"_binary.jpg"));
	}
	
	private void imagelaplacian() throws IOException {
		bufferedImage = ImageIO.read(new File(img_path +"/"+name+"_contrast1.0.jpg"));
		
		
		int filter[]=new int[5];
		int output[][]=new int [image_h][image_w];
		
		//這樣可以直接處理陣列爆掉的問題
		for(int x=1 ; x<image_h-1 ; x++) {
			
			for(int y=1 ; y<image_w-1 ; y++) {
				filter[0]	=	bufferedImage.getRGB(y-1, x)&0XFF;
				filter[1]	=	bufferedImage.getRGB(y, x-1)&0XFF;
				filter[2]	=	bufferedImage.getRGB(y, x+1)&0XFF;
				filter[3]	=	bufferedImage.getRGB(y+1, x)&0XFF;
				filter[4]	=	bufferedImage.getRGB(y, x)&0XFF;
				
				int laplacian_value = (filter[0]+filter[1]+filter[2]+filter[3])-4*filter[4];
				output[y][x]=laplacian_value;

			}
			
		}
		for(int x=1 ; x<image_h-1 ; x++) {
					
			for(int y=1 ; y<image_w-1 ; y++) {
				
				int pixel = bufferedImage.getRGB(y, x);
				
				int alpha 	= (pixel >> 24) & 0xFF;
				
				if(output[y][x]>255)
					output[y][x]=255;
				else if(output[y][x]<0)	
					output[y][x]=0;
				
				pixel = alpha<<24 | output[y][x] <<16 | output[y][x]<<8 | output[y][x];
				
				bufferedImage.setRGB(y , x , pixel);	
				
			}
		}
		System.out.println("imagelaplacian完成");
		ImageIO.write(bufferedImage, "jpg", new File(img_path+"/"+name+"_laplacian.jpg"));
	}
	
	private void imgmaxfilter() throws IOException {
		
		bufferedImage = ImageIO.read(new File(img_path +"/"+name+"_laplacian.jpg"));
		
	
		int filter[]=new int[9];
		int output[][]=new int [image_h][image_w];
		//這樣可以直接處理陣列爆掉的問題
		for(int x=1 ; x<image_h-1 ; x++) {
			
			for(int y=1 ; y<image_w-1 ; y++) {
				
				
					filter[0]=bufferedImage.getRGB(y-1, x-1);
					filter[1]=bufferedImage.getRGB(y-1, x);
					filter[2]=bufferedImage.getRGB(y-1, x+1);
					filter[3]=bufferedImage.getRGB(y, x-1);
					filter[4]=bufferedImage.getRGB(y, x);
					filter[5]=bufferedImage.getRGB(y, x+1);
					filter[6]=bufferedImage.getRGB(y+1, x);
					filter[7]=bufferedImage.getRGB(y, x+1);
					filter[8]=bufferedImage.getRGB(y+1, x+1);
					Arrays.sort(filter);
					
					output[y][x]= filter[8];
				
			}
			
		}
		for(int x=1 ; x<image_h-1 ; x++) {
			
			for(int y=1 ; y<image_w-1 ; y++) {
				
				int pixel = bufferedImage.getRGB(y, x);
				
				int alpha 	= (pixel >> 24) & 0xFF;
				
				pixel = alpha<<24 | output[y][x] <<16 | output[y][x]<<8 | output[y][x];
				
				bufferedImage.setRGB(y , x , pixel);	
				
			}
		}	
		System.out.println("imgmaxfilter完成");
		ImageIO.write(bufferedImage, "jpg", new File(img_path+"/"+name+"_maxfilter.jpg"));
	}

}
