package forest;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.Dimension;

public class NodeModel extends Object{
  protected Point2D.Double stPoint;
  protected Point2D.Double finPoint;
  protected Integer id;//nodes:で与えられた番号
  protected String name;
  protected Integer nameCount;//このノードの名前の文字数
  protected Integer depst;//ノードの深さ
  //protected Integer amountOfChildren;//このノードが持つ子の数
  protected Integer listNum;//ForestModel.listNodeにおける当NodeModelの順位。
  private double wordPointer = 9.0;//文字の大きさ（１２ポイント=16ピクセルに設定）
  private double wordMargin = 2.0;//文字の横の余白（片側２ピクセル に設定）
  private double wordMarginHigh = 2.0;//文字の縦の余白（片側２ピクセル に設定）
  private double nodeSegument = 25.0;//ノードの初期の余白（25ピクセルに設定）
  private double nodeMarginHigh = 2.0;//ノードの縦の余白（2ピクセルに設定）
  private double nodeMargin = 25.0;//ノードの横の余白（25ピクセルに設定）

  //コンストラクタ
  public NodeModel(Point2D.Double aStPoint, String aName, Integer aDepst, Integer aListNum){
    this.stPoint = aStPoint;
    this.finPoint = aStPoint;
    this.id = 0;
    this.name = aName;
    this.nameCount = aName.length();
    //System.out.println(name);
    this.depst = aDepst;
    //this.amountOfChildren = 0;
    this.listNum = aListNum;
    return;
  }

  protected void setStPoint(Point2D.Double aStPoint){
    this.stPoint = aStPoint;
    return;
  }

  protected Point2D.Double getStPoint(){
    return this.stPoint;
  }

  protected void setFinPoint(Point2D.Double aFinPoint){
    this.finPoint = aFinPoint;
    return;
  }

  protected Point2D.Double getFinPoint(){
    return this.finPoint;
  }

  protected void setId(Integer aId){
    this.id = aId;
    return;
  }

  protected Integer getId(){
    return this.id;
  }

  protected String getName(){
    return this.name;
  }

  protected Integer getNameCount(){
    return this.nameCount;
  }

  protected Integer getDepst(){
    return this.depst;
  }

  protected void setNameCount(Integer aNameCount){
    this.nameCount = aNameCount;
    return;
  }

  protected Integer getNum(){
    return this.listNum;
  }

  /*protected void increaseChildren(){
    this.amountOfChildren++;
    return;
  }*/

  public double getMargin(){
    return this.nodeMargin;
  }

  public double getMarginHigh(){
    return this.nodeMarginHigh;
  }

  public double getSegument(){
    return this.nodeSegument;
  }

  public double getHigh(){
    double high = this.wordPointer + this.wordMarginHigh;
    return high;
  }

  public Dimension drawDimension(){
    Dimension aDimension = new Dimension((int)this.nodeWidth(), (int)this.nodeHigh());
    return aDimension;
  }

  public double drawStX(){
    double x = this.stPoint.getX();
    return x;
  }

  public double drawStY(){
    double y = this.stPoint.getY();
    return y;
  }

  public double drawFinX(){
    double x = this.finPoint.getX();
    return x;
  }

  public double drawFinY(){
    double y = this.finPoint.getY();
    return y;
  }

  public double nodeWidth(){
    double nodeWidth = this.wordMargin*2 + this.wordPointer*this.nameCount*2/3;
    return nodeWidth;
  }

  public double nodeHigh(){
    double nodeHigh = this.wordPointer + this.wordMarginHigh*2;
    return nodeHigh;
  }

/*  public void updateByDrag(Point aPoint){
    this.updateTapArea();
    return;
  }*/
}
