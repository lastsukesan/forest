package forest;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.event.MouseInputAdapter;
import javafx.scene.input.ScrollEvent;

	public class ForestController extends mvc.Controller implements MouseWheelListener{
		protected ForestModel model;
		protected ForestView view;
        
		public ForestController(){
    	super();
			model = null;
			view = null;
    	return;
  	}

		public void setView(ForestView aView){
			view = aView;
			view.addMouseWheelListener(this);
			return;
		}

		/*@Override
		public void mouseDragged(MouseEvent aMouseEvent){
    	this.getForestModel().updateByDrag(aMouseEvent.getPoint());
    	this.getForestView().update();
    	return;
  	}

		@Override
  	public void mousePressed(MouseEvent aMouseEvent){
    	this.getForestModel().updateByPress(aMouseEvent.getPoint());
			return;
		}

		@Override
  	public void mouseReleased(MouseEvent aMouseEvent){
    	this.getForestModel().updateByRelease(aMouseEvent.getPoint());
    	return;
  	}*/

		public void mouseWheelMoved(MouseWheelEvent aMouseWheelEvent){
			Integer scrollAmount = -(aMouseWheelEvent.getWheelRotation());
			if(scrollAmount == 0){return;}
			Point aPoint = new Point(0, scrollAmount);
			Integer someModifiers = aMouseWheelEvent.getModifiersEx();
			if(someModifiers > 0){aPoint = new Point(scrollAmount, 0);}
			this.view.updateByWheel(aPoint);
			this.view.scrollBy(aPoint);
			this.view.repaint();
			//System.out.println(scrollAmount);
			return;
		}

		/*private ForestModel getForestModel(){
    	ForestModel forestModel = (ForestModel)model;
    	return forestModel;
  	}*/

		/*private ForestView getForestView(){
    	ForestView forestView = (ForestView)view;
    	return forestView;
  	}*/

	}
