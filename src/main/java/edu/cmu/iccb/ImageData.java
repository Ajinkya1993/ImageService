package edu.cmu.iccb;

//Used to get/post image bytes
public class ImageData {
	private String name;
	private String base64Data;
	private String imageId;
	
	public ImageData() {
		this.name = null;
		this.base64Data = null;
		this.imageId = null;
	}
	
	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBase64Data() {
		return base64Data;
	}
	public void setBase64Data(String base6Data) {
		this.base64Data = base6Data;
	}
	
}