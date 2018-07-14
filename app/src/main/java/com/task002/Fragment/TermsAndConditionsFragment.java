package com.task002.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.task002.R;

public class TermsAndConditionsFragment extends Fragment {
    private WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_terms_and_conditions, container, false);
        webView = (WebView) fragment.findViewById(R.id.web_view);
        webView.loadUrl("https://termsfeed.com/blog/sample-terms-and-conditions-template/");
        return fragment;
    }

}
