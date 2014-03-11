package cr.gov.respiremossalud.ui;

//import com.actionbarsherlock.internal.nineoldandroids.animation.Animator;
//import com.actionbarsherlock.internal.nineoldandroids.animation.Animator.AnimatorListener;
//import com.actionbarsherlock.internal.nineoldandroids.animation.AnimatorSet;
//import com.actionbarsherlock.internal.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.PropertyValuesHolder;
import com.nineoldandroids.view.animation.AnimatorProxy;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.ScrollView;

public class UIScrollView extends ScrollView {
	
	private Runnable scrollerTask;
	private int initialPosition;
	private int newCheck = 100;

	public interface OnScrollStoppedListener{
	    void onScrollStopped();
	}
	
	public interface OnScrollChangedListener {
	    void onScrollChanged( UIScrollView v, int l, int t, int oldl, int oldt );
	}
	
	private OnScrollChangedListener onScrollChangedListener;

	public void setOnScrollChangedListener(OnScrollChangedListener l) {
	    this.onScrollChangedListener = l;
	}
	
	private OnScrollStoppedListener onScrollStoppedListener;

	
	
	public UIScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);

		scrollerTask = new Runnable() {
	        public void run() {
	            int newPosition = getScrollY();
	            if(initialPosition - newPosition == 0){//has stopped
	                if(onScrollStoppedListener!=null){
	                    onScrollStoppedListener.onScrollStopped();
	                }
	            }else{
	                initialPosition = getScrollY();
	                UIScrollView.this.postDelayed(scrollerTask, newCheck);
	            }
	        }
	    };
		
	}
	
	public void easingScrollTo(int x, int y){
		
		PropertyValuesHolder pvh = PropertyValuesHolder.ofInt("scrollY", this.getScrollY(), y);
		ObjectAnimator yTranslate = ObjectAnimator.ofPropertyValuesHolder(AnimatorProxy.NEEDS_PROXY ?
		        AnimatorProxy.wrap(this) : this, pvh);
//		.start();
		
//		ObjectAnimator xTranslate = ObjectAnimator.ofInt(this, "scrollX", x);
//	    ObjectAnimator yTranslate = ObjectAnimator.ofInt(this, "scrollY", y);
	    
	    AnimatorSet animators = new AnimatorSet();
	    animators.setDuration(1000L);
	    animators.play(yTranslate);
//	    animators.playTogether(xTranslate, yTranslate);
	    animators.setInterpolator(new AccelerateDecelerateInterpolator());
	    animators.addListener(new AnimatorListener() {

	        @Override
	        public void onAnimationStart(Animator arg0) {
	            // TODO Auto-generated method stub
	        }

	        @Override
	        public void onAnimationRepeat(Animator arg0) {
	            // TODO Auto-generated method stub

	        }

	        @Override
	        public void onAnimationEnd(Animator arg0) {
	            // TODO Auto-generated method stub

	        }

	        @Override
	        public void onAnimationCancel(Animator arg0) {
	            // TODO Auto-generated method stub

	        }
	    });
	    animators.start();
	}
	
//	onscrollch
	
//	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
//	    mOnScrollViewListener.onScrollChanged( this, l, t, oldl, oldt );
//	    super.onScrollChanged( l, t, oldl, oldt );
//	}
	
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		// TODO Auto-generated method stub
	    onScrollChangedListener.onScrollChanged( this, l, t, oldl, oldt );
		super.onScrollChanged(l, t, oldl, oldt);
	}
	
	public void setOnScrollStoppedListener(UIScrollView.OnScrollStoppedListener listener){
	    onScrollStoppedListener = listener;
	}
	
	public void startScrollerTask(){
	    initialPosition = getScrollY();
	    UIScrollView.this.postDelayed(scrollerTask, newCheck);
	}

}
