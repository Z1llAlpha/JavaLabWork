import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class SortManager {
	
	public static void CreateBlocks(int blocksCount) { //����� �������� ������
		final String currentDirectory = System.getProperty("user.dir"); //������� ������� ������� ���������
		File tempFile = new File(currentDirectory+"\\blocks"); //�������� ���������� ����� ��� ������
		RemakeDirectory(tempFile);
		final String blocksDirectory = currentDirectory+"\\blocks\\"; //������� ���������� ������
		int[] numbersCountArray = new int[] {100,500,1000,5000,10000,50000}; //�������� � �������������� ������ ����� ����� � ������������ �����, � �������� ����� ������� �����
		for(int i=0;i<blocksCount;i++) { //��������� �� ������� �����
			for(int j=0;j<6;j++) { //��������� 6 ���, �.�. � ����� 6 ������
				int numbersCount = numbersCountArray[j]; //������� ���������� ����,����������� ��� ������ � ���� �����
				try {
					DataOutputStream dos = new DataOutputStream(new FileOutputStream(blocksDirectory+"block"+i+"numbers"+String.valueOf(numbersCount)+".bin")); //������� ����� ������
					dos.writeInt(numbersCount); //������� ����� �����
					Random random = new Random(); //�������� ������ ������ Random
					for(int k=0;k<numbersCount;k++) { //�������� ������� ���, ������� ����� ����� �������� � ����
						int tempInt = random.nextInt(1000000); //������� ��������� ����� �� 0 �� 10^6
						dos.writeInt(tempInt); //������� ���� ����� � ���� ����� ����� ������
					}
					dos.close();
				} catch (IOException e) { //��������� ������
					e.printStackTrace(); //����� ������
				}
			}
		}
	}
	
	public static long[] ReadFile(String cfilename) { //����� ������ �� �����
		final String currentDirectory = System.getProperty("user.dir"); //������� ������� ������� ���������
		final String blocksDirectory = currentDirectory+"\\blocks\\"; //������� ���������� ������
		final String filename = blocksDirectory+cfilename+".bin"; //������� ������ ��� �����
		long[] elements = null; //�������� ������ ��������� �����
		try {
			DataInputStream dis = new DataInputStream(new FileInputStream(filename)); //������� ����� ������
			int numbersCount=dis.readInt(); //������� ���������� �����
			elements = new long[numbersCount]; //�������������� ������ ��������� �����
			for(int i=0;i<numbersCount;i++) { //��������� ������ ���������� ���
				elements[i]=dis.readInt(); //������� ������� ������� �����
			}
			dis.close();
		} catch (IOException e) { //��������� ������
			e.printStackTrace(); //����� ������
		}
		return elements; //������ ������ ��������� �����
	}
	
	public static void PerformSort(String sortName,long[] time) { //����� ������� ����������
		switch(sortName) { //� ����������� �� �������� ���������� ����������� �������� ������ �����������
			case "���������� �������":
				SelectionSort(time);
				break;
			case "�������":
				BubbleSort(time);
				break;
			case "����� �������":
				SmartBubbleSort(time);
				break;
			case "������ (������� 5)":
				ShakerSort(time);
				break;
			case "������� ����������":
				QuickSort(time,0,time.length-1);
				break;
		}
	}
	
	public static void WriteFile(String filename,int[] elements) { //����� ������ � ����
		final String currentDirectory = System.getProperty("user.dir"); //������� ������� ������� ���������
		final String blocksDirectory = currentDirectory+"\\blocks\\"; //������� ���������� ������
		final String totalFilename = blocksDirectory+filename+".bin"; //������� ������ ��� �����
		File tempFile = new File(totalFilename); //�������� ��������� �������� ����������
		if(tempFile.exists()) { //���� ���� ����������
			tempFile.delete(); //������ ���
		}
		try {
			DataOutputStream dos = new DataOutputStream(new FileOutputStream(totalFilename)); //������� ����� ������
			for(int i=0;i<elements.length;i++) { //��������� �� ������� ��������
				dos.writeInt(elements[i]); //������� � ���� ������� �������
			}
		} catch (IOException e) { //��������� ������
			e.printStackTrace(); //����� ������
		}
	}
	
	public static void RemakeDirectory(File tempFile) { //��������� ������������ �����
		if(!tempFile.exists()) { //���� ����� �� ����������
			tempFile.mkdir(); //�������� ����� ��� ������
		} else { //���� ����� ����������
			String[] files = tempFile.list(); //������� ������ ������ � �����
			for(String s:files) { //��������� �� ������� ����� � �����
				File currentFile = new File(tempFile.getPath(),s); //�������� ��������� �������� ����������
				currentFile.delete(); //������ ������� ���� �� �����
			}
			tempFile.delete(); //������ �����
			if(!tempFile.exists()) { //���� ����� �� ����������
				tempFile.mkdir(); //�������� ����� ��� ������
			}
		}
	}
	
	private static void SelectionSort(long[] time) { //��������� ���������� �������
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
	
	private static void BubbleSort(long[] time) { //��������� ���������� ���������
		for (int i=0;i<time.length;i++) {
			for (int j=0;j<time.length-1;j++) {
				if (time[j]>time[j+1]) {
					Swap(time,j,j+1);
				}
			}
		}
	}
	
	private static void SmartBubbleSort(long[] time) { //��������� ����� ���������� ���������
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
	
	private static void ShakerSort(long[] time) { //��������� ������ ����������
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
	
	private static void QuickSort(long[] time, int leftBorder, int rightBorder) { //��������� ������� ����������
        int leftMarker = leftBorder;
        int rightMarker = rightBorder;
        long pivot = time[(leftMarker + rightMarker) / 2];
        do {
            // ������� ����� ������ ����� ������� ���� ������� ������, ��� pivot
            while (time[leftMarker] < pivot) {
                leftMarker++;
            }
            // ������� ������ ������, ���� ������� ������, ��� pivot
            while (time[rightMarker] > pivot) {
                rightMarker--;
            }
            // ��������, �� ����� �������� ������� ��������, �� ������� ��������� �������
            if (leftMarker <= rightMarker) {
                // ����� ������ ����� ������ ������� ������ ���� �� ������ ��������� swap
                if (leftMarker < rightMarker) {
                	Swap(time,leftMarker,rightMarker);
                }
                // �������� �������, ����� �������� ����� �������
                leftMarker++;
                rightMarker--;
            }
        } while (leftMarker <= rightMarker);

        // ��������� ���������� ��� ������
        if (leftMarker < rightBorder) {
            QuickSort(time, leftMarker, rightBorder);
        }
        if (leftBorder < rightMarker) {
            QuickSort(time, leftBorder, rightMarker);
        }
	}
	
	private static void Swap(long[] time, int ind1, int ind2) { //��������� ������ ���� ��������� �������
	    long tmp = time[ind1];
	    time[ind1] = time[ind2];
	    time[ind2] = tmp;
	}
	
}
