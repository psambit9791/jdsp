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

package com.github.psambit9791.jdsp.filter.adaptive;

/**
 * <h1>Affine Projection (AP) adaptive filter</h1>
 * The AP adaptive filter uses affine projection algorithm to improve the performance of LMS-based adaptive filters.
 * This filter is useful when the input data is highly correlated.
 * AP uses multiple input vectors for each sample where the number of input vectors depends on the projection order.
 *
 * Cite: Gonzalez, A., Ferrer, M., Albu, F., & De Diego, M. (2012, August). Affine projection algorithms:
 * Evolution to smart and fast algorithms and applications. In 2012 Proceedings of the 20th European Signal Processing
 * Conference (EUSIPCO) (pp. 1965-1969). IEEE.
 *
 * @author Sambit Paul
 * @version 1.0
 */
public class AP {
}
