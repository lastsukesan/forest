package forest;

import java.util.List;
import java.util.ArrayList;
import java.io.File;
//import java.io.FileReader;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//import java.util.function.Consumer;

public class ForestModel extends mvc.Model{
  public List<NodeModel> listNode;
  public List<BranchModel> listBranch;
  private String chekerStr = "|";
  private Integer depst = 0;//当該ノードの深さ
  private Integer wc = 0;//ワードカウント(現在の文字が文字列の先頭から何番目かを測る変数)
  private Integer cc = 0;//カッティングカウント（文字列の何文字目以降をノードの名前として扱うかを測る変数）
  private Pattern aPattern = Pattern.compile("^[0-9a-zA-Z]+$");
  private Matcher aMatcher;
  private String nodeName;
  //protected Point2D.Double origin = new Point2D.Double(0.0, 0.0);
  //private Integer maxWC = 0;//文字数の最大値

  public ForestModel(BufferedReader aBuffer){
    super();
    this.initialize();
    this.readFile(aBuffer);
    this.lineUpNode();
    this.lineUpBranch();
    return;
  }

  private void initialize(){
    listNode = new ArrayList<NodeModel>();
    listBranch = new ArrayList<BranchModel>();
    return;
  }

    //読み取ったバッファーを仕分けする
  public void readFile(BufferedReader aBuffer){

    String aString;
    /*Consumer<Object> aConsumer = (Object aObject)->{
      aString = aBuffer.readLine();
    };*/
    Integer flag = 0;
    Integer count = 0;
    try{
      while((aString = aBuffer.readLine()) != null){
        if(aString.equals("nodes:")){
          flag = 1;
          count = 0;
        }else if(aString.equals("branches:")){
          flag = 2;
          count = 0;
        }
        if(flag == 0 && count != 0){
          this.appendDepstAndName(aString, count);
        }else if(flag == 1 && count != 0){
          this.appendNodeId(aString);
        }else if(flag == 2 && count != 0){
          this.appendBranch(aString);
        }
        count++;
      }
    }catch (IOException e){
      System.out.println(e);
    }
    return;
  }

  //ノードの深度を計測し、ForestModelに渡す
  private void appendDepstAndName(String aString, Integer count){
    String[] aStr = aString.split("");
    wc = 0;
    depst = 0;
    cc = 0;
    Integer flag = 0;
    for(final String str : aStr){
      this.checkedStrings(str);
      aMatcher = aPattern.matcher(str);
      if(aMatcher.find() && flag == 0){
        cc = wc;
        flag = 1;
        //System.out.println(aString);
      }
      wc++;
    }
    nodeName = aString.substring(cc);
    //this.measurementMaxWC(nodeName);
    this.appendDepstAndName(depst, count, nodeName);
    return;
  }

  //trees:より、"|"を文字列内に発見するたびにdepst++
  private void checkedStrings(String str){
    if(str.equals(chekerStr)){
      depst++;
    }
    return;
  }

 //nodes:より、ノードの名前からidを割り当てる
  private void appendNodeId(String aString){
    String[] str = aString.split(", ",2);
    Integer id = Integer.valueOf(str[0]).intValue();
    this.appendNodeId(id, str[1]);
    return;
  }

 //branches:より、ブランチを割り当てていく
  private void appendBranch(String aString){
    String[] str = aString.split(", ",2);
    Integer parent = Integer.parseInt(str[0]);
    Integer child = Integer.parseInt(str[1]);
    Integer temp;
    Integer tempDepst = 0;
    Integer tempPareNum = 0;
    Integer tempChilNum = 0;
    if(this.correct(parent, child)){
      temp = parent;
      parent = child;
      child = temp;
    }
    for(NodeModel aNodeModel : this.listNode){
      if(aNodeModel.getId() == parent){
        tempDepst = aNodeModel.getDepst();
        tempPareNum = aNodeModel.getNum();
      }
      if(aNodeModel.getId() == child){
        tempChilNum = aNodeModel.getNum();
      }
      if(tempPareNum != 0 && tempChilNum != 0){break;}
    }
    for(Integer i = tempPareNum; i < tempChilNum; i++){
      if(this.listNode.get(i).getDepst() < tempDepst){return;}
      //System.out.println(i);
    }
    this.appendBranchNum(parent, child);
    return;
  }

