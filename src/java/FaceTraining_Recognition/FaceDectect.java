/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FaceTraining_Recognition;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.*;
import com.googlecode.javacv.cpp.opencv_objdetect.CvHaarClassifierCascade;
import com.googlecode.javacv.cpp.opencv_core.CvSize;
import com.googlecode.javacv.*;
import static com.googlecode.javacv.cpp.opencv_core.*;     
import static com.googlecode.javacv.cpp.opencv_highgui.*;  
import static com.googlecode.javacv.cpp.opencv_imgproc.*;  
import static com.googlecode.javacv.cpp.opencv_objdetect.*;


import java.io.* ;       



public class FaceDectect {
    private static final String CASCADE_FILE = "FaceRec\\haarcascade_frontalface_alt.xml";
    private static final int Min_neighbors= 6;
    //public static final double small_scale = 1;
    private int numFloder;
    private File writerTrain;
    private Writer out;
   // private static int trainPic_stan = 0;

    /**
     * @param originalImage
     * @return
     * @throws Exception 
     
     */
    public List<CvRect> detect(IplImage originalImage, String storeflodername, String filename, String father, boolean flag) throws Exception // true for recognition,false for detect
    {
        CvHaarClassifierCascade cascade = new CvHaarClassifierCascade( cvLoad(CASCADE_FILE));  //装载特征
    	CvMat submat = null;
    	Vector<CvMat> ret = new Vector<CvMat>();
    	List<CvRect> faceList = new ArrayList<CvRect>();  
        
        if(flag)  //主要是 recognize的时候用
        {
            IplImage grayImage = IplImage.create(originalImage.width(), originalImage.height(), IPL_DEPTH_8U, 1); //图片
            //IplImage small_img = IplImage.create((int)Math.round(originalImage.width()/small_scale), (int)Math.round(originalImage.height()/small_scale), IPL_DEPTH_8U, 1); //图片
            cvConvertImage(originalImage, grayImage, 6);
            //cvResize(grayImage, small_img, CV_INTER_LINEAR);        
            CvMemStorage storage = CvMemStorage.create();      
            CvSeq faces = cvHaarDetectObjects(grayImage, cascade, storage, 1.2, Min_neighbors, CV_HAAR_DO_CANNY_PRUNING);
            
            IplImage resizedImage = IplImage.create(100, 100, grayImage.depth(),grayImage.nChannels());
            faceList.clear();
            for (int i = 0; i < faces.total(); i++) { 
                CvRect r = new CvRect(cvGetSeqElem(faces, i));
                faceList.add(r); 
                IplImage croppedImage = IplImage.create(r.width(), r.height(),grayImage.depth(), grayImage.nChannels());  // creat a fix size pic
                cvSetImageROI(grayImage, r);                                     
                cvCopy(grayImage, croppedImage);                                                                   
                cvResize(croppedImage, resizedImage, CV_INTER_AREA);  
                cvEqualizeHist( resizedImage, resizedImage );
                cvSaveImage(storeflodername + i +".jpg", resizedImage); 
            }
        	return faceList;
        }
        else  //train时候用的
        {
            IplImage grayImage = IplImage.create(originalImage.width(), originalImage.height(), IPL_DEPTH_8U, 1); //图片
            //IplImage small_img = IplImage.create((int)Math.round(originalImage.width()/small_scale), (int)Math.round(originalImage.height()/small_scale), IPL_DEPTH_8U, 1); //图片
            cvConvertImage(originalImage, grayImage, 6);  
            //cvResize(grayImage, small_img, CV_INTER_LINEAR);        
            CvMemStorage storage = CvMemStorage.create();      
            CvSeq faces = cvHaarDetectObjects(grayImage, cascade, storage, 1.2, Min_neighbors, CV_HAAR_DO_CANNY_PRUNING);
            if(faces.total()==0)
            {
            	System.out.println("***********"+faces.total()+"this pic is not useful: "+ filename);
            	return faceList;
            }
            
            IplImage resizedImage = IplImage.create(100, 100, grayImage.depth(),grayImage.nChannels());
            CvRect r = new CvRect(cvGetSeqElem(faces, 0));
            //faceList.add(r); 
            IplImage croppedImage = IplImage.create(r.width(), r.height(),grayImage.depth(), grayImage.nChannels());  // creat a fix size pic
            cvSetImageROI(grayImage, r);   
            cvCopy(grayImage, croppedImage);                                                                                   
            cvResize(croppedImage, resizedImage, CV_INTER_AREA);  
            String finalStore = findFloder(storeflodername,filename,father);
            cvSaveImage(finalStore, resizedImage); 
           
            String streamContext = Integer.toString(numFloder)+" "+father+" "+finalStore;
            System.out.println("------------"+streamContext);
		out.append(streamContext);
		out.append("\r\n");         
	        return faceList;
			
        }
    }
    
    public void detect(String strOgriFloder) throws Exception
    {
    	writerTrain = new File(Test.train_txt);
     	out = new FileWriter(writerTrain);                      
    	numFloder = 0;
    	File fileOrignFloder = new File(strOgriFloder);
    	String[] strFloderNames = fileOrignFloder.list();
    	
    	for(int i=0;i<strFloderNames.length;i++)
    	{
    		numFloder = i+1;
    		String newFloder = strFloderNames[i];
    		File fileNewFloder = new File(strOgriFloder+newFloder);
    		String[] newFiles = fileNewFloder.list();
                System.out.println(strOgriFloder+newFloder);
    		for(int j=0;j< newFiles.length;j++)
    		{
                        //System.out.println(strOgriFloder+newFloder+File.separator+newFiles[j]);
    			IplImage img = cvLoadImage(strOgriFloder+newFloder+File.separator+newFiles[j]);
    			IplImage ori_resizedImage = IplImage.create(1000, 1000, img.depth(),img.nChannels());
        		cvResize(img, ori_resizedImage, CV_INTER_AREA);
    		        System.out.println(strOgriFloder+newFloder+File.separator+newFiles[j]+">>>>>>>>>>"+ Test.trainPic_Stan+newFloder+File.separator+newFiles[j]);
    			detect(ori_resizedImage , Test.trainPic_Stan,newFiles[j],newFloder,false);
    			
    			cvReleaseImage(img); 
    		}
    	}
    	out.close();
    }

    
    public String findFloder(String storeflodername, String filename, String father)
    {
    	File file = new File(storeflodername+father);
    	if(!file.exists())
    		file.mkdir();
    	
    	return storeflodername+father+File.separator+filename;
    }
        
}           

