package forest;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

public class BranchModel extends Object
{
  private Integer parentNum;
  private Integer childNum;
  private Point2D.Double upStPoint;
  private Point2D.Double upFinPoint;
  private Point2D.Double dwStPoint;
  private Point2D.Double dwFinPoint;

  protected BranchModel(Integer parent, Integer child, Point2D.Double aUpStPoint, Point2D.Double aDwStPoint){
    this.parentNum = parent;
    this.childNum = child;
    this.upStPoint = aUpStPoint;
    this.upFinPoint = aUpStPoint;
    this.dwStPoint = aDwStPoint;
    this.dwFinPoint = aDwStPoint;
    return;
  }

  protected void setUpFinPoint(Point2D.Double aUpFinPoint){
    this.upFinPoint = aUpFinPoint;
    return;
  }

  protected void setDwFinPoint(Point2D.Double aDwFinPoint){
    this.dwFinPoint = aDwFinPoint;
    return;
  }

  public Integer getUpStPointX(){
    return (int)this.upStPoint.getX();
  }

  public Integer getUpStPointY(){
    return (int)this.upStPoint.getY();
  }

  public Integer getUpFinPointX(){
    return (int)this.upFinPoint.getX();
  }

  public Integer getUpFinPointY(){
    return (int)this.upFinPoint.getY();
  }

  public Integer getDwStPointX(){
    return (int)this.dwStPoint.getX();
  }

  public Integer getDwStPointY(){
    return (int)this.dwStPoint.getY();
  }

  public Integer getDwFinPointX(){
    return (int)this.dwFinPoint.getX();
  }

  public Integer getDwFinPointY(){
    return (int)this.dwFinPoint.getY();
  }

  protected Integer getParentNum(){
    return this.parentNum;
  }

  protected Integer getChildNum(){
    return this.childNum;
  }

  //public Point makeStPoint(Integer parent, )

  /*public void makeBranch(Integer parentNum, Integer childNum){
    //upperPoint = upper;
    //downPoint = downner;
    return;
  */
}
