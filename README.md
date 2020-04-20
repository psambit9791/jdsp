<p align="center">
  <img src="https://github.com/psambit9791/jDSP/blob/master/res/img/jDSP_logo.png" alt="jDSP Logo"/>
</p>

[![Build Status](https://travis-ci.com/psambit9791/jDSP.svg?branch=master)](https://travis-ci.com/psambit9791/jDSP)
[![codecov](https://codecov.io/gh/psambit9791/jDSP/branch/master/graph/badge.svg)](https://codecov.io/gh/psambit9791/jDSP)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg?)](https://github.com/psambit9791/jDSP/blob/master/LICENSE)

jDSP is a library of signal processing tools aiming to provide functionalities as available in scipy-signal package for   
Python. The goal is to provide an easy-to-use APIs for performing complex operation on signals eliminating the necessity of  
understanding the low-level complexities of the processing pipeline.  
  
## Table of Contents  
  
- [Getting Started](#getting-started)  
- [Prerequisites](#prerequisites)  
- [Usage](#usage)  
  + [Convolution](#convolution)  
  + [Cross Correlation](#cross-correlation)  
  + [Detrending](#detrending)  
  + [Smoothing](#smoothing)  
  + [Fourier Transform](#fourier-transform)  
      - [Discrete Fourier Transform](#discrete-fourier-transform)  
      - [Inverse Discrete Fourier Transform](#inverse-discrete-fourier-transform)  
  + [Matlab style Filters](#matlab-style-filters)  
      - [Butterworth filter](#butterworth-filter)  
        * [Low Pass Filter](#low-pass-filter)  
        * [High Pass Filter](#high-pass-filter)  
        * [Band Pass Filter](#band-pass-filter)  
        * [Band Stop Filter](#band-stop-filter)  
      - [Chebyshev Type 1 and 2 filters](#chebyshev-type-1-and-2-filters)  
        * [Low Pass Filter](#low-pass-filter-1)  
        * [High Pass Filter](#high-pass-filter-1)  
        * [Band Pass Filter](#band-pass-filter-1)  
        * [Band Stop Filter](#band-stop-filter-1)  
      - [Bessel filter](#bessel-filter)  
        * [Low Pass Filter](#low-pass-filter-2)  
        * [High Pass Filter](#high-pass-filter-2)  
        * [Band Pass Filter](#band-pass-filter-2)  
        * [Band Stop Filter](#band-stop-filter-2)  
  + [Savitzky Golay Filter](#savitzky-golay-filter)  
  + [Median Filter](#median-filter)  
  + [Wiener Filter](#wiener-filter)  
  + [Hilbert Transform](#hilbert-transform)  
  + [Peak Detection](#peak-detection)  
      - [Find Relative Minima](#find-relative-minima)  
      - [Find Relative Maxima](#find-relative-maxima)  
      - [Detect peaks](#detect-peaks)  
- [Running the tests](#running-the-tests)  
- [Authors](#authors)  
- [License](#license)  
  
<sub><sup><i><a href='http://ecotrust-canada.github.io/markdown-toc/'>Table of contents generated with markdown-toc</a></i></sup></sub>  
  
## Getting Started
  
To get a copy of this project, clone this project using:  
  
```  
git clone https://github.com/psambit9791/jDSP.git  
```  
  
### Prerequisites  
  
To use this library, there are a few dependencies which need to be satisfied.  
  
First of all, Java needs to be installed on the system.  
  
**Java Installation**  
```  
sudo apt-get install openjdk-8-jdk  
  
java -version  
```  
  
**Dependencies are Apache Math3 and IIRJ**  
  
To download them separately and add using your IDE, use this method:  
  
```  
wget http://apache.mirror.anlx.net//commons/math/binaries/commons-math3-3.6.1-bin.tar.gz  
wget https://repo1.maven.org/maven2/org/knowm/xchart/xchart/3.6.2/xchart-3.6.2.jar
wget https://search.maven.org/remotecontent?filepath=uk/me/berndporr/iirj/1.1/iirj-1.1.jar  
```  
  
To use maven to install, use this:  
  
```  
sudo apt install maven  
  
mvn install 
```  
  
### Usage  
  
#### Convolution  
  
Convolution works in 3 modes:  
1. Full: The output is the full discrete linear convolution of the input signal and kernel  
2. Same: The output is the same size as input signal, centered with respect to the ‘full’ output  
3. Valid: The output consists only of those elements that do not rely on the zero-padding  
  
```  
double[] signal = {1.0, 2.0, 3.0, 4.0, 5.0}; //define the signal double[] kernel = {1.0, 0.0, 1.0, 0.5}; //define the convolution kernel  
String mode = "full"; //can be "full", "same", "valid"  
Convolution con1 = new Convolution(signal, kernel, mode); //create convolution object  
double[] out = con1.convolve(); //perform convolution and get the result  
```  
  
#### Cross Correlation  
  
Cross Correlation works in 3 modes:  
1. Full: The output is the full discrete linear convolution of the input signal and kernel  
2. Same: The output is the same size as input signal, centered with respect to the ‘full’ output  
3. Valid: The output consists only of those elements that do not rely on the zero-padding  
  
```  
final double[] signal = {1.0, 2.0, 3.0, 4.0, 5.0}; //define the signal final double[] kernel = {1.0, 0.0, 1.0, 0.5}; //define the convolution kernel  
String mode = "full"; //can be "full", "same", "valid"  
CrossCorrelation cc1 = new CrossCorrelation(this.signal, this.kernel, mode); //create cross-correlation object  
double[] out = cc1.cross_correlate(); //perform cross-correlation and get the result  
```  
  
#### Detrending  
  
Detrending works in 3 modes:  
1. Linear: The result of a linear least-squares fit to data is subtracted from data  
2. Constant: The mean of the data is subtracted from data  
3. Polynomial: Removes a polynomial trend line (based on power) from signal  
  
```  
double[] signal = {1.0, 2.0, 3.0, 4.0, 5.0}; //define the signal String mode = "linear"; //can be "linear", "constant"  
Detrend d1 = new Detrend(signal, "linear"); //create detrending object  
//OR  
Detrend d1 = new Detrend(signal, 2); //create detrending object  
double[] out = d1.detrendSignal(); //perform detrending and get the detrended signal  
```  
  
#### Smoothing  
  
Smoothing works in 2 modes:  
1. Rectangular: Performs an uniform smoothing  
2. Triangular: Performs a weighted smoothing  
  
```  
double[] signal = {1.0, 2.0, 3.0, 4.0, 5.0}; //define the signal String mode = "rectangular"; //can be "rectangular", "triangular"  
Smooth s1 = new Smooth(signal, mode); //create smoothing object  
double[] out = s1.smoothSignal(); //perform smoothing and get the smoothed signal  
```  
  
#### Fourier Transform  
  
Fourier analysis is a method for expressing a function as a sum of periodic components, and for recovering the signal from those components. When both the function and its Fourier transform are replaced with discretized counterparts, it is called the discrete Fourier transform (DFT).  
The information for this section has been taken from [scipy.fft Tutorial](https://docs.scipy.org/doc/scipy/reference/tutorial/fft.html). Please refer to this for further information.  
  
##### Discrete Fourier Transform  
  
Discrete Fourier Transform performs fourier transform on a discrete signal to calculate the frequency components within the signal.  
It produces the frequency component in the complex number space and the [components are mirrored](https://dsp.stackexchange.com/a/4827).  
  
This class can return only the positive half of the components or the total analysis.  
It can also be used to receive the absolute values or the complex values.  
  
```  
DiscreteFourier fft1 = new DiscreteFourier(signal); //creates the DFT object with a signal of tye double[]  
fft1.fft(); //performs the fourier transform  
  
double[] outAbsPos = fft1.returnAbsolute(true); // returns the absolute values of the positive half of the FFT output  
double[] outAbsFull = fft1.returnAbsolute(false); // returns the absolute values of the complete FFT output  
  
double[][] outCmplxPos = fft1.returnFull(true); // returns the complex values of the positive half of the FFT output  
double[][] outCmplxFull = fft1.returnFull(false); // returns the complex values of the complete FFT output  
```  
  
##### Inverse Discrete Fourier Transform  
  
Discrete Fourier Transform performs inverse fourier transform on a sequence to generate the original signal.  
It requires the full frequency response generated from DFT and does not work with only the positive/negative halves.  
  
This class generates output in both real and complex format and also takes real and complex values as input.  
It can be used to get only the real components, the absolute values or the full complex signal.  
  
```  
InverseDiscreteFourier transformer = new InverseDiscreteFourier(seq); //Creates the iDFT object with a sequence of type double[] or double[][]  
transformer.ifft(); //performs the inverse fourier transform  
  
double[] outReal = transformer.get_real_signal(); //returns only the real components of the generated complex signal  
double[] outAbsolute = transformer.get_absolute_signal(); //returns the absolute values of the generated complex signal  
double[][] out = transformer.get_complex_signal(); //returns the complex signal  
```  
  
#### Matlab style Filters  
  
This library presently supports Butterworth, Bessel and Chebyshev Type I and Type II filters. This provides an abstraction on top of this [filter design library](https://github.com/berndporr/iirj) for ease of use.  
Please refer to this [document](https://basedados.aeroubi.pt/pluginfile.php/610/mod_resource/content/0/ClassicFilters_BTTWRTH_BSSL_CHBCHV_LPTC.pdf) if you want to understand what each type of filter can be used for, or you can also use this [version](https://github.com/psambit9791/jDSP/blob/master/res/doc/filter_information.pdf) which is a duplicate kept within this repository for reference.  
  
##### Butterworth filter  
  
The Butterworth filter is a type of signal processing filter designed to have a frequency response as flat as possible in the passband.  
This section implements 4 types of filters:  
1. Low-Pass  
2. High-Pass  
3. Band-Pass  
4. Band-Stop  
  
###### Low Pass Filter  
  
```  
int Fs = 100; //Sampling Frequency in Hz  
int order = 4; //order of the filter  
int cutOff = 29; //Cut-off Frequency  
Butterworth flt = new Butterworth(signal, Fs); //signal is of type double[]  
double[] result = flt.low_pass_filter(order, cutOff); //get the result after filtering  
```  
  
###### High Pass Filter  
  
```  
int Fs = 100; //Sampling Frequency in Hz  
int order = 4; //order of the filter  
int cutOff = 29; //Cut-off Frequency  
Butterworth flt = new Butterworth(signal, Fs); //signal is of type double[]  
double[] result = flt.high_pass_filter(order, cutOff); //get the result after filtering  
```  
  
###### Band Pass Filter  
  
```  
int Fs = 100; //Sampling Frequency in Hz  
int order = 4; //order of the filter  
int lowCutOff = 12; //Lower Cut-off Frequency  
int highCutOff = 18; //Higher Cut-off Frequency  
Butterworth flt = new Butterworth(signal, Fs); //signal is of type double[]  
double[] result = flt.band_pass_filter(order, lowCutOff, highCutOff); //get the result after filtering  
```  
  
###### Band Stop Filter  
  
```  
int Fs = 100; //Sampling Frequency in Hz  
int order = 4; //order of the filter  
int lowCutOff = 7; //Lower Cut-off Frequency  
int highCutOff = 28; //Higher Cut-off Frequency  
Butterworth flt = new Butterworth(signal, Fs); //signal is of type double[]  
double[] result = flt.band_stop_filter(order, lowCutOff, highCutOff); //get the result after filtering  
```  
  
##### Chebyshev Type 1 and 2 filters  
  
Chebyshev filters are signal processing filters having a steeper roll-off than Butterworth filters, and have passband ripple (type 1) or stopband ripple (type 2).  
This section implements 4 types of filters:  
1. Low-Pass  
2. High-Pass  
3. Band-Pass  
4. Band-Stop  
  
###### Low Pass Filter  
  
```  
int filterType = 1; //Can be 1 (for type 1) or 2 (for type 2)  
int Fs = 100; //Sampling Frequency in Hz  
int order = 4; //order of the filter  
int cutOff = 29; //Cut-off Frequency  
Butterworth flt = new Butterworth(signal, Fs, filterType); //signal is of type double[]  
double rippleFactor = 1; //maximum ripple allowed below unity gain  
double[] result = flt.low_pass_filter(order, cutOff, rippleFactor); //get the result after filtering  
```  
  
###### High Pass Filter  
  
```  
int filterType = 1; //Can be 1 (for type 1) or 2 (for type 2)  
int Fs = 100; //Sampling Frequency in Hz  
int order = 4; //order of the filter  
int cutOff = 29; //Cut-off Frequency  
Butterworth flt = new Butterworth(signal, Fs, filterType); //signal is of type double[]  
double rippleFactor = 1; //maximum ripple allowed below unity gain  
double[] result = flt.high_pass_filter(order, cutOff, rippleFactor); //get the result after filtering  
```  
  
###### Band Pass Filter  
  
```  
int filterType = 1; //Can be 1 (for type 1) or 2 (for type 2)  
int Fs = 100; //Sampling Frequency in Hz  
int order = 4; //order of the filter  
int lowCutOff = 12; //Lower Cut-off Frequency  
int highCutOff = 18; //Higher Cut-off Frequency  
Butterworth flt = new Butterworth(signal, Fs, filterType); //signal is of type double[]  
double rippleFactor = 1; //maximum ripple allowed below unity gain  
double[] result = flt.band_pass_filter(order, lowCutOff, highCutOff, rippleFactor); //get the result after filtering  
```  
  
###### Band Stop Filter  
  
```  
int filterType = 1; //Can be 1 (for type 1) or 2 (for type 2)  
int Fs = 100; //Sampling Frequency in Hz  
int order = 4; //order of the filter  
int lowCutOff = 7; //Lower Cut-off Frequency  
int highCutOff = 28; //Higher Cut-off Frequency  
Butterworth flt = new Butterworth(signal, Fs, filterType); //signal is of type double[]  
double rippleFactor = 1; //maximum ripple allowed below unity gain  
double[] result = flt.band_stop_filter(order, lowCutOff, highCutOff, rippleFactor); //get the result after filtering  
```  
  
##### Bessel filter  
  
The Bessel filter is a type of signal processing filter with a maximally flat group/phase delay (maximally linear phase response), which preserves the wave shape of filtered signals in the passband.  
From the [__scipy.signal__](https://docs.scipy.org/doc/scipy/reference/generated/scipy.signal.bessel.html#scipy.signal.bessel) page, Bessel filter can be used under three norms:  
1. "phase": The filter is normalized such that the phase response reaches its midpoint at critical frequencies.  
2. "delay": The filter is normalized such that the group delay in the passband is inverse of the critical frequencies. This is the **natural** type.  
3. "mag": The filter is normalized such that the gain magnitude is -3 dB at critical frequencies.  
  
At the moment, only the "delay" norm has been implemented.  
  
This section implements 4 types of filters:  
1. Low-Pass  
2. High-Pass  
3. Band-Pass  
4. Band-Stop  
  
###### Low Pass Filter  
  
```  
int Fs = 100; //Sampling Frequency in Hz  
int order = 4; //order of the filter  
int cutOff = 29; //Cut-off Frequency  
Bessel flt = new Bessel(signal, Fs); //signal is of type double[]  
double[] result = flt.low_pass_filter(order, cutOff); //get the result after filtering  
```  
  
###### High Pass Filter  
  
```  
int Fs = 100; //Sampling Frequency in Hz  
int order = 4; //order of the filter  
int cutOff = 29; //Cut-off Frequency  
Bessel flt = new Bessel(signal, Fs); //signal is of type double[]  
double[] result = flt.high_pass_filter(order, cutOff); //get the result after filtering  
```  
  
###### Band Pass Filter  
  
```  
int Fs = 100; //Sampling Frequency in Hz  
int order = 4; //order of the filter  
int lowCutOff = 12; //Lower Cut-off Frequency  
int highCutOff = 18; //Higher Cut-off Frequency  
Bessel flt = new Bessel(signal, Fs); //signal is of type double[]  
double[] result = flt.band_pass_filter(order, lowCutOff, highCutOff); //get the result after filtering  
```  
  
###### Band Stop Filter  
  
```  
int Fs = 100; //Sampling Frequency in Hz  
int order = 4; //order of the filter  
int lowCutOff = 7; //Lower Cut-off Frequency  
int highCutOff = 28; //Higher Cut-off Frequency  
Bessel flt = new Bessel(signal, Fs); //signal is of type double[]  
double[] result = flt.band_stop_filter(order, lowCutOff, highCutOff); //get the result after filtering  
```  
  
#### Savitzky Golay Filter 
  
A Savitzky–Golay filter is a digital filter that can be applied to a set of digital data points for the purpose of smoothing the data, that is, to increase the precision of the data without distorting the signal tendency.  
Applies a Savitzky–Golay filter on the input signal. The filter works in 4 modes (which determine the way in wich the signal is padded before convolution):  
  
This is for a signal: [a b c d]  
1. nearest: [a a a a | a b c d | d d d d]  
2. constant: [0 0 0 0 | a b c d | 0 0 0 0]  
3. mirror: [c d c b | a b c d | c b a b]  
4. wrap: [a b c d | a b c d | a b c d]  
  
```  
int windowSize = 7; //length of the filter window  
int polyOrder = 2; //order of the polynomial used to fit the samples  
Savgol s1 = new Savgol(signal, windowSize, polyOrder); //signal is of type double[]  
double[] out = s1.savgol_filter(mode); //get the result after filtering, mode is of type String  
```  
  
#### Median Filter  
  
The Median Filter is a non-linear digital filtering technique, often used to remove noise from an image or signal as a pre-processing step.  
Applies a Median filter on the input signal.  
  
```  
int windowSize = 5; //can be anything less than length of signal  
MedianFilter mf = new MedianFilter(signal, windowSize); //signal is of type double[]  
double[] out = mf.median_filter(); //get the result after filtering  
```  
  
#### Wiener Filter  
  
The Wiener filter is a simple deblurring filter for denoising signals.  
Applies a Wiener filter on the input signal.  
  
```  
int windowSize = 5; //can be anything less than length of signal  
Wiener wf = new Wiener(signal, windowSize); //signal is of type double[]  
double[] out = wf.wiener_filter(); //get the result after filtering  
```  
  
#### Hilbert Transform  
  
The Hilbert transform constructs the complex-valued analytic signal from a real signal  
Applies the Hilbert Transform on the input real signal.  
  
```  
double Fs = 100.0; // Sampling Frequency in Hz  
Hilbert h = new Hilbert(signal); //signal is of type double[]  
h.hilbert_transform(); //perform the Hilbert Transform  
double[][] out = h.get_output() //returns the complex analytical signal corresponding to the input signal  
double[] ampOut = h.get_amplitude_envelope(); //returns the amplitude envelope of the input signal  
double[] phaseOut = h.get_instantaneous_phase(); //returns the instantaneous phase of the input signal  
double[] freqOut = h.get_instantaneous_frequency(Fs); //returns the instantaneous frequency of the input signal  
```  
  
#### Peak Detection  
  
##### Find Relative Minima  
Calculates the relative minima of data.  
  
```  
FindPeak fp = new FindPeak(signal); //signal is a double[] array  
int[] out = fp.detect_relative_minima();  
```  
  
##### Find Relative Maxima  
Calculates the relative maxima of data.  
  
```  
FindPeak fp = new FindPeak(signal); //signal is a double[] array  
int[] out = fp.detect_relative_maxima();  
```  
  
##### Detect peaks
Find peaks within the signal and generate properties for those peaks (based on Scipy Signal find_peaks() function)  
  
```  
FindPeak fp = new FindPeak(signal); //signal is a double[] array  
PeakObject p = fp.detect_peaks();  
int[] out = p.getMidpoints()  
```  
  
Find relative peak height based on neighbouring troughs  
  
```  
FindPeak fp = new FindPeak(signal); //signal is a double[] array  
PeakObject p = fp.detect_peaks();  
int[] out = p.getMidpoints()  
```  
  
## Running the tests 
 
**! For Developers !**
Test Cases are organised in a similar structure as the classes.  
Each class has a test case for each functionality implemented within it.  
  
The tests work on certain pre-defined inputs and their corresponding outputs, and asserts the truth of the   
generated output compared to the tre output.  
  
**Example**  
```  
Module: Detrend  
Input: {1.0, 2.0, 3.0, 4.0, 5.0}  
```  
  
The output is shown as:  
  
![image](https://github.com/psambit9791/jDSP/blob/master/res/img/SigTest.png)  
  
  
## Authors  
  
* [**Sambit Paul**](https://github.com/psambit9791)  
  
See also the list of [contributors](https://github.com/psambit9791/jDSP/contributors) who participated in this project.  
  
## License  
  
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.