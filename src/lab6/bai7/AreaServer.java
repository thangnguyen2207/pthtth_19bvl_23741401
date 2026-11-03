package lab6.bai7;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class AreaServer extends UnicastRemoteObject implements AreaInterface {

    public AreaServer() throws RemoteException {
        super();
    }

    public double rectangleArea(double width, double height) throws RemoteException {
        return width * height;
    }

    public double circleArea(double radius) throws RemoteException {
        return Math.PI * radius * radius;
    }

    public double triangleArea(double base, double height) throws RemoteException {
        return (base * height) / 2;
    }
}