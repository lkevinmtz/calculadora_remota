package com.example;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

// CLIENTE
// Se conecta al servidor, obtiene la calculadora remota y permite al usuario operar con un menú
public class ClienteCalc {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            CalculadoraInterface calc = (CalculadoraInterface) registry.lookup("Calculadora");
            Scanner scanner = new Scanner(System.in);
            int opcion = -1;

            System.out.println("=======================");
            System.out.println("Calculadora RMI");
            System.out.println("=======================");

            while (opcion != 5) {
                System.out.println("\n --- MENU ---");
                System.out.println("1. Suma");
                System.out.println("2. Resta");
                System.out.println("3. Multiplicación");
                System.out.println("4. División");
                System.out.println("5. Salir");
                System.out.println("Elige una opcíon: ");

                if (scanner.hasNextInt()) {
                    opcion = scanner.nextInt();
                    scanner.nextLine(); // Limpiar el buffer

                } else {
                    System.out.println("[!] Opción inválida. Ingresa un número del 1 al 5.");
                    scanner.nextLong();
                    continue;
                }

                // Opciones 1 a 4 necesitan dos números
                if (opcion >= 1 && opcion <= 4) {
                    System.out.println("Numero A: ");
                    double a = 0, b = 0;

                    // Leer y validar el primer numero
                    if (scanner.hasNextDouble()) {
                        a = scanner.nextDouble();
                    } else {
                        System.out.println("[!] Valor Invalido.");
                        scanner.nextLine();
                        continue;
                    }

                    // Leer y validar el segundo numero
                    if (scanner.hasNextDouble()) {
                        b = scanner.nextDouble();
                    } else {
                        System.out.println("[!] Valor Invalido.");
                        scanner.nextLine();
                        continue;
                    }

                    // Ejecutar la operación elegida.
                    // Cada llamada viaja por red al servidor, que calcula y devuelve el resultado.
                    switch (opcion) {
                        case 1:
                            System.out.println(" Resultado: " + a + " + " + b + " = " + calc.sumar(a, b));
                            break;
                        case 2:
                            System.out.println(" Resultado: " + a + " - " + b + " = " + calc.restar(a, b));
                            break;
                        case 3:
                            System.out.println(" Resultado: " + a + " * " + b + " = " + calc.multiplicar(a, b));
                            break;
                        case 4:
                            // División: captura la excepción si el usuario intenta dividir entre cero
                            try {
                                System.out.println(" Resultado: " + a + " / " + b + " = " + calc.dividir(a, b));
                            } catch (Exception e) {
                                System.out.println(" [!] " + e.getMessage());
                            }
                            break;
                    }

                } else if (opcion == 5) {
                    System.out.println(" Cerrando calculadora. Hasta luego.");
                } else {
                    System.out.println(" [!] Opcion no valida. Elige entre 1 y 5.");
                }
            }

            scanner.close();

        } catch (Exception e) {
            System.err.println("Error en el cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }
}