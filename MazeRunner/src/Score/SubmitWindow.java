package Score;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.media.opengl.GLAutoDrawable;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import Main.MazeRunner;
import Main.Menu;
import Player.UserInput;

public class SubmitWindow extends JFrame {
	private JButton submit2;
	private JTextField Name;
	private JLabel SN;
	private boolean submit = false;

	public SubmitWindow(Score score){
		Container pane = this.getContentPane();
		BoxLayout layout = new BoxLayout(pane,BoxLayout.Y_AXIS);
		setLayout(layout);
		SN = new JLabel("Enter your name: ");
		pane.add(SN);
		Name = new JTextField();
		pane.add(Name);
		submit2 = new JButton("Submit score");
		pane.add(submit2);

		eventsubmit s1 = new eventsubmit(score);
		submit2.addActionListener(s1);
	}
	public class eventsubmit implements ActionListener {
		private Score score;

		public eventsubmit(Score score)
		{
			this.score = score;
		}

		public void actionPerformed(ActionEvent s1){
			String name = Name.getText();
			score.submitScore(name);
			dispose();
			submit = true;
		}
	}
	
	public boolean getSubmit()
	{
		return this.submit;
	}
}
