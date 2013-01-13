/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FaceTraining_Recognition;

/**
 *
 * @author Administrator
 */
import static com.googlecode.javacv.cpp.opencv_highgui.cvLoadImage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import com.googlecode.javacv.cpp.opencv_core.CvRect;
import com.googlecode.javacv.cpp.opencv_core.IplImage;


public class Test {
	
	public final static String trainPic_Org = "FaceRec"+File.separator+"trainPic_org"+File.separator;
	public final static String trainPic_Stan = "FaceRec"+File.separator+"trainPic_stan"+File.separator;
	public final static String Recg_Pic = "FaceRec"+File.separator+"lena.jpg";
	public final static String Recg_Floder = "FaceRec"+File.separator+"RecgPic"+File.separator;
	public final static String train_txt = "FaceRec"+File.separator+"train.txt";
	public final static String test_txt = "FaceRec"+File.separator+"test.txt";
	public final static String face_data = "FaceRec"+File.separator+"face_data.xml";
	public final static String Result_floder = "FaceRec"+File.separator+"Result_floder"+File.separator;
	public final static String Root_floder = "FaceRec"+File.separator;
	public final static String Cut_floder = "FaceRec"+File.separator+"Cut_floder"+File.separator;
//	static List<CvRect> RecList = null;
//	public static void main(String[] args) throws Exception
//	{
//		FaceRecognition faceRec = new FaceRecognition();
//		FaceDectect faces = new FaceDectect();
//		
//	        faces.detect(trainPic_Org);
//		
//	        System.out.println("have translated the orgpic to stand pic.");
//	       	faceRec.learn(train_txt);
//		
//	        System.out.println("*******have been trained into the data.xml************");
//		IplImage origimg = cvLoadImage(Recg_Pic);
//		System.out.println("*******have load the image************");
//		RecList = faces.detect(origimg, Recg_Floder,null, null,true); //rec	
//		faceRec.recognizeFileList();
//		System.out.println("---------------------------------------");
//		int num = faceRec.ResultNames.size();
//		System.out.println("("+num+" = "+ RecList.size() +")");
//		for(int i=0;i<num;i++)
//		{
//			 System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//			 System.out.println("the person is  : "+ faceRec.ResultNames.get(i));
//			 System.out.println("the posion is  : "+ RecList.get(i).x()+", "+ RecList.get(i).y()+ "high: "+RecList.get(i).height()
//					 + "width: "+ RecList.get(i).width());
//			 System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//		}
//	    faceRec.draw(num, origimg, RecList, faceRec.ResultNames);
//		System.out.println("*******sucess************");
//		//
//		
//	}
}
