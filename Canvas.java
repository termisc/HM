package hashContextTest;

import java.awt.*; //描画用ライブラリ(クラスの入ったパッケージ)を利用する
import javax.swing.*; //GUI用ライブラリを利用する
 
public class Canvas extends JPanel { //パネルのクラスJPanelを継承してクラスを作る
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int x = 0;
	int y = 0;
	
	public void setPos(int _x , int _y) {
		x = _x;
		y = _y;
	}
	
	public void paintComponent(Graphics g) { //描画するメソッドをオーバーライドする
		g.setColor(new Color(255, 180, 99)); //ペンの色を設定する
		g.fillOval(x, y, 10, 10); //楕円を描く
	}
 
	public static void main(String[] args) {
		JFrame app = new JFrame(); //窓（フレーム）を表示するクラスJFrameのインスタンスを生成する
		app.add(new Canvas()); //パネルを窓にはめる
		app.setSize(400, 300); //窓の大きさを設定する
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //窓を閉じた（Xボタンを押した）時の動作（プログラムを終了する）を設定する．
		app.setVisible(true); //窓を可視化する
	}
}