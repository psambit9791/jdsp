/*
 *
 *  * Copyright (c) 2020 Sambit Paul
 *  *
 *  * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *  *
 *  * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *  *
 *  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package com.github.psambit9791.jdsp.misc;

import org.apache.commons.math3.distribution.NormalDistribution;
/**
 * <h1>Random Module</h1>
 * The Random class is used to generate pseudorandom numbers as samples or in different array dimensions.
 * The class can generate random numbers as integers in a range, doubles between 0.0 and 1.0 and numbers from a normal distribution.
 * <p>
 *
 * @author  Sambit Paul
 * @version 1.0
 */
public class Random {

    private long seed;
    private double mean = 0.0;
    private double sd = 1.0;

    private NormalDistribution nd = new NormalDistribution(this.mean, this.sd);
    private java.util.Random rd = new java.util.Random();

    /**
     * Random constructor for creating the generator object with custom seed and shape set to 0
     * @param seed Number used to initialize a pseudorandom number generator
     */
    public Random(long seed) {
        this.seed = seed;
        this.nd.reseedRandomGenerator(this.seed);
        this.rd.setSeed(this.seed);
    }

    /**
     * Random constructor for creating the generator object with seed set to 42 and shape set to 0
     */
    public Random() {
        this.seed = 42;
        this.nd.reseedRandomGenerator(this.seed);
        this.rd.setSeed(this.seed);
    }

    /**
     * Set a custom seed to the random number generators
     * @param seed Number used to initialize a pseudorandom number generator
     */
    public void setSeed(long seed) {
        this.seed = seed;
        this.nd.reseedRandomGenerator(this.seed);
        this.rd.setSeed(this.seed);
    }

    /**
     * Set mean and standard deviation for the normal distribution from which to draw the random numbers
     * @param mean Mean of the normal distribution
     * @param sd Standard distribution of the normal distribution
     */
    public void setMeanAndSD(double mean, double sd) {
        this.mean = mean;
        this.sd = sd;
        this.nd = new NormalDistribution(this.mean, this.sd);
    }

    /**
     * Generate a random sample from the normal distribution
     * @return double Random sample from the normal distribution
     */
    public double randomNormalSample() {
        return this.nd.sample();
    }

    /**
     * Generate a 1D array of random samples from the normal distribution
     * @return double[] 1D array of random samples from the normal distribution
     */
    public double[] randomNormal1D(int[] shape) {
        if (shape.length != 1) {
            throw new IllegalArgumentException("Shape must have exactly 1 item. Currently set to " + shape.length + ".");
        }
        double[] matrix = new double[shape[0]];
        for (int i=0; i<shape[0]; i++) {
            matrix[i] = this.nd.sample();
        }
        return matrix;
    }

    /**
     * Generate a 2D array of random samples from the normal distribution
     * @return double[][] 2D array of random samples from the normal distribution
     */
    public double[][] randomNormal2D(int[] shape) {
        if (shape.length != 2) {
            throw new IllegalArgumentException("Shape must have exactly 2 items. Currently set to " + shape.length + ".");
        }
        double[][] matrix = new double[shape[0]][shape[1]];
        for (int i=0; i<shape[0]; i++) {
            for (int j=0; j<shape[1]; j++) {
                matrix[i][j] = this.nd.sample();
            }
        }
        return matrix;
    }

    /**
     * Generate a 3D array of random samples from the normal distribution
     * @return double[][][] 3D array of random samples from the normal distribution
     */
    public double[][][] randomNormal3D(int[] shape) {
        if (shape.length != 3) {
            throw new IllegalArgumentException("Shape must have exactly 3 items. Currently set to " + shape.length + ".");
        }
        double[][][] matrix = new double[shape[0]][shape[1]][shape[2]];
        for (int i=0; i<shape[0]; i++) {
            for (int j=0; j<shape[1]; j++) {
                for (int k=0; k<shape[2]; k++ ){
                    matrix[i][j][k] = this.nd.sample();
                }
            }
        }
        return matrix;
    }

