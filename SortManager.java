import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class SortManager {
	
	public static void CreateBlocks(int blocksCount) { //метод создания блоков
		final String currentDirectory = System.getProperty("user.dir"); //получим текущий каталог программы
		File tempFile = new File(currentDirectory+"\\blocks"); //создадим переменную папки для блоков
		RemakeDirectory(tempFile);
		final String blocksDirectory = currentDirectory+"\\blocks\\"; //получим директорию блоков
		int[] numbersCountArray = new int[] {100,500,1000,5000,10000,50000}; //создадим и инициализируем массив целых чисел с количествами чисел, с которыми нужно создать файлы
		for(int i=0;i<blocksCount;i++) { //пройдемся по каждому блоку
			for(int j=0;j<6;j++) { //пройдемся 6 раз, т.к. в блоке 6 файлов
				int numbersCount = numbersCountArray[j]; //получим количество цифр,необходимое для записи в этом файле
				try {
					DataOutputStream dos = new DataOutputStream(new FileOutputStream(blocksDirectory+"block"+i+"numbers"+String.valueOf(numbersCount)+".bin")); //откроем поток записи
					dos.writeInt(numbersCount); //запишем число чисел
					Random random = new Random(); //создадим объект класса Random
					for(int k=0;k<numbersCount;k++) { //пройдеся столько раз, сколько чисел нужно записать в файл
						int tempInt = random.nextInt(1000000); //получим случайное число от 0 до 10^6
						dos.writeInt(tempInt); //запишем наше число в файл через поток записи
					}
					dos.close();
				} catch (IOException e) { //обработки ошибок
					e.printStackTrace(); //вывод ошибок
				}
			}
		}
	}
	
	public static long[] ReadFile(String cfilename) { //метод чтения из файла
		final String currentDirectory = System.getProperty("user.dir"); //получим текущий каталог программы
		final String blocksDirectory = currentDirectory+"\\blocks\\"; //получим директорию блоков
		final String filename = blocksDirectory+cfilename+".bin"; //получим полное имя файла
		long[] elements = null; //создадим массив элементов файла
		try {
			DataInputStream dis = new DataInputStream(new FileInputStream(filename)); //откроем поток чтения
			int numbersCount=dis.readInt(); //считаем количество чисел
			elements = new long[numbersCount]; //инициализируем массив элементов файла
			for(int i=0;i<numbersCount;i++) { //пройдемся нужное количество раз
				elements[i]=dis.readInt(); //считаем текущий элемент файла
			}
			dis.close();
		} catch (IOException e) { //обработка ошибок
			e.printStackTrace(); //вывод ошибок
		}
		return elements; //вернем массив элементов файла
	}
	
	public static void PerformSort(String sortName,long[] time) { //метод запуска сортировки
		switch(sortName) { //в зависимости от названия сортировки отсортируем элементы нужной сортировкой
			case "Сортировка выбором":
				SelectionSort(time);
				break;
			case "Пузырек":
				BubbleSort(time);
				break;
			case "Умный пузырек":
				SmartBubbleSort(time);
				break;
			case "Шейкер (задание 5)":
				ShakerSort(time);
				break;
			case "Быстрая сортировка":
				QuickSort(time,0,time.length-1);
				break;
		}
	}
	
	public static void WriteFile(String filename,int[] elements) { //метод записи в файл
		final String currentDirectory = System.getProperty("user.dir"); //получим текущий каталог программы
		final String blocksDirectory = currentDirectory+"\\blocks\\"; //получим директорию блоков
		final String totalFilename = blocksDirectory+filename+".bin"; //получим полное имя файла
		File tempFile = new File(totalFilename); //создадим временную файловую переменную
		if(tempFile.exists()) { //если файл существует
			tempFile.delete(); //удалим его
		}
		try {
			DataOutputStream dos = new DataOutputStream(new FileOutputStream(totalFilename)); //откроем поток записи
			for(int i=0;i<elements.length;i++) { //пройдемся по каждому элементу
				dos.writeInt(elements[i]); //запишем в файл текущий элемент
			}
		} catch (IOException e) { //обработка ошибок
			e.printStackTrace(); //вывод ошибок
		}
	}
	
	public static void RemakeDirectory(File tempFile) { //процедура пересоздания папки
		if(!tempFile.exists()) { //если папки не существует
			tempFile.mkdir(); //создадим папку для блоков
		} else { //если папка существует
			String[] files = tempFile.list(); //получим список файлом в папке
			for(String s:files) { //пройдемся по каждому файлу в папке
				File currentFile = new File(tempFile.getPath(),s); //создадим временную файловую переменную
				currentFile.delete(); //удалим текущий файл из папки
			}
			tempFile.delete(); //удалим папку
			if(!tempFile.exists()) { //если папки не существует
				tempFile.mkdir(); //создадим папку для блоков
			}
		}
	}
	
	private static void SelectionSort(long[] time) { //процедура сортировки выбором
		for (int left = 0; left < time.length; left++) {
			int minInd = left;
			for (int i = left; i < time.length; i++) {
				if (time[i] < time[minInd]) {
					minInd = i;
				}
			}
			Swap(time, left, minInd);
		}
	}
	
	private static void BubbleSort(long[] time) { //процедура сортировки пузырьком
		for (int i=0;i<time.length;i++) {
			for (int j=0;j<time.length-1;j++) {
				if (time[j]>time[j+1]) {
					Swap(time,j,j+1);
				}
			}
		}
	}
	
	private static void SmartBubbleSort(long[] time) { //процедура умной сортировки пузырьком
		boolean flag=true;
		while(flag) {
			flag=false;
			for(int i=0;i<time.length-1;i++) {
				if(time[i]>time[i+1]) {
					flag=true;
					Swap(time,i,i+1);
				}
			}
		}
	}
	
	private static void ShakerSort(long[] time) { //процедура шейкер сортировки
		boolean swapped=false;
		do {
			for(int i=0;i<time.length-2;i++) {
				if (time[i]>time[i+1]) {
					Swap(time,i,i+1);
					swapped=true;
				}
			}
			if (!swapped) {
				break;
			}
			swapped=false;
			for(int i=time.length-2;i>0;i--) {
				if (time[i]>time[i+1]) {
					Swap(time,i,i+1);
					swapped=true;
				}
			}
		}
		while(swapped);
	}
	
	private static void QuickSort(long[] time, int leftBorder, int rightBorder) { //процедура быстрой сортировки
        int leftMarker = leftBorder;
        int rightMarker = rightBorder;
        long pivot = time[(leftMarker + rightMarker) / 2];
        do {
            // Двигаем левый маркер слева направо пока элемент меньше, чем pivot
            while (time[leftMarker] < pivot) {
                leftMarker++;
            }
            // Двигаем правый маркер, пока элемент больше, чем pivot
            while (time[rightMarker] > pivot) {
                rightMarker--;
            }
            // Проверим, не нужно обменять местами элементы, на которые указывают маркеры
            if (leftMarker <= rightMarker) {
                // Левый маркер будет меньше правого только если мы должны выполнить swap
                if (leftMarker < rightMarker) {
                	Swap(time,leftMarker,rightMarker);
                }
                // Сдвигаем маркеры, чтобы получить новые границы
                leftMarker++;
                rightMarker--;
            }
        } while (leftMarker <= rightMarker);

        // Выполняем рекурсивно для частей
        if (leftMarker < rightBorder) {
            QuickSort(time, leftMarker, rightBorder);
        }
        if (leftBorder < rightMarker) {
            QuickSort(time, leftBorder, rightMarker);
        }
	}
	
	private static void Swap(long[] time, int ind1, int ind2) { //процедура обмена двух элементов массива
	    long tmp = time[ind1];
	    time[ind1] = time[ind2];
	    time[ind2] = tmp;
	}
	
}
