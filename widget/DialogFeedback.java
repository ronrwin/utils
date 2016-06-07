package com.audionote.widget;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.audionote.R;
import com.audionote.constant.TaskKey;
import com.audionote.task.BaseTask;
import com.audionote.task.TaskListener;
import com.audionote.task.TaskResult;
import com.audionote.task.impl.FeedBackTask;
import com.audionote.util.SoftInputUtils;
import com.audionote.util.ToastUtils;

public class DialogFeedback extends BaseDialog implements OnClickListener {

	private View mWindow;

	EditText mEdit;
	DialogProgress progress;

	public DialogFeedback(Context context) {
		super(context);

		mWindow = addMyCustomView(R.layout.dialog_edittext);
		SoftInputUtils.showSoftInput(context, mWindow);
		
		mEdit = (EditText) mWindow.findViewById(R.id.edit);
		mEdit.requestFocus();
		mEdit.setHint(context.getString(R.string.feedback_tip));

		progress = new DialogProgress(mContext);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.commit) {
			commitFeedback();
		}
		super.onClick(v);
	}

	private void commitFeedback() {
		String s = mEdit.getText().toString();
		if (!s.equals("")) {
			new FeedBackTask(mContext, new TaskListener<Void>(null) {

				@Override
				public boolean preExecute(BaseTask<Void> task, Integer taskKey) {
					progress.show();
					return super.preExecute(task, taskKey);
				}

				@Override
				public void onResult(TaskResult<Void> result) {
					progress.dismiss();
					if (result.getCode() == TaskResult.OK) {
						ToastUtils.showMessage(mContext, "Success");
						dismiss();
						SoftInputUtils.hideSoftInputFromWindow(mContext,
								mEdit);
					}
					super.onResult(result);
				}

			}, TaskKey.FEED_BACK).commit(s);
		}
	}

}