    /**
     * Generate a random sample between 0.0 and 1.0
     * @return double Random decimal
     */
    public double randomDoubleSample() {
        return this.rd.nextDouble();
    }

    /**
     * Generate a 1D array of random samples between 0.0 and 1.0
     * @return double[] 1D array of random decimals
     */
    public double[] randomDouble1D(int[] shape) {
        if (shape.length != 1) {
            throw new IllegalArgumentException("Shape must have exactly 1 item. Currently set to " + shape.length + ".");
        }
        double[] matrix = new double[shape[0]];
        for (int i=0; i<shape[0]; i++) {
            matrix[i] = this.rd.nextDouble();
        }
        return matrix;
    }

    /**
     * Generate a 2D array of random samples between 0.0 and 1.0
     * @return double[][] 2D array of random decimals
     */
    public double[][] randomDouble2D(int[] shape) {
        if (shape.length != 2) {
            throw new IllegalArgumentException("Shape must have exactly 2 items. Currently set to " + shape.length + ".");
        }
        double[][] matrix = new double[shape[0]][shape[1]];
        for (int i=0; i<shape[0]; i++) {
            for (int j=0; j<shape[1]; j++) {
                matrix[i][j] = this.rd.nextDouble();
            }
        }
        return matrix;
    }

    /**
     * Generate a 3D array of random samples between 0.0 and 1.0
     * @return double[][][] 3D array of random decimals
     */
    public double[][][] randomDouble3D(int[] shape) {
        if (shape.length != 3) {
            throw new IllegalArgumentException("Shape must have exactly 3 items. Currently set to " + shape.length + ".");
        }
        double[][][] matrix = new double[shape[0]][shape[1]][shape[2]];
        for (int i=0; i<shape[0]; i++) {
            for (int j=0; j<shape[1]; j++) {
                for (int k=0; k<shape[2]; k++ ){
                    matrix[i][j][k] = this.rd.nextDouble();
                }
            }
        }
        return matrix;
    }

    /**
     * Generate a random integer between 0 and the upper bound
     * @param upper_bound The maximum number up to which the integers can be generated
     * @return int Random integer
     */
    public int randomIntSample(int upper_bound) {
        upper_bound++;
        return this.rd.nextInt(upper_bound);
    }

    /**
     * Generate a random integer between the lower bound and the upper bound
     * @param lower_bound The minimum number from which the integers can be generated
     * @param upper_bound The maximum number up to which the integers can be generated
     * @return int Random integer
     */
    public int randomIntSample(int lower_bound, int upper_bound) {
        if (lower_bound >= upper_bound) {
            throw new IllegalArgumentException("Upper bound must be less than lower bound");
        }
        upper_bound++;
        return this.rd.nextInt(upper_bound - lower_bound) + lower_bound;
    }

    /**
     * Generate a 1D array of random integer between 0 and the upper bound
     * @param upper_bound The maximum number up to which the integers can be generated
     * @return int[] 1D array of random integers
     */
    public int[] randomInt1D(int[] shape, int upper_bound) {
        if (shape.length != 1) {
            throw new IllegalArgumentException("Shape must have exactly 1 item. Currently set to " + shape.length + ".");
        }
        upper_bound++;
        int[] matrix = new int[shape[0]];
        for (int i=0; i<shape[0]; i++) {
            matrix[i] = this.rd.nextInt(upper_bound);
        }
        return matrix;
    }

    /**
     * Generate a 1D array of random integer between the lower bound and the upper bound
     * @param lower_bound The minimum number from which the integers can be generated
     * @param upper_bound The maximum number up to which the integers can be generated
     * @return int[] 1D array of random integers
     */
    public int[] randomInt1D(int[] shape, int lower_bound, int upper_bound) {
        if (shape.length != 1) {
            throw new IllegalArgumentException("Shape must have exactly 1 item. Currently set to " + shape.length + ".");
        }
        if (lower_bound >= upper_bound) {
            throw new IllegalArgumentException("Upper bound must be less than lower bound");
        }
        upper_bound++;
        int[] matrix = new int[shape[0]];
        for (int i=0; i<shape[0]; i++) {
            matrix[i] = this.rd.nextInt(upper_bound - lower_bound) + lower_bound;
        }
        return matrix;
    }

