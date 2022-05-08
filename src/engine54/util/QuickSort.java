package engine54.util;

public class QuickSort{


    public static void sort(int[] arr){
        sort(arr,0,arr.length-1);
    }

    public static void sort(long[] arr){
        sort(arr,0,arr.length-1);
    }

    public static void sort(float[] arr){
        sort(arr,0,arr.length-1);
    }

    public static void sort(double[] arr){
        sort(arr,0,arr.length-1);
    }


    public static void sort(int[] arr,int first,int last){
        if(first>=last)
            return;

        int middle=arr[(first+last)/2];
        int left=first;
        int right=last;
        do{
            while(arr[left]<middle)
                left++;
            while(arr[right]>middle)
                right--;
            if(left<=right){
                int tmp=arr[left];
                arr[left]=arr[right];
                arr[right]=tmp;
                left++;
                right--;
            }
        }while(left<right);

        sort(arr,first,right);
        sort(arr,left,last);
    }

    public static void sort(long[] arr,int first,int last){
        if(first>=last)
            return;

        long middle=arr[(first+last)/2];
        int left=first;
        int right=last;
        do{
            while(arr[left]<middle)
                left++;
            while(arr[right]>middle)
                right--;
            if(left<=right){
                long tmp=arr[left];
                arr[left]=arr[right];
                arr[right]=tmp;
                left++;
                right--;
            }
        }while(left<right);

        sort(arr,first,right);
        sort(arr,left,last);
    }

    public static void sort(float[] arr,int first,int last){
        if(first>=last)
            return;

        float middle=arr[(first+last)/2];
        int left=first;
        int right=last;
        do{
            while(arr[left]<middle)
                left++;
            while(arr[right]>middle)
                right--;
            if(left<=right){
                float tmp=arr[left];
                arr[left]=arr[right];
                arr[right]=tmp;
                left++;
                right--;
            }
        }while(left<right);

        sort(arr,first,right);
        sort(arr,left,last);
    }

    public static void sort(double[] arr,int first,int last){
        if(first>=last)
            return;

        double middle=arr[(first+last)/2];
        int left=first;
        int right=last;
        do{
            while(arr[left]<middle)
                left++;
            while(arr[right]>middle)
                right--;
            if(left<=right){
                double tmp=arr[left];
                arr[left]=arr[right];
                arr[right]=tmp;
                left++;
                right--;
            }
        }while(left<right);

        sort(arr,first,right);
        sort(arr,left,last);
    }


}
