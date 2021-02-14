package sample;

import java.util.ArrayList;
import java.util.List;

public class ReadServer implements Runnable{
    Thread thr;
    NetworkUtil networkUtil;
    static ArrayList<String> loggedIn = new ArrayList<>();
    public ReadServer(NetworkUtil networkUtil) {
        this.networkUtil = networkUtil;
        this.thr = new Thread(this);
        thr.start();
    }

    public void run() {
        try {
            while (true) {
                Object o = networkUtil.read();
                if (o instanceof String) {
                    String text = (String) o;
                    String[] splitText = text.split("#");
                    if(splitText[0].equals("U")){
                        if(!loggedIn.contains(splitText[1])) {
                            try {
                                int type = DataBase.getInstance().getUserType(splitText[1]);
                                if (type == 1) {
                                    loggedIn.add(splitText[1]);
                                    networkUtil.write("MANUFACTURER");
                                } else if (type == 2) {
                                    loggedIn.add(splitText[1]);
                                    networkUtil.write("VIEWER");
                                }else{
                                    networkUtil.write("FAILURE");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }else{
                            networkUtil.write("ALREADY LOGGED IN");
                        }
                    }else if(splitText[0].equals("P")){
                        try{
                            boolean check = DataBase.getInstance().checkPassword(splitText[1],splitText[2]);
                            if(check){
                                networkUtil.write("SUCCESS");
                            }else{
                                networkUtil.write("FAILURE");
                            }
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }else if(splitText[0].equals("V")){
                        try{
                            List<Car> carList = DataBase.getInstance().viewCars();
                            networkUtil.write(carList);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else if(splitText[0].equals("V2")){
                        try{
                            List<Car> carList = DataBase.getInstance().viewCars();
                            networkUtil.write(carList);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if(splitText[0].equals("SR")){
                        try{
                            List<Car> carList = DataBase.getInstance().searchByRegistration(splitText[1]);
                            networkUtil.write(carList);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else if(splitText[0].equals("SM")){
                        try{
                            List<Car> carList = DataBase.getInstance().searchByMakerModel(splitText[1],splitText[2]);
                            networkUtil.write(carList);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else if(splitText[0].equals("A")){
                        try{
                            Car car = new Car();
                            car.setRegistration(splitText[1]);
                            car.setYear(Integer.parseInt(splitText[2]));
                            car.setMake(splitText[3]);
                            car.setModel(splitText[4]);
                            car.setColor1(splitText[5]);
                            car.setColor2(splitText[6]);
                            car.setColor3(splitText[7]);
                            car.setPrice(Integer.parseInt(splitText[8]));
                            car.setQuantity(Integer.parseInt(splitText[9]));
                            boolean check = DataBase.getInstance().addCar(car);
                            if(check)
                                networkUtil.write("SUCCESS");
                            else
                                networkUtil.write("FAILURE");
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }else if(splitText[0].equals("D")){
                        try{
                            boolean check = DataBase.getInstance().deleteCar(Integer.parseInt(splitText[1]));
                            if(check)
                                networkUtil.write("SUCCESS");
                            else
                                networkUtil.write("FAILURE");
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }else if(splitText[0].equals("B")){
                        try{
                            Boolean check = DataBase.getInstance().buyCar(Integer.parseInt(splitText[1]));
                            if(check)
                                networkUtil.write("SUCCESS");
                            else
                                networkUtil.write("FAILURE");
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                } else if(o instanceof Car){
                    Car car= (Car)o;
                    try{
                        boolean check = DataBase.getInstance().saveChanges(car);
                        if(check){
                            networkUtil.write("SUCCESS");
                        }else{
                            networkUtil.write("FAILURE");
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("ReadServer caught error");
        }
    }
}
