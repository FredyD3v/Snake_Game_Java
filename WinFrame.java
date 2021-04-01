import javax.swing.JFrame;

public class WinFrame extends JFrame{
	/*Creates The Window Frame For Our Game*/
	WinFrame(){
		/*Gives the frame some display properties*/
		this.add(new WinPanel());
		this.setTitle("FredyD3v Snake");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.pack();
	}
}
