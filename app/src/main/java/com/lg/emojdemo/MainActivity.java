package com.lg.emojdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import io.github.rockerhieu.emojicon.EmojiconEditText;
import io.github.rockerhieu.emojicon.EmojiconGridFragment;
import io.github.rockerhieu.emojicon.EmojiconTextView;
import io.github.rockerhieu.emojicon.EmojiconsFragment;
import io.github.rockerhieu.emojicon.emoji.Emojicon;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, EmojiconGridFragment.OnEmojiconClickedListener, EmojiconsFragment.OnEmojiconBackspaceClickedListener {

    private EmojiconEditText mEditEmojicon;
    private EmojiconTextView mTxtEmojicon;
    private boolean hasClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.imageView).setOnClickListener(this);
        findViewById(R.id.bt_fs).setOnClickListener(this);
        mEditEmojicon = (EmojiconEditText) findViewById(R.id.editEmojicon);
        mEditEmojicon.setOnClickListener(this);
        mTxtEmojicon = (EmojiconTextView) findViewById(R.id.txtEmojicon);
        setEmojiconFragment(false);
    }

    private void setEmojiconFragment(boolean useSystemDefault) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.emojicons, EmojiconsFragment.newInstance(useSystemDefault))
                .commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageView: // 表情按钮
                if (hasClick) {
                    findViewById(R.id.emojicons).setVisibility(View.GONE);
                } else {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
                    findViewById(R.id.emojicons).setVisibility(View.VISIBLE);
                }
                hasClick = !hasClick;
                break;
            case R.id.bt_fs: // 发送按钮
                mTxtEmojicon.setText(mTxtEmojicon.getText() + "" + mEditEmojicon.getText());
                mEditEmojicon.setText("");
                break;
            case R.id.editEmojicon: // 输入框
                findViewById(R.id.emojicons).setVisibility(View.GONE);
                hasClick = !hasClick;
                break;
        }
    }

    @Override
    public void onEmojiconClicked(Emojicon emojicon) {
        EmojiconsFragment.input(mEditEmojicon, emojicon);
    }

    @Override
    public void onEmojiconBackspaceClicked(View v) {
        EmojiconsFragment.backspace(mEditEmojicon);
    }

    @Override
    public void onBackPressed() {
        if(hasClick){
            findViewById(R.id.emojicons).setVisibility(View.GONE);
            hasClick = !hasClick;
        }else {
            super.onBackPressed();
        }
    }
}
