package org.karamja.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.SpinnerListModel;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JSpinner;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JRadioButton;

public class Editor extends JFrame {

	static BufferedImage img = null;
	static BufferedImage selectedImg = null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Editor frame = new Editor();
					frame.setTitle("Karamja Interface Generator");
					frame.setVisible(true);
					//frame.
					
				    img = ImageIO.read(new File("2823.png"));
				    
				    
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	int boxHeight = 0;
	int boxWidth = 0;
	Point buttonCurrentPoint = null;
	Point buttonEndPoint = null;
	private JTextField buttonEndX;
	private JTextField buttonEndY;
	private ArrayList<String> buttons = new ArrayList<>();
	Point buttonStartPoint = null;
	private JTextField buttonStartX;
	private boolean buttonStartXActive = false;
	private JTextField buttonStartY;
	private JPanel contentPane;
	Point currentPoint = null;
	ArrayList<Button> displayButtons = new ArrayList<Button>();
	
	ArrayList<Image> displayImages = new ArrayList<Image>();
	private boolean drawBorder = false;
	private ArrayList<String> drawStrings = new ArrayList<>();
	private ArrayList<DrawString> displayDrawStrings = new ArrayList<>();
	Point endPoint = null;
	private JTextField height;
	private ArrayList<String> images = new ArrayList<>();
	private int mouseX = 0;
	private int mouseY = 0;
	

	Point startPoint = null;
	
