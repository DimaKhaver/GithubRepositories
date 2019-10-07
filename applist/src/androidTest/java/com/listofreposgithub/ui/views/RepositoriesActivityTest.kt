package com.listofreposgithub.ui.views

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.listofreposgithub.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RepositoriesActivityTest {

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(RepositoriesActivity::class.java)

    @Test
    fun recyclerViewTest() {
        Espresso.onView(withId(R.id.repositories_list))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(1))
    }

}
