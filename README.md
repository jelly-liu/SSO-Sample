# SSO-Sample
An sample show how SSO system works  
The project include 4 modules, sys1, sys2(no usefull already), sys3-sso, sys-common

# How to run  
0. config your hosts on windows 10, hosts path is C:\Windows\System32\drivers\etc\hosts  
    127.0.0.1	www.sys1.com  
    127.0.0.1	www.sys2.com  
    127.0.0.1	www.sys3-sso.com  
1. run StartSys1.java  
    this will start sys1, and sys2  
2. run StartSys3SSO.java  
    this will start sys3-sso server  
3. access http://www.sys1.com:9091 or http://www.sys2.com:9092 from browser  
    http://www.sys1.com:9091  
    http://www.sys2.com:9092  
    http://www.sys3-sso.com:9093
