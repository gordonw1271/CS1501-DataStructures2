package cs1501_p3;

import java.util.NoSuchElementException;
import java.io.IOException;

public class PricePQ
{
    private Car[] priceArray;
    private DLB priceIndex;
    private int count;
    //==============================================================================
    public PricePQ(){
        priceArray = new Car[1];
        priceIndex = new DLB();
        this.count = 0;
    }
    public Car[] getArray(){
        return priceArray;
    }
    public DLB getIndex(){
        return priceIndex;
    }
    //=========================== ADD ==============================================
    public void add(Car c)throws IllegalStateException{
        DLBNode car = priceIndex.find(c.getVIN());
        if(car == null){
            if(priceArray.length == this.count){     //array is full
                Car[] priceTemp = new Car[priceArray.length * 2];
                for(int i=0;i<count;i++){
                    priceTemp[i] = this.priceArray[i];
                }
                priceTemp[count] = c;
                this.priceArray = priceTemp;
            }else{          //array is not full
                this.priceArray[count] = c;
            }
            priceIndex.add(c.getVIN(),count);
            count++;
            restorePriceArrayUp(count-1);
        }else{
            //do nothing, duplicate car
            throw new IllegalStateException();
        }
    }

    private void restorePriceArrayUp(int index){
        if(index == 0){
            //do nothing
        }else{
            int parentIndex = (index - 1) / 2;
            if(priceArray[index].getPrice() < priceArray[parentIndex].getPrice()){
                Car temp = priceArray[index];
                priceArray[index] = priceArray[parentIndex];
                priceArray[parentIndex] = temp;

                priceIndex.find(priceArray[index].getVIN()).setIndex(index);
                priceIndex.find(priceArray[parentIndex].getVIN()).setIndex(parentIndex);
                restorePriceArrayUp(parentIndex);
            }
        }
    }
    //=========================== GET ==============================================
    public Car get(String vin) throws NoSuchElementException{
        DLBNode temp = priceIndex.find(vin);
        if(temp == null){
            throw new NoSuchElementException();
        }else{
            return priceArray[temp.getIndex()];
        }
    }
    //=========================== UPDATE PRICE ======================================
    public void updatePrice(String vin, int newPrice) throws NoSuchElementException{
        DLBNode temp = priceIndex.find(vin);
        if(temp==null){
            throw new NoSuchElementException();
        }else{
            int ind = temp.getIndex();
            priceArray[ind].setPrice(newPrice);
            restorePriceArrayDown(ind);
            restorePriceArrayUp(ind);
        }
    }

    private void restorePriceArrayDown(int index){
        int rightIndex = 2*index+2;
        int leftIndex = 2*index+1;

        if(leftIndex > this.count-1){
            //do nothing
        }else if(rightIndex <= this.count-1){ // has left and right child
            int ind;
            if(priceArray[rightIndex].getPrice() < priceArray[leftIndex].getPrice()){
                // Right has higher priority
                ind = rightIndex;
            }else{
                ind = leftIndex;
            }
            
            if(priceArray[index].getPrice() > priceArray[ind].getPrice()){
                Car temp = priceArray[index];
                priceArray[index] = priceArray[ind];
                priceArray[ind] = temp;

                priceIndex.find(priceArray[index].getVIN()).setIndex(index);
                priceIndex.find(priceArray[ind].getVIN()).setIndex(ind);

                restorePriceArrayDown(ind);
            }
        }else{  // has only left child
            if(priceArray[index].getPrice() > priceArray[leftIndex].getPrice()){
                Car temp = priceArray[index];
                priceArray[index] = priceArray[leftIndex];
                priceArray[leftIndex] = temp;

                priceIndex.find(priceArray[index].getVIN()).setIndex(index);
                priceIndex.find(priceArray[leftIndex].getVIN()).setIndex(leftIndex);

                restorePriceArrayDown(leftIndex);
            }
        }
    }
    //=========================== SET NEW MILEAGE ====================================
    public void updateMileage(String vin, int newMileage)throws NoSuchElementException{
        DLBNode car = priceIndex.find(vin);
        if(car == null){
            throw new NoSuchElementException();
        }else{
            priceArray[car.getIndex()].setMileage(newMileage);
        }
    }
    //=========================== SET NEW COLOR ======================================
    public void updateColor(String vin, String newColor) throws NoSuchElementException{
        DLBNode car = priceIndex.find(vin);
        if(car == null){
            throw new NoSuchElementException();
        }else{
            priceArray[car.getIndex()].setColor(newColor);
        }
    }
    //================================= REMOVE ======================================
    public void remove(String vin) throws NoSuchElementException{
        DLBNode removeCar = priceIndex.find(vin);
        if(removeCar == null || removeCar.getIndex() == -1){
            throw new NoSuchElementException();
        }else{
            int ind = removeCar.getIndex();
            removeCar.setIndex(-1);

            if(ind == count -1){
                priceArray[ind] = null;
                count--;
            }else{
                priceArray[ind] = priceArray[count - 1];
                priceArray[count - 1] = null;
                
                priceIndex.find(priceArray[ind].getVIN()).setIndex(ind);
                count--;
                restorePriceArrayDown(ind);
                restorePriceArrayUp(ind);
            }
        }
    }
    //=========================== GET LOW PRICE ======================================
    public Car getLowPrice(){
        if(count == 0){
            return null;
        }else{
            return priceArray[0];
        }
    }
}