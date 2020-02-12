jDSP - Digital Signal Processing for Java
==========================================

![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)

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

Detrending works in 2 modes:
1. Linear: The result of a linear least-squares fit to data is subtracted from data
2. Constant: The mean of the data is subtracted from data

```
double[] signal = {1.0, 2.0, 3.0, 4.0, 5.0}; //define the signal 
String mode = "linear"; //can be "linear", "constant"
Detrend d1 = new Detrend(signal, "linear"); //create detrending object
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

## Running the tests

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

![image](https://i.imgur.com/xHABTBM.png)

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
