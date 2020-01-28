Realizado por Jimmy Andres Moya Suarez y Orlando Antonio Gelves Kerguelen

# ARSW-Labs

# Lab01 (BBP Formula)

Este laboratorio contiene ejercicios de introducción a la programacion en java usando hilos.

1. **Part I - Introducción a hilos en java**
La diferencia entre start() y run() es que al usar start , no espera a que acabe un proceso para ejecutar el siguiente, se sobrepone uno sobre otro, al usar run() realiza el proceso en el orden programado, adicional a esto el metodo start usa 2 hilos de forma concurrente uno que usa el metodo start y el otro usa el metodo run, al llamar rl metodo run, solo se llama a si mismo.

![](Capturas/Captura.PNG)

2. **Part II - BBP Formula Exercise**

Se modifico la funcion "PiDigits.getDigits()" para que reciba un parametro N el cual corresponde al numero de hilos en el que se va a dividir la solución de la siguiente forma:

![](Capturas/getDigits.PNG)

Se realizo la siguiente prueba en la cual se usa de 1 a 3 hilos para general la solución:

![](Capturas/PruebaDigits.PNG)

3. **Part III - Performance Evaluation**
