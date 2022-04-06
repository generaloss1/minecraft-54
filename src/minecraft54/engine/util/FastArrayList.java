package minecraft54.engine.util;

public class FastArrayList<T>{


    private Object[] arrayList;
    private int elementsInArray;


    public FastArrayList(){
        this(16);
    }

    public FastArrayList(int n){
        arrayList=new Object[n];
    }

    public void add(T x){
        if(checkIfArrayFull())
            copyArray(true);

        arrayList[elementsInArray]=x;
        elementsInArray++;
    }

    public void set(int index,T x){
        if(index>=arrayList.length)
            return;
        arrayList[index]=x;
    }

    public void add(int index,T x){
        if(checkIfArrayFull())
            copyArray(true);

        if(index>=arrayList.length)
            return;

        Object temp=arrayList[index];
        arrayList[index]=x;

        Object temp2;
        for(int i=index; i<arrayList.length-1; i++){
            temp2=arrayList[i+1];
            arrayList[i+1]=temp;
            temp=temp2;
        }

        copyArray(false);
        elementsInArray++;
    }

    public T get(int index){
        return (T)arrayList[index];
    }

    public int size(){
        return elementsInArray;
    }

    public boolean isEmpty(){
        return elementsInArray==0;
    }

    public boolean contains(Object o){
        return find(o)>=0;
    }

    public int find(Object n){
        if(n==null)
            return -1;
        for(int i=0; i<arrayList.length; i++)
            if(n.equals(arrayList[i]))
                return i;
        return -1;
    }

    public synchronized void remove(T n){
        for(int i=0; i<elementsInArray; i++){
            if(n.equals(arrayList[i])){
                arrayList[i]=null;
                elementsInArray--;
                copyArray(false);
                return;
            }
        }
    }

    public synchronized void remove(int i){
        arrayList[i]=null;
        elementsInArray--;
        copyArray(false);
    }

    public void clear(){
        for(int i=0; i<elementsInArray; i++)
            arrayList[i]=null;
        arrayList=new Object[10];
        elementsInArray=0;
    }

    private boolean checkIfArrayFull(){
        return arrayList.length==elementsInArray;
    }

    private void copyArray(boolean doubleIncrease){
        Object[] tempArray=new Object[arrayList.length*(doubleIncrease?2:1)];

        int tempElement=0;
        for(int i=0; i<arrayList.length; i++,tempElement++){
            if(arrayList[i]==null){
                tempElement--;
                continue;
            }
            tempArray[tempElement]=arrayList[i];
        }

        arrayList=tempArray;
    }

}