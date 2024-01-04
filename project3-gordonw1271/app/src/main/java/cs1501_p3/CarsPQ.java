package cs1501_p3;

import java.util.NoSuchElementException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CarsPQ implements CarsPQ_Inter
{
    private PricePQ pricePQ;
    private MilePQ milePQ;
    private DLB makeModelDLB;

    public CarsPQ(){
        pricePQ = new PricePQ();
        milePQ = new MilePQ();
        makeModelDLB = new DLB();
    }

    public CarsPQ(String file){
        pricePQ = new PricePQ();
        milePQ = new MilePQ();
        makeModelDLB = new DLB();

        BufferedReader carFile = null;
        try {
            carFile = new BufferedReader(new FileReader(file));
            String line;
            line = carFile.readLine(); //get rid of first line

            while ((line = carFile.readLine()) != null) {
                String[] props = line.split(":");
                Car c = new Car(props[0],props[1],props[2],Integer.parseInt(props[3]),Integer.parseInt(props[4]),props[5]);
                add(c);
            }
            carFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void add(Car c) throws IllegalStateException{
        pricePQ.add(c);
        milePQ.add(c);

        String car = c.getMake()+c.getModel();
        makeModelDLB.add(car,0);
        DLBNode node = makeModelDLB.find(car);
        node.getPricePQ().add(c);
        node.getMilePQ().add(c);
    }

    public Car get(String vin) throws NoSuchElementException{
        return pricePQ.get(vin);
    }

    public void updatePrice(String vin, int newPrice) throws NoSuchElementException{
        pricePQ.updatePrice(vin,newPrice);
        milePQ.updatePrice(vin, newPrice);

        Car c = pricePQ.get(vin);
        String car = c.getMake()+c.getModel();
        DLBNode node = makeModelDLB.find(car);
        node.getPricePQ().updatePrice(vin, newPrice);
        node.getMilePQ().updatePrice(vin, newPrice);
    }

    public void updateMileage(String vin, int newMileage) throws NoSuchElementException{
        pricePQ.updateMileage(vin, newMileage);
        milePQ.updateMileage(vin, newMileage);

        Car c = pricePQ.get(vin);
        String car = c.getMake()+c.getModel();
        DLBNode node = makeModelDLB.find(car);
        node.getPricePQ().updateMileage(vin, newMileage);
        node.getMilePQ().updateMileage(vin, newMileage);
    }

    public void updateColor(String vin, String newColor) throws NoSuchElementException{
        pricePQ.updateColor(vin, newColor);
        milePQ.updateColor(vin, newColor);
        
        Car c = pricePQ.get(vin);
        String car = c.getMake()+c.getModel();
        DLBNode node = makeModelDLB.find(car);
        node.getPricePQ().updateColor(vin, newColor);
        node.getMilePQ().updateColor(vin, newColor);
    }

    public void remove(String vin) throws NoSuchElementException{

        Car c = pricePQ.get(vin);

        pricePQ.remove(vin);
        milePQ.remove(vin);

        DLBNode node = makeModelDLB.find(c.getMake()+c.getModel());
        node.getPricePQ().remove(vin);
        node.getMilePQ().remove(vin);        
    }

    public Car getLowPrice(){
        return pricePQ.getLowPrice();
    }

    public Car getLowPrice(String make, String model){
        DLBNode node = makeModelDLB.find(make+model);
        if(node == null){
            return null;
        }
        return node.getPricePQ().getLowPrice();
    }

    public Car getLowMileage(){
        return milePQ.getLowMileage();
    }

    public Car getLowMileage(String make, String model){
        DLBNode node = makeModelDLB.find(make+model);
        if(node == null){
            return null;
        }
        return node.getMilePQ().getLowMileage();
    }
}