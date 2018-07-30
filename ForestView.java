package forest;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.Graphics;
import java.awt.Color;
import java.util.List;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.Point;
//import java.lang.Float;
import java.awt.Font;
//import java.util.HashSet;
import mvc.Controller;

public class ForestView extends mvc.View implements Runnable{
  static final long serialVersionUID = 42L;

  private double dpi = 96.0;
  private double dtp = 72.0;
  List<Integer> checkList = new ArrayList<Integer>();
  List<Integer> depstList = new ArrayList<Integer>();
  List<NodeModel> listNode = this.getForestModel().getListNode();
  //List<Integer> listChild = ArrayList<Integer>();
  private Integer count = 0;
  private Point currentPoint = new Point(0 ,0);
  private Integer currentX = 0;
  private Integer currentY = 0;
  private Integer sx;
  private Integer sy;
  private Integer fx;
  private Integer fy;

  public ForestView(ForestModel aModel, ForestController aController){
    super(aModel, aController);
    aController.setView(this);
    for(final NodeModel aNodeModel : this.listNode){
      this.listAppend(aNodeModel);
    }
    return;
  }

  private void listAppend(NodeModel aNodeModel){
    this.depstList.add(aNodeModel.getDepst());
    return;
  }

  private void drawNode(Graphics aGraphics, Integer yet, Point aPoint){
    Integer x = 0;
    Integer y = 0;
    Integer width = 0;
    Integer height = 0;
    //System.out.println(yet);
    for(final NodeModel aNodeModel : this.listNode){
      if(aNodeModel.drawDimension() != null){
        if(aNodeModel.getNum() > yet){
          x = (int)aNodeModel.drawStX();
          y = (int)aNodeModel.drawStY();
          //System.out.println("0:"+yet+aNodeModel.getNum());
          //this.drawBranch(aGraphics, 0, aPoint);
          //this.repaint();
        }else{
          x = (int)aNodeModel.drawFinX();
          y = (int)aNodeModel.drawFinY();
          //System.out.println("1:"+yet+aNodeModel.getNum());
          //this.drawBranch(aGraphics, aNodeModel.getId(), aPoint);
          //this.repaint();
        }
      }
      width = (int)aNodeModel.drawDimension().getWidth();
      height = (int)aNodeModel.drawDimension().getHeight();
      aGraphics.drawRect(x+(int)aPoint.getX(), y+(int)aPoint.getY(), width, height);
      this.drawName(aGraphics, x, y, aNodeModel.getName(), aPoint);
      //System.out.println(yet);
    }
    //System.out.println(this.checkList.size());
    if(yet != 0){this.drawBranch(aGraphics, this.checkList.get(yet-1), currentPoint);}
    return;
  }

  private void drawBranch(Graphics aGraphics, Integer id, Point aPoint){
    List<BranchModel> listBranch = this.getForestModel().getListBranch();
    for(final BranchModel aBranchModel : listBranch){
      this.sx = aBranchModel.getUpStPointX();
      this.sy = aBranchModel.getUpStPointY();
      this.fx = aBranchModel.getDwStPointX();
      this.fy = aBranchModel.getDwStPointY();
      if(aBranchModel.getParentNum().equals(id)){
        this.sx = aBranchModel.getUpFinPointX();
        this.sy = aBranchModel.getUpFinPointY();
        this.fx = aBranchModel.getDwStPointX();
        this.fy = aBranchModel.getDwStPointY();
      }
      if(aBranchModel.getChildNum().equals(id)){
        this.sx = aBranchModel.getUpFinPointX();
        this.sy = aBranchModel.getUpFinPointY();
        this.fx = aBranchModel.getDwFinPointX();
        this.fy = aBranchModel.getDwFinPointY();
      }else if(!aBranchModel.getParentNum().equals(id) && !aBranchModel.getChildNum().equals(id)){
        for(final Integer check : checkList){
          if(aBranchModel.getParentNum().equals(check)){
            this.sx = aBranchModel.getUpFinPointX();
            this.sy = aBranchModel.getUpFinPointY();
            this.fx = aBranchModel.getDwStPointX();
            this.fy = aBranchModel.getDwStPointY();
          }
          if(aBranchModel.getChildNum().equals(check)){
            this.sx = aBranchModel.getUpFinPointX();
            this.sy = aBranchModel.getUpFinPointY();
            this.fx = aBranchModel.getDwFinPointX();
            this.fy = aBranchModel.getDwFinPointY();
          }
        }
      }
      aGraphics.drawLine(this.sx+(int)aPoint.getX(), this.sy+(int)aPoint.getY(), this.fx+(int)aPoint.getX(), this.fy+(int)aPoint.getY());
    }
    return;
  }

  private void drawName(Graphics aGraphics, Integer x, Integer y, String aString, Point aPoint){
    Font font1 = new Font(Font.SERIF, Font.PLAIN, 12);
    aGraphics.setFont(font1);
    aGraphics.drawString(aString, x+2+(int)aPoint.getX(), y+10+(int)aPoint.getY());
    return;
  }

  public void updateByWheel(Point aPoint){
    currentX = (int)currentPoint.getX() + (int)aPoint.getX();
    currentY = (int)currentPoint.getY() + (int)aPoint.getY();
    currentPoint.setLocation(currentX, currentY);
    return;
  }

  @Override
  public void paintComponent(Graphics aGraphics)
  {
    super.paintComponent(aGraphics);
    aGraphics.setColor(Color.black);
    this.drawNode(aGraphics, this.count, currentPoint);
    if(this.count < this.listNode.size()){
      this.checkList.add(this.listNode.get(count).getId());
      count++;
    }
    //System.out.println("終わり");
    //this.drawAxis(aGraphics);
    //this.drawSpurGear(aGraphics);
    //this.drawPinionGear(aGraphics);
    return;
  }

  public ForestModel getForestModel(){
    ForestModel forestModel = (ForestModel)model;
    return forestModel;
  }

  public void run(){
    while(true){
      update();
      try{
        Thread.sleep(500);
      }catch(Exception e){}
      }
    }


/*  public double convertDp2Px(double dp){
    double px = (dp/this.dtp)*this.dpi;
    return px;
  }*/

}