  /*public String toString(String aString){
    StringBuffer aBuffer = new StringBuffer();
    aBuffer.append("resource/data/");
    aBuffer.append(aString);
    return aBuffer.toString();
  }*/



  private void appendDepstAndName(Integer depst, Integer count, String nodeName){
    listNode.add(new NodeModel(this.makeNodeStPoint((double)count), nodeName, depst, count));
    return;
  }

  private void appendNodeId(Integer id, String name){
    for(final NodeModel aNodeModel : listNode){
      if(aNodeModel.getName().equals(name) && aNodeModel.getId().equals(0)){
        aNodeModel.setId(id);
        break;
      }
    }
    return;
  }

  private void appendBranchNum(Integer parent, Integer child){
    listBranch.add(new BranchModel(parent, child, this.makeBranchUpStPoint(parent), this.makeBranchDwStPoint(child)));
    return;
  }

  private Point2D.Double makeNodeStPoint(double count){
    double y = (count-1.0)*18.0;
    Point2D.Double stPoint = new Point2D.Double(2.0, y);
    return stPoint;
  }

  private Point2D.Double makeBranchUpStPoint(Integer parent){
    Point2D.Double tempPoint = new Point2D.Double();
    double tempNodeWidth = 0.0;
    double high = 0.0;
    //NodeModel tempModel = new NodeModel();
    for(final NodeModel aNodeModel : listNode){
      if(aNodeModel.getId().equals(parent)){
        tempPoint = aNodeModel.getStPoint();
        tempNodeWidth = aNodeModel.nodeWidth();
        high = aNodeModel.nodeHigh();
        break;
      }
    }
    //tempPoint = tempModel.getStPoint();
    //tempNameCount = tempModel.getNameCount();
    double coodinateX = tempPoint.getX() + tempNodeWidth;
    double coodinateY = tempPoint.getY() + high/2;
    Point2D.Double branchUpStPoint = new Point2D.Double(coodinateX, coodinateY);
    return branchUpStPoint;
  }

  private Point2D.Double makeBranchDwStPoint(Integer child){
    Point2D.Double tempPoint = new Point2D.Double();
    double high = 0.0;
    for(final NodeModel aNodeModel : listNode){
      if(aNodeModel.getId().equals(child)){
        tempPoint = aNodeModel.getStPoint();
        high = aNodeModel.nodeHigh();
        break;
      }
    }
    double coodinateX = tempPoint.getX();
    double coodinateY = tempPoint.getY() + high/2;
    Point2D.Double branchDwStPoint = new Point2D.Double(coodinateX, coodinateY);
    return branchDwStPoint;
  }

  private Boolean correct(Integer parent, Integer child){
    Integer pNum = 0;
    Integer cNum = 0;
    for(final NodeModel aNodeModel : listNode){
      if(aNodeModel.getId().equals(parent)){pNum = aNodeModel.getNum();}
      if(aNodeModel.getId().equals(child)){cNum = aNodeModel.getNum();}
    }
    if(pNum > cNum){return true;}
    return false;
  }

  /*public void measurementMaxWC(String aNodeName){
    if(maxWC < aNodeName.length()){maxWC = aNodeName.length();}
    return;
  }*/

