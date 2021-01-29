package me.ccrama.redditslide.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SwitchCompat;

import java.util.Map;

import me.ccrama.redditslide.R;
import me.ccrama.redditslide.SettingValues;
import me.ccrama.redditslide.SubmissionCache;
import me.ccrama.redditslide.Views.CreateCardView;

/**
 * Created by ccrama on 9/17/2015.
 */
public class EditCardsLayout extends BaseActivityAnim {
    @Override
    public void onCreate(Bundle savedInstance) {
        overrideRedditSwipeAnywhere();
        overrideSwipeFromAnywhere();

        super.onCreate(savedInstance);

        applyColorTheme();
        setContentView(R.layout.activity_settings_theme_card);
        setupAppBar(R.id.toolbar, R.string.settings_layout_default, true, true);

        initViews();
    }

    private void initViews() {
        LinearLayout layout = initLayout();
        initCurrentPicture(layout);
        noThumbnailsHandler(layout);
        initCurrentView(layout);
        initActionbar(layout);
        initAppCompatCheckBox(R.id.hidebutton, layout, SettingValues.hideButton);
        initAppCompatCheckBox(R.id.savebutton, layout, SettingValues.saveButton);
        initSwitchCompatComponent(R.id.commentlast, SettingValues.commentLastVisit, false, SettingValues.PREF_COMMENT_LAST_VISIT);
        initSwitchCompatComponent(R.id.domain, SettingValues.showDomain, false, SettingValues.PREF_SHOW_DOMAIN);
        initSwitchCompatComponent(R.id.selftextcomment, SettingValues.hideSelftextLeadImage, false, SettingValues.PREF_SELFTEXT_IMAGE_COMMENT);
        initSwitchCompatComponent(R.id.abbreviateScores, SettingValues.hidePostAwards, false, SettingValues.PREF_ABBREVIATE_SCORES);
        initSwitchCompatComponent(R.id.hidePostAwards, SettingValues.hidePostAwards, false, SettingValues.PREF_HIDE_POST_AWARDS);
        initSwitchCompatComponent(R.id.titleTop, SettingValues.titleTop, false, SettingValues.PREF_TITLE_TOP);
        initSwitchCompatComponent(R.id.votes, SettingValues.votesInfoLine, true, SettingValues.PREF_VOTES_INFO_LINE);
        initSwitchCompatComponent(R.id.votes, SettingValues.votesInfoLine, true, SettingValues.PREF_VOTES_INFO_LINE);
        initSwitchCompatComponent(R.id.contenttype, SettingValues.typeInfoLine, true, SettingValues.PREF_TYPE_INFO_LINE);
        initSwitchCompatComponent(R.id.selftext, SettingValues.cardText, false, SettingValues.PREF_CARD_TEXT);
        initSwitchCompatComponent(R.id.action, layout, SettingValues.switchThumb);
        initSwitchCompatComponent(R.id.tagsetting, layout, SettingValues.smallTag);
    }

    private LinearLayout initLayout() {
        final LinearLayout layout = (LinearLayout) findViewById(R.id.card);
        layout.removeAllViews();
        layout.addView(CreateCardView.CreateView(layout));
        return layout;
    }

    private void noThumbnailsHandler(LinearLayout layout) {
        if (!SettingValues.noThumbnails) {
            final SwitchCompat bigThumbnails = (SwitchCompat) findViewById(R.id.bigThumbnails);
            assert bigThumbnails != null; //def won't be null

            bigThumbnails.setChecked(SettingValues.bigThumbnails);
            bigThumbnails.setOnCheckedChangeListener((buttonView, isChecked) -> {
                SettingValues.prefs.edit().putBoolean("bigThumbnails", isChecked).apply();
                SettingValues.bigThumbnails = isChecked;

                if (!SettingValues.bigPicCropped) {
                    layout.removeAllViews();

                    layout.addView(CreateCardView.setBigPicEnabled(false, layout));
                    resetAllOverriddenPictureValues();
                }
            });
        }

    }

