package com.iitdh.sonusourav.instigo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.RandomTransitionGenerator;

public class LoginFragment extends Fragment
{
	Button button_login;
	View form_login, imglogo, label_signup, darkoverlay;
	KenBurnsView kbv;
	private DisplayMetrics dm;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.fragment_login, container, false);
		dm=getResources().getDisplayMetrics();
		form_login=v.findViewById(R.id.form_login);
		imglogo=v.findViewById(R.id.fragmentloginLogo);
		kbv=(KenBurnsView) v.findViewById(R.id.fragmentloginKenBurnsView1);
		darkoverlay=v.findViewById(R.id.fragmentloginView1);
		label_signup=v.findViewById(R.id.label_signup);
		return v;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		RandomTransitionGenerator generator = new RandomTransitionGenerator(20000, new AccelerateDecelerateInterpolator());
		kbv.setTransitionGenerator(generator);
		imglogo.animate().setStartDelay(2000).setDuration(2000).alpha(1).start();
		darkoverlay.animate().setStartDelay(2000).setDuration(2000).alpha(0.6f).start();
		label_signup.animate().setStartDelay(2000).setDuration(2000).alpha(1).start();
		form_login.animate().translationY(dm.heightPixels).setStartDelay(0).setDuration(0).start();
		form_login.animate().translationY(0).setDuration(1500).alpha(1).setStartDelay(2000).start();
	}
}