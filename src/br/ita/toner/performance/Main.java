package br.ita.toner.performance;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import br.ita.toner.data.DataLoader;
import br.ita.toner.data.Individual;
import br.ita.toner.data.SparseMatrix;
import br.ita.toner.ga.GeneticAlgorithm;

public class Main {

	static final DataLoader loader = new DataLoader();

	public static void main(String[] args) throws IOException {
		
		PrintWriter pw = null;
		
		try {
			pw = new PrintWriter(new File("performance_analysis.csv"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("file,n,m,n_x_m,time,fitness,maxtoner\n");
		
		try (Stream<Path> paths = Files.walk(Paths.get("/home/lucas/git/tonerscheduling/resources"))) {
			
			paths.forEach(filePath -> {

				if (Files.isRegularFile(filePath)) {
					
					SparseMatrix matrix = loader.loadFileAsSparseMatrix(filePath.toString());

					for (int j = 0; j < 1; j++) {
						
						GeneticAlgorithm ga = new GeneticAlgorithm(matrix);

						long start = System.currentTimeMillis();
						
						Individual i = ga.search();

						double time = (System.currentTimeMillis() - start) / 1000.0;

						double fitness = i.getFitness();

						int n = matrix.getNumberOfRows();

						int m = matrix.getNumberOfColumns();

						int maxToner = ga.getMaxToner(i, matrix);
						
						sb.append("'").append(filePath).append("'")
						  .append(",")
						  .append(n).append(",").append(m).append(",").append(n*m)
						  .append(",")
						  .append(time)
						  .append(",")
						  .append(fitness)
						  .append(",")
						  .append(maxToner)
						  .append("\n");
						
						System.out.println("'" + filePath + "'," + n + "," + m + "," + (n*m) + "," + time + "," + fitness + "," + maxToner);
					}
				}
			});
		}
		
		pw.write(sb.toString());
		
		pw.close();
		
        System.out.println("done!");
	}
}
