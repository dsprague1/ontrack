package com.sleepycat.ontrack;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CreditsFragment extends Fragment 
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{super.onCreate(savedInstanceState);}
	
	 private static Spannable removeUnderlines(Spannable p_Text) {  
         URLSpan[] spans = p_Text.getSpans(0, p_Text.length(), URLSpan.class);  
         for (URLSpan span : spans) {  
              int start = p_Text.getSpanStart(span);  
              int end = p_Text.getSpanEnd(span);  
              p_Text.removeSpan(span);  
              span = new URLSpanNoUnderline(span.getURL());  
              p_Text.setSpan(span, start, end, 0);  
         }  
         return p_Text;  
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.fragment_credits, parent, false);
		
		TextView devTitle =(TextView)v.findViewById(R.id.davisLink1);
		devTitle.setClickable(true);
		String devtext = "<a href='http://www.davissprague.com'> Developer:</a>";
		Spannable spannedText = Spannable.Factory.getInstance().newSpannable(
				Html.fromHtml(devtext));
		devTitle.setMovementMethod(LinkMovementMethod.getInstance());
		Spannable processedText = removeUnderlines(spannedText);
		devTitle.setText(processedText);
		
		TextView devName =(TextView)v.findViewById(R.id.davisLink2);
		devName.setClickable(true);
		String davistext = "<a href='http://www.davissprague.com'>  Davis Sprague, Sleepy Cat</a>";
		Spannable spanned2Text = Spannable.Factory.getInstance().newSpannable(
				Html.fromHtml(davistext));
		devName.setMovementMethod(LinkMovementMethod.getInstance());
		Spannable processed2Text = removeUnderlines(spanned2Text);
		devName.setText(processed2Text);
		
		TextView sleepyTitle =(TextView)v.findViewById(R.id.maxLink1);
		sleepyTitle.setClickable(true);
		String sleepyText = "<a href='http://www.linkedin.com/pub/max-boucher/92/800/1'> Sleepy Cat Logo:</a>";
		Spannable spanned3Text = Spannable.Factory.getInstance().newSpannable(Html.fromHtml(sleepyText));
		sleepyTitle.setMovementMethod(LinkMovementMethod.getInstance());
		Spannable processed3Text = removeUnderlines(spanned3Text);
		sleepyTitle.setText(processed3Text);
		
		TextView sleepyName =(TextView)v.findViewById(R.id.maxLink2);
		sleepyName.setClickable(true);
		String maxtext = "<a href='http://www.linkedin.com/pub/max-boucher/92/800/1'>  Max Boucher</a>";
		Spannable spanned4Text = Spannable.Factory.getInstance().newSpannable(
				Html.fromHtml(maxtext));
		sleepyName.setMovementMethod(LinkMovementMethod.getInstance());
		Spannable processed4Text = removeUnderlines(spanned4Text);
		sleepyName.setText(processed4Text);
		
		return v;
	}
}
