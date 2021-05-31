//импортируем все необходимое
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

public class Main { //главный класс программы

	static int blocksCount; //переменная для хранения количества блоков
	static String selectedBlock = null; //переменная для хранения текущего выбранного блока
	static String selectedFile = null; //переменная для хранения текущего выбранного файла
	static String selectedSort = null; //переменная для хранения текущей выбранной сортировки
	static long[] elements; //переменная для хранения элементов блока
	static String[] sortsArray = new String[] {"Сортировка выбором","Пузырек","Умный пузырек","Шейкер (задание 5)","Быстрая сортировка"}; //массив с названиями сортировок
	static int[] numbersCountArray = new int[] {100,500,1000,5000,10000,50000}; //массив с размерами файлов
	public static void main(String[] args) { //главный метод программы aka точка входа в программу
		//создадим окна
		Frame frameMain = new Frame();
		Frame frameSortAll = new Frame();
		Frame frameTable = new Frame();
		//отключим и сделаем невидимыми все окна
		frameSortAll.setVisible(false);
		frameSortAll.setEnabled(false);
		frameMain.setEnabled(false);
		frameMain.setVisible(false);
		frameTable.setVisible(false);
		frameTable.setEnabled(false);
		//создадим компоненты графического интерфейса
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
		//настроим компоненты графического интерфейса
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
		GridBagConstraints constraintsConfig = new GridBagConstraints(); //создадим объект с настройками параметров
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
		//инициализируем свойства некоторых компонентов графического интерфейса
		blocksCountLabel.setText("Введите кол-во блоков: ");
		createBlocksButton.setLabel("Создать блоки");
		blockSelectChoice.add("Выберите блок");
		fileSelectChoice.add("Выберите файл");
		sortSelectChoice.add("Выберите сортировку");
		sortFileButton.setLabel("Отсортировать файл");
		timeElapsedLabel.setText("0 нс");
		sortAllButton.setLabel("Задание #7");
		inputFileLabel.setText("Исходный файл");
		outputFileLabel.setText("Отсортированный файл");
		backButton.setLabel("<= Назад");
		autoSortButton.setLabel("Отсортировать");
		allSortProgressLabel.setText("Прогресс: 0/100");
		goToPlotButton.setLabel("График");
		goToTableButton.setLabel("Таблица");
		goToPlotButton.setEnabled(false);
		goToTableButton.setEnabled(false);
		//подготовим объект класса SortManager
		SortManager sortManager = new SortManager(); //создадим объект класса SortManager
		//назначим обработчики событий для графических компонентов
		frameMain.addWindowListener(new WindowAdapter() { //добавим обработчик события
			public void windowClosing(WindowEvent windowEvent) { //обработчик события закрытия главного окна
				System.exit(0); //завершим программу
			}
		});
		frameSortAll.addWindowListener(new WindowAdapter() { //добавим обработчик события
			public void windowClosing(WindowEvent windowEvent) { //обработчик события закрытия окна сортировки нескольких блоков
				System.exit(0); //завершим программу
			}
		});
		createBlocksButton.addActionListener(new ActionListener() { //добавим обработчик события
			public void actionPerformed(ActionEvent e) { //обработчик события нажатия на кнопку создания блоков
				try {
					blocksCount = Integer.valueOf(blocksCountTextField.getText()); //считаем количество блоков
				} catch(Exception ex) {}
				sortManager.CreateBlocks(blocksCount); //создадим нужное количество блоков
				ConfigureBlockSelect(blockSelectChoice,fileSelectChoice,sortSelectChoice); //выведем блоки в соответствующий выкидной список
			}
		});
		blockSelectChoice.addItemListener(new ItemListener() { //добавим обработчик события
			public void itemStateChanged(ItemEvent e) { //обработчик события выбора блока из выпадающего списка
				selectedBlock = blockSelectChoice.getSelectedItem(); //считаем текущий выбранный блок
				ConfigureFileSelect(fileSelectChoice,sortSelectChoice); //выведем файлы блока в соответствующий выкидной список
			}
		});
		fileSelectChoice.addItemListener(new ItemListener() { //добавим обработчик события
			public void itemStateChanged(ItemEvent e) { //обработчик события выбора файла из выпадающего списка
				selectedFile = fileSelectChoice.getSelectedItem(); //считаем текущий выбранный файл
				ConfigureSortSelect(sortSelectChoice); //выведем список сортировок в соответствующий выкидной список
			}
		});
		sortSelectChoice.addItemListener(new ItemListener() { //добавим обработчик события
			public void itemStateChanged(ItemEvent e) { //обработчик события выбора сортировки из выпадающего списка
				selectedSort = sortSelectChoice.getSelectedItem(); //считаем текущую выбранную сортировку
			}
		});
		sortFileButton.addActionListener(new ActionListener() { //добавим обработчик события
			public void actionPerformed(ActionEvent e) { //обработчик нажатия на кнопку сортировки
				if(selectedBlock!=null && selectedFile!=null && selectedSort!=null) { //если выбран блок, файл и сортировка
					inputFileList.removeAll(); //очистим список элементов исходного файла
					outputFileList.removeAll(); //очистим список элементов отсортированного файла
					elements = SortManager.ReadFile(selectedBlock+"numbers"+selectedFile); //считаем элементы из файла
					ElementsToList(inputFileList,elements); //выведем элементы исходного файла
					Instant startTime = Instant.now(); //получим текущее время
					SortManager.PerformSort(selectedSort,elements); //отсортируем наши элементы
					Instant finishTime = Instant.now(); //получим текущее время
					long elapsed = Duration.between(startTime, finishTime).toNanos(); //посчитаем время, затраченное на сортировку
					String timeMeasurmentUnit=" нс"; //переменная с текущей единицой измерения времени
					if (elapsed>1000) { //если сортировка шла дольше 1мс
						elapsed=elapsed/1000; //пересчитаем затраченное время
						timeMeasurmentUnit=" мс"; //сменим единицу измерения
					}
					ElementsToList(outputFileList,elements); //выведем отсортрованные элементы
					timeElapsedLabel.setText(String.valueOf(elapsed)+timeMeasurmentUnit); //выведем время, затраченное на сортировку
				} else { //если выбрано не все
					timeElapsedLabel.setText("Для начала сортировки - выберите блок, файл и метод сортировки"); //выведем сообщение пользователю
				}
			}
		});
		sortAllButton.addActionListener(new ActionListener() { //добавим обработчик событий
			public void actionPerformed(ActionEvent e) { //обработчик нажатия на кнопку открытия окна сортировки нескольких файлов
				frameMain.setVisible(false); //сделаем главное окно невидимым
				frameMain.setEnabled(false); //отключим главное окно
				frameSortAll.setEnabled(true); //окно сортировки нескольких файлов
				frameSortAll.setVisible(true); //сделаем окно сортировки нескольких файлов видимым
			}
		});
		backButton.addActionListener(new ActionListener() { //добавим обработчик событий
			public void actionPerformed(ActionEvent e) { //обработчик нажатия на кнопку возврата
				frameSortAll.setVisible(false);  //сделаем окно сортировки несколько файлов невидимым
				frameSortAll.setEnabled(false); //отключим окно сортировки нескольких файлов
				frameMain.setEnabled(true); //включим главное окно
				frameMain.setVisible(true); //сделаем главное окно видимым
			}
		});
		autoSortButton.addActionListener(new ActionListener() { //добавим обработчик события
			public void actionPerformed(ActionEvent e) { //обработчик события нажатия на кнопку автоматической сортировки
				long[][] sortsTime = new long[5][6]; //массив для хранения времен выполнения сортировок
				for(int i=0;i<5;i++) { //пройдемся по каждому элементу массива
					for(int j=0;j<6;j++) {
						sortsTime[i][j]=0; //инициализируем его
					}
				}
				SortManager.CreateBlocks(5); //создадим 5 блоков
				int progressConst = (5*5*6)/100; //создадим констанут для просчета прогресса
				int iterator=1;
				for(int s=0;s<5;s++) { //пройдемся по каждому виду сортировки
					for(int b=0;b<5;b++) { //пройдемся по каждому блоку
						for(int f=0;f<6;f++) { //пройдемся по каждому файлу
							String currentFileName="block"+String.valueOf(b)+"numbers"+String.valueOf(numbersCountArray[f]); //просчитаем текущее название файла
							elements=SortManager.ReadFile(currentFileName); //считаем текущий файл
							Instant startTime = Instant.now(); //получим текущее время
							SortManager.PerformSort(sortsArray[s], elements); //отсортируем элементы текущего файла текущей сортировкой
							Instant finishTime = Instant.now(); //получим текущее время
							long elapsed = Duration.between(startTime, finishTime).toNanos(); //посчитаем время, затраченное на сортировку
							//SortManager.WriteFile(currentFileName, elements); //запишем отсортированные элементы в файл
							sortsTime[s][f]+=elapsed; //добавим время, затраченное на текущую сортировку в массив
							allSortProgressLabel.setText("Прогресс: "+String.valueOf((int) (progressConst*iterator/1.5))+"/100"); //выведем текущий прогресс
							iterator++; //увеличим значение переменной-итератора
						}
					}
				}
				for(int i=0;i<5;i++){ //пройдемся по каждому элементу массива
					for(int j=0;j<6;j++) {
						sortsTime[i][j]=sortsTime[i][j]/5; //посчитаем среднее затраченное время
					}
				}
				//создадим массив с элементами таблицы
				String[][] data = new String[][] {{"Кол-во файлов","Простой выбор(Задание 2)","Простой обмен(Задание 3)","Умный пузырек(задание 4)","Шейкер(Задание 5)","Быстрая сортировка(Задание 6)"},
													{"100", String.valueOf(sortsTime[0][0]), String.valueOf(sortsTime[1][0]), String.valueOf(sortsTime[2][0]), String.valueOf(sortsTime[3][0]), String.valueOf(sortsTime[4][0])},
													{"500", String.valueOf(sortsTime[0][1]), String.valueOf(sortsTime[1][1]), String.valueOf(sortsTime[2][1]), String.valueOf(sortsTime[3][1]), String.valueOf(sortsTime[4][1])},
													{"1000", String.valueOf(sortsTime[0][2]), String.valueOf(sortsTime[1][2]), String.valueOf(sortsTime[2][2]), String.valueOf(sortsTime[3][2]), String.valueOf(sortsTime[4][2])},
													{"5000", String.valueOf(sortsTime[0][3]), String.valueOf(sortsTime[1][3]), String.valueOf(sortsTime[2][3]), String.valueOf(sortsTime[3][3]), String.valueOf(sortsTime[4][3])},
													{"10000", String.valueOf(sortsTime[0][4]), String.valueOf(sortsTime[1][4]), String.valueOf(sortsTime[2][4]), String.valueOf(sortsTime[3][4]), String.valueOf(sortsTime[4][4])},
													{"50000", String.valueOf(sortsTime[0][5]), String.valueOf(sortsTime[1][5]), String.valueOf(sortsTime[2][5]), String.valueOf(sortsTime[3][5]), String.valueOf(sortsTime[4][5])}};
				for(int i=0;i<7;i++) { //пройдемся по каждой строке
					for(int j=0;j<6;j++) { //пройдемся по каждому столбцу
						tableLabels[i][j].setText(data[i][j]); //выведем в таблицу данные
					}
				}
				goToTableButton.setEnabled(true); //активируем кнопку перехода к таблице
				goToPlotButton.setEnabled(false); //активируем кнопку перехода к графику
			}
		});
		goToTableButton.addActionListener(new ActionListener() { //добавим обработчик событий
			public void actionPerformed(ActionEvent e) { //обрабочтик нажатия на кнопку перехода к таблице
				frameTable.setEnabled(true); //активируем окно с таблицей
				frameTable.setVisible(true); //сделаем окно с таблицей видимым
				frameSortAll.setEnabled(false); //деактивируем окно седьмого задания
			}
		});
		frameTable.addWindowListener(new WindowAdapter() { //добавим обработчик события
			public void windowClosing(WindowEvent windowEvent) { //обработчик события закрытия окна с таблицей
				frameTable.setEnabled(false); //деактивируем окно с таблицей
				frameTable.setVisible(false); //сделаем окно с таблицей невидимым
				frameSortAll.setEnabled(true); //активируем оккн 7 задания
			}
		});
		goToPlotButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//
			}
		});
		//включим и выведим окно
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
	
	private static void ConfigureBlockSelect(Choice blockChoice,Choice fileChoice,Choice sortChoice) { //процедура вывода списка блоков
		blockChoice.removeAll(); //очистим список блоков
		fileChoice.removeAll(); //очистим список файлов
		sortChoice.removeAll(); //очистим список сортировок
		for(int i=0;i<blocksCount;i++) { //пройдемся по каждому блоку
			blockChoice.add("block"+String.valueOf(i+1)); //добавим его в список
		}
	}
	
	private static void ConfigureFileSelect(Choice fileChoice,Choice sortChoice) { //процедура вывода списка файлов
		fileChoice.removeAll(); //очистим список файлов
		sortChoice.removeAll(); //очистим список сортировок
		for(int i=0;i<6;i++) { //пройдемся по каждому файлу
			fileChoice.add(String.valueOf(numbersCountArray[i])); //выведем его в список
		}
	}
	
	private static void ConfigureSortSelect(Choice sortSelect) { //процедура вывода списка сортировок
		sortSelect.removeAll(); //очстим список
		for(int i=0;i<5;i++) { //пройдемся по каждой сортировке
			sortSelect.add(sortsArray[i]); //добавим сортировку в список
		}
	}
	
	private static void ElementsToList(List list,long[] elements2) { //процедура вывода элементов в список
		for(int i=0;i<elements2.length;i++) { //пройдемся по каждому элементу
			list.add(String.valueOf(elements2[i])); //добавим элемент в список
		}
	}
	
}
