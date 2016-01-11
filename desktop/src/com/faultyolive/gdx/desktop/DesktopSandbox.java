package com.faultyolive.gdx.desktop;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.faultyolive.gdx.Sample;

public class DesktopSandbox extends JFrame {
	public DesktopSandbox () throws HeadlessException {
		super("libgdx Sandbox");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(new SampleList());
		pack();
		setSize(getWidth(), 300);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * Runs the {@link Sample} with the given name.
	 *
	 * @param name the name of a sample
	 * @return {@code true} if the sample was found and run, {@code false} otherwise
	 */
	public static boolean runSample(String name) {
		boolean useGL30 = false;
		Sample test = Sample.create(name);
		if (test == null) {
			return false;
		}
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 640;
		config.height = 480;
		config.title = name;
		config.forceExit = false;
		if (useGL30) {
			config.useGL30 = true;
			ShaderProgram.prependVertexCode = "#version 140\n#define varying out\n#define attribute in\n";
			ShaderProgram.prependFragmentCode = "#version 140\n#define varying in\n#define texture2D texture\n#define gl_FragColor fragColor\nout vec4 fragColor;\n";
		} else {
			config.useGL30 = false;
			ShaderProgram.prependVertexCode = "";
			ShaderProgram.prependFragmentCode = "";
		}
		new LwjglApplication(test, config);
		return true;
	}

	class SampleList extends JPanel {
		public SampleList () {
			setLayout(new BorderLayout());

			final JButton button = new JButton("Run Sample");

			final JList<String> list = new JList<String>(Sample.all());
			JScrollPane pane = new JScrollPane(list);

			DefaultListSelectionModel m = new DefaultListSelectionModel();
			m.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			m.setLeadAnchorNotificationEnabled(false);
			list.setSelectionModel(m);

			list.addMouseListener(new MouseAdapter() {
				public void mouseClicked (MouseEvent event) {
					if (event.getClickCount() == 2) button.doClick();
				}
			});

			list.addKeyListener(new KeyAdapter() {
				public void keyPressed (KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) button.doClick();
				}
			});

			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed (ActionEvent e) {
					String testName = list.getSelectedValue();
					dispose();
					runSample(testName);
				}
			});

			add(pane, BorderLayout.CENTER);
			add(button, BorderLayout.SOUTH);
		}
	}

	/**
	 * Runs a sample.
	 *
	 * If no arguments are provided on the command line, shows a list of samples to choose from.
	 * If an argument is present, the sample with that name will immediately be run.
	 *
	 * @param argv command line arguments
	 */
	public static void main (String[] argv) throws Exception {
		if (argv.length > 0) {
			if (runSample(argv[0])) {
				return;
			}
		}
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		new DesktopSandbox();
	}
}