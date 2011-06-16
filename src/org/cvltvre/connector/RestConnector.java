package org.cvltvre.connector;

import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.cvltvre.utils.CustomLocationListener;
import org.cvltvre.utils.IOUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class RestConnector {
	
	
	public static String connect(double distance) {
		Double latitude=CustomLocationListener.location.getLatitude();
		Double longitude=CustomLocationListener.location.getLongitude();
		
		HttpGet httpGet=new HttpGet("http://www.cvltvre.com/pg/api/rest/json/?method=museum.get_geo_by_coordinates&top="+(latitude+distance)+"&bottom="+(latitude-distance)+"&left="+(longitude-distance)+"&right="+(longitude+distance));
		HttpClient client=new DefaultHttpClient();
		try {
			HttpResponse httpResponse=client.execute(httpGet);
			HttpEntity entity=httpResponse.getEntity();
			InputStream stream=entity.getContent();
			return IOUtils.toString(stream);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * @param string
	 * @return
	 */
	public static Bitmap getThumb(String url,Context context) {
		Bitmap thumb=null;
		try{
			HttpGet httpGet=new HttpGet("http://www.cvltvre.com/"+url);
			HttpClient client=new DefaultHttpClient();
			HttpResponse httpResponse=client.execute(httpGet);
			HttpEntity entity=httpResponse.getEntity();
			InputStream stream=entity.getContent();
			thumb=BitmapFactory.decodeStream(stream);
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(thumb==null){
			thumb=BitmapFactory.decodeResource(context.getResources(), org.cvltvre.R.drawable.museum);
		}
		return thumb;
	}

}