    /**
     * Generate a 2D array of random integer between 0 and the upper bound
     * @param upper_bound The maximum number up to which the integers can be generated
     * @return int[][] 2D array of random integers
     */
    public int[][] randomInt2D(int[] shape, int upper_bound) {
        if (shape.length != 2) {
            throw new IllegalArgumentException("Shape must have exactly 2 items. Currently set to " + shape.length + ".");
        }
        upper_bound++;
        int[][] matrix = new int[shape[0]][shape[1]];
        for (int i=0; i<shape[0]; i++) {
            for (int j=0; j<shape[1]; j++) {
                matrix[i][j] = this.rd.nextInt(upper_bound);
            }
        }
        return matrix;
    }

    /**
     * Generate a 2D array of random integer between the lower bound and the upper bound
     * @param lower_bound The minimum number from which the integers can be generated
     * @param upper_bound The maximum number up to which the integers can be generated
     * @return int[][] 2D array of random integers
     */
    public int[][] randomInt2D(int[] shape, int lower_bound, int upper_bound) {
        if (shape.length != 2) {
            throw new IllegalArgumentException("Shape must have exactly 2 items. Currently set to " + shape.length + ".");
        }
        if (lower_bound >= upper_bound) {
            throw new IllegalArgumentException("Upper bound must be less than lower bound");
        }
        upper_bound++;
        int[][] matrix = new int[shape[0]][shape[1]];
        for (int i=0; i<shape[0]; i++) {
            for (int j=0; j<shape[1]; j++) {
                matrix[i][j] = this.rd.nextInt(upper_bound - lower_bound) + lower_bound;
            }
        }
        return matrix;
    }

    /**
     * Generate a 3D array of random integer between 0 and the upper bound
     * @param upper_bound The maximum number up to which the integers can be generated
     * @return int[][][] 3D array of random integers
     */
    public int[][][] randomInt3D(int[] shape, int upper_bound) {
        if (shape.length != 3) {
            throw new IllegalArgumentException("Shape must have exactly 3 items. Currently set to " + shape.length + ".");
        }
        upper_bound++;
        int[][][] matrix = new int[shape[0]][shape[1]][shape[2]];
        for (int i=0; i<shape[0]; i++) {
            for (int j=0; j<shape[1]; j++) {
                for (int k=0; k<shape[2]; k++ ){
                    matrix[i][j][k] = this.rd.nextInt(upper_bound);
                }
            }
        }
        return matrix;
    }

    /**
     * Generate a 3D array of random integer between the lower bound and the upper bound
     * @param lower_bound The minimum number from which the integers can be generated
     * @param upper_bound The maximum number up to which the integers can be generated
     * @return int[][][] 3D array of random integers
     */
    public int[][][] randomInt3D(int[] shape, int lower_bound, int upper_bound) {
        if (shape.length != 3) {
            throw new IllegalArgumentException("Shape must have exactly 3 items. Currently set to " + shape.length + ".");
        }
        if (lower_bound >= upper_bound) {
            throw new IllegalArgumentException("Upper bound must be less than lower bound");
        }
        upper_bound++;
        int[][][] matrix = new int[shape[0]][shape[1]][shape[2]];
        for (int i=0; i<shape[0]; i++) {
            for (int j=0; j<shape[1]; j++) {
                for (int k=0; k<shape[2]; k++ ){
                    matrix[i][j][k] = this.rd.nextInt(upper_bound - lower_bound) + lower_bound;
                }
            }
        }
        return matrix;
    }

}
