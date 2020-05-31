<p align="center">
  <img src="https://github.com/psambit9791/jDSP/blob/master/res/img/jDSP_logo.png" alt="jDSP Logo"/>
</p>

[![Build Status](https://travis-ci.com/psambit9791/jDSP.svg?branch=master)](https://travis-ci.com/psambit9791/jDSP)
[![codecov](https://codecov.io/gh/psambit9791/jDSP/branch/master/graph/badge.svg)](https://codecov.io/gh/psambit9791/jDSP)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.psambit9791/jdsp.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.psambit9791%22%20AND%20a:%22jdsp%22)
[![javadoc](https://javadoc.io/badge2/com.github.psambit9791/jdsp/javadoc.svg)](https://javadoc.io/doc/com.github.psambit9791/jdsp)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg?)](https://github.com/psambit9791/jDSP/blob/master/LICENSE)

jDSP is a library of signal processing tools aiming to provide functionalities as available in scipy-signal package for 
Python. The goal is to provide an easy-to-use APIs for performing complex operation on signals eliminating the necessity 
of  understanding the low-level complexities of the processing pipeline.  

## Quick Start

<br/>

To get the latest stable release of jDSP:

### Gradle
```
implementation 'com.github.psambit9791:jdsp:0.2.0'
```

### Maven
```
<dependency>
  <groupId>com.github.psambit9791</groupId>
  <artifactId>jdsp</artifactId>
  <version>0.2.0</version>
</dependency>
```

<br/>

For the latest development version of jDSP (unreleased):

### Github
```  
git clone https://github.com/psambit9791/jDSP.git  
``` 

<br/>

## Documentation

See the [**Wiki**](https://github.com/psambit9791/jDSP/wiki) for general use of jDSP.

See the [**javadocs**](https://javadoc.io/doc/com.github.psambit9791/jdsp) for more advanced documentation.

<br/>

## Usage

| **Class** 		| **Type**		    | **Description**	|
| ------------- | ------------- | ------------- |
| <a href="https://javadoc.io/static/com.github.psambit9791/jdsp/0.2.0/com/github/psambit9791/jdsp/filter/Butterworth.html">com.github.psambit9791.jdsp.filter.Butterworth</a> | Frequency-based Filter | Implements Butterworth Filter for low-pass, high-pass, band-pass and band-stop operation |
| <a href="https://javadoc.io/static/com.github.psambit9791/jdsp/0.2.0/com/github/psambit9791/jdsp/filter/Chebyshev.html">com.github.psambit9791.jdsp.filter.Chebyshev</a> | Frequency-based **Filter** | Implements Chebyshev Filter (Type 1 and Type 2) for low-pass, high-pass, band-pass and band-stop operation |
| <a href="https://javadoc.io/static/com.github.psambit9791/jdsp/0.2.0/com/github/psambit9791/jdsp/filter/Bessel.html">com.github.psambit9791.jdsp.filter.Bessel</a> | Frequency-based **Filter** | Implements Bessel Filter for low-pass, high-pass, band-pass and band-stop operation |
| <a href="https://javadoc.io/static/com.github.psambit9791/jdsp/0.2.0/com/github/psambit9791/jdsp/filter/Median.html">com.github.psambit9791.jdsp.filter.Median</a> | Kernel-based **Filter** | Implements Median Filter for smoothing while maintaining the sharp edges |
| <a href="https://javadoc.io/static/com.github.psambit9791/jdsp/0.2.0/com/github/psambit9791/jdsp/filter/Savgol.html">com.github.psambit9791.jdsp.filter.Savgol</a> | Kernel-based **Filter** | Implements Savitzky–Golay Filter for smoothing using Savitzky–Golay coefficients |
| <a href="https://javadoc.io/static/com.github.psambit9791/jdsp/0.2.0/com/github/psambit9791/jdsp/filter/Median.html">com.github.psambit9791.jdsp.filter.Wiener</a> | Kernel-based **Filter** | Implements Wiener Filter for the sharpening operation |
||||
| <a href="https://javadoc.io/static/com.github.psambit9791/jdsp/0.2.0/com/github/psambit9791/jdsp/signal/Convolution.html">com.github.psambit9791.jdsp.signal.Convolution </a> | **Signal** Operation | Implements the convolve() and convolve1d() operation |
| <a href="https://javadoc.io/static/com.github.psambit9791/jdsp/0.2.0/com/github/psambit9791/jdsp/signal/CrossCorrelation.html">com.github.psambit9791.jdsp.signal.CrossCorrelation</a> | **Signal** Operation | Implements the cross-correlation operation |
| <a href="https://javadoc.io/static/com.github.psambit9791/jdsp/0.2.0/com/github/psambit9791/jdsp/signal/Detrend.html">com.github.psambit9791.jdsp.signal.Detrend</a> | **Signal** Operation | Implements the detrend operaton to remove trends from a signal |
| <a href="https://javadoc.io/static/com.github.psambit9791/jdsp/0.2.0/com/github/psambit9791/jdsp/signal/Smooth.html">com.github.psambit9791.jdsp.signal.Smooth</a> | **Signal** Operation | Implements convolutional smoothing with rectangular and triangular window |
| <a href="https://javadoc.io/static/com.github.psambit9791/jdsp/0.2.0/com/github/psambit9791/jdsp/signal/Decimate.html">com.github.psambit9791.jdsp.signal.Decimate</a> | **Signal** Operation | Implements the decimation operation to downsample a signal after applying an anti-aliasing filter |
| <a href="https://javadoc.io/static/com.github.psambit9791/jdsp/0.2.0/com/github/psambit9791/jdsp/signal/GeneratePeriodic.html">com.github.psambit9791.jdsp.signal.GeneratePeriodic</a> | **Signal** Operation | Helps to generate sin, cosine and square waves |
||||
| <a href="https://javadoc.io/static/com.github.psambit9791/jdsp/0.2.0/com/github/psambit9791/jdsp/transform/DiscreteFourier.html">com.github.psambit9791.jdsp.transform.DiscreteFourier</a> | **Transformation** | Applies the Discrete Fourier Transform on a signal |
| <a href="https://javadoc.io/static/com.github.psambit9791/jdsp/0.2.0/com/github/psambit9791/jdsp/transform/InverseDiscreteFourier.html">com.github.psambit9791.jdsp.transform.InverseDiscreteFourier</a> | **Transformation** | Applies the Inverse Discrete Fourier Transform on a sequence and returns the original signal |
| <a href="https://javadoc.io/static/com.github.psambit9791/jdsp/0.2.0/com/github/psambit9791/jdsp/transform/Hilbert.html">com.github.psambit9791.jdsp.transform.Hilbert</a> | **Transformation** | Applies the Hilbert Transform on a signal and provides funtions to return amplitude, phase and frequency information |

<br/>

## Authors  
  
* [**Sambit Paul**](https://github.com/psambit9791)
  
Shout out to all the [contributors](https://github.com/psambit9791/jDSP/contributors) who participated in this project.

<br/>

## Supporting jDSP 

jDSP is an open source project. <br /> You can help by becoming a sponsor on <a href="https://patreon.com/sambitpaul">Patreon</a> or doing a one time donation on <a href="https://paypal.me/psambit9791">PayPal</a> <br />

<a href="https://patreon.com/sambitpaul"><img src="https://c5.patreon.com/external/logo/become_a_patron_button.png" alt="Become a Patron" /> </a>

<br/>

## License  
  
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details. <br/> 
You are free to use, modify and distribute this software, as long as the copyright header is left intact.