import java.util.Random;

public class Main {

	static void printArray(int arr[]) {
		int n = arr.length;
		for (int i = 0; i < n; ++i)
			System.out.print(arr[i] + " ");
		System.out.println();
	}

	static Random r = new Random();

	public static void fillArrayEqual(int[] arr) {
		int len=arr.length;
		int num = r.nextInt(100000);
		for (int i = 0; i < len; i++) {
			arr[i] = num;
		}
	}

	public static void fillArrayRandom(int[] arr) {
		int len=arr.length;
		for (int i = 0; i < len; i++) {
			arr[i] = r.nextInt(100000);
		}
	}

	public static void fillArrayIncreasing(int[] arr) {
		int len=arr.length;
		for (int i = 0; i < len; i++) {
			arr[i] = i + 1;
		}
	}

	public static void fillArrayDecreasing(int[] arr) {
		int len=arr.length;
		for (int i = 0; i < len; i++) {
			arr[i] = len - i;
		}
	}
	
	
	
	
	public static int shellSort(int arr[])
	{
		int n = arr.length;

		// Start with a big gap, then reduce the gap
		for (int gap = n/2; gap > 0; gap /= 2)
		{
			// Do a gapped insertion sort for this gap size.
			// The first gap elements a[0..gap-1] are already
			// in gapped order keep adding one more element
			// until the entire array is gap sorted
			for (int i = gap; i < n; i += 1)
			{
				// add a[i] to the elements that have been gap
				// sorted save a[i] in temp and make a hole at
				// position i
				int temp = arr[i];

				// shift earlier gap-sorted elements up until
				// the correct location for a[i] is found
				int j;
				for (j = i; j >= gap && arr[j - gap] > temp; j -= gap)
					arr[j] = arr[j - gap];

				// put temp (the original a[i]) in its correct
				// location
				arr[j] = temp;
			}
		}
		return 0;
	}
	
	
	
	public static void heapSort(int arr[]) {
		int n=arr.length;
		
		for (int i = n/2-1; i >= 0; i--) {
			heapify(arr,n,i);
		}
		
		for (int i = n-1; i >0; i--) {
			int temp=arr[0];
			arr[0]=arr[i];
			arr[i]=temp;
			
			heapify(arr,i,0);
			
		}
	}
	

	public static void heapify(int arr[],int n,int i) {
		int largest=i;
		int l=2*i+1;
		int r=2*i+2;
		
		if (l<n&& arr[l]>arr[largest]) {
			largest=l;
		}
		
		if (r<n&& arr[r]>arr[largest]) {
			largest=r;
		}
		
		
		if (largest!=i) {
			int swap=arr[i];
			arr[i]=arr[largest];
			arr[largest]=swap;
			
			heapify(arr,n,largest);
		}
	}
	
	
	
	
	 /////
	
	
	
	private static void swap(int i, int j,int[] a)
	{
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}

	// To maxHeap a subtree rooted with node i which is
	// an index in a[]. heapN is size of heap
	private static void maxHeap(int i, int heapN, int begin,int[] a)
	{
		int temp = a[begin + i - 1];
		int child;

		while (i <= heapN / 2) {
			child = 2 * i;

			if (child < heapN
				&& a[begin + child - 1] < a[begin + child])
				child++;

			if (temp >= a[begin + child - 1])
				break;

			a[begin + i - 1] = a[begin + child - 1];
			i = child;
		}
		a[begin + i - 1] = temp;
	}

	// Function to build the heap (rearranging the array)
	private static void heapify(int begin, int end, int heapN,int[] a)
	{
		for (int i = (heapN) / 2; i >= 1; i--)
			maxHeap(i, heapN, begin,a);
	}

	// main function to do heapsort
	private static void heapSort(int begin, int end,int[] a)
	{
		int heapN = end - begin;

		// Build heap (rearrange array)
		heapify(begin, end, heapN,a);

		// One by one extract an element from heap
		for (int i = heapN; i >= 1; i--) {

			// Move current root to end
			swap(begin, begin + i,a);

			// call maxHeap() on the reduced heap
			maxHeap(1, i, begin,a);
		}
	}

