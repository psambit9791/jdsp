<p align="center">
  <img src="https://github.com/psambit9791/jDSP/blob/master/res/img/jDSP_logo.png" alt="jDSP Logo"/>
</p>

![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg?style=plastic)

jDSP is a library of signal processing tools aiming to provide functionalities as available in scipy-signal package for 
Python. The goal is to provide an easy-to-use APIs for performing complex operation on signals eliminating the necessity of
understanding the low-level complexities of the processing pipeline.

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

**Download Apache Math3 and IIRJ**
```
wget http://apache.mirror.anlx.net//commons/math/binaries/commons-math3-3.6.1-bin.tar.gz

wget https://search.maven.org/remotecontent?filepath=uk/me/berndporr/iirj/1.1/iirj-1.1.jar
```

### Usage

#### Convolution

Convolution works in 3 modes:
1. Full: The output is the full discrete linear convolution of the input signal and kernel
2. Same: The output is the same size as input signal, centered with respect to the ‘full’ output
3. Valid: The output consists only of those elements that do not rely on the zero-padding

```
double[] signal = {1.0, 2.0, 3.0, 4.0, 5.0}; //define the signal 
double[] kernel = {1.0, 0.0, 1.0, 0.5}; //define the convolution kernel
String mode = "full"; //can be "full", "same", "valid"
Convolution con1 = new Convolution(signal, kernel, mode); //create convolution object
con1.convolve(); //perform convolution
double[] out = con1.getOutput(); //get the result of the convolution
```

#### Cross Correlation

Cross Correlation works in 3 modes:
1. Full: The output is the full discrete linear convolution of the input signal and kernel
2. Same: The output is the same size as input signal, centered with respect to the ‘full’ output
3. Valid: The output consists only of those elements that do not rely on the zero-padding

```
final double[] signal = {1.0, 2.0, 3.0, 4.0, 5.0}; //define the signal 
final double[] kernel = {1.0, 0.0, 1.0, 0.5}; //define the convolution kernel
String mode = "full"; //can be "full", "same", "valid"
CrossCorrelation cc1 = new CrossCorrelation(this.signal, this.kernel, mode); //create cross-correlation object
cc1.crossCorrelate(); //perform cross-correlation
double[] out = cc1.getOutput(); //get the result of the cross-correlation
```

#### Detrending

Detrending works in 3 modes:
1. Linear: The result of a linear least-squares fit to data is subtracted from data
2. Constant: The mean of the data is subtracted from data
3. Polynomial: Removes a polynomial trend line (based on power) from signal

```
double[] signal = {1.0, 2.0, 3.0, 4.0, 5.0}; //define the signal 
String mode = "linear"; //can be "linear", "constant"
Detrend d1 = new Detrend(signal, "linear"); //create detrending object
//OR
Detrend d1 = new Detrend(signal, 2); //create detrending object
d1.detrendSignal(); //perform detrending
double[] out = d1.getOutput(); //get the result of the detrending
```

#### Smoothing

Smoothing works in 2 modes:
1. Rectangular: Performs an uniform smoothing
2. Triangular: Performs a weighted smoothing

```
double[] signal = {1.0, 2.0, 3.0, 4.0, 5.0}; //define the signal 
String mode = "rectangular"; //can be "rectangular", "triangular"
Smooth s1 = new Smooth(signal, mode); //create smoothing object
s1.smoothSignal(); //perform smoothing
double[] out = s1.getOutput(); //get the result of the smoothing
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

##### Detect peaks (including flat peaks) based on Scipy Signal
Finds peaks within the signal and generate properties for those peaks

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

## Running the tests (For Developers)

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

<!---
## Deployment

Add additional notes about how to deploy this on a live system

## Built With

* [Dropwizard](http://www.dropwizard.io/1.0.2/docs/) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [ROME](https://rometools.github.io/rome/) - Used to generate RSS Feeds

## Contributing

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us.


## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/your/project/tags). 
--->

## Authors

* [**Sambit Paul**](https://github.com/psambit9791)

See also the list of [contributors](https://github.com/psambit9791/jDSP/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