	private JTextField startX;
	private boolean startXActive = false, startYActive = false, widthActive = false,
			heightActive = false, drawingBox = false, drawingButton = false, drawingImage = false, collectingStringX = false;
	private JTextField startY;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField width;
	int x = 0;
	int y = 0;
	/**
	 * Create the frame.
	 * I guess the eclipse editor put everything in here D: Lol
	 */
	public Editor() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 950, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(2, 2));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.WEST);
		
		final JTextPane textPane = new JTextPane();
		contentPane.add(textPane, BorderLayout.SOUTH);
		
		final JPanel display = new JPanel(){

			@Override
			public Color getBackground(){
				return Color.RED;
			}
			
			@Override
            public Dimension getPreferredSize() {
                return new Dimension(512, 334);
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                
                if (startPoint != null && currentPoint != null) {
	                g.setColor(Color.black);
	                int x = Math.min(startPoint.x, currentPoint.x);
	                int y = Math.min(startPoint.y, currentPoint.y);
	                int width = Math.abs(startPoint.x - currentPoint.x);
	                int height = Math.abs(startPoint.y - currentPoint.y);
	                
	                g.drawRect(x, y, width, height);
	                g.fillRect(x, y, width, height);
                }
                g.setColor(Color.BLUE);
                for(int i = 0; i < displayButtons.size(); i++){
	                int x = displayButtons.get(i).getStartX();
	                int y = displayButtons.get(i).getStartY();
	                int endX = Math.abs(displayButtons.get(i).getStartX() - displayButtons.get(i).getEndX());
	                int endY = Math.abs(displayButtons.get(i).getStartY() - displayButtons.get(i).getEndY());
	                
	                g.drawRect(x, y, endX, endY);
	                g.fillRect(x, y, endX, endY);
                }
                if (buttonStartPoint != null && buttonCurrentPoint != null) {
	                int x = Math.min(buttonStartPoint.x, buttonCurrentPoint.x);
	                int y = Math.min(buttonStartPoint.y, buttonCurrentPoint.y);
	                int width = Math.abs(buttonStartPoint.x - buttonCurrentPoint.x);
	                int height = Math.abs(buttonStartPoint.y - buttonCurrentPoint.y);
	                
	                g.drawRect(x, y, width, height);
	                g.fillRect(x, y, width, height);
                }
                
                g.setColor(Color.WHITE);
                for(int i = 0; i < displayDrawStrings.size(); i++){
                	g.drawString(displayDrawStrings.get(i).getString(), displayDrawStrings.get(i).getX(), displayDrawStrings.get(i).getY());
                }
                for(int i = 0; i < displayImages.size(); i++){
                	g.drawImage(displayImages.get(i).getImage(), displayImages.get(i).getStartX(), displayImages.get(i).getStartY(), this);
                }
                
                if(selectedImg != null){
                	g.drawImage(selectedImg, mouseX, mouseY, this);
                }
                
                Graphics gfx = img.getGraphics();
                g.drawImage(img, 0, 334-4, this);
            }

		};
		contentPane.add(display, BorderLayout.CENTER);
		
		textField = new JTextField();
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Generate Code");
		
		JLabel lblNewLabel = new JLabel("Box Start X");
		
		final JLabel lblMethodName = new JLabel("Method Name");
		
		JLabel lblBoxStartY = new JLabel("Box Start Y");
		
		JLabel lblBoxEndX = new JLabel("Box Width");
		
		JLabel lblBoxEndY = new JLabel("Box Height");
		
		startX = new JTextField();
		startX.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				drawingBox = true;
				startXActive = true;
			}
            @Override
            public void focusLost(FocusEvent e) {
            	drawingBox = false;
				startXActive = false;
            }
		});
		startX.setColumns(10);
		
		startY = new JTextField();
		startY.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				startYActive = true;
			}
            @Override
            public void focusLost(FocusEvent e) {
				startYActive = false;
            }
		});
		startY.setColumns(10);
		
		width = new JTextField();
		width.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				widthActive = true;
			}
            @Override
            public void focusLost(FocusEvent e) {
				widthActive = false;
            }
		});
		width.setColumns(10);
		
		height = new JTextField();
		height.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				heightActive = true;
			}
            @Override
            public void focusLost(FocusEvent e) {
            	heightActive = false;
            }
		});
		height.setColumns(10);
		
		final JCheckBox chckbxBoxBorder = new JCheckBox("Box Border?");
		chckbxBoxBorder.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if(chckbxBoxBorder.isSelected()){
					drawBorder = true;
				} else {
					drawBorder = false;
				}
			}
		});
		
		JButton btnAddMouseButton = new JButton("Add Mouse Button");
		btnAddMouseButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int x = Integer.parseInt(buttonStartX.getText());
				int y = Integer.parseInt(buttonStartY.getText());
				int endX = Integer.parseInt(buttonEndX.getText());
				int endY = Integer.parseInt(buttonEndY.getText());
				String s =
						"if ((super.mouseX > " + x + " || super.mouseX < " + endX + ") && (super.mouseY > " + y + " || super.mouseY < " + endY + ")){//handle click}\n"
				;
				buttons.add(s);
				displayButtons.add(new Button(x, y, endX, endY));
				buttonStartX.setText("");
				buttonStartY.setText("");
				buttonEndX.setText("");
				buttonEndY.setText("");
				
			}
		});
		
		buttonStartX = new JTextField();
		buttonStartX.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				buttonStartXActive = true;
				drawingButton = true;
			}
            @Override
            public void focusLost(FocusEvent e) {
            	buttonStartXActive = false;
            	drawingButton = false;
            }
		});
		buttonStartX.setColumns(10);
		
		buttonStartY = new JTextField();
		buttonStartY.setColumns(10);
		
		buttonEndX = new JTextField();
		buttonEndX.setColumns(10);
		
		buttonEndY = new JTextField();
		buttonEndY.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Starting X");
		
		JLabel lblStartingY = new JLabel("Starting Y");
		
		JLabel lblEndX = new JLabel("End X");
		
		JLabel lblEndY = new JLabel("End Y");
		final String fileNames[] = new String[5000];
		final JSpinner spinner = new JSpinner(new SpinnerListModel(fileNames));
		spinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				try {
					//System.out.println(spinner.getValue().toString());
					selectedImg = ImageIO.read(new File("." + File.separator + "imgs" +  File.separator + spinner.getValue().toString()));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		spinner.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//selectedImg = spinner.getValue();
			}
		});
		
		JLabel lblSelectImage = new JLabel("Select Image");
		
		JButton btnNewButton_1 = new JButton("Reload");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String s = "." + File.separator + "imgs";
				File f = new File(s);
				//String fileNames[] = new String[200];
				int i = 0;
				for(File file : f.listFiles()){
					fileNames[i] = file.getName();
				    System.out.println(file.getName());
				   i++;
				}
			}
		});
		
		JButton btnSubmitImage = new JButton("Submit Image");
		btnSubmitImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int x = mouseX;
				int y = mouseY;
				//int endX = Integer.parseInt(buttonEndX.getText());
				//int endY = Integer.parseInt(buttonEndY.getText());
				String s =
						"gameGraphics.drawPicture(" + mouseX + "," + mouseY + ", SPRITE_MEDIA_START + " + spinner.getValue().toString().replaceAll(".png", "") +");\n"
				;
				images.add(s);
				displayImages.add(new Image(selectedImg, x, y));
				
			}
		});
		
		final JRadioButton rdbtnNewRadioButton = new JRadioButton("Editing");
		rdbtnNewRadioButton.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if(rdbtnNewRadioButton.isSelected()){
					drawingImage = true;
				} else {
					drawingImage = false;
				}
			}
		});
		
		JButton btnSubmitString = new JButton("Submit String");
		btnSubmitString.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				displayDrawStrings.add(new DrawString(textField_3.getText(), Integer.parseInt(textField_1.getText()),Integer.parseInt(textField_2.getText())));
				String s ="gameGraphics.drawString(" + textField_3.getText() + ", " + textField_1.getText() + "," + textField_2.getText() + ", 1, 0xffffff);\n"
				;
				drawStrings.add(s);
				
				textField_3.setText("");
				textField_2.setText("");
				textField_1.setText("");
				display.repaint();
			}
		});
		
		textField_1 = new JTextField();
		textField_1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				collectingStringX = true;
			}
			@Override
			public void focusLost(FocusEvent e) {
				collectingStringX = false;
			}
		});
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		
		JLabel lblStringX = new JLabel("String X");
		
		JLabel lblStringY = new JLabel("String Y");
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		
		JLabel lblString = new JLabel("String");
		GroupLayout groupPanel = new GroupLayout(panel);
		groupPanel.setHorizontalGroup(
			groupPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupPanel.createSequentialGroup()
					.addGroup(groupPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupPanel.createSequentialGroup()
							.addGroup(groupPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(groupPanel.createSequentialGroup()
									.addContainerGap()
									.addGroup(groupPanel.createParallelGroup(Alignment.LEADING)
										.addComponent(lblMethodName)
										.addComponent(lblNewLabel)
										.addComponent(lblBoxStartY)
										.addComponent(lblBoxEndX)
										.addComponent(lblBoxEndY)
										.addComponent(lblNewLabel_1)
										.addComponent(lblStartingY)
										.addComponent(lblEndX)
										.addComponent(lblEndY)
										.addComponent(lblSelectImage)
										.addComponent(rdbtnNewRadioButton))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(groupPanel.createParallelGroup(Alignment.LEADING)
										.addComponent(height, 97, 97, 97)
										.addComponent(width, 97, 97, 97)
										.addComponent(startY, 97, 97, 97)
										.addComponent(startX, 97, 97, 97)
										.addComponent(textField, 97, 97, 97)
										.addComponent(buttonStartX, 97, 97, 97)
										.addComponent(buttonStartY, 97, 97, 97)
										.addComponent(buttonEndX, 97, 97, 97)
										.addComponent(buttonEndY, 97, 97, 97)
										.addComponent(btnAddMouseButton, 0, 0, Short.MAX_VALUE)
										.addComponent(spinner, GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
										.addComponent(btnSubmitImage)))
								.addGroup(groupPanel.createSequentialGroup()
									.addGap(84)
									.addComponent(btnNewButton)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblStringX)
								.addComponent(lblStringY)))
						.addGroup(groupPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblString)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupPanel.createParallelGroup(Alignment.TRAILING)
							.addGroup(groupPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(chckbxBoxBorder)
								.addComponent(btnNewButton_1))
							.addComponent(btnSubmitString))
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		groupPanel.setVerticalGroup(
			groupPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(groupPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblMethodName)
						.addComponent(chckbxBoxBorder))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(startX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblBoxStartY)
						.addComponent(startY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblBoxEndX)
						.addComponent(width, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblStringX))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblBoxEndY)
						.addComponent(height, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblStringY))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblString))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(buttonStartX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1)
						.addComponent(btnSubmitString))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(buttonStartY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblStartingY))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(buttonEndX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblEndX))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(buttonEndY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblEndY))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnAddMouseButton)
					.addGap(18)
					.addGroup(groupPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblSelectImage)
						.addComponent(btnNewButton_1))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnSubmitImage)
						.addComponent(rdbtnNewRadioButton))
					.addPreferredGap(ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
					.addComponent(btnNewButton)
					.addContainerGap())
		);
		panel.setLayout(groupPanel);
		
		
		Rectangle box;
		display.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if(drawingBox)
					currentPoint = e.getPoint();
				if(drawingButton)
					buttonCurrentPoint = e.getPoint();
				if(drawingImage){
					mouseX = e.getX();
					mouseY = e.getY();
				}
				//if(drawingBox){
					display.repaint();
				//}
			}
		});
		display.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(buttonStartXActive) {
					buttonStartX.setText(Integer.toString(e.getX()));
				} else if(startXActive){
					startX.setText(Integer.toString(e.getX()));
				} else if(startYActive){
					startY.setText(Integer.toString(e.getY()));
				} else if(widthActive){
					
				} else if(heightActive){
					
				} else if(collectingStringX){
					textField_1.setText(Integer.toString(e.getX()));
					textField_2.setText(Integer.toString(e.getY()));
				}
				if(spinner.getValue() != null){
					display.repaint();
				}
			}
			@Override
			public void mousePressed(MouseEvent e) {
				if(drawingBox){
					startPoint = e.getPoint();
					startX.setText(Integer.toString(startPoint.x));
					startY.setText(Integer.toString(startPoint.y));
				} else if (drawingButton){
					buttonStartPoint = e.getPoint();
					buttonStartX.setText(Integer.toString(buttonStartPoint.x));
					buttonStartY.setText(Integer.toString(buttonStartPoint.y));
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
		        endPoint = e.getPoint();
				buttonEndPoint = e.getPoint();
		        if(drawingBox){
		        	width.setText(Integer.toString(Math.abs(startPoint.x - currentPoint.x)));
                	height.setText(Integer.toString(Math.abs(startPoint.y - currentPoint.y)));
		        //currentPoint = null;
		        } else if (drawingButton){
		        	buttonEndX.setText(Integer.toString(buttonCurrentPoint.x));
		        	buttonEndY.setText(Integer.toString(buttonCurrentPoint.y));
		        }
		        display.repaint();
			}
		});
		
		
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				StringBuffer generated = new StringBuffer(textField.getText() + "(){" + "\n"
			+	"gameGraphics.drawBox(" + startX.getText()+","+startY.getText()+ ","+ width.getText() + "," + height.getText()+ ",0);\n");
				if(drawBorder){
					generated.append("gameGraphics.drawBoxEdge(" + startX.getText()+","+startY.getText()+ ","+ width.getText() + "," + height.getText()+ ", 0xffffff);\n");
				}
				
				for(int i = 0; i < buttons.size(); i++){
					generated.append(buttons.get(i));
				}
				for(int i = 0; i < images.size(); i++){
					generated.append(images.get(i));
				}
				for(int i = 0; i < drawStrings.size(); i++){
					generated.append(drawStrings.get(i));
				}
				
				generated.append("\n }");
				String g = generated.toString();
				textPane.setText(g);
				
				x = Integer.parseInt(startX.getText());
				y = Integer.parseInt(startY.getText());
				boxWidth = Integer.parseInt(width.getText());
				boxHeight =Integer.parseInt(height.getText());
				
			}
		});

	}
}
