package sample;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBase {
    public static final String DBName = "warehouse.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:E:\\1-2\\CSE108\\Offline 6\\1805072\\WarehouseSystem\\" + DBName;

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USER_ID = "id";
    public static final String COLUMN_USER_TYPE = "user_type";
    public static final String COLUMN_USER_NAME = "username";
    public static final String COLUMN_USER_PASSWORD = "password";

    public static final String TABLE_CARS = "cars";
    public static final String COLUMN_CAR_ID = "id";
    public static final String COLUMN_CAR_REGISTRATION = "registration";
    public static final String COLUMN_CAR_YEAR = "year";
    public static final String COLUMN_CAR_MAKE = "make";
    public static final String COLUMN_CAR_MODEL = "model";
    public static final String COLUMN_CAR_COLOR1 = "color1";
    public static final String COLUMN_CAR_COLOR2 = "color2";
    public static final String COLUMN_CAR_COLOR3 = "color3";
    public static final String COLUMN_CAR_PRICE = "price";
    public static final String COLUMN_CAR_QUANTITY = "quantity";


    public static final String QUERY_USER_TYPE = "SELECT (SELECT " + COLUMN_USER_TYPE +
            " FROM " + TABLE_USERS +
            " WHERE " + COLUMN_USER_NAME + " = ?) AS " + COLUMN_USER_TYPE;

    public static final String QUERY_CHECK_PASSWORD = "SELECT " + COLUMN_USER_PASSWORD +
            " FROM " + TABLE_USERS +
            " WHERE " + COLUMN_USER_NAME + " = ?";

    public static final String QUERY_VIEW_CARS = "SELECT * FROM " + TABLE_CARS;

    public static final String QUERY_SEARCH_BY_REG = "SELECT * FROM " + TABLE_CARS +
            " WHERE " + COLUMN_CAR_REGISTRATION + " = ? COLLATE NOCASE";

    public static final String QUERY_SEARCH_BY_MAKE = "SELECT * FROM " + TABLE_CARS +
            " WHERE " + COLUMN_CAR_MAKE + " = ? COLLATE NOCASE";

    public static final String QUERY_SEARCH_BY_MODEL = "SELECT * FROM " + TABLE_CARS +
            " WHERE " + COLUMN_CAR_MAKE + " = ?" + " AND " + COLUMN_CAR_MODEL + " = ?" + " COLLATE NOCASE";

    public static final String QUERY_INSERT_CAR = "INSERT INTO " + TABLE_CARS +
            "(" + COLUMN_CAR_REGISTRATION + ", " + COLUMN_CAR_YEAR + ", " + COLUMN_CAR_MAKE + ", " + COLUMN_CAR_MODEL +
            ", " + COLUMN_CAR_COLOR1 + ", " + COLUMN_CAR_COLOR2 + ", " + COLUMN_CAR_COLOR3 + ", " + COLUMN_CAR_PRICE +
            ", " + COLUMN_CAR_QUANTITY + ") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String QUERY_EDIT_CAR = "UPDATE " + TABLE_CARS + " SET " +
            COLUMN_CAR_REGISTRATION + " = ?, " + COLUMN_CAR_YEAR + " = ?, " + COLUMN_CAR_MAKE + " = ?, " + COLUMN_CAR_MODEL +
            " = ?, " + COLUMN_CAR_COLOR1 + " = ?, " + COLUMN_CAR_COLOR2 + " = ?, " + COLUMN_CAR_COLOR3 + " = ?, " + COLUMN_CAR_PRICE +
            " = ?, " + COLUMN_CAR_QUANTITY + " = ?" + " WHERE " + COLUMN_CAR_ID + " = ?";

    public static final String QUERY_DELETE_CAR = "DELETE FROM " + TABLE_CARS + " WHERE " + COLUMN_CAR_ID + " = ?";

    public static final String QUERY_BUY_CAR = "UPDATE " + TABLE_CARS + " SET " +
            COLUMN_CAR_QUANTITY + " = ?" + " WHERE " + COLUMN_CAR_ID + " = ?";

    public static final String QUERY_IF_AVAILABLE = "SELECT (SELECT " + COLUMN_CAR_QUANTITY + " FROM " + TABLE_CARS +
            " WHERE "+ COLUMN_CAR_ID + " = ?) AS " + COLUMN_CAR_QUANTITY;

    private PreparedStatement queryUserType;
    private PreparedStatement queryCheckPassword;
    private PreparedStatement queryViewCars;
    private PreparedStatement querySearchCarsByReg;
    private PreparedStatement querySearchCarsByMake;
    private PreparedStatement querySearchCarsByModel;
    private PreparedStatement queryAddNewCar;
    private PreparedStatement queryEditCar;
    private PreparedStatement queryDeleteCar;
    private PreparedStatement queryBuyCar;
    private PreparedStatement queryIfAvailable;
    private Connection conn;

    private static DataBase instance = new DataBase();

    private DataBase() {

    }

    public static DataBase getInstance() {
        return instance;
    }

    public boolean open() {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            queryUserType = conn.prepareStatement(QUERY_USER_TYPE);
            queryCheckPassword = conn.prepareStatement(QUERY_CHECK_PASSWORD);
            queryViewCars = conn.prepareStatement(QUERY_VIEW_CARS);
            querySearchCarsByReg = conn.prepareStatement(QUERY_SEARCH_BY_REG);
            querySearchCarsByMake = conn.prepareStatement(QUERY_SEARCH_BY_MAKE);
            querySearchCarsByModel = conn.prepareStatement(QUERY_SEARCH_BY_MODEL);
            queryAddNewCar = conn.prepareStatement(QUERY_INSERT_CAR);
            queryEditCar = conn.prepareStatement(QUERY_EDIT_CAR);
            queryDeleteCar = conn.prepareStatement(QUERY_DELETE_CAR);
            queryBuyCar = conn.prepareStatement(QUERY_BUY_CAR);
            queryIfAvailable = conn.prepareStatement(QUERY_IF_AVAILABLE);
            return true;
        } catch (SQLException e) {
            System.out.println("Connection null: " + e.getMessage());
            return false;
        }
    }

    public void close() {
        try {
            if (queryUserType != null) {
                queryUserType.close();
            }
            if (queryCheckPassword != null) {
                queryCheckPassword.close();
            }
            if (queryViewCars != null) {
                queryViewCars.close();
            }
            if (querySearchCarsByReg != null) {
                querySearchCarsByReg.close();
            }
            if (querySearchCarsByMake != null) {
                querySearchCarsByMake.close();
            }
            if (querySearchCarsByModel != null) {
                querySearchCarsByModel.close();
            }
            if (queryAddNewCar != null) {
                queryAddNewCar.close();
            }
            if (queryEditCar != null) {
                queryEditCar.close();
            }
            if (queryDeleteCar != null) {
                queryDeleteCar.close();
            }
            if (queryBuyCar != null) {
                queryBuyCar.close();
            }
            if (queryIfAvailable != null) {
                queryIfAvailable.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Couldn't close connection: " + e.getMessage());
        }
    }

    public int getUserType(String username) throws SQLException {
        queryUserType.setString(1, username);
        ResultSet results = queryUserType.executeQuery();
        if(results!=null)
            return results.getInt(COLUMN_USER_TYPE);
        else{
            return 0;
        }
    }

    public boolean checkPassword(String username, String password) throws SQLException {
        queryCheckPassword.setString(1, username);
        ResultSet results = queryCheckPassword.executeQuery();
        if (results.getString(COLUMN_USER_PASSWORD).equals(password)) {
            return true;
        } else {
            return false;
        }
    }

    public List<Car> viewCars() {
        try {
            List<Car> carList = new ArrayList<>();
            return getCars(carList, queryViewCars);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Car> searchByRegistration(String reg) {
        try {
            List<Car> carList = new ArrayList<>();
            querySearchCarsByReg.setString(1, reg);
            return getCars(carList, querySearchCarsByReg);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Car> searchByMakerModel(String maker, String model) {
        try {
            List<Car> carList = new ArrayList<>();
            if(model.equalsIgnoreCase("any")){
                querySearchCarsByMake.setString(1, maker);
                return getCars(carList, querySearchCarsByMake);
            }else{
                querySearchCarsByModel.setString(1, maker);
                querySearchCarsByModel.setString(2, model);
                return getCars(carList, querySearchCarsByModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<Car> getCars(List<Car> carList, PreparedStatement query) throws SQLException {
        ResultSet results = query.executeQuery();
        if (results != null) {
            while (results.next()) {
                Car car = new Car();
                car.setId(results.getInt(COLUMN_CAR_ID));
                car.setRegistration(results.getString(COLUMN_CAR_REGISTRATION));
                car.setYear(results.getInt(COLUMN_CAR_YEAR));
                car.setMake(results.getString(COLUMN_CAR_MAKE));
                car.setModel(results.getString(COLUMN_CAR_MODEL));
                car.setColor1(results.getString(COLUMN_CAR_COLOR1));
                car.setColor2(results.getString(COLUMN_CAR_COLOR2));
                car.setColor3(results.getString(COLUMN_CAR_COLOR3));
                car.setPrice(results.getInt(COLUMN_CAR_PRICE));
                car.setQuantity(results.getInt(COLUMN_CAR_QUANTITY));
                carList.add(car);
            }
        } else {
            System.out.println("results null pacche");
        }
        return carList;
    }

    public boolean saveChanges(Car car) throws SQLException {
        queryEditCar.setString(1, car.getRegistration());
        queryEditCar.setInt(2, car.getYear());
        queryEditCar.setString(3, car.getMake());
        queryEditCar.setString(4, car.getModel());
        queryEditCar.setString(5, car.getColor1());
        queryEditCar.setString(6, car.getColor2());
        queryEditCar.setString(7, car.getColor3());
        queryEditCar.setInt(8, car.getPrice());
        queryEditCar.setInt(9, car.getQuantity());
        queryEditCar.setInt(10, car.getId());
        int peep = queryEditCar.executeUpdate();
        return peep > 0;
    }

    public boolean addCar(Car car) throws SQLException {
        queryAddNewCar.setString(1, car.getRegistration());
        queryAddNewCar.setInt(2, car.getYear());
        queryAddNewCar.setString(3, car.getMake());
        queryAddNewCar.setString(4, car.getModel());
        queryAddNewCar.setString(5, car.getColor1());
        queryAddNewCar.setString(6, car.getColor2());
        queryAddNewCar.setString(7, car.getColor3());
        queryAddNewCar.setInt(8, car.getPrice());
        queryAddNewCar.setInt(9, car.getQuantity());
        int peep = queryAddNewCar.executeUpdate();
        return peep > 0;
    }

    public boolean deleteCar(int id) throws SQLException {
        queryDeleteCar.setInt(1, id);
        int peep = queryDeleteCar.executeUpdate();
        return peep > 0;
    }

    public Boolean buyCar(int id) throws SQLException {
        queryIfAvailable.setInt(1,id);
        ResultSet results = queryIfAvailable.executeQuery();
        if(results != null) {
            int quantity = results.getInt(COLUMN_CAR_QUANTITY);
            System.out.println("DB quantity : "+quantity);
            if(quantity>0) {
                quantity-=1;
                queryBuyCar.setInt(1, quantity);
                queryBuyCar.setInt(2, id);
                int peep = queryBuyCar.executeUpdate();
                return peep > 0;
            }else if(quantity==0){
                deleteCar(id);
            }
        }
        return false;
    }
}