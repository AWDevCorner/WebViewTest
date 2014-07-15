package it.androidworld.devcorner.webViewTest;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import it.androidworld.devcorner.webViewTest.R;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	public static class PlaceholderFragment extends Fragment {

		WebView myWebView;
		EditText myEditText;
		Button btnToJs;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			//
            myWebView = (WebView) rootView.findViewById(R.id.mybrowser);

			final MyJavaScriptInterface myJavaScriptInterface = 
					new MyJavaScriptInterface(getActivity());

            myWebView.addJavascriptInterface(myJavaScriptInterface,
					"AndroidFunction");

            myWebView.getSettings().setJavaScriptEnabled(true);
            myWebView.loadUrl("file:///android_asset/index.html");

            myEditText = (EditText) rootView.findViewById(R.id.msg);
            btnToJs = (Button) rootView.findViewById(R.id.sendmsg);
            btnToJs.setOnClickListener(new Button.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					String msgToSend = myEditText.getText().toString();
                    myWebView.loadUrl("javascript:callFromActivity(\""
							+ msgToSend + "\")");

				}
			});

			//
			return rootView;
		}
		
		public class MyJavaScriptInterface {

			Context mContext;

			MyJavaScriptInterface(Context c) {
				mContext = c;
			}

			@JavascriptInterface
			public void showToast(String toast) {
				Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
			}

			@JavascriptInterface
			public void openAndroidDialog() {
				AlertDialog.Builder myDialog = new AlertDialog.Builder(mContext);
				myDialog.setTitle("Dialog!");
				myDialog.setMessage("Adesso ci credi?");
				myDialog.setPositiveButton("Ok", null);
				myDialog.show();
			}


		}

	}

}
