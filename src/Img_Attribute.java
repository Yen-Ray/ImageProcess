class Img_Attribute {
	
	protected int image_w;
	protected int image_h;
	protected String img_path;
	
	public Img_Attribute() {
		System.out.println("Img_Attribute_設定");
	}
	
	public void set_img_Attribute(String path,int h,int w) {
		img_path=path;
		image_h=h;
		image_w=w;
	}
	
	public void show() {
		System.out.println("路徑："+img_path);
		System.out.println("圖片尺寸(W x H)："+image_w+"x"+image_h);
	}
}
