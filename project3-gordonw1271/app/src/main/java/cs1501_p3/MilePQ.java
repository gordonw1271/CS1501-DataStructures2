package cs1501_p3;

import java.util.NoSuchElementException;
import java.io.IOException;

public class MilePQ
{
    private Car[] mileArray;
    private DLB mileIndex;
    private int count;
    //==============================================================================
    public MilePQ(){
        mileArray = new Car[1];
        mileIndex = new DLB();
        this.count = 0;
    }
    public Car[] getArray(){
        return mileArray;
    }
    public DLB getIndex(){
        return mileIndex;
    }
    //=========================== ADD ==============================================
    public void add(Car c)throws IllegalStateException{
        if(mileArray.length == this.count){     //array is full
            Car[] mileTemp = new Car[mileArray.length * 2];
            for(int i=0;i<count;i++){
                mileTemp[i] = this.mileArray[i];
            }
            mileTemp[count] = c;
            this.mileArray = mileTemp;
        }else{          //array is not full
            this.mileArray[count] = c;
        }
        mileIndex.add(c.getVIN(),count);
        count++;
        restoreMileArrayUp(count-1);
    }

    private void restoreMileArrayUp(int index){
        if(index == 0){
            //do nothing
        }else{
            int parentIndex = (index - 1) / 2;
            if(mileArray[index].getMileage() < mileArray[parentIndex].getMileage()){
                Car temp = mileArray[index];
                mileArray[index] = mileArray[parentIndex];
                mileArray[parentIndex] = temp;

                mileIndex.find(mileArray[index].getVIN()).setIndex(index);
                mileIndex.find(mileArray[parentIndex].getVIN()).setIndex(parentIndex);
                restoreMileArrayUp(parentIndex);
            }
        }
    }
    //=========================== UPDATE PRICE ======================================
    public void updatePrice(String vin, int newPrice) throws NoSuchElementException{
        DLBNode temp = mileIndex.find(vin);
        int ind = temp.getIndex();

        mileArray[ind].setPrice(newPrice);
    }
    //=========================== SET NEW MILEAGE ====================================
    public void updateMileage(String vin, int newMileage)throws NoSuchElementException{
        DLBNode car = mileIndex.find(vin);
        int index = car.getIndex();
        mileArray[index].setMileage(newMileage);
        restoreMileArrayDown(index);
        restoreMileArrayUp(index);
    }
    private void restoreMileArrayDown(int index){
        int rightIndex = 2*index+2;
        int leftIndex = 2*index+1;

        if(leftIndex > this.count-1){
            //do nothing
        }else{
            if(rightIndex <= this.count-1){ // has left and right child
                int ind;
                if(mileArray[rightIndex].getMileage() < mileArray[leftIndex].getMileage()){
                    // Right has higher priority
                    ind = rightIndex;
                }else{
                    ind = leftIndex;
                }
                
                if(mileArray[index].getMileage() > mileArray[ind].getMileage()){
                    Car temp = mileArray[index];
                    mileArray[index] = mileArray[ind];
                    mileArray[ind] = temp;

                    mileIndex.find(mileArray[index].getVIN()).setIndex(index);
                    mileIndex.find(mileArray[ind].getVIN()).setIndex(ind);

                    restoreMileArrayDown(ind);
                }
            }else{  // has only left child
                if(mileArray[index].getMileage() > mileArray[leftIndex].getMileage()){
                    Car temp = mileArray[index];
                    mileArray[index] = mileArray[leftIndex];
                    mileArray[leftIndex] = temp;

                    mileIndex.find(mileArray[index].getVIN()).setIndex(index);
                    mileIndex.find(mileArray[leftIndex].getVIN()).setIndex(leftIndex);

                    restoreMileArrayDown(leftIndex);
                }
            }
        }
    }
    //=========================== SET NEW COLOR ======================================
    public void updateColor(String vin, String newColor) throws NoSuchElementException{
        DLBNode car = mileIndex.find(vin);
        mileArray[car.getIndex()].setColor(newColor);
    }
    //================================= REMOVE ======================================
    public void remove(String vin) throws NoSuchElementException{
        DLBNode removeCar = mileIndex.find(vin);
        int ind = removeCar.getIndex();
        removeCar.setIndex(-1);

        if(ind == count -1){
            mileArray[ind] = null;
            count--;
        }else{
            mileArray[ind] = mileArray[count - 1];
            mileArray[count - 1] = null;
            
            mileIndex.find(mileArray[ind].getVIN()).setIndex(ind);
            count--;
            restoreMileArrayDown(ind);
            restoreMileArrayUp(ind);
        }
    }
    //=========================== GET LOW MILEAGE ====================================
    public Car getLowMileage(){
        if(count == 0){
            return null;
        }else{
            return mileArray[0];
        }
    }
}