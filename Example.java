package forest;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import javax.swing.JFrame;
import mvc.Model;
import mvc.View;

/**
 * 樹状整列の例題クラス：使い方の典型を示すのが目的のプログラムです。<br>
 * Makefileを用いた実行方法は以下の通りです。<br>
 * $ make tree  # 木を整列描画<br>
 * $ make forest  # 森を整列描画<br>
 * $ make semilattice  # 亜格子状の森を整列描画<br>
 */
public class Example extends Object
{
	/**
	 * 第1引数で樹状整列データファイルを受け取って樹状整列を実行します。<br>
	 * $ java -Dfile.encoding=UTF-8 -Xmx512m -Xss1024k -jar forest.jar resource/data/tree.txt<br>
	 * $ java -Dfile.encoding=UTF-8 -Xmx512m -Xss1024k -jar forest.jar resource/data/forest.txt<br>
	 * $ java -Dfile.encoding=UTF-8 -Xmx512m -Xss1024k -jar forest.jar resource/data/semilattice.txt<br>
	 * @param arguments 樹状整列データファイルを第1引数とする引数文字列群
	 */
	public static void main(String[] arguments)
	{
		//System.out.println("あっチョンプリケ");
		if(arguments != null){
            long start = System.currentTimeMillis();
			// 引数が無い（樹状整列データファイルの在り処がわからない）をチェックする。
			if (arguments.length < 1)
			{
				System.err.println("There are too few arguments.");
				System.exit(1);
			}
			//System.out.println("あっチョンプリケ");
			// 第1引数で指定された樹状整列データファイルの存在をチェックする。
			File aFile = new File(arguments[0]);
			if (!(aFile.exists()))
			{
				System.err.println("'" + aFile + "' does not exist.");
				System.exit(1);
			}
			//aFileReader.close();
			//System.out.println(aBufferedReader);
			try{
				FileReader aFileReader = new FileReader(aFile);
				BufferedReader aBuffer = new BufferedReader(aFileReader);
				ForestModel aModel = new ForestModel(aBuffer);
				ForestView aView = new ForestView(aModel, new ForestController());
                long end = System.currentTimeMillis();
                System.out.println((end - start)  + "ms");
				Thread aThread = new Thread(aView);
				aThread.start();

				// ウィンドウを生成して開く。
				JFrame aWindow = new JFrame(aFile.getName());
				aWindow.getContentPane().add(aView);
				aWindow.setMinimumSize(new Dimension(400, 300));
				aWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				aWindow.setSize(800, 600);
				aWindow.setLocationRelativeTo(null);
				aWindow.setVisible(true);
			}catch (IOException e){
      	System.out.println(e);
    	}
		}else{
			System.out.println("arguments is nothing.");
			System.exit(1);
		}



		/**********

		 // MVCを作成する。
		ForestModel aModel = new ForestModel(aFile);
		ForestView aView = new ForestView(aModel);



		// 樹状整列のアニメーションを行う。
		aModel.animate();

		**********/
		return;
	}
}