  private void lineUpNode(){
    Integer aDepst = 0;
    Integer tempDepst = 0;
    Integer tempNameCount = 0;
    double tempNodeWidth = 0.0;
    Point2D.Double tempPoint = new Point2D.Double(0.0, 0.0);
    Integer aId = 0;
    for(final NodeModel aNodeModel : listNode){
      aDepst = aNodeModel.getDepst();
      if(aDepst == 0){this.renewalPoint(aNodeModel, tempPoint);}
      else if(aDepst > tempDepst){this.lateralMovement(aNodeModel, tempPoint, tempNodeWidth);}
      else if(aDepst == tempDepst){this.verticalMovement(aNodeModel, tempPoint);}
      else if(aDepst < tempDepst){this.returnMovement(aNodeModel, tempPoint, aDepst);}
      tempDepst = aDepst;
      tempPoint.setLocation(aNodeModel.getFinPoint());
      tempNameCount = aNodeModel.getNameCount();
      tempNodeWidth = aNodeModel.nodeWidth();
    }
    return;
  }

  private void renewalPoint(NodeModel aNodeModel, Point2D.Double aPrePoint){
    double y = aPrePoint.getY() + aNodeModel.getMarginHigh() + aNodeModel.nodeHigh();
    Point2D.Double newPoint = new Point2D.Double(aNodeModel.getSegument(), y);
    aNodeModel.setFinPoint(newPoint);
    return;
  }

  private void lateralMovement(NodeModel aNodeModel, Point2D.Double aPrePoint, double aTempNodeWidth){
    double x = aPrePoint.getX() + aTempNodeWidth + aNodeModel.getMargin();
    Point2D.Double newPoint = new Point2D.Double(x, aPrePoint.getY());
    aNodeModel.setFinPoint(newPoint);
    return;
  }

  private void verticalMovement(NodeModel aNodeModel, Point2D.Double aPrePoint){
    double y = aPrePoint.getY() + aNodeModel.getMarginHigh() + aNodeModel.nodeHigh();
    Point2D.Double newPoint = new Point2D.Double(aPrePoint.getX(), y);
    aNodeModel.setFinPoint(newPoint);
    return;
  }

  private void returnMovement(NodeModel aNodeModel, Point2D.Double aPrePoint, Integer aDepst){
    double x = 0.0;
    double y = aPrePoint.getY() + aNodeModel.getMarginHigh() + aNodeModel.nodeHigh();
    for(final NodeModel aModel : listNode){
      if(aModel.getFinPoint().equals(aModel.getStPoint())){
        break;
      }else if(aModel.getDepst() == aDepst){
        x = aModel.getFinPoint().getX();
      }
    }
    Point2D.Double newPoint = new Point2D.Double(x, y);
    aNodeModel.setFinPoint(newPoint);
    return;
  }

  private void lineUpBranch(){
    for(final BranchModel aBranchModel : listBranch){
      this.upMovement(aBranchModel);
      this.dwMovement(aBranchModel);
    }
    return;
  }

  private void upMovement(BranchModel aBranchModel){
    double x = 0.0;
    double y = 0.0;
    for(final NodeModel aNodeModel : listNode){
      if(aBranchModel.getParentNum().equals(aNodeModel.getId())){
        x = aNodeModel.getFinPoint().getX() + aNodeModel.nodeWidth();
        y = aNodeModel.getFinPoint().getY() + aNodeModel.nodeHigh()/2;
        Point2D.Double newPoint = new Point2D.Double(x, y);
        aBranchModel.setUpFinPoint(newPoint);
      }
    }
    return;
  }

  private void dwMovement(BranchModel aBranchModel){
    double x = 0.0;
    double y = 0.0;
    for(final NodeModel aNodeModel : listNode){
      if(aBranchModel.getChildNum().equals(aNodeModel.getId())){
        x = aNodeModel.getFinPoint().getX();
        y = aNodeModel.getFinPoint().getY() + aNodeModel.nodeHigh()/2;
        break;
      }
    }
    Point2D.Double newPoint = new Point2D.Double(x, y);
    aBranchModel.setDwFinPoint(newPoint);
    return;
  }

/*  public void updateByDrag(Point aPoint){
    if(!isStop) return;
    NodeModel.updateByDrag(aPoint);
    BranchModel.updateByDrag(aPoint);
    this.updateCurrent();
    return;
  }*/


  public List<NodeModel> getListNode(){
    return listNode;
  }

  public List<BranchModel> getListBranch(){
    return listBranch;
  }

}