	// function that implements insertion sort
	private static void insertionSort(int left, int right,int[] a)
	{

		for (int i = left; i <= right; i++) {
			int key = a[i];
			int j = i;

			// Move elements of arr[0..i-1], that are
			// greater than the key, to one position ahead
			// of their current position
			while (j > left && a[j - 1] > key) {
				a[j] = a[j - 1];
				j--;
			}
			a[j] = key;
		}
	}

	// Function for finding the median of the three elements
	private static int findPivot(int a1, int b1, int c1,int[] a)
	{
		int max = Math.max(Math.max(a[a1], a[b1]), a[c1]);
		int min = Math.min(Math.min(a[a1], a[b1]), a[c1]);
		int median = max ^ min ^ a[a1] ^ a[b1] ^ a[c1];
		if (median == a[a1])
			return a1;
		if (median == a[b1])
			return b1;
		return c1;
	}

	// This function takes the last element as pivot, places
	// the pivot element at its correct position in sorted
	// array, and places all smaller (smaller than pivot)
	// to the left of the pivot
	// and greater elements to the right of the pivot
	private static int partition(int low, int high,int[] a)
	{

		// pivot
		int pivot = a[high];

		// Index of smaller element
		int i = (low - 1);
		for (int j = low; j <= high - 1; j++) {

			// If the current element is smaller
			// than or equal to the pivot
			if (a[j] <= pivot) {

				// increment index of smaller element
				i++;
				swap(i, j,a);
			}
		}
		swap(i + 1, high,a);
		return (i + 1);
	}

	// The main function that implements Introsort
	// low --> Starting index,
	// high --> Ending index,
	// depthLimit --> recursion level
	private static void sortDataUtil(int begin, int end, int depthLimit,int[] a)
	{
		if (end - begin > 16) {
			if (depthLimit == 0) {

				// if the recursion limit is
				// occurred call heap sort
				heapSort(begin, end,a);
				return;
			}

			depthLimit = depthLimit - 1;
			int pivot = findPivot(begin,
				begin + ((end - begin) / 2) + 1,
										end,a);
			swap(pivot, end,a);

			// p is partitioning index,
			// arr[p] is now at right place
			int p = partition(begin, end,a);

			// Separately sort elements before
			// partition and after partition
			sortDataUtil(begin, p - 1, depthLimit,a);
			sortDataUtil(p + 1, end, depthLimit,a);
		}

		else {
			// if the data set is small,
			// call insertion sort
			insertionSort(begin, end,a);
		}
	}

	// A utility function to begin the
	// Introsort module
	public static void introSortData(int[] a)
	{
		int n=a.length;

		// Initialise the depthLimit
		// as 2*log(length(data))
		int depthLimit
			= (int)(2 * Math.floor(Math.log(n) /
								Math.log(2)));

		sortDataUtil(0, n - 1, depthLimit,a);
	}
	
	
	
	
	
	
	
	
	

	public static void main(String[] args) {

		

		
		
		
		int arr[]= new int[100000000];
		
		
		
		
		//int arr[]= new int[1000];
		//int arr[]= new int[10000];
		//int arr[]= new int[100000];
		
		//fillArrayEqual(arr);
		fillArrayRandom(arr);
		//fillArrayIncreasing(arr);
		//fillArrayDecreasing(arr);
		
		
		
		
		
		
		
		

		  
		  
		  
		 
		  
		  
		 

		
		
         
       
        
        
        

        long beginTime = 0, endTime = 0;
        
        beginTime = System.currentTimeMillis();
        heapSort(arr);
        //shellSort(arr);
        //introSortData(arr);
        
        
        endTime = System.currentTimeMillis();
        long estimatedTime=endTime - beginTime;
        System.out.println("Çalýþma Süresi : " + estimatedTime );
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

	}

}
