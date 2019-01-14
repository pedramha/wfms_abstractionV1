# wfms_abstractionV1
The Orchestration Engines (currently Cadence and Zeebe supported) have to be started before starting this Generic Worker.
To start, please do the following steps:
* First download the source code:  
    git clone https://github.com/pedramha/wfms_abstractionV1.git
* Go to the respective folder:  
    cd wfms_abstractionV1
* Run the following commands:  
    Mvn clean install  
    Java –jar 

Now the worker is polling the server for HTTP tasks, and as soon as the workflow reaches the HTTP tasks the worker performs the HTTP call to the respective HTTP endpoint.  
A short video which demonstrates how the tool functions in debugging mode can be found at the following address:
https://drive.google.com/file/d/1ITe5DmTGa48_d_6PM9DhsBnL2EAqbW_y/view?usp=sharing
