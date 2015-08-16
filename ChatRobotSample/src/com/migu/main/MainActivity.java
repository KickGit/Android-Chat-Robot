package com.migu.main;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.util.EncodingUtils;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.migu.main.R.string;
import com.migu.object.TulingMessage;
import com.migu.object.TulingMessageNews;
import com.migu.object.TulingMessagePlanes;
import com.migu.object.TulingMessageTrains;
import com.migu.object.TulingMessageVideoBooks;
import com.migu.object.TulingNews;
import com.migu.object.TulingPlanes;
import com.migu.object.TulingTrains;
import com.migu.object.TulingVideoBooks;
import com.migu.utils.ConstantPools;
import com.migu.utils.TulingMessageCheck;

public class MainActivity extends Activity implements OnClickListener {

	private EditText medt_input;
	private TextView mtxv_show;
	private Button mbtn_send;
	private RequestQueue mRequestQueue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initViews();

	}

	private void initViews() {
		// TODO Auto-generated method stub
		medt_input = (EditText) findViewById(R.id.edt_input);
		mtxv_show = (TextView) findViewById(R.id.txv_show);
		mbtn_send = (Button) findViewById(R.id.btn_send);
		mbtn_send.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_send:
			SendMessageCheck();
			break;

		default:
			break;
		}
	}

	private void SendMessageCheck() {
		// TODO Auto-generated method stub
		if (medt_input.getText().toString().isEmpty()) {
			Toast.makeText(getApplicationContext(), getResources().getString(R.string.inputNotEmpty), Toast.LENGTH_SHORT).show();
		} else {
			SendMessage();
		}
	}

	private void SendMessage() {
		// TODO Auto-generated method stub
		try {
			String APIKEY = getResources().getString(R.string.tulingRobotKey);
			String USERID = getResources().getString(R.string.tulingUserId);
			String INFO = URLEncoder.encode(medt_input.getText().toString(), "utf-8");
			String url = "http://www.tuling123.com/openapi/api?key=" + APIKEY + "&info=" + INFO + "&userid" + USERID;

			medt_input.setText("");
			mRequestQueue = Volley.newRequestQueue(getApplicationContext());
			StringRequest stringRequest = new StringRequest(url, new Listener<String>() {

				@Override
				public void onResponse(String response) {
					// TODO Auto-generated method stub
					Log.d(ConstantPools.TAG_STRING, "response: "+response);
					Gson gson = new Gson();
					TulingMessage message = gson.fromJson(response, TulingMessage.class);
					int checkCode = TulingMessageCheck.Check(message.getCode());
					
					if (checkCode == ConstantPools.TULING_TEXTTYPE) {
						mtxv_show.setText(message.getText());
					}else if (checkCode == ConstantPools.TULING_URLTYPE) {
						try {
							String urlResponse = URLDecoder.decode(message.getUrl(),"utf-8");
							StringBuilder stringBuilder = new StringBuilder();
							stringBuilder.append(message.getText());
							stringBuilder.append(ConstantPools.EnterKey);
							stringBuilder.append(urlResponse);
							mtxv_show.setText(stringBuilder.toString());
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}else if (checkCode == ConstantPools.TULING_NEWSTYPE) {
						TulingMessageNews newsMessage = gson.fromJson(response, TulingMessageNews.class);
						
						StringBuilder stringBuilder = new StringBuilder();
						stringBuilder.append(newsMessage.getText());
						stringBuilder.append(ConstantPools.EnterKey);
						Log.d(ConstantPools.TAG_STRING, "strigbuild 1: "+stringBuilder.toString());
						ArrayList<TulingNews> templist = newsMessage.getList();
						
						for (int i = 0; i < templist.size(); i++) {
							TulingNews temptulingNews = (TulingNews) templist.get(i);
							Log.d(ConstantPools.TAG_STRING, temptulingNews.getArticle().toString());
							stringBuilder.append(temptulingNews.getArticle());
							stringBuilder.append(ConstantPools.EnterKey);
							stringBuilder.append(temptulingNews.getSource());
							stringBuilder.append(ConstantPools.EnterKey);
							Log.d(ConstantPools.TAG_STRING, "strigbuild: "+stringBuilder.toString());
							try {
								stringBuilder.append(URLDecoder.decode(temptulingNews.getIcon(), ConstantPools.UTF8));
								stringBuilder.append(ConstantPools.EnterKey);
								stringBuilder.append(URLDecoder.decode(temptulingNews.getDetailurl(), ConstantPools.UTF8));
								mtxv_show.setText(stringBuilder.toString());
							} catch (UnsupportedEncodingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
					}else if (checkCode == ConstantPools.TULING_TRAINSTYPE) {
						TulingMessageTrains trainsMessage = gson.fromJson(response, TulingMessageTrains.class);
						
						StringBuilder stringBuilder = new StringBuilder();
						stringBuilder.append(trainsMessage.getText());
						stringBuilder.append(ConstantPools.EnterKey);
						Log.d(ConstantPools.TAG_STRING, "strigbuild 1: "+stringBuilder.toString());
						ArrayList<TulingTrains> templist = trainsMessage.getList();
						for (int i = 0; i < templist.size(); i++) {
							TulingTrains temptulingTrains = (TulingTrains) templist.get(i);
							Log.d(ConstantPools.TAG_STRING, temptulingTrains.getTrainnum().toString());
							stringBuilder.append(temptulingTrains.getStart());
							stringBuilder.append("------");
							stringBuilder.append(temptulingTrains.getTerminal());
							stringBuilder.append(ConstantPools.EnterKey);
							stringBuilder.append("出发时间: ");
							stringBuilder.append(temptulingTrains.getStarttime());
							stringBuilder.append(ConstantPools.EnterKey);
							stringBuilder.append("到达时间");
							stringBuilder.append(temptulingTrains.getEndtime());
							
							Log.d(ConstantPools.TAG_STRING, "strigbuild: "+stringBuilder.toString());
							try {
								stringBuilder.append(URLDecoder.decode(temptulingTrains.getIcon(), ConstantPools.UTF8));
								stringBuilder.append(ConstantPools.EnterKey);
								stringBuilder.append(URLDecoder.decode(temptulingTrains.getDetailurl(), ConstantPools.UTF8));
								mtxv_show.setText(stringBuilder.toString());
							} catch (UnsupportedEncodingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}else if (checkCode == ConstantPools.TULING_PLAINTYPE) {

						TulingMessagePlanes planesMessage = gson.fromJson(response, TulingMessagePlanes.class);
						
						StringBuilder stringBuilder = new StringBuilder();
						stringBuilder.append(planesMessage.getText());
						stringBuilder.append(ConstantPools.EnterKey);
						Log.d(ConstantPools.TAG_STRING, "strigbuild 1: "+stringBuilder.toString());
						ArrayList<TulingPlanes> templist = planesMessage.getList();
						for (int i = 0; i < templist.size(); i++) {
							TulingPlanes temp = (TulingPlanes) templist.get(i);
							stringBuilder.append(temp.getFlight());
							stringBuilder.append(ConstantPools.EnterKey);
							stringBuilder.append(temp.getRoute());
							stringBuilder.append(ConstantPools.EnterKey);
							stringBuilder.append("出发时间: ");
							stringBuilder.append(temp.getStarttime());
							stringBuilder.append(ConstantPools.EnterKey);
							stringBuilder.append("到达时间: ");
							stringBuilder.append(temp.getEndtime());
							stringBuilder.append(ConstantPools.EnterKey);
							stringBuilder.append(temp.getState());
							Log.d(ConstantPools.TAG_STRING, "strigbuild: "+stringBuilder.toString());
							try {
								stringBuilder.append(URLDecoder.decode(temp.getIcon(), ConstantPools.UTF8));
								stringBuilder.append(ConstantPools.EnterKey);
								stringBuilder.append(URLDecoder.decode(temp.getDetailurl(), ConstantPools.UTF8));
								mtxv_show.setText(stringBuilder.toString());
							} catch (UnsupportedEncodingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					
					}else if (checkCode == ConstantPools.TULING_VIDEO_BOOK_TYPE) {

						TulingMessageVideoBooks videoBookMessage = gson.fromJson(response, TulingMessageVideoBooks.class);
						
						StringBuilder stringBuilder = new StringBuilder();
						stringBuilder.append(videoBookMessage.getText());
						stringBuilder.append(ConstantPools.EnterKey);
						Log.d(ConstantPools.TAG_STRING, "strigbuild 1: "+stringBuilder.toString());
						ArrayList<TulingVideoBooks> templist = videoBookMessage.getList();
						for (int i = 0; i < templist.size(); i++) {
							TulingVideoBooks temp = (TulingVideoBooks) templist.get(i);
							stringBuilder.append(temp.getName());
							stringBuilder.append(ConstantPools.EnterKey);
							stringBuilder.append(temp.getInfo());
							stringBuilder.append(ConstantPools.EnterKey);
							try {
								stringBuilder.append(URLDecoder.decode(temp.getIcon(), ConstantPools.UTF8));
								stringBuilder.append(ConstantPools.EnterKey);
								stringBuilder.append(URLDecoder.decode(temp.getDetailurl(), ConstantPools.UTF8));
								mtxv_show.setText(stringBuilder.toString());
							} catch (UnsupportedEncodingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					
					}
				}
			}, new ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError error) {
					// TODO Auto-generated method stub
					error.printStackTrace();
				}
			});

			mRequestQueue.add(stringRequest);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
