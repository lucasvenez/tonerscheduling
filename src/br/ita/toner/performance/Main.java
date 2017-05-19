package br.ita.toner.performance;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import br.ita.toner.ga.GeneticAlgorithm;
import br.ita.toner.ga.data.DataLoader;
import br.ita.toner.ga.individual.Individual;
import br.ita.toner.ga.individual.SparseMatrix;

public class Main {

	static final DataLoader loader = new DataLoader();

	public static void main(String[] args) throws IOException {
		
		long start = System.currentTimeMillis();

		try (Stream<Path> paths = Files.walk(Paths.get("/home/lucas/workspace/toneis/resources"))) {

			paths.forEach(filePath -> {

				if (Files.isRegularFile(filePath)) {
					
					SparseMatrix matrix = loader.loadFileAsSparseMatrix(filePath.toString());

					for (int j = 0; j < 1; j++) {
						
						PrintWriter pw = null;
						try {
							pw = new PrintWriter(new File("performance_analysis.csv"));
							
							if (j == 0)
								pw.write("file,n,m,time,fitness\n");
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
						
						StringBuilder sb = new StringBuilder();
						
						GeneticAlgorithm ga = new GeneticAlgorithm(matrix);

						Individual i = ga.search();

						double time = (System.currentTimeMillis() - start) / 1000.0;

						double fitness = i.getFitness();

						int n = matrix.getNumberOfRows();

						int m = matrix.getNumberOfColumns();

						sb.append("'")
						  .append(filePath.toString().split("/")[6])
						  .append("'")
						  .append(",")
						  .append(n).append(",").append(m).append(",")
						  .append(time).append(",").append(fitness);
						
						System.out.println(sb.toString());
						
						pw.write(sb.append("\n").toString());
						
						pw.close();
					}
				}
			});
		}
		
        System.out.println("done!");
	}
}
