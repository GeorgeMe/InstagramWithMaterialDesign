package demo.binea.com.instagramwithmaterialdesign.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextSwitcher;

import butterknife.ButterKnife;
import butterknife.InjectView;
import demo.binea.com.instagramwithmaterialdesign.R;
import demo.binea.com.instagramwithmaterialdesign.Util;

/**
 * Created by xubinggui on 15/3/14.
 */
public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

	private static final int ANIMATED_ITEMS_COUNT = 2;

	private Context context;
	private int lastAnimatedPosition = -1;
	private int itemsCount = 0;

	private OnFeedItemClickListener onFeedItemClickListener;

	public FeedAdapter(Context context) {
		this.context = context;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		final View view = LayoutInflater.from(context).inflate(R.layout.item_feed, parent, false);
		return new CellFeedViewHolder(view);
	}

	private void runEnterAnimation(View view, int position) {
		if (position >= ANIMATED_ITEMS_COUNT - 1) {
			return;
		}

		if (position > lastAnimatedPosition) {
			lastAnimatedPosition = position;
			view.setTranslationY(Util.getScreenHeight(context));
			view.animate()
					.translationY(0)
					.setInterpolator(new DecelerateInterpolator(3.f))
					.setDuration(700)
					.start();
		}
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
		runEnterAnimation(viewHolder.itemView, position);
		CellFeedViewHolder holder = (CellFeedViewHolder) viewHolder;
		if (position % 2 == 0) {
			holder.ivFeedCenter.setImageResource(R.drawable.img_feed_center_1);
			holder.ivFeedBottom.setImageResource(R.drawable.img_feed_bottom_1);
		} else {
			holder.ivFeedCenter.setImageResource(R.drawable.img_feed_center_2);
			holder.ivFeedBottom.setImageResource(R.drawable.img_feed_bottom_2);
		}

		holder.btnComments.setOnClickListener(this);
		holder.btnComments.setTag(position);
	}

	@Override
	public int getItemCount() {
		return itemsCount;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btnComments) {
			if (onFeedItemClickListener != null) {
				onFeedItemClickListener.onCommentsClick(v, (Integer) v.getTag());
			}
		}
	}

	public static class CellFeedViewHolder extends RecyclerView.ViewHolder {
		@InjectView(R.id.ivFeedCenter)
		ImageView ivFeedCenter;
		@InjectView(R.id.ivFeedBottom)
		ImageView ivFeedBottom;
		@InjectView(R.id.btnComments)
		ImageButton btnComments;
		@InjectView(R.id.btnLike)
		ImageButton btnLike;
		@InjectView(R.id.btnMore)
		ImageButton btnMore;
		@InjectView(R.id.vBgLike)
		View vBgLike;
		@InjectView(R.id.ivLike)
		ImageView ivLike;
		@InjectView(R.id.tsLikesCounter)
		TextSwitcher tsLikesCounter;
		@InjectView(R.id.ivUserProfile)
		ImageView ivUserProfile;
		@InjectView(R.id.vImageRoot)
		FrameLayout vImageRoot;

//		SendingProgressView vSendingProgress;
		View vProgressBg;

		public CellFeedViewHolder(View view) {
			super(view);
			ButterKnife.inject(this, view);
		}

	}

	public void updateItems() {
		itemsCount = 10;
		notifyDataSetChanged();
	}

	public void setOnFeedItemClickListener(OnFeedItemClickListener onFeedItemClickListener) {
		this.onFeedItemClickListener = onFeedItemClickListener;
	}

	public interface OnFeedItemClickListener {
		public void onCommentsClick(View v, int position);
	}
}
