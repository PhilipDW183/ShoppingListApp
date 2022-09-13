package com.example.myshoppinglist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ramotion.paperonboarding.PaperOnboardingFragment;
import com.ramotion.paperonboarding.PaperOnboardingPage;
import com.ramotion.paperonboarding.listeners.PaperOnboardingOnRightOutListener;

import java.util.ArrayList;

public class OnboardActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_onboarding);
        getSupportActionBar().hide();
        
        showOnboard();

    }

    public void showOnboard() {
        fragmentManager = getSupportFragmentManager();
        final PaperOnboardingFragment paperOnboardingFragment = PaperOnboardingFragment.newInstance(getDataforOnboarding());
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.frame_layout, paperOnboardingFragment);

        fragmentTransaction.commit();

        paperOnboardingFragment.setOnRightOutListener(new PaperOnboardingOnRightOutListener() {
            @Override
            public void onRightOut() {

                Intent intent = new Intent(OnboardActivity.this, MainActivity.class);
                startActivity(intent);

                finish();
                return;
            }
        });
    }

    private ArrayList<PaperOnboardingPage> getDataforOnboarding() {

        PaperOnboardingPage source = new PaperOnboardingPage("My Shopping List", "Welcome to your shopping list app", Color.parseColor("#ffb174"),R.mipmap.ic_launcher_round, R.drawable.onboarding_arrow);
        PaperOnboardingPage source1 = new PaperOnboardingPage("Adding items", "Use the add button in the bottom right hand corner of the screen to add new items to the list", Color.parseColor("#22eaaa"),R.drawable.ic_baseline_add_32_large, R.drawable.onboarding_arrow);
        PaperOnboardingPage source2 = new PaperOnboardingPage("Deleting items", "Use the bin shaped button on each item in the list to delete them", Color.parseColor("#ee5a5a"),R.drawable.delete_button_large, R.drawable.onboarding_arrow);
        PaperOnboardingPage source3 = new PaperOnboardingPage("Editing items", "Use the pencil shaped button on each item in the list to edit them", Color.parseColor("#73a3e6"),R.drawable.edit_button_large, R.drawable.onboarding_arrow);
        PaperOnboardingPage source4 = new PaperOnboardingPage("In the basket", "Use the tick box on the left of each item to mark them as in your basket or purchased", Color.parseColor("#6be373"),R.drawable.item_checkbox_large, R.drawable.onboarding_arrow);
        PaperOnboardingPage source5 = new PaperOnboardingPage("Removing multiple items", "Use the sweeping icon in the bottom left to remove all items in the basket (those that have a tick next to them) \n\n\n\nSwipe from right to left to start the app", Color.parseColor("#de861b"),R.drawable.ic_baseline_cleaning_services_24_large, R.drawable.onboarding_arrow);

        // array list is used to store
        // data of onboarding screen
        ArrayList<PaperOnboardingPage> elements = new ArrayList<>();

        // all the sources(data to show on screens)
        // are added to array list
        elements.add(source);
        elements.add(source1);
        elements.add(source2);
        elements.add(source3);
        elements.add(source4);
        elements.add(source5);

        return elements;

    };



}
