//����������� ��� �����������
import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.List;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.Duration;
import java.time.Instant;

public class Main { //������� ����� ���������

	static int blocksCount; //���������� ��� �������� ���������� ������
	static String selectedBlock = null; //���������� ��� �������� �������� ���������� �����
	static String selectedFile = null; //���������� ��� �������� �������� ���������� �����
	static String selectedSort = null; //���������� ��� �������� ������� ��������� ����������
	static long[] elements; //���������� ��� �������� ��������� �����
	static String[] sortsArray = new String[] {"���������� �������","�������","����� �������","������ (������� 5)","������� ����������"}; //������ � ���������� ����������
	static int[] numbersCountArray = new int[] {100,500,1000,5000,10000,50000}; //������ � ��������� ������
	public static void main(String[] args) { //������� ����� ��������� aka ����� ����� � ���������
		//�������� ����
		Frame frameMain = new Frame();
		Frame frameSortAll = new Frame();
		Frame frameTable = new Frame();
		//�������� � ������� ���������� ��� ����
		frameSortAll.setVisible(false);
		frameSortAll.setEnabled(false);
		frameMain.setEnabled(false);
		frameMain.setVisible(false);
		frameTable.setVisible(false);
		frameTable.setEnabled(false);
		//�������� ���������� ������������ ����������
		Label blocksCountLabel = new Label();
		TextField blocksCountTextField = new TextField();
		Button createBlocksButton = new Button();
		Choice blockSelectChoice = new Choice();
		Choice fileSelectChoice = new Choice();
		Choice sortSelectChoice = new Choice();
		Button sortFileButton = new Button();
		Label timeElapsedLabel = new Label();
		Button sortAllButton = new Button();
		Label inputFileLabel = new Label();
		Label outputFileLabel = new Label();
		List inputFileList = new List();
		List outputFileList = new List();
		Button backButton = new Button();
		Button autoSortButton = new Button();
		Label allSortProgressLabel = new Label();
		Button goToTableButton = new Button();
		Button goToPlotButton = new Button();
		Label[][] tableLabels = new Label[7][6];
		GridBagLayout layoutManagerMain = new GridBagLayout();
		GridBagLayout layoutManagerSortAll = new GridBagLayout();
		GridBagLayout layoutManagerPlots = new GridBagLayout();
		GridLayout layoutManagerTable = new GridLayout(7,6);
		//�������� ���������� ������������ ����������
		frameMain.setTitle("Laboratory work | Main");
		frameMain.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		frameMain.setLayout(layoutManagerMain);
		frameSortAll.setTitle("Laboratory work | All sorts");
		frameSortAll.setLayout(layoutManagerSortAll);
		frameTable.setTitle("Laboratory work | Table");
		frameTable.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		Dimension tableSizeDim = new Dimension();
		tableSizeDim.setSize(1280, 720);
		frameTable.setMinimumSize(tableSizeDim);
		frameTable.setBackground(Color.black);
		layoutManagerTable.setHgap(5);
		layoutManagerTable.setVgap(5);
		frameTable.setLayout(layoutManagerTable);
		GridBagConstraints constraintsConfig = new GridBagConstraints(); //�������� ������ � ����������� ����������
		constraintsConfig.anchor=constraintsConfig.WEST;
		constraintsConfig.fill=constraintsConfig.HORIZONTAL;
		constraintsConfig.gridx=0;
		constraintsConfig.gridy=0;
		frameMain.add(blocksCountLabel, constraintsConfig);
		constraintsConfig.gridx=1;
		constraintsConfig.gridy=0;
		frameMain.add(blocksCountTextField, constraintsConfig);
		constraintsConfig.gridx=2;
		constraintsConfig.gridy=0;
		frameMain.add(createBlocksButton, constraintsConfig);
		constraintsConfig.gridx=0;
		constraintsConfig.gridy=2;
		frameMain.add(blockSelectChoice, constraintsConfig);
		constraintsConfig.gridx=1;
		constraintsConfig.gridy=2;
		frameMain.add(fileSelectChoice, constraintsConfig);
		constraintsConfig.gridx=2;
		constraintsConfig.gridy=2;
		frameMain.add(sortSelectChoice, constraintsConfig);
		constraintsConfig.gridx=2;
		constraintsConfig.gridy=3;
		frameMain.add(sortFileButton, constraintsConfig);
		constraintsConfig.gridx=0;
		constraintsConfig.gridy=3;
		frameMain.add(timeElapsedLabel, constraintsConfig);
		constraintsConfig.gridx=1;
		constraintsConfig.gridy=3;
		frameMain.add(sortAllButton, constraintsConfig);
		constraintsConfig.gridx=0;
		constraintsConfig.gridy=3;
		frameMain.add(inputFileLabel, constraintsConfig);
		constraintsConfig.gridx=1;
		constraintsConfig.gridy=3;
		frameMain.add(outputFileLabel, constraintsConfig);
		constraintsConfig.gridx=0;
		constraintsConfig.gridy=4;
		frameMain.add(inputFileList, constraintsConfig);
		constraintsConfig.gridx=1;
		constraintsConfig.gridy=4;
		frameMain.add(outputFileList, constraintsConfig);
		constraintsConfig.gridx=0;
		constraintsConfig.gridy=0;
		frameSortAll.add(backButton, constraintsConfig);
		constraintsConfig.gridx=1;
		constraintsConfig.gridy=0;
		frameSortAll.add(autoSortButton, constraintsConfig);
		constraintsConfig.gridx=0;
		constraintsConfig.gridy=1;
		frameSortAll.add(allSortProgressLabel,constraintsConfig);
		constraintsConfig.gridx=0;
		constraintsConfig.gridy=2;
		frameSortAll.add(goToPlotButton,constraintsConfig);
		constraintsConfig.gridx=1;
		constraintsConfig.gridy=2;
		frameSortAll.add(goToTableButton,constraintsConfig);
		for(int i=0;i<7;i++) {
			constraintsConfig.gridy=i;
			for(int j=0;j<6;j++) {
				constraintsConfig.gridx=j;
				tableLabels[i][j]=new Label();
				tableLabels[i][j].setBackground(Color.white);
				frameTable.add(tableLabels[i][j], constraintsConfig);
			}
		}
		//�������������� �������� ��������� ����������� ������������ ����������
		blocksCountLabel.setText("������� ���-�� ������: ");
		createBlocksButton.setLabel("������� �����");
		blockSelectChoice.add("�������� ����");
		fileSelectChoice.add("�������� ����");
		sortSelectChoice.add("�������� ����������");
		sortFileButton.setLabel("������������� ����");
		timeElapsedLabel.setText("0 ��");
		sortAllButton.setLabel("������� #7");
		inputFileLabel.setText("�������� ����");
		outputFileLabel.setText("��������������� ����");
		backButton.setLabel("<= �����");
		autoSortButton.setLabel("�������������");
		allSortProgressLabel.setText("��������: 0/100");
		goToPlotButton.setLabel("������");
		goToTableButton.setLabel("�������");
		goToPlotButton.setEnabled(false);
		goToTableButton.setEnabled(false);
		//���������� ������ ������ SortManager
		SortManager sortManager = new SortManager(); //�������� ������ ������ SortManager
		//�������� ����������� ������� ��� ����������� �����������
		frameMain.addWindowListener(new WindowAdapter() { //������� ���������� �������
			public void windowClosing(WindowEvent windowEvent) { //���������� ������� �������� �������� ����
				System.exit(0); //�������� ���������
			}
		});
		frameSortAll.addWindowListener(new WindowAdapter() { //������� ���������� �������
			public void windowClosing(WindowEvent windowEvent) { //���������� ������� �������� ���� ���������� ���������� ������
				System.exit(0); //�������� ���������
			}
		});
		createBlocksButton.addActionListener(new ActionListener() { //������� ���������� �������
			public void actionPerformed(ActionEvent e) { //���������� ������� ������� �� ������ �������� ������
				try {
					blocksCount = Integer.valueOf(blocksCountTextField.getText()); //������� ���������� ������
				} catch(Exception ex) {}
				sortManager.CreateBlocks(blocksCount); //�������� ������ ���������� ������
				ConfigureBlockSelect(blockSelectChoice,fileSelectChoice,sortSelectChoice); //������� ����� � ��������������� �������� ������
			}
		});
		blockSelectChoice.addItemListener(new ItemListener() { //������� ���������� �������
			public void itemStateChanged(ItemEvent e) { //���������� ������� ������ ����� �� ����������� ������
				selectedBlock = blockSelectChoice.getSelectedItem(); //������� ������� ��������� ����
				ConfigureFileSelect(fileSelectChoice,sortSelectChoice); //������� ����� ����� � ��������������� �������� ������
			}
		});
		fileSelectChoice.addItemListener(new ItemListener() { //������� ���������� �������
			public void itemStateChanged(ItemEvent e) { //���������� ������� ������ ����� �� ����������� ������
				selectedFile = fileSelectChoice.getSelectedItem(); //������� ������� ��������� ����
				ConfigureSortSelect(sortSelectChoice); //������� ������ ���������� � ��������������� �������� ������
			}
		});
		sortSelectChoice.addItemListener(new ItemListener() { //������� ���������� �������
			public void itemStateChanged(ItemEvent e) { //���������� ������� ������ ���������� �� ����������� ������
				selectedSort = sortSelectChoice.getSelectedItem(); //������� ������� ��������� ����������
			}
		});
		sortFileButton.addActionListener(new ActionListener() { //������� ���������� �������
			public void actionPerformed(ActionEvent e) { //���������� ������� �� ������ ����������
				if(selectedBlock!=null && selectedFile!=null && selectedSort!=null) { //���� ������ ����, ���� � ����������
					inputFileList.removeAll(); //������� ������ ��������� ��������� �����
					outputFileList.removeAll(); //������� ������ ��������� ���������������� �����
					elements = SortManager.ReadFile(selectedBlock+"numbers"+selectedFile); //������� �������� �� �����
					ElementsToList(inputFileList,elements); //������� �������� ��������� �����
					Instant startTime = Instant.now(); //������� ������� �����
					SortManager.PerformSort(selectedSort,elements); //����������� ���� ��������
					Instant finishTime = Instant.now(); //������� ������� �����
					long elapsed = Duration.between(startTime, finishTime).toNanos(); //��������� �����, ����������� �� ����������
					String timeMeasurmentUnit=" ��"; //���������� � ������� �������� ��������� �������
					if (elapsed>1000) { //���� ���������� ��� ������ 1��
						elapsed=elapsed/1000; //����������� ����������� �����
						timeMeasurmentUnit=" ��"; //������ ������� ���������
					}
					ElementsToList(outputFileList,elements); //������� �������������� ��������
					timeElapsedLabel.setText(String.valueOf(elapsed)+timeMeasurmentUnit); //������� �����, ����������� �� ����������
				} else { //���� ������� �� ���
					timeElapsedLabel.setText("��� ������ ���������� - �������� ����, ���� � ����� ����������"); //������� ��������� ������������
				}
			}
		});
		sortAllButton.addActionListener(new ActionListener() { //������� ���������� �������
			public void actionPerformed(ActionEvent e) { //���������� ������� �� ������ �������� ���� ���������� ���������� ������
				frameMain.setVisible(false); //������� ������� ���� ���������
				frameMain.setEnabled(false); //�������� ������� ����
				frameSortAll.setEnabled(true); //���� ���������� ���������� ������
				frameSortAll.setVisible(true); //������� ���� ���������� ���������� ������ �������
			}
		});
		backButton.addActionListener(new ActionListener() { //������� ���������� �������
			public void actionPerformed(ActionEvent e) { //���������� ������� �� ������ ��������
				frameSortAll.setVisible(false);  //������� ���� ���������� ��������� ������ ���������
				frameSortAll.setEnabled(false); //�������� ���� ���������� ���������� ������
				frameMain.setEnabled(true); //������� ������� ����
				frameMain.setVisible(true); //������� ������� ���� �������
			}
		});
		autoSortButton.addActionListener(new ActionListener() { //������� ���������� �������
			public void actionPerformed(ActionEvent e) { //���������� ������� ������� �� ������ �������������� ����������
				long[][] sortsTime = new long[5][6]; //������ ��� �������� ������ ���������� ����������
				for(int i=0;i<5;i++) { //��������� �� ������� �������� �������
					for(int j=0;j<6;j++) {
						sortsTime[i][j]=0; //�������������� ���
					}
				}
				SortManager.CreateBlocks(5); //�������� 5 ������
				int progressConst = (5*5*6)/100; //�������� ��������� ��� �������� ���������
				int iterator=1;
				for(int s=0;s<5;s++) { //��������� �� ������� ���� ����������
					for(int b=0;b<5;b++) { //��������� �� ������� �����
						for(int f=0;f<6;f++) { //��������� �� ������� �����
							String currentFileName="block"+String.valueOf(b)+"numbers"+String.valueOf(numbersCountArray[f]); //���������� ������� �������� �����
							elements=SortManager.ReadFile(currentFileName); //������� ������� ����
							Instant startTime = Instant.now(); //������� ������� �����
							SortManager.PerformSort(sortsArray[s], elements); //����������� �������� �������� ����� ������� �����������
							Instant finishTime = Instant.now(); //������� ������� �����
							long elapsed = Duration.between(startTime, finishTime).toNanos(); //��������� �����, ����������� �� ����������
							//SortManager.WriteFile(currentFileName, elements); //������� ��������������� �������� � ����
							sortsTime[s][f]+=elapsed; //������� �����, ����������� �� ������� ���������� � ������
							allSortProgressLabel.setText("��������: "+String.valueOf((int) (progressConst*iterator/1.5))+"/100"); //������� ������� ��������
							iterator++; //�������� �������� ����������-���������
						}
					}
				}
				for(int i=0;i<5;i++){ //��������� �� ������� �������� �������
					for(int j=0;j<6;j++) {
						sortsTime[i][j]=sortsTime[i][j]/5; //��������� ������� ����������� �����
					}
				}
				//�������� ������ � ���������� �������
				String[][] data = new String[][] {{"���-�� ������","������� �����(������� 2)","������� �����(������� 3)","����� �������(������� 4)","������(������� 5)","������� ����������(������� 6)"},
													{"100", String.valueOf(sortsTime[0][0]), String.valueOf(sortsTime[1][0]), String.valueOf(sortsTime[2][0]), String.valueOf(sortsTime[3][0]), String.valueOf(sortsTime[4][0])},
													{"500", String.valueOf(sortsTime[0][1]), String.valueOf(sortsTime[1][1]), String.valueOf(sortsTime[2][1]), String.valueOf(sortsTime[3][1]), String.valueOf(sortsTime[4][1])},
													{"1000", String.valueOf(sortsTime[0][2]), String.valueOf(sortsTime[1][2]), String.valueOf(sortsTime[2][2]), String.valueOf(sortsTime[3][2]), String.valueOf(sortsTime[4][2])},
													{"5000", String.valueOf(sortsTime[0][3]), String.valueOf(sortsTime[1][3]), String.valueOf(sortsTime[2][3]), String.valueOf(sortsTime[3][3]), String.valueOf(sortsTime[4][3])},
													{"10000", String.valueOf(sortsTime[0][4]), String.valueOf(sortsTime[1][4]), String.valueOf(sortsTime[2][4]), String.valueOf(sortsTime[3][4]), String.valueOf(sortsTime[4][4])},
													{"50000", String.valueOf(sortsTime[0][5]), String.valueOf(sortsTime[1][5]), String.valueOf(sortsTime[2][5]), String.valueOf(sortsTime[3][5]), String.valueOf(sortsTime[4][5])}};
				for(int i=0;i<7;i++) { //��������� �� ������ ������
					for(int j=0;j<6;j++) { //��������� �� ������� �������
						tableLabels[i][j].setText(data[i][j]); //������� � ������� ������
					}
				}
				goToTableButton.setEnabled(true); //���������� ������ �������� � �������
				goToPlotButton.setEnabled(false); //���������� ������ �������� � �������
			}
		});
		goToTableButton.addActionListener(new ActionListener() { //������� ���������� �������
			public void actionPerformed(ActionEvent e) { //���������� ������� �� ������ �������� � �������
				frameTable.setEnabled(true); //���������� ���� � ��������
				frameTable.setVisible(true); //������� ���� � �������� �������
				frameSortAll.setEnabled(false); //������������ ���� �������� �������
			}
		});
		frameTable.addWindowListener(new WindowAdapter() { //������� ���������� �������
			public void windowClosing(WindowEvent windowEvent) { //���������� ������� �������� ���� � ��������
				frameTable.setEnabled(false); //������������ ���� � ��������
				frameTable.setVisible(false); //������� ���� � �������� ���������
				frameSortAll.setEnabled(true); //���������� ���� 7 �������
			}
		});
		goToPlotButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//
			}
		});
		//������� � ������� ����
		frameMain.pack();
		frameSortAll.pack();
		frameTable.pack();
		frameMain.setEnabled(true);
		frameSortAll.setEnabled(false);
		frameTable.setEnabled(false);
		frameSortAll.setVisible(false);
		frameTable.setVisible(false);
		frameMain.setVisible(true);
	}
	
	private static void ConfigureBlockSelect(Choice blockChoice,Choice fileChoice,Choice sortChoice) { //��������� ������ ������ ������
		blockChoice.removeAll(); //������� ������ ������
		fileChoice.removeAll(); //������� ������ ������
		sortChoice.removeAll(); //������� ������ ����������
		for(int i=0;i<blocksCount;i++) { //��������� �� ������� �����
			blockChoice.add("block"+String.valueOf(i+1)); //������� ��� � ������
		}
	}
	
	private static void ConfigureFileSelect(Choice fileChoice,Choice sortChoice) { //��������� ������ ������ ������
		fileChoice.removeAll(); //������� ������ ������
		sortChoice.removeAll(); //������� ������ ����������
		for(int i=0;i<6;i++) { //��������� �� ������� �����
			fileChoice.add(String.valueOf(numbersCountArray[i])); //������� ��� � ������
		}
	}
	
	private static void ConfigureSortSelect(Choice sortSelect) { //��������� ������ ������ ����������
		sortSelect.removeAll(); //������ ������
		for(int i=0;i<5;i++) { //��������� �� ������ ����������
			sortSelect.add(sortsArray[i]); //������� ���������� � ������
		}
	}
	
	private static void ElementsToList(List list,long[] elements2) { //��������� ������ ��������� � ������
		for(int i=0;i<elements2.length;i++) { //��������� �� ������� ��������
			list.add(String.valueOf(elements2[i])); //������� ������� � ������
		}
	}
	
}
