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

To use the different functionalities provided in this library, an object for each class needs to be created with the 
necessary arguments. Then, the process needs to be called on the object. Finally, the getOutput() function returns the
results.

**Example** 

```
double[] original = {1.0, 2.0, 3.0, 4.0, 5.0};
Detrend d1 = new Detrend(original, "linear");
d1.detrendSignal();
double[] out = d1.getDetrendedSignal();
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

![image](https://drive.google.com/file/d/1BzcTEdUTKjjKacMMPCeFkc5H68uJzs_N/view?usp=sharing)

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
