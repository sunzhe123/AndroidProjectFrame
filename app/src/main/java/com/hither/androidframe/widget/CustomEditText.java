package com.hither.androidframe.widget;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hither.androidframe.R;

/**
 * Created by Administrator on 15-11-17.
 */
public class CustomEditText extends LinearLayout {
    private TextView editText_customText;
    private EditText editText_customEdit;
    private ImageView editText_customImage;

    public CustomEditText(Context context) {
        super(context);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.edittextcustom_layout, this);
        editText_customText = (TextView) findViewById(R.id.editText_customText);
        editText_customEdit = (EditText) findViewById(R.id.editText_customEdit);
        editText_customImage = (ImageView) findViewById(R.id.editText_customImage);
        editText_customEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    editText_customImage.setVisibility(View.VISIBLE);

                } else {
                    editText_customImage.setVisibility(View.INVISIBLE);
                }
            }
        });
        editText_customImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editText_customEdit.setText("");
            }
        });
    }

    public void setText(String text) {
        editText_customText.setText(text);
    }

    public void setHint(String hint) {
        editText_customEdit.setHint(hint);
    }

    public void setEditTextType(String type) {
        if ("PASSWORD".equals(type)) {
            editText_customEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }

    }

    public String getText() {
        return editText_customEdit.getText().toString();
    }
}
