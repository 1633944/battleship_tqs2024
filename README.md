# Proyecto: Batalla Naval - Test i qualitat del software

**Eric Chaves Sánchez** - 1633944  
**Pau Leyva García** - 1636526  
**Dimecres 10:30**  

Este proyecto implementa un juego de **Batalla Naval** en Java y contiene pruebas unitarias para verificar el correcto funcionamiento de la lógica del juego, especialmente la clase PartidaController. Las pruebas unitarias utilizan la biblioteca **Mockito** para simular las dependencias y aislar el código bajo prueba.

## Descripción

El juego de **Batalla Naval** se juega entre dos jugadores, cada uno con un tablero y barcos. Los jugadores se turnan para disparar y tratar de hundir los barcos del oponente. El juego finaliza cuando uno de los jugadores ha hundido todos los barcos del otro.

### Características principales:

- **Jugadores**: Dos jugadores interactúan en el juego, cada uno tiene un tablero para colocar sus barcos.
- **Barcos**: Los jugadores colocan barcos en sus tableros y los disparos pueden ser "Tocados", "Tocados y hundidos" o "Agua".
- **Turnos**: Los jugadores se turnan para disparar a las posiciones del tablero del oponente.
- **Condiciones de Victoria**: El juego termina cuando un jugador hunde todos los barcos del oponente.

## Estructura del Proyecto

La estructura del proyecto es la siguiente:


- **controller**: Contiene la clase PartidaController, que gestiona el flujo del juego.
- **model**: Incluye las clases Partida, Jugador, Barco, Tablero, que representan los elementos principales del juego.
- **view**: Contiene la interfaz para interactuar con el jugador a través de la consola.
- **test**: Contiene las pruebas unitarias para garantizar el funcionamiento del controlador y la lógica del juego.

## Requisitos

- JDK 11 o superior
- **JUnit 5** para realizar las pruebas unitarias.
- **Mockito** para simular dependencias y realizar pruebas unitarias más efectivas.
