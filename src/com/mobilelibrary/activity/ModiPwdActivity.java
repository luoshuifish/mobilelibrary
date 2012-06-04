package com.mobilelibrary.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mobilelibrary.R;
/**
 * Modify the password 
 * @author Ryan
 *
 */
public class ModiPwdActivity extends BaseActivity {

	private EditText newPasswordtEditText;
	private EditText repeatPasswordeEditText;
	private Button confrimButton;
	private String newPassword;
	private String repeatPasswrod;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modify_password);
		
		setTopTitle(this,R.string.set_password);
		setBackButton(this, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ModiPwdActivity.this.finish();
			}
		});
		
		newPasswordtEditText = (EditText) this.findViewById(R.id.et_new_pwd);
		repeatPasswordeEditText = (EditText) this.findViewById(R.id.et_repeat_pwd);
		confrimButton = (Button) this.findViewById(R.id.btn_modi_pwd);
		
		confrimButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				newPassword = newPasswordtEditText.getText().toString();
				repeatPasswrod = repeatPasswordeEditText.getText().toString();
				if ((!newPassword.equals("") && !repeatPasswrod.equals("")) && !newPassword.equals(repeatPasswrod)) {
					Toast.makeText(ModiPwdActivity.this,  R.string.not_same, Toast.LENGTH_SHORT).show();
				}
				if (newPassword.equals("") || repeatPasswrod.equals("")) {
					Toast.makeText(ModiPwdActivity.this,  R.string.password_not_empty, Toast.LENGTH_SHORT).show();
				}
				if (newPassword.equals(repeatPasswrod) && !newPassword.equals("")){
					
				}
			}
		});
		
	}
}
