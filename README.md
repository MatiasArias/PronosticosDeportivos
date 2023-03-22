# PronosticosDeportivos - Argentina Programa - Desarrollador Java Inicial

## Introducción
Nos han solicitado el desarrollo de un programa de Pronósticos Deportivos.
Un pronóstico deportivo consta de un posible resultado de un partido (que un equipo gane,
pierda o empate), propuesto por una persona que está participando de una competencia
contra otras.
Cada partido tendrá un resultado. Este resultado se utilizará para otorgar puntos a los
participantes de la competencia según el acierto de sus pronósticos.
Finalmente, quien gane la competencia será aquella persona que sume mayor cantidad de
puntos.
## Consigna
La propuesta del trabajo práctico consiste en implementar un programa de consola que dada
la información de resultados de partidos e información de pronósticos, ordene por puntaje
obtenido a los participantes.
## Alcance
En este trabajo práctico nos limitaremos a pronosticar los resultados de los partidos, sin
importar los goles ni la estructura del torneo (si es grupo, eliminatoria u otro); simplemente se
sumarán puntos y se obtendrá un listado final.
A continuación, se propone un diagrama de clases inicial que puede ser modificado en
cualquier momento.

<p align="center">

<img src=https://user-images.githubusercontent.com/71903793/227000356-876d5f6b-77cb-49fc-ab13-2f473886dd57.png>

</p>

### Entrega 1
A partir del esquema original propuesto, desarrollar un programa que lea un archivo de
partidos y otro de resultados, el primero correspondiente a una ronda y el otro que contenga
los pronósticos de una persona (Se considera una única ronda y un único participante en esta entrega). Cada ronda debe tener una cantidad fija de partidos, por
ejemplo 2. El programa debe:

* Estar subido en un repositorio de GIT
* Tomar como argumento 2 rutas a cada archivo que se necesita
* Al leer las líneas de los archivos debe instanciar objetos de las clases propuestas
* Debe imprimir por pantalla el puntaje de la persona

__Importante__

Se debe considerar la forma de identificar los partidos de forma unívoca para su correcto
procesamiento. Está permitido modificar la estructura del archivo si así lo considera.

#### Ejemplo

Archivo **resultados.csv**

| Equipo 1  | Cant. goles 1 | Cant. goles 2 | Equipo 2       |
| --------- | -------------| -------------| -------------- |
| Argentina | 1            | 2            | Arabia Saudita |
| Polonia   | 0            | 0            | México         |

Archivo **pronosticos.csv**

| Equipo | Gana 1 | Empata | Gana 2 | Equipo |
|--------|--------|--------|--------|--------|
| Argentina | X |   |   | Arabia Saudita |
| Polonia |  | X |   | México |

Leyendo los 2 (dos) archivos, y suponiendo que cada resultado acertado suma 1 (un) punto, la
salida del programa debe ser: Puntaje = 1


