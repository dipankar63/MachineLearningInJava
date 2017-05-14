package com.dipankar.machinelearning;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LinearRegression {
	private static class Data {
		private double x;
		private double y;

		public Data(double x, double y) {
			this.x = x;
			this.y = y;
		}
	}

	private static class Parameter {
		private double m;
		private double b;

		public Parameter(double m, double b) {
			this.m = m;
			this.b = b;
		}
	}

	public static void main(String args[]) {
		Model model = new Model();
		model.process();

	}

	static class Model {

		private void gradientDescent(List<Data> points, Parameter parameter, double learningRate, int numItearations) {
			for (int i = 0; i < numItearations; i++) {
				double[] gd = stepGradient(points, parameter, learningRate);
				parameter.m -= learningRate * gd[0];
				parameter.b -= learningRate * gd[1];
			}

		}

		private double[] stepGradient(List<Data> points, Parameter parameter, double learningRate) {
			int N = points.size();
			double gdm = 0;
			double gdb = 0;
			for (Data d : points) {
				double x = d.x;
				double y = d.y;
				gdm += (2 / (float) N) * (-1 * x * (y - (parameter.m * x + parameter.b)));
				gdb += (2 / (float) N) * (-1 * (y - (parameter.m * x + parameter.b)));

			}
			return new double[] { gdm, gdb };
		}

		private List<Data> getTheDataPoints(String fileName) {
			List<Data> dataPoints = new ArrayList<>();
			try (FileReader fr = new FileReader(new File(fileName)); BufferedReader br = new BufferedReader(fr)) {
				String readline;
				while ((readline = br.readLine()) != null) {
					String[] a = readline.split(",");
					double x = Double.parseDouble(a[0]);
					double y = Double.parseDouble(a[1]);
					dataPoints.add(new Data(x, y));
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return dataPoints;
		}

		public void process() {
			// Get the data
			/*
			 * date file name is data12
			 * data are in below format
			 * 1,2
			   2,4
			   4,8				
			 */
			List<Data> points = getTheDataPoints("data12");
			// Define the hyper parameters
			double learningRate = .0001;
			int numIterations = 1000;
			/*
			 * Intialize m & b as 0
			 */
			Parameter parameter = new Parameter(0, 0);
			gradientDescent(points, parameter, learningRate, numIterations);
			System.out.println("After Gradient Descent Paramters are: m = " + parameter.m + "b = " + parameter.b);

		}

	}

}
