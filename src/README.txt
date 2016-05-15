To compile on server:
javac -cp .:mazeSolver/SampleSolver.jar *.java

To run on server:
java -cp .:mazeSolver/SampleSolver.jar MazeTester inputFilename n

PARA FILE:
(normal, tunnel, hex)
(recurBack, kruskal, modiPrim) (none, sample, bidir, recurBack)
(row) (col) size
(row) (col) entrance
(row) (col) exit
() () () () tunnel list