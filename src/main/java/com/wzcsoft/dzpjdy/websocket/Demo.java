package com.wzcsoft.dzpjdy.websocket;

import com.wzcsoft.dzpjdy.websocket.Vapi;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class Demo {
	Vapi V = new Vapi();

	public static Demo vguangSample = null;
	private JFrame frame;
	private JTextArea decodeTextArea;
	private JLabel lblDeviceStatus;
	private JCheckBox QRCODE;
	private JCheckBox EAN8;
	private JCheckBox EAN13;
	private JCheckBox CODE39;
	private JCheckBox CODE93;
	private JCheckBox CODE128;
	private JCheckBox PDF417;
	private JCheckBox DATAMATRIX;
	private JCheckBox ITF;
	private JCheckBox UPCE;
	private JCheckBox UPCA;
	private JCheckBox INDUSTRIAL25;
	boolean decodestate = false;
	private JButton buttonBegin;


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					vguangSample = new Demo();
					vguangSample.frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public Demo() throws UnsupportedEncodingException {
		//初始化控件
		initialize();
		//打开设备
		//V.vbarOpen("1",1);   //打开设备
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 742, 494);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		buttonBegin = new JButton("打开设备");
		buttonBegin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("------------------------------------------------");
				System.out.println(arg0);
				//打开设备
				if(V.vbarOpen())
				{
					JOptionPane.showMessageDialog(null, "连接设备成功 ", "连接设备状态", JOptionPane.ERROR_MESSAGE);
					V.vbarAddSymbolType(1, true);
					QRCODE.setSelected(true);
					deviceThread();
					decodestate = true;
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					buttonBegin.setEnabled(false);


				}
				else
				{
					JOptionPane.showMessageDialog(null, "连接设备失败 ", "连接设备状态", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		buttonBegin.setBounds(113, 413, 93, 23);
		frame.getContentPane().add(buttonBegin);



		JButton buttonQuit = new JButton("退出程序");
		buttonQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				decodestate = false;
				deviceThreadstop();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.exit(0);
			}
		});
		buttonQuit.setBounds(210, 413, 93, 23);
		frame.getContentPane().add(buttonQuit);

		decodeTextArea = new JTextArea();
		decodeTextArea.setRows(5);
		decodeTextArea.setLineWrap(true);
		decodeTextArea.setColumns(10);
		decodeTextArea.setBounds(113, 78, 347, 211);
		frame.getContentPane().add(decodeTextArea);

		JLabel label = new JLabel("解码结果：");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setFont(new Font("宋体", Font.BOLD, 14));
		label.setBounds(10, 78, 93, 23);
		frame.getContentPane().add(label);




		QRCODE = new JCheckBox("QRCODE");
		QRCODE.setBounds(567, 80, 103, 23);
		frame.getContentPane().add(QRCODE);
		QRCODE.setEnabled(false);
		QRCODE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				V.vbarAddSymbolType(1,true);   // QRCODE 1
				System.out.println("add code");
			}
		});



		CODE39 = new JCheckBox("CODE39");
		CODE39.setBounds(567, 100, 103, 23);
		frame.getContentPane().add(CODE39);
		CODE39.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(CODE39.isSelected())
				{
					V.vbarAddSymbolType(5,true);
				}
				else
				{
					V.vbarAddSymbolType(5,false);
				}

			}
		});

		CODE93 = new JCheckBox("CODE93");
		CODE93.setBounds(567, 120, 103, 23);
		frame.getContentPane().add(CODE93);
		CODE93.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(CODE93.isSelected())
				{
					V.vbarAddSymbolType(6,true); //CODE93 6
				}
				else
				{
					V.vbarAddSymbolType(6,false); //CODE93 6
				}
			}
		});

		PDF417 = new JCheckBox("PDF417");
		PDF417.setBounds(567, 140, 103, 23);
		frame.getContentPane().add(PDF417);
		PDF417.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(PDF417.isSelected())
				{
					V.vbarAddSymbolType(10,true);   //PDF417 10
				}
				else
				{
					V.vbarAddSymbolType(10,false);   //PDF417 10
				}

			}
		});

		EAN8 = new JCheckBox("EAN8");
		EAN8.setBounds(567, 160, 103, 23);
		frame.getContentPane().add(EAN8);
		EAN8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(EAN8.isSelected())
				{
					V.vbarAddSymbolType(2,true); //EAN8  2
				}
				else
				{
					V.vbarAddSymbolType(2,false); //EAN8  2
				}

			}
		});

		EAN13 = new JCheckBox("EAN13");
		EAN13.setBounds(567, 180, 103, 23);
		frame.getContentPane().add(EAN13);
		EAN13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(EAN13.isSelected())
				{
					V.vbarAddSymbolType(3,true); //EAN13 3
				}
				else
				{
					V.vbarAddSymbolType(3,false); //EAN13 3
				}

			}
		});

		UPCE = new JCheckBox("UPCE");
		UPCE.setBounds(567, 200, 103, 23);
		frame.getContentPane().add(UPCE);
		UPCE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(UPCE.isSelected())
				{
					V.vbarAddSymbolType(14,true);  // UPCE 14
				}
				else
				{
					V.vbarAddSymbolType(14,false);  // UPCE 14
				}

			}
		});

		UPCA = new JCheckBox("UPCA");
		UPCA.setBounds(567, 220, 103, 23);
		frame.getContentPane().add(UPCA);
		UPCA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(UPCA.isSelected())
				{
					V.vbarAddSymbolType(15,true); //UPCA 15
				}
				else
				{
					V.vbarAddSymbolType(15,false); //UPCA 15
				}

			}
		});



		ITF = new JCheckBox("ITF");
		ITF.setBounds(567, 240, 103, 23);
		frame.getContentPane().add(ITF);
		ITF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ITF.isSelected())
				{
					V.vbarAddSymbolType(12,true);  //ITF  12
				}
				else
				{
					V.vbarAddSymbolType(12,false);  //ITF  12
				}
			}
		});

		CODE128 = new JCheckBox("CODE128");
		CODE128.setBounds(567, 260, 103, 23);
		frame.getContentPane().add(CODE128);
		CODE128.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(CODE128.isSelected())
				{
					V.vbarAddSymbolType(7,true);  //CODE128 7
				}
				else
				{
					V.vbarAddSymbolType(7,false);  //CODE128 7
				}
			}
		});



		DATAMATRIX = new JCheckBox("DATAMATRIX");
		DATAMATRIX.setBounds(567, 280, 103, 23);
		frame.getContentPane().add(DATAMATRIX);
		DATAMATRIX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(DATAMATRIX.isSelected())
				{
					V.vbarAddSymbolType(11,true);  //DATAMATRIX  11
				}
				else
				{
					V.vbarAddSymbolType(11,false);  //DATAMATRIX  11
				}

			}
		});

		INDUSTRIAL25 = new JCheckBox("INDUSTRIAL25");
		INDUSTRIAL25.setBounds(567, 300, 113, 23);
		frame.getContentPane().add(INDUSTRIAL25);
		INDUSTRIAL25.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(INDUSTRIAL25.isSelected())
				{
					V.vbarAddSymbolType(11,true);  //INDUSTRIAL25  16
				}
				else
				{
					V.vbarAddSymbolType(11,false);  //INDUSTRIAL25  16
				}

			}
		});

		JLabel label_1 = new JLabel("码制：");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("宋体", Font.BOLD, 14));
		label_1.setBounds(507, 80, 54, 23);
		frame.getContentPane().add(label_1);

		JButton buttonBeep1 = new JButton("响一声");
		buttonBeep1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				V.vbarBeep();
			}
		});
		buttonBeep1.setBounds(265, 380, 93, 23);
		frame.getContentPane().add(buttonBeep1);

		JLabel lblNewLabel_5 = new JLabel("设备状态：");
		lblNewLabel_5.setFont(new Font("宋体", Font.BOLD, 14));
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_5.setBounds(10, 324, 93, 23);
		frame.getContentPane().add(lblNewLabel_5);

		lblDeviceStatus = new JLabel("设备未连接");
		lblDeviceStatus.setBounds(122, 324, 93, 23);
		lblDeviceStatus.setEnabled(false);
		frame.getContentPane().add(lblDeviceStatus);

		JButton buttonLightOn = new JButton("开灯");
		buttonLightOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				V.vbarBacklight(true);
			}
		});
		buttonLightOn.setBounds(113, 380, 66, 23);
		frame.getContentPane().add(buttonLightOn);

		JButton buttonLightOff = new JButton("关灯");
		buttonLightOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				V.vbarBacklight(false);
			}
		});
		buttonLightOff.setBounds(189, 380, 66, 23);
		frame.getContentPane().add(buttonLightOff);
	}

	boolean stateflag = false;
	class Devices implements Runnable{
		public void run(){
			while(decodestate)
			{
				if(V.vbarIsConnect())
				{
					if(!stateflag)
					{
						lblDeviceStatus.setText("设备已连接");
						lblDeviceStatus.setEnabled(true);
						V.vbarBacklight(true);
						stateflag = true;
					}
					String decode = null;
					try {
						decode = V.vbarScan();

					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(decode != null)
					{
						V.vbarBeep();
						try {
							Thread.sleep(20);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						decodeTextArea.setText(decode);
					}
				}
				else
				{
					if(stateflag)
					{
						lblDeviceStatus.setText("设备未连接");
						stateflag = false;
					}

				}
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	Devices devices = new Devices();
	Thread device = new Thread(devices);
	public void deviceThread(){
		device.start();
	}
	public void deviceThreadstop()
	{
		if(device.isAlive())
		{
			device.stop();
		}
	}


}
	