    private void initAppCompatCheckBox(int viewId, LinearLayout layout, boolean settingIsChecked) {
        final boolean[] settingIsCheckedReference = {settingIsChecked};
        final AppCompatCheckBox appCompatCheckBox = (AppCompatCheckBox) findViewById(viewId);

        appCompatCheckBox.setChecked(settingIsCheckedReference[0]);
        appCompatCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            settingIsCheckedReference[0] = isChecked;
            setHideSaveButtonVisibility(layout);
            SettingValues.prefs.edit().putBoolean(SettingValues.PREF_SAVE_BUTTON, isChecked).apply();
        });
    }

    private void initSwitchCompatComponent(int viewId, LinearLayout layout, boolean settingIsChecked) {
        final SwitchCompat switchCompat = (SwitchCompat) findViewById(viewId);
        final boolean[] settingIsCheckedReference = {settingIsChecked};
        switchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            settingIsCheckedReference[0] = isChecked;
            layout.removeAllViews();
            layout.addView(CreateCardView.setSwitchThumb(isChecked, layout));
        });
    }

    private void initSwitchCompatComponent(int viewId, boolean settingIsChecked, boolean shouldEvictAll, String preference) {
        final SwitchCompat switchCompat = (SwitchCompat) findViewById(viewId);
        final boolean[] settingIsCheckedReference = {settingIsChecked};
        switchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            settingIsCheckedReference[0] = isChecked;
            SettingValues.prefs.edit().putBoolean(preference, isChecked).apply();
            if (shouldEvictAll) {
                SubmissionCache.evictAll();
            }
        });
    }

    private void setHideSaveButtonVisibility(LinearLayout layout) {
        layout.findViewById(R.id.hide).setVisibility(SettingValues.hideButton && SettingValues.actionbarVisible ? View.VISIBLE : View.GONE);
        layout.findViewById(R.id.save).setVisibility(SettingValues.saveButton && SettingValues.actionbarVisible ? View.VISIBLE : View.GONE);
    }

    public void setCurrentPictureText(TextView currentPicture) {
        if (SettingValues.bigPicCropped) {
            currentPicture.setText(R.string.mode_cropped);
        } else if (SettingValues.bigPicEnabled) {
            currentPicture.setText(R.string.mode_bigpic);
        } else if (SettingValues.noThumbnails) {
            currentPicture.setText(R.string.mode_no_thumbnails);
        } else {
            currentPicture.setText(R.string.mode_thumbnail);
        }
    }


    private void resetAllOverriddenPictureValues() {
        SharedPreferences.Editor e = SettingValues.prefs.edit();
        for (Map.Entry<String, ?> map : SettingValues.prefs.getAll().entrySet()) {
            if (map.getKey().startsWith("picsenabled")) {
                e.remove(map.getKey()); //reset all overridden values
            }
        }
        e.apply();
    }

    public void initActionbar(LinearLayout layout) {
        String actionbarText = getActionbarText();
        ((TextView) findViewById(R.id.actionbar_current)).setText(actionbarText);

        findViewById(R.id.actionbar).setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(EditCardsLayout.this, v);
            popup.getMenuInflater().inflate(R.menu.actionbar_mode, popup.getMenu());

            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.always:
                        actionBarUpdateSharedPreferences(false);
                        removeViewsAndAddView(layout, CreateCardView.setActionbarVisible(true, layout));
                        break;
                    case R.id.tap:
                        actionBarUpdateSharedPreferences(true);
                        removeViewsAndAddView(layout, CreateCardView.setActionbarVisible(false, layout));
                        break;
                    case R.id.button:
                        layout.removeAllViews();
                        layout.addView(CreateCardView.setActionbarVisible(false, layout));
                        break;
                }
                ((TextView) findViewById(R.id.actionbar_current)).setText(actionbarText);
                return true;
            });

            popup.show();
        });
    }

    private String getActionbarText() {
        String actionbarText;
        if (!SettingValues.actionbarVisible) {
            if (SettingValues.actionbarTap) {
                actionbarText = getString(R.string.tap_actionbar);
            } else {
                actionbarText = getString(R.string.press_actionbar);
            }
        } else {
            actionbarText = getString(R.string.always_actionbar);
        }
        return actionbarText;
    }

    public void initCurrentPicture(LinearLayout layout) {
        final TextView currentPicture = (TextView) findViewById(R.id.picture_current);
        setCurrentPictureText(currentPicture);

        findViewById(R.id.picture).setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(EditCardsLayout.this, v);
            popup.getMenuInflater().inflate(R.menu.pic_mode_settings, popup.getMenu());
            View cardBigPicEnabled = CreateCardView.setBigPicEnabled(true, layout);
            View cardBigPicDisabled = CreateCardView.setBigPicEnabled(false, layout);

            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.bigpic:
                        case R.id.noThumbnails:
                            removeViewsAndAddView(layout, cardBigPicEnabled);
                            resetAllOverriddenPictureValues();
                            break;
                        case R.id.cropped:
                            removeViewsAndAddView(layout, cardBigPicEnabled);
                            break;
                        case R.id.thumbnail:
                            removeViewsAndAddView(layout, cardBigPicDisabled);
                            resetAllOverriddenPictureValues();
                            break;
                    }
                    setCurrentPictureText(currentPicture);
                    return true;
                }
            });
            popup.show();
        });
    }

    public void initCurrentView(LinearLayout layout) {
        ((TextView) findViewById(R.id.view_current)).setText(getCurrentViewText());

        findViewById(R.id.view).setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(EditCardsLayout.this, v);
            popup.getMenuInflater().inflate(R.menu.card_mode_settings, popup.getMenu());

            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.center:
                        removeViewsAndAddView(layout, CreateCardView.setMiddleCard(true, layout));
                        break;
                    case R.id.card:
                        removeViewsAndAddView(layout, CreateCardView.setCardViewType(CreateCardView.CardEnum.LARGE, layout));
                        break;
                    case R.id.list:
                        removeViewsAndAddView(layout, CreateCardView.setCardViewType(CreateCardView.CardEnum.LIST, layout));
                        break;
                    case R.id.desktop:
                        removeViewsAndAddView(layout, CreateCardView.setCardViewType(CreateCardView.CardEnum.DESKTOP, layout));
                        break;
                }
                ((TextView) findViewById(R.id.view_current)).setText(getCurrentViewText());
                return true;
            });

            popup.show();
        });
    }

    public String getCurrentViewText() {
        String currentViewText;
        if (CreateCardView.isCard()) {
            if ((CreateCardView.isMiddle())) {
                currentViewText = getString(R.string.mode_centered);
            } else {
                currentViewText = getString(R.string.mode_card);
            }
        } else {
            if (CreateCardView.isDesktop()) {
                currentViewText = getString(R.string.mode_desktop_compact);
            } else {
                currentViewText = getString(R.string.mode_list);
            }
        }
        return currentViewText;
    }

    private void removeViewsAndAddView(LinearLayout layout, View view) {
        layout.removeAllViews();
        layout.addView(view);
    }

    private void actionBarUpdateSharedPreferences(boolean actionbarTap) {
        SettingValues.actionbarTap = actionbarTap;
        SettingValues.prefs.edit().putBoolean(SettingValues.PREF_ACTIONBAR_TAP, false).apply();
    }
}
