# SSO-Sample
An sample show how SSO system works  
The project include 4 modules, sys1, sys2(no usefull already), sys3-sso is an https server base on SSL, sys-common

# How to make SSO-Sample works  
0. modify webapp path in sub class, according to your build out path(either absolute or relative)
    StartSys1.java, StartSys3SSO.java
0. run the follow command on your windows to create an keystore, the jetty SSL connector will use it  
    keytool -keystore keystore -alias jetty -genkey -keyalg RSA  
    and modify keystore path in StartSys3SSO.java
0. config your hosts on windows 10, hosts path is C:\Windows\System32\drivers\etc\hosts  
    127.0.0.1	www.sys1.com  
    127.0.0.1	www.sys2.com  
    127.0.0.1	www.sys3-sso.com  
1. run StartSys1.java  
    this will start sys1, and sys2  
2. run StartSys3SSO.java  
    this will start sys3-sso, is an https server based on SSL  
3. access http://www.sys1.com:9091 or http://www.sys2.com:9092 from browser  
    http://www.sys1.com:9091  
    http://www.sys2.com:9092  
    https://www.sys3-sso.com:9093
