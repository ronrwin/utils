package com.audionote.widget;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.audionote.R;
import com.audionote.interfaces.IDialogCommitListener;
import com.audionote.util.SoftInputUtils;

public class DialogEdit extends BaseDialog implements OnClickListener {

	private View mWindow;

	public EditText mEdit;
	IDialogCommitListener mListener;

	public DialogEdit(Context context, IDialogCommitListener listener) {
		super(context);

		mWindow = addMyCustomView(R.layout.dialog_edittext);
		SoftInputUtils.showSoftInput(context, mWindow);
		mListener = listener;

		mEdit = (EditText) mWindow.findViewById(R.id.edit);
		mEdit.requestFocus();

	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.commit) {
			String s = mEdit.getText().toString();
			mListener.commit(s);
		}
		super.onClick(v);
	}

}
