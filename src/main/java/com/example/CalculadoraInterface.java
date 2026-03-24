package com.example;

import java.rmi.Remote;
import java.rmi.RemoteException;

// Extiende Remote: Le dice a java que esta interfaz puede ser usada desde otra maquina por la red
public interface CalculadoraInterface extends Remote {

    // Cada metodo lanza RemoteException porque la llamada viaja por red y puede falar en cualquier momento

    double sumar(double a, double b) throws RemoteException;
    double restar(double a, double b) throws RemoteException;
    double multiplicar(double a, double b) throws RemoteException;
    double dividir(double a, double b) throws RemoteException; // Valida que b != 0 en la implementacion
}