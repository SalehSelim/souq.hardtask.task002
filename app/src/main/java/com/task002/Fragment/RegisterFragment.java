package com.task002.Fragment;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.task002.Api.ApiClient;
import com.task002.Api.ApiInterface;
import com.task002.Model.City;
import com.task002.Model.Country;
import com.task002.R;
import com.task002.Utilities.Constants;
import com.task002.Utilities.Utilities;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends Fragment  {
    private Spinner spin_country, spin_city, spin_code;
    private Button btn_change_language;
    private Context context;
    private TextView tv_terms_and_conditions;
    private ArrayList<Country> countries;
    private ArrayList<City> cities;
    private String selected_language;
    private ApiInterface apiInterface;
    private View fragment ;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        fragment = inflater.inflate(R.layout.fragment_register, container, false);
        context = container.getContext();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        selected_language = Utilities.getPreference(context, Constants.SELECTED_LANGUAGE);
        getAndInitiateViews();
        assignClickListeners();
        getCountries();
        return fragment;
    }

    private void changeLanguage(String current_language) {

        if (current_language.equals(Constants.ARABIC)) {
            Resources res = context.getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            android.content.res.Configuration config = res.getConfiguration();
            config.locale = new Locale(Constants.ENGLISH);
            res.updateConfiguration(config, dm);
            Utilities.setPreference(context, Constants.SELECTED_LANGUAGE, Constants.ENGLISH);
            RegisterFragment registerFragment = new RegisterFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment,registerFragment,"").commit();
        } else {
            Resources res = context.getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            android.content.res.Configuration config = res.getConfiguration();
            config.locale = new Locale(Constants.ARABIC);
            res.updateConfiguration(config, dm);
            Utilities.setPreference(context, Constants.SELECTED_LANGUAGE, Constants.ARABIC);
            RegisterFragment registerFragment = new RegisterFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment,registerFragment,"").commit();

        }
    }

    public void getCountries() {
        Call<ArrayList<Country>> call = apiInterface.getCountries();
        call.enqueue(new Callback<ArrayList<Country>>() {
            @Override
            public void onResponse(Call<ArrayList<Country>> call, Response<ArrayList<Country>> response) {
                countries = response.body();
                ArrayList<String> titles = new ArrayList<>();
                ArrayList<String> codes = new ArrayList<>();
                if ((selected_language.equals(Constants.ARABIC)))
                    for (Country country : countries) {
                        codes.add(country.getCode());
                        titles.add(country.getTitleAR());
                    }

                else
                    for (Country country : countries) {
                        codes.add(country.getCode());
                        titles.add(country.getTitleEN());
                    }
                //Set Adapter For Countries
                ArrayAdapter<String> countriesArrayAdapter = new ArrayAdapter<>
                        (context, android.R.layout.simple_spinner_item, titles); //selected item will look like a spinner set from XML
                countriesArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin_country.setAdapter(countriesArrayAdapter);

                // Set Adapter For Codes
                ArrayAdapter<String> codesArrayAdapter = new ArrayAdapter<>
                        (context, android.R.layout.simple_spinner_item, codes); //selected item will look like a spinner set from XML
                codesArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin_code.setAdapter(codesArrayAdapter);

            }

            @Override
            public void onFailure(Call<ArrayList<Country>> call, Throwable t) {

            }

        });
    }

    public void getCities(String countryId) {
        Call<ArrayList<City>> call = apiInterface.getCities(countryId);
        call.enqueue(new Callback<ArrayList<City>>() {
            @Override
            public void onResponse(Call<ArrayList<City>> call, Response<ArrayList<City>> response) {
                cities = response.body();
                ArrayList<String> titles = new ArrayList<>();

                if ((selected_language.equals(Constants.ARABIC)))
                    for (City city : cities) {
                        titles.add(city.getTitleAR());
                    }
                else
                    for (City city : cities) {
                        titles.add(city.getTitleEN());
                    }

                //Set Adapter For Cities
                ArrayAdapter<String> citiesArrayAdapter = new ArrayAdapter<>
                        (context, android.R.layout.simple_spinner_item, titles); //selected item will look like a spinner set from XML
                citiesArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin_city.setAdapter(citiesArrayAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<City>> call, Throwable t) {

            }

        });
    }

    private void getAndInitiateViews() {
        spin_country = (Spinner) fragment.findViewById(R.id.spin_country);
        spin_city = (Spinner) fragment.findViewById(R.id.spin_city);
        spin_code = (Spinner) fragment.findViewById(R.id.spin_code);
        tv_terms_and_conditions = (TextView) fragment.findViewById(R.id.tv_terms_conditions);
        btn_change_language = (Button) fragment.findViewById(R.id.btn_change_language);
        SpannableString spannableString = new SpannableString(getString(R.string.by_clicking_register_you_agree_to_terms_and_conditions));

        if (selected_language.equals(Constants.ARABIC))
        {
            spin_country.setBackgroundResource(R.drawable.list_ar);
            spin_city.setBackgroundResource(R.drawable.list_ar);
            spin_code.setBackgroundResource(R.drawable.list_small_ar);
            spin_code.setTextDirection(View.TEXT_DIRECTION_RTL);

            final ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    TermsAndConditionsFragment termsAndConditionsFragment = new TermsAndConditionsFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment,termsAndConditionsFragment).addToBackStack("").commit();
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(getResources().getColor(R.color.red));
                    ds.setUnderlineText(false);
                }
            };

            spannableString.setSpan(clickableSpan,31,46,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv_terms_and_conditions.setText(spannableString);
            tv_terms_and_conditions.setMovementMethod(LinkMovementMethod.getInstance());
        }
        else
        {
            final ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    TermsAndConditionsFragment termsAndConditionsFragment = new TermsAndConditionsFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment,termsAndConditionsFragment).addToBackStack("").commit();
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(getResources().getColor(R.color.red));
                    ds.setUnderlineText(false);
                }
            };

            spannableString.setSpan(clickableSpan,34,54,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv_terms_and_conditions.setText(spannableString);
            tv_terms_and_conditions.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    private void assignClickListeners()
    {
        btn_change_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selected_language = Utilities.getPreference(context, Constants.SELECTED_LANGUAGE);
                changeLanguage(selected_language);
            }
        });
        spin_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getCities(countries.get(position).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